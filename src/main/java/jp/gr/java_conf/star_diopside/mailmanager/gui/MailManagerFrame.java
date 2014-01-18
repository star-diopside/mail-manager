package jp.gr.java_conf.star_diopside.mailmanager.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import jp.gr.java_conf.star_diopside.mailmanager.controller.action.MailManagerExecAction;
import jp.gr.java_conf.star_diopside.mailmanager.controller.action.MailManagerSelectDestDirAction;
import jp.gr.java_conf.star_diopside.mailmanager.controller.action.MailManagerSelectOrigDirAction;
import jp.gr.java_conf.star_diopside.mailmanager.controller.model.MailManagerModel;
import jp.gr.java_conf.star_diopside.mailmanager.controller.support.AbstractModelActionListener;
import jp.gr.java_conf.star_diopside.mailmanager.controller.support.ActionModelDriven;
import jp.gr.java_conf.star_diopside.mailmanager.util.ApplicationContextUtils;

import org.springframework.context.ApplicationContext;

/**
 * MailManagerウィンドウクラス
 */
public class MailManagerFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField txtOrigDir;
    private JTextField txtDestDir;
    private JButton btnOrigDir;
    private JButton btnDestDir;
    private JButton btnExec;

    /**
     * コンストラクタ
     */
    public MailManagerFrame() {
        setTitle("MailManager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelMain = new JPanel();
        panelMain.setBorder(new EmptyBorder(16, 16, 16, 16));
        getContentPane().add(panelMain, BorderLayout.CENTER);
        GridBagLayout gbl_panelMain = new GridBagLayout();
        gbl_panelMain.columnWidths = new int[] { 0, 0, 0, 0 };
        gbl_panelMain.rowHeights = new int[] { 0, 0, 0 };
        gbl_panelMain.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
        gbl_panelMain.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
        panelMain.setLayout(gbl_panelMain);

        JLabel lblOrigDir = new JLabel("コピー元ディレクトリ: ");
        GridBagConstraints gbc_lblOrigDir = new GridBagConstraints();
        gbc_lblOrigDir.insets = new Insets(0, 0, 5, 5);
        gbc_lblOrigDir.anchor = GridBagConstraints.EAST;
        gbc_lblOrigDir.gridx = 0;
        gbc_lblOrigDir.gridy = 0;
        panelMain.add(lblOrigDir, gbc_lblOrigDir);

        txtOrigDir = new JTextField();
        GridBagConstraints gbc_txtOrigDir = new GridBagConstraints();
        gbc_txtOrigDir.insets = new Insets(0, 0, 5, 5);
        gbc_txtOrigDir.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtOrigDir.gridx = 1;
        gbc_txtOrigDir.gridy = 0;
        panelMain.add(txtOrigDir, gbc_txtOrigDir);
        txtOrigDir.setColumns(10);

        btnOrigDir = new JButton("選択");
        GridBagConstraints gbc_btnOrigDir = new GridBagConstraints();
        gbc_btnOrigDir.insets = new Insets(0, 0, 5, 0);
        gbc_btnOrigDir.gridx = 2;
        gbc_btnOrigDir.gridy = 0;
        panelMain.add(btnOrigDir, gbc_btnOrigDir);

        JLabel lblDestDir = new JLabel("コピー先ディレクトリ: ");
        GridBagConstraints gbc_lblDestDir = new GridBagConstraints();
        gbc_lblDestDir.anchor = GridBagConstraints.EAST;
        gbc_lblDestDir.insets = new Insets(0, 0, 0, 5);
        gbc_lblDestDir.gridx = 0;
        gbc_lblDestDir.gridy = 1;
        panelMain.add(lblDestDir, gbc_lblDestDir);

        txtDestDir = new JTextField();
        GridBagConstraints gbc_txtDestDir = new GridBagConstraints();
        gbc_txtDestDir.insets = new Insets(0, 0, 0, 5);
        gbc_txtDestDir.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtDestDir.gridx = 1;
        gbc_txtDestDir.gridy = 1;
        panelMain.add(txtDestDir, gbc_txtDestDir);
        txtDestDir.setColumns(10);

        btnDestDir = new JButton("選択");
        GridBagConstraints gbc_btnDestDir = new GridBagConstraints();
        gbc_btnDestDir.gridx = 2;
        gbc_btnDestDir.gridy = 1;
        panelMain.add(btnDestDir, gbc_btnDestDir);

        JPanel panelBottom = new JPanel();
        panelBottom.setBorder(new MatteBorder(1, 0, 0, 0, (Color) SystemColor.activeCaptionBorder));
        getContentPane().add(panelBottom, BorderLayout.SOUTH);
        GridBagLayout gbl_panelBottom = new GridBagLayout();
        gbl_panelBottom.columnWidths = new int[] { 305, 129, 0 };
        gbl_panelBottom.rowHeights = new int[] { 43, 0 };
        gbl_panelBottom.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
        gbl_panelBottom.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
        panelBottom.setLayout(gbl_panelBottom);

        JPanel panelButtonLeft = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panelButtonLeft.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        panelButtonLeft.setBorder(new EmptyBorder(6, 11, 6, 11));
        GridBagConstraints gbc_panelButtonLeft = new GridBagConstraints();
        gbc_panelButtonLeft.anchor = GridBagConstraints.WEST;
        gbc_panelButtonLeft.insets = new Insets(0, 0, 0, 5);
        gbc_panelButtonLeft.fill = GridBagConstraints.VERTICAL;
        gbc_panelButtonLeft.gridx = 0;
        gbc_panelButtonLeft.gridy = 0;
        panelBottom.add(panelButtonLeft, gbc_panelButtonLeft);

        JButton btnClose = new JButton("閉じる");
        panelButtonLeft.add(btnClose);

        JPanel panelButtonRight = new JPanel();
        FlowLayout flowLayout_1 = (FlowLayout) panelButtonRight.getLayout();
        flowLayout_1.setAlignment(FlowLayout.RIGHT);
        panelButtonRight.setBorder(new EmptyBorder(6, 11, 6, 11));
        GridBagConstraints gbc_panelButtonRight = new GridBagConstraints();
        gbc_panelButtonRight.anchor = GridBagConstraints.NORTHEAST;
        gbc_panelButtonRight.gridx = 1;
        gbc_panelButtonRight.gridy = 0;
        panelBottom.add(panelButtonRight, gbc_panelButtonRight);

        btnExec = new JButton("処理実行(R)");
        panelButtonRight.add(btnExec);
        btnExec.setMnemonic(KeyEvent.VK_R);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnf = new JMenu("ファイル(F)");
        mnf.setMnemonic(KeyEvent.VK_F);
        menuBar.add(mnf);

        JMenuItem mntmx = new JMenuItem("アプリケーションの終了(X)");
        mntmx.setMnemonic(KeyEvent.VK_X);
        mnf.add(mntmx);

    }

    /**
     * 初期化する。
     */
    public void init() {

        // イベント定義を行う。
        ApplicationContext context = ApplicationContextUtils.getApplicationContext();

        btnOrigDir.addActionListener(new MailManagerModelActionListener(context
                .getBean(MailManagerSelectOrigDirAction.class)));

        btnDestDir.addActionListener(new MailManagerModelActionListener(context
                .getBean(MailManagerSelectDestDirAction.class)));

        btnExec.addActionListener(new MailManagerModelActionListener(context.getBean(MailManagerExecAction.class)));

        // デフォルトボタンの設定を行う。
        getRootPane().setDefaultButton(btnExec);

        // 自身の初期化を行う。
        pack();
        setLocationByPlatform(true);
        setVisible(true);
    }

    /**
     * MailManagerアクションモデル
     */
    private class MailManagerModelActionListener extends AbstractModelActionListener<MailManagerModel> {

        /**
         * コンストラクタ
         * 
         * @param action MailManagerアクションモデル
         */
        public MailManagerModelActionListener(ActionModelDriven<MailManagerModel> action) {
            super(action);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Component getParent() {
            return MailManagerFrame.this;
        }

        /**
         * 画面入力値をもとにモデルを取得する。
         */
        @Override
        public MailManagerModel getModel() {

            MailManagerModel model = new MailManagerModel();

            model.setOrigDir(txtOrigDir.getText());
            model.setDestDir(txtDestDir.getText());

            return model;
        }

        /**
         * モデルをもとに画面入力値に反映する。
         */
        @Override
        public void setModel(MailManagerModel model) {

            txtOrigDir.setText(model.getOrigDir());
            txtDestDir.setText(model.getDestDir());
        }
    }
}
