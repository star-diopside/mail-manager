package jp.mailmanager.controller.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import jp.mailmanager.constant.Labels;
import jp.mailmanager.constant.Messages;
import jp.mailmanager.controller.model.MailManagerModel;
import jp.mailmanager.exception.BusinessException;
import jp.mailmanager.service.MailFileManager;
import jp.mailmanager.util.MessageBuilder;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;

/**
 * MailManager処理実行イベントクラス
 */
@Controller
@Scope(value = "thread", proxyMode = ScopedProxyMode.INTERFACES)
public class MailManagerExecActionImpl implements MailManagerExecAction {

    /** メッセージID：空ディレクトリ警告メッセージ */
    private static final String MESSAGE_NOT_EMPTY = "Message.MailManagerExec.Confirm.NotEmptyDestDir";

    /** モデル */
    private MailManagerModel model;

    /** 親コンポーネント */
    private Component parent;

    /** メッセージソース */
    @Autowired
    private MessageSourceAccessor message;

    /** メールファイル管理処理クラス */
    @Autowired
    private MailFileManager mailFileManager;

    @Override
    public MailManagerModel getModel() {
        return this.model;
    }

    @Override
    public void setModel(MailManagerModel model) {
        this.model = model;
    }

    @Override
    public void setParent(Component parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // メッセージビルダー
        MessageBuilder messageBuilder = new MessageBuilder(message);

        // コピー元ディレクトリの入力必須チェックを行う。
        if (StringUtils.isEmpty(model.getOrigDir())) {
            // エラーメッセージを組み立てる。
            messageBuilder.init();
            messageBuilder.setMessageCode(Messages.ERROR_REQUIRED);
            messageBuilder.addArgumentCode(Labels.COPY_ORIG_DIR);

            // エラーメッセージを表示する。
            JOptionPane.showMessageDialog(parent, messageBuilder.getMessage(), message.getMessage(Labels.ERROR),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // コピー先ディレクトリの入力必須チェックを行う。
        if (StringUtils.isEmpty(this.model.getDestDir())) {
            // エラーメッセージを組み立てる。
            messageBuilder.init();
            messageBuilder.setMessageCode(Messages.ERROR_REQUIRED);
            messageBuilder.addArgumentCode(Labels.COPY_DEST_DIR);

            // エラーメッセージを表示する。
            JOptionPane.showMessageDialog(parent, messageBuilder.getMessage(), message.getMessage(Labels.ERROR),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // コピー元ディレクトリの存在チェックを行う。
        File origDir = new File(model.getOrigDir());

        if (!origDir.isDirectory()) {
            // エラーメッセージを組み立てる。
            messageBuilder.init();
            messageBuilder.setMessageCode(Messages.ERROR_NOT_EXISTS);
            messageBuilder.addArgumentCode(Labels.COPY_ORIG_DIR);

            // エラーメッセージを表示する。
            JOptionPane.showMessageDialog(parent, messageBuilder.getMessage(), message.getMessage(Labels.ERROR),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // コピー先ディレクトリが空ディレクトリであるか確認する。
        File destDir = new File(model.getDestDir());

        if (!ArrayUtils.isEmpty(destDir.list())) {
            // 確認ダイアログを表示し、キャンセルされた場合は処理を終了する。
            if (JOptionPane.showConfirmDialog(parent, message.getMessage(MESSAGE_NOT_EMPTY),
                    message.getMessage(Labels.CONFIRM), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
                return;
            }
        }

        // 処理実行前に確認を行う。
        if (JOptionPane.showConfirmDialog(parent, message.getMessage(Messages.CONFIRM_BEFORE_EXECUTE),
                message.getMessage(Labels.CONFIRM), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
            return;
        }

        // 処理を実行する。
        LinkedHashMap<File, Exception> errors;

        try {
            errors = mailFileManager.copyMailFiles(origDir, destDir);
        } catch (BusinessException ex) {
            // エラーダイアログを表示する。
            JOptionPane.showMessageDialog(parent, ex.getMessage(), message.getMessage(Labels.ERROR),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 処理結果を出力する。
        File result = new File(destDir, "errors.csv");

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(result), "UTF-8"))) {

            for (Map.Entry<File, Exception> error : errors.entrySet()) {
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
}
