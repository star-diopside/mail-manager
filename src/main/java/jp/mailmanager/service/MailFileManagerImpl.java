package jp.mailmanager.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import jp.mailmanager.exception.BusinessException;
import jp.mailmanager.util.ThreadLocalUtils;

import org.apache.commons.io.FileUtils;
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
    private MessageSourceAccessor messageSourceAccessor;

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

        // コピー元ディレクトリが存在しない場合、例外をスローする。
        if (!inputDirectory.isDirectory()) {
            throw new BusinessException(messageSourceAccessor.getMessage("Error.IsNotDirectory",
                    new Object[] { inputDirectory }));
        }

        // コピー先ディレクトリが作成できない場合、例外をスローする。
        if (!outputDirectory.mkdirs()) {
            throw new BusinessException(messageSourceAccessor.getMessage("Error.NotMakeDirectory",
                    new Object[] { outputDirectory }));
        }

        // メールファイル解析のため、Sessionオブジェクトを生成する。
        Properties props = System.getProperties();
        Session session = Session.getDefaultInstance(props);

        // 再帰的にファイルコピーを行う。
        LinkedHashMap<File, Exception> errors = new LinkedHashMap<>();
        recursiveCopyMailFiles(inputDirectory, outputDirectory, session, errors);

        return errors;
    }

    /**
     * メールファイル(EML)を再帰的にコピーする。
     * 
     * @param inputDirectory コピー元メールファイル格納ディレクトリパス
     * @param outputDirectory コピー先ディレクトリパス
     * @param session メールファイル解析に使用するSessionオブジェクト
     * @param errors エラーが発生した場合のエラー情報を格納するマップオブジェクト。
     *            エラーが発生したファイルをキー、エラー情報を値に保持する。
     */
    private void recursiveCopyMailFiles(File inputDirectory, File outputDirectory, Session session,
            LinkedHashMap<File, Exception> errors) {

        for (String fileName : inputDirectory.list()) {

            File inputFile = new File(inputDirectory, fileName);

            if (inputFile.isDirectory()) {
                // サブディレクトリの処理を行う。
                recursiveCopyMailFiles(inputFile, new File(outputDirectory, fileName), session, errors);

            } else {
                try {
                    // メッセージ内容をもとにコピー先ファイルパスを生成する。
                    File outputFile;

                    try (InputStream is = new FileInputStream(inputFile)) {
                        MimeMessage msg = new MimeMessage(session, is);
                        outputFile = createMailFileName(msg, outputDirectory);
                    }

                    // ファイルコピーを行う。
                    FileUtils.copyFile(inputFile, outputFile);

                } catch (Exception e) {
                    // マップオブジェクトにエラー情報を追加する。
                    errors.put(inputFile, e);
                }
            }
        }
    }

    /**
     * MimeMessageオプジェクトをもとにファイル名を生成する。
     * 
     * @param msg MimeMessageオブジェクト
     * @param outputDirectory コピー先ディレクトリパス
     * @return コピー先ファイルパス
     * @throws MessagingException メッセージ解析でエラーが発生した場合
     */
    protected File createMailFileName(MimeMessage msg, File outputDirectory) throws MessagingException {

        // 拡張子
        final String ext = ".eml";

        // ベース名
        String sent = ThreadLocalUtils.FORMATTER_YYYYMMDDHHMMSS.get().format(msg.getSentDate());
        String subject = msg.getSubject();
        String base = sent + "_" + subject;

        // コピー先ファイルパス
        File outputFile = new File(outputDirectory, base + ext);

        int count = 1;

        // 同名のファイル名が存在する場合、ファイル名に連番を付与する。
        while (outputFile.exists()) {
            count++;
            outputFile = new File(outputDirectory, base + " (" + count + ")" + ext);
        }

        return outputFile;
    }
}
