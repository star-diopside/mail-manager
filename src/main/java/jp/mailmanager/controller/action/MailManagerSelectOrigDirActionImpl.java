package jp.mailmanager.controller.action;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

import jp.mailmanager.controller.model.MailManagerModel;

@Controller
@Scope(value = "thread", proxyMode = ScopedProxyMode.INTERFACES)
public class MailManagerSelectOrigDirActionImpl implements MailManagerSelectOrigDirAction {

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

        if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            this.model.setOrigDir(chooser.getSelectedFile().getAbsolutePath());
        }
    }
}
