package jp.gr.java_conf.star_diopside.mailmanager.gui.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import jp.gr.java_conf.star_diopside.mailmanager.constant.Labels;
import jp.gr.java_conf.star_diopside.mailmanager.constant.Messages;
import jp.gr.java_conf.star_diopside.mailmanager.exception.BusinessException;
import jp.gr.java_conf.star_diopside.mailmanager.gui.support.ComponentUtils;
import jp.gr.java_conf.star_diopside.mailmanager.service.MailFileManager;
import jp.gr.java_conf.star_diopside.mailmanager.util.MessageBuilder;

import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;

public class MailManagerExecActionListener implements ActionListener {

    /** メッセージID：空ディレクトリ警告メッセージ */
    private static final String MESSAGE_NOT_EMPTY = "Message.MailManagerExec.Confirm.NotEmptyDestDir";

    /** メッセージソース */
    @Autowired
    private MessageSourceAccessor message;

    /** メールファイル管理処理クラス */
    @Autowired
    private MailFileManager mailFileManager;

    private JTextField txtOrigDir;
    private JTextField txtDestDir;

    public void setTxtOrigDir(JTextField txtOrigDir) {
        this.txtOrigDir = txtOrigDir;
    }

    public void setTxtDestDir(JTextField txtDestDir) {
        this.txtDestDir = txtDestDir;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JFrame parent = ComponentUtils.findParent((Component) e.getSource(), JFrame.class);

        // コピー元ディレクトリの入力必須チェックを行う。
        if (StringUtils.isBlank(txtOrigDir.getText())) {
            // エラーメッセージを組み立てる。
            MessageBuilder messageBuilder = new MessageBuilder(message);
            messageBuilder.setMessageCode(Messages.ERROR_REQUIRED);
            messageBuilder.addArgumentCode(Labels.COPY_ORIG_DIR);

            // エラーメッセージを表示する。
            JOptionPane.showMessageDialog(parent, messageBuilder.getMessage(), message.getMessage(Labels.ERROR),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // コピー先ディレクトリの入力必須チェックを行う。
        if (StringUtils.isBlank(txtDestDir.getText())) {
            // エラーメッセージを組み立てる。
            MessageBuilder messageBuilder = new MessageBuilder(message);
            messageBuilder.setMessageCode(Messages.ERROR_REQUIRED);
            messageBuilder.addArgumentCode(Labels.COPY_DEST_DIR);

            // エラーメッセージを表示する。
            JOptionPane.showMessageDialog(parent, messageBuilder.getMessage(), message.getMessage(Labels.ERROR),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // コピー元ディレクトリの存在チェックを行う。
        Path origDir = Paths.get(txtOrigDir.getText());

        if (!Files.isDirectory(origDir)) {
            // エラーメッセージを組み立てる。
            MessageBuilder messageBuilder = new MessageBuilder(message);
            messageBuilder.setMessageCode(Messages.ERROR_NOT_EXISTS);
            messageBuilder.addArgumentCode(Labels.COPY_ORIG_DIR);

            // エラーメッセージを表示する。
            JOptionPane.showMessageDialog(parent, messageBuilder.getMessage(), message.getMessage(Labels.ERROR),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // コピー先ディレクトリが空ディレクトリであるか確認する。
        Path destDir = Paths.get(txtDestDir.getText());

        try {
            if (!isEmptyDirectory(destDir)) {
                // 確認ダイアログを表示し、キャンセルされた場合は処理を終了する。
                if (JOptionPane.showConfirmDialog(parent, message.getMessage(MESSAGE_NOT_EMPTY),
                        message.getMessage(Labels.CONFIRM), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
                    return;
                }
            }
        } catch (IOException ex) {
            // エラーダイアログを表示する。
            JOptionPane.showMessageDialog(parent, ex.getMessage(), message.getMessage(Labels.ERROR),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 処理実行前に確認を行う。
        if (JOptionPane.showConfirmDialog(parent, message.getMessage(Messages.CONFIRM_BEFORE_EXECUTE),
                message.getMessage(Labels.CONFIRM), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
            return;
        }

        // 処理を実行する。
        LinkedHashMap<Path, Exception> errors;

        try {
            errors = mailFileManager.copyMailFiles(origDir, destDir);
        } catch (BusinessException ex) {
            // エラーダイアログを表示する。
            JOptionPane.showMessageDialog(parent, ex.getMessage(), message.getMessage(Labels.ERROR),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 処理結果を出力する。
        Path result = destDir.resolve("errors.csv");

        try (BufferedWriter bw = Files.newBufferedWriter(result, Charsets.UTF_8)) {
            for (Map.Entry<Path, Exception> error : errors.entrySet()) {
                bw.write(StringEscapeUtils.escapeCsv(error.getKey().toString()));
                bw.write(',');

                StringWriter sw = new StringWriter();
                try (PrintWriter pw = new PrintWriter(sw)) {
                    error.getValue().printStackTrace(pw);
                }
                bw.write(StringEscapeUtils.escapeCsv(sw.toString()));

                bw.newLine();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // 完了メッセージを出力する。
        JOptionPane.showMessageDialog(parent, message.getMessage(Messages.INFO_COMPLETE),
                message.getMessage(Labels.INFO), JOptionPane.INFORMATION_MESSAGE);
    }

    private static boolean isEmptyDirectory(Path path) throws IOException {

        if (Files.notExists(path)) {
            return true;
        } else if (!Files.isDirectory(path)) {
            return false;
        } else {
            try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {
                return !ds.iterator().hasNext();
            }
        }
    }
}
