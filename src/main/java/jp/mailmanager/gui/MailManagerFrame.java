package jp.mailmanager.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * MailManagerウィンドウクラス
 */
public class MailManagerFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private MailManagerPanel panel;

    /**
     * コンストラクタ
     */
    public MailManagerFrame() {
        setTitle("MailManager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new MailManagerPanel();
        getContentPane().add(panel, BorderLayout.CENTER);
    }

    /**
     * 初期化する。
     */
    public void init() {

        // コンテナの初期化を行う。
        panel.init();

        // 自身の初期化を行う。
        pack();
        setLocationByPlatform(true);
        setVisible(true);
    }
}
