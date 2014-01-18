package jp.gr.java_conf.star_diopside.mailmanager.controller.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;

import jp.gr.java_conf.star_diopside.mailmanager.controller.model.MailManagerModel;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

/**
 * MailManagerコピー先ディレクトリ選択イベントクラス
 */
@Controller
@Scope(value = "thread", proxyMode = ScopedProxyMode.INTERFACES)
public class MailManagerSelectDestDirActionImpl implements MailManagerSelectDestDirAction {

    /** モデル */
    private MailManagerModel model;

    /** 親コンポーネント */
    private Component parent;

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

        JFileChooser chooser = new JFileChooser();

        chooser.setSelectedFile(new File(this.model.getDestDir()));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (chooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            this.model.setDestDir(chooser.getSelectedFile().getAbsolutePath());
        }
    }
}
