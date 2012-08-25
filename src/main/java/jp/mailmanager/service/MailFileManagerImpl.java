package jp.mailmanager.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;

import jp.mailmanager.constant.Messages;
import jp.mailmanager.exception.BusinessException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

/**
 * メールファイル管理処理クラス
 */
@Service
public class MailFileManagerImpl implements MailFileManager {

    /** メッセージソース */
    @Autowired
    private MessageSourceAccessor message;

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkedHashMap<File, Exception> copyMailFiles(String inputDirectory, String outputDirectory)
            throws BusinessException {
        return copyMailFiles(new File(inputDirectory), new File(outputDirectory));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkedHashMap<File, Exception> copyMailFiles(File inputDirectory, File outputDirectory)
            throws BusinessException {

        // 再帰的にファイルコピーを行う。
        LinkedHashMap<File, Exception> errors = new LinkedHashMap<>();
        recursiveCopyMailFiles(inputDirectory, outputDirectory, errors);

        return errors;
    }

    /**
     * メールファイル(EML)を再帰的にコピーする。
     * 
     * @param inputDirectory コピー元メールファイル格納ディレクトリパス
     * @param outputDirectory コピー先ディレクトリパス
     * @param errors エラーが発生した場合のエラー情報を格納するマップオブジェクト。
     *            エラーが発生したファイルをキー、エラー情報を値に保持する。
     * @throws BusinessException 入出力ディレクトリのチェックエラーが発生した場合
     */
    private void recursiveCopyMailFiles(File inputDirectory, File outputDirectory, LinkedHashMap<File, Exception> errors)
            throws BusinessException {

        // コピー元ディレクトリが存在しない場合、例外をスローする。
        if (!inputDirectory.isDirectory()) {
            throw new BusinessException(message.getMessage(Messages.ERROR_NOT_EXISTS_DIR,
                    new Object[] { inputDirectory }));
        }

        // コピー先ディレクトリが作成できない場合、例外をスローする。
        if (!outputDirectory.exists() && !outputDirectory.mkdirs()) {
            throw new BusinessException(message.getMessage(Messages.ERROR_NOT_MAKE_DIR,
                    new Object[] { outputDirectory }));
        }

        for (String fileName : inputDirectory.list()) {

            File inputFile = new File(inputDirectory, fileName);

            if (inputFile.isDirectory()) {
                // サブディレクトリの処理を行う。
                recursiveCopyMailFiles(inputFile, new File(outputDirectory, fileName), errors);

            } else {
                try {
                    // ファイルコピーを行う。
                    FileUtils.copyFile(inputFile, createOutputFileName(inputFile, outputDirectory));
                } catch (Exception e) {
                    // マップオブジェクトにエラー情報を追加する。
                    errors.put(inputFile, e);
                }
            }
        }
    }

    /**
     * コピー先ファイル名を生成する。
     * 
     * @param inputFile コピー元ファイル
     * @param outputDirectory コピー先ディレクトリ
     * @return コピー先ファイル
     * @throws IOException ファイル入出力エラーが発生した場合
     */
    protected File createOutputFileName(File inputFile, File outputDirectory) throws IOException {

        // 入力ファイルのハッシュ値を生成する。
        String hashInputFile;
        try (InputStream is = new FileInputStream(inputFile)) {
            hashInputFile = DigestUtils.shaHex(is);
        }

        // 拡張子
        String ext = FilenameUtils.getExtension(inputFile.getPath());
        if (!StringUtils.isEmpty(ext)) {
            ext = '.' + ext;
        }

        // ベース名
        String base = hashInputFile;

        // コピー先ファイルパス
        File outputFile = new File(outputDirectory, base + ext);

        int count = 1;

        // 同名のファイル名が存在する場合
        while (outputFile.exists() && !equalsFile(inputFile, hashInputFile, outputFile)) {
            // ファイル名に連番を付与する。
            count++;
            outputFile = new File(outputDirectory, base + " (" + count + ")" + ext);
        }

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
    private boolean equalsFile(File inputFile, String hashInputFile, File outputFile) throws IOException {

        // ファイルサイズの一致チェックを行う。
        if (inputFile.length() != outputFile.length()) {
            return false;
        }

        // ファイルのハッシュ値を比較する。
        try (InputStream is = new FileInputStream(outputFile)) {
            if (hashInputFile.compareToIgnoreCase(DigestUtils.shaHex(is)) != 0) {
                return false;
            }
        }

        // ファイルのバイトデータ一致チェックを行う。
        byte[] inputFileBytes = FileUtils.readFileToByteArray(inputFile);
        byte[] outputFileBytes = FileUtils.readFileToByteArray(outputFile);

        return Arrays.equals(inputFileBytes, outputFileBytes);
    }
}
