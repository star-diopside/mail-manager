package jp.gr.java_conf.star_diopside.mailmanager.gui.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.text.JTextComponent;

import jp.gr.java_conf.star_diopside.mailmanager.gui.support.ComponentUtils;

public class DirectorySelectActionListener implements ActionListener {

    private JTextComponent textComponent;

    public void setTextComponent(JTextComponent textComponent) {
        this.textComponent = textComponent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JFileChooser chooser = new JFileChooser();

        chooser.setSelectedFile(Paths.get(textComponent.getText()).toFile());
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (chooser.showOpenDialog(ComponentUtils.findParent((Component) e.getSource(), JFrame.class)) == JFileChooser.APPROVE_OPTION) {
            textComponent.setText(chooser.getSelectedFile().toPath().toString());
        }
    }
}
