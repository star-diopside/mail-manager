package jp.gr.java_conf.star_diopside.mailmanager.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
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
    public LinkedHashMap<Path, Exception> copyMailFiles(final Path inputDirectory, final Path outputDirectory)
            throws BusinessException {

        // 実行IDを取得する。
        final Integer executionId = this.fileCheckExecutionSeqMapper.getNextVal();

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

        // 再帰的にファイルコピーを行う。
        final LinkedHashMap<Path, Exception> errors = new LinkedHashMap<>();

        try {
            Files.walkFileTree(inputDirectory, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path relativePath = inputDirectory.relativize(dir);
                    Path outDir = outputDirectory.resolve(relativePath);
                    if (Files.notExists(outDir)) {
                        Files.createDirectory(outDir);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path relativePath = inputDirectory.relativize(file.getParent());
                    try {
                        // ファイルコピーを行う。
                        Files.copy(file, createOutputFileName(executionId, file, outputDirectory.resolve(relativePath)));
                    } catch (IOException e) {
                        errors.put(file, e);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    errors.put(file, exc);
                    return FileVisitResult.CONTINUE;
                }
            });

        } catch (IOException e) {
            throw new BusinessException(message.getMessage(Messages.ERROR_UNCAUGHT), e);
        }

        return errors;
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
