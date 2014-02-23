package jp.gr.java_conf.star_diopside.mailmanager.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.LinkedHashMap;

import jp.gr.java_conf.star_diopside.mailmanager.constant.Messages;
import jp.gr.java_conf.star_diopside.mailmanager.dao.entity.FileCheckResultImpl;
import jp.gr.java_conf.star_diopside.mailmanager.dao.entity.FileCheckResultPKImpl;
import jp.gr.java_conf.star_diopside.mailmanager.dao.mapper.FileCheckResultMapper;
import jp.gr.java_conf.star_diopside.mailmanager.exception.BusinessException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * メールファイル管理処理クラス
 */
@Service
public class MailFileManagerImpl implements MailFileManager {

    /** メッセージソース */
    @Autowired
    private MessageSourceAccessor message;

    /** ファイルチェック実行シーケンスMapper */
    @Autowired
    private FileCheckExecutionSeqMapper fileCheckExecutionSeqMapper;

    /** ファイルチェック結果Mapper */
    @Autowired
    private FileCheckResultMapper fileCheckResultMapper;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public LinkedHashMap<Path, Exception> copyMailFiles(String inputDirectory, String outputDirectory)
            throws BusinessException {
        return copyMailFiles(Paths.get(inputDirectory), Paths.get(outputDirectory));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public LinkedHashMap<Path, Exception> copyMailFiles(Path inputDirectory, Path outputDirectory)
            throws BusinessException {

        // 実行IDを取得する。
        Integer executionId = this.fileCheckExecutionSeqMapper.getNextVal();

        // 再帰的にファイルコピーを行う。
        LinkedHashMap<Path, Exception> errors = new LinkedHashMap<>();
        recursiveCopyMailFiles(executionId, inputDirectory, outputDirectory, errors);

        return errors;
    }

    /**
     * メールファイル(EML)を再帰的にコピーする。
     * 
     * @param executionId 実行ID
     * @param inputDirectory コピー元メールファイル格納ディレクトリパス
     * @param outputDirectory コピー先ディレクトリパス
     * @param errors エラーが発生した場合のエラー情報を格納するマップオブジェクト。
     *            エラーが発生したファイルをキー、エラー情報を値に保持する。
     * @throws BusinessException 入出力ディレクトリのチェックエラーが発生した場合
     */
    private void recursiveCopyMailFiles(Integer executionId, Path inputDirectory, Path outputDirectory,
            LinkedHashMap<Path, Exception> errors) throws BusinessException {

        // コピー元ディレクトリが存在しない場合、例外をスローする。
        if (!Files.isDirectory(inputDirectory)) {
            throw new BusinessException(message.getMessage(Messages.ERROR_NOT_EXISTS_DIR,
                    new Object[] { inputDirectory }));
        }

        // コピー先ディレクトリが作成できない場合、例外をスローする。
        try {
            Files.createDirectories(outputDirectory);
        } catch (IOException e) {
            throw new BusinessException(message.getMessage(Messages.ERROR_NOT_MAKE_DIR,
                    new Object[] { outputDirectory }), e);
        }

        try (DirectoryStream<Path> inputDirectoryStream = Files.newDirectoryStream(inputDirectory)) {
            for (Path inputFile : inputDirectoryStream) {
                Path fileName = inputDirectory.relativize(inputFile);

                if (Files.isDirectory(inputFile)) {
                    // サブディレクトリの処理を行う。
                    recursiveCopyMailFiles(executionId, inputFile, outputDirectory.resolve(fileName), errors);

                } else {
                    try {
                        // ファイルコピーを行う。
                        Files.copy(inputFile, createOutputFileName(executionId, inputFile, outputDirectory));
                    } catch (Exception e) {
                        // マップオブジェクトにエラー情報を追加する。
                        errors.put(inputFile, e);
                    }
                }
            }

        } catch (IOException e) {
            throw new BusinessException(message.getMessage(Messages.ERROR_NOT_MAKE_DIR,
                    new Object[] { outputDirectory }), e);
        }
    }

    /**
     * コピー先ファイル名を生成する。
     * 
     * @param executionId 実行ID
     * @param inputFile コピー元ファイル
     * @param outputDirectory コピー先ディレクトリ
     * @return コピー先ファイル
     * @throws IOException ファイル入出力エラーが発生した場合
     */
    protected Path createOutputFileName(Integer executionId, Path inputFile, Path outputDirectory) throws IOException {

        // 入力ファイルのハッシュ値を生成する。
        String hashInputFile;
        try (InputStream is = Files.newInputStream(inputFile)) {
            hashInputFile = DigestUtils.sha1Hex(is);
        }

        // 拡張子
        String ext = FilenameUtils.getExtension(inputFile.toString());
        if (!StringUtils.isEmpty(ext)) {
            ext = '.' + ext;
        }

        // ベース名
        String base = hashInputFile;

        // コピー先ファイルパス
        Path outputFile = outputDirectory.resolve(base + ext);

        int count = 1;

        // 同名のファイル名が存在する場合
        while (Files.exists(outputFile) && !equalsFile(inputFile, hashInputFile, outputFile)) {
            // ファイル名に連番を付与する。
            count++;
            outputFile = outputDirectory.resolve(base + " (" + count + ")" + ext);
        }

        // ファイルチェック結果レコードを登録する。
        FileCheckResultPKImpl param = new FileCheckResultPKImpl();
        param.setExecutionId(executionId);
        param.setFileHash(hashInputFile);
        Integer fileSeq = this.fileCheckResultMapper.getMaxFileSeq(param);

        if (fileSeq == null) {
            fileSeq = 1;
        } else {
            fileSeq++;
        }

        FileCheckResultImpl result = new FileCheckResultImpl();
        Timestamp now = new Timestamp(System.currentTimeMillis());

        result.setExecutionId(executionId);
        result.setFileHash(hashInputFile);
        result.setFileSeq(fileSeq);
        result.setInputDirPath(inputFile.toAbsolutePath().normalize().getParent().toString());
        result.setInputFileName(inputFile.getFileName().toString());
        result.setOutputDirPath(outputFile.toAbsolutePath().normalize().getParent().toString());
        result.setOutputFileName(outputFile.getFileName().toString());
        result.setRegisterDatetime(now);
        result.setRegisterUserId(StringUtils.EMPTY);
        result.setUpdatedDatetime(now);
        result.setUpdatedUserId(StringUtils.EMPTY);
        result.setVersion(0);

        this.fileCheckResultMapper.insert(result);

        return outputFile;
    }

    /**
     * ファイルの一致チェックを行う。
     * 
     * @param inputFile コピー元ファイル
     * @param hashInputFile コピー元ファイルのハッシュ値
     * @param outputFile コピー先ファイル
     * @return ファイルデータが一致する場合はtrue、一致しない場合はfalseを返す。
     * @throws IOException ファイル入出力エラーが発生した場合
     */
    private boolean equalsFile(Path inputFile, String hashInputFile, Path outputFile) throws IOException {

        // ファイルサイズの一致チェックを行う。
        if (Files.size(inputFile) != Files.size(outputFile)) {
            return false;
        }

        // ファイルのハッシュ値を比較する。
        try (InputStream is = Files.newInputStream(outputFile)) {
            if (hashInputFile.compareToIgnoreCase(DigestUtils.sha1Hex(is)) != 0) {
                return false;
            }
        }

        // ファイルのバイトデータ一致チェックを行う。
        byte[] inputFileBytes = Files.readAllBytes(inputFile);
        byte[] outputFileBytes = Files.readAllBytes(outputFile);

        return Arrays.equals(inputFileBytes, outputFileBytes);
    }
}
