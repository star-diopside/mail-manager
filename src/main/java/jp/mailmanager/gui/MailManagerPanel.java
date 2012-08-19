package jp.mailmanager.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.springframework.context.ApplicationContext;

import jp.mailmanager.controller.action.MailManagerSelectDestDirAction;
import jp.mailmanager.controller.action.MailManagerSelectOrigDirAction;
import jp.mailmanager.controller.model.MailManagerModel;
import jp.mailmanager.controller.support.AbstractModelActionListener;
import jp.mailmanager.controller.support.ActionModelDriven;
import jp.mailmanager.util.ApplicationContextUtils;

/**
 * MailManagerコンテナクラス
 */
public class MailManagerPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextField txtOrigDir;
    private JTextField txtDestDir;
    private JButton btnOrigDir;
    private JButton btnDestDir;

    /**
     * コンストラクタ
     */
    public MailManagerPanel() {
        setBorder(new EmptyBorder(16, 16, 16, 16));
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        JLabel lblOrigDir = new JLabel("コピー元ディレクトリ: ");
        GridBagConstraints gbc_lblOrigDir = new GridBagConstraints();
        gbc_lblOrigDir.insets = new Insets(0, 0, 5, 5);
        gbc_lblOrigDir.anchor = GridBagConstraints.EAST;
        gbc_lblOrigDir.gridx = 0;
        gbc_lblOrigDir.gridy = 0;
        add(lblOrigDir, gbc_lblOrigDir);

        txtOrigDir = new JTextField();
        GridBagConstraints gbc_txtOrigDir = new GridBagConstraints();
        gbc_txtOrigDir.insets = new Insets(0, 0, 5, 5);
        gbc_txtOrigDir.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtOrigDir.gridx = 1;
        gbc_txtOrigDir.gridy = 0;
        add(txtOrigDir, gbc_txtOrigDir);
        txtOrigDir.setColumns(10);

        btnOrigDir = new JButton("選択");
        GridBagConstraints gbc_btnOrigDir = new GridBagConstraints();
        gbc_btnOrigDir.insets = new Insets(0, 0, 5, 0);
        gbc_btnOrigDir.gridx = 2;
        gbc_btnOrigDir.gridy = 0;
        add(btnOrigDir, gbc_btnOrigDir);

        JLabel lblDestDir = new JLabel("コピー先ディレクトリ: ");
        GridBagConstraints gbc_lblDestDir = new GridBagConstraints();
        gbc_lblDestDir.anchor = GridBagConstraints.EAST;
        gbc_lblDestDir.insets = new Insets(0, 0, 0, 5);
        gbc_lblDestDir.gridx = 0;
        gbc_lblDestDir.gridy = 1;
        add(lblDestDir, gbc_lblDestDir);

        txtDestDir = new JTextField();
        GridBagConstraints gbc_txtDestDir = new GridBagConstraints();
        gbc_txtDestDir.insets = new Insets(0, 0, 0, 5);
        gbc_txtDestDir.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtDestDir.gridx = 1;
        gbc_txtDestDir.gridy = 1;
        add(txtDestDir, gbc_txtDestDir);
        txtDestDir.setColumns(10);

        btnDestDir = new JButton("選択");
        GridBagConstraints gbc_btnDestDir = new GridBagConstraints();
        gbc_btnDestDir.gridx = 2;
        gbc_btnDestDir.gridy = 1;
        add(btnDestDir, gbc_btnDestDir);
    }

    /**
     * 初期化する。
     */
    public void init() {

        ApplicationContext context = ApplicationContextUtils.getApplicationContext();

        btnOrigDir.addActionListener(new MailManagerModelActionListener(context
                .getBean(MailManagerSelectOrigDirAction.class)));

        btnDestDir.addActionListener(new MailManagerModelActionListener(context
                .getBean(MailManagerSelectDestDirAction.class)));
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
            return MailManagerPanel.this;
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
