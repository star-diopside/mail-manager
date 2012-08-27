package jp.mailmanager.main;

import java.util.ResourceBundle;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import jp.mailmanager.gui.MailManagerFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * メインクラス
 */
public class Main implements Launcher {

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    /** リターンコード：正常終了 */
    public static final int RETURN_SUCCESS = 0;

    /** リターンコード：エラー終了 */
    public static final int RETURN_ERROR = 1;

    /**
     * エントリーポイント
     * 
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {

        // メインクラスのインスタンスを取得する。
        Launcher launcher = new Main();

        // メイン処理を実行する。
        int result = launcher.run(args);

        if (result != RETURN_SUCCESS) {
            System.exit(result);
        }
    }

    /**
     * メイン処理
     * 
     * @param args コマンドライン引数
     * @return 終了ステータスコード
     */
    public int run(String[] args) {

        try {
            // ルックアンドフィールの設定を行う。
            updateLookAndFeel();

            // メインウィンドウを表示する。
            MailManagerFrame frame = new MailManagerFrame();
            frame.init();

            // 正常終了のリターンコードを返す。
            return RETURN_SUCCESS;

        } catch (Throwable t) {
            // エラーログを出力する。
            LOGGER.error(t.getMessage(), t);

            // エラー終了のリターンコードを返す。
            return RETURN_ERROR;
        }
    }

    /**
     * 設定ファイル情報をもとにルックアンドフィールを変更する。
     * 
     * @throws UnsupportedLookAndFeelException ルックアンドフィールがサポートされていない場合
     * @throws IllegalAccessException ルックアンドフィールのクラスまたは初期化子にアクセスできない場合
     * @throws InstantiationException ルックアンドフィールのクラスの新しいインスタンスを生成できなかった場合
     * @throws ClassNotFoundException ルックアンドフィールのクラスが見つからない場合
     */
    private void updateLookAndFeel() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {

        ResourceBundle resource = ResourceBundle.getBundle("mail-manager");

        // ルックアンドフィール種別が設定されていない場合、処理を終了する。
        if (!resource.containsKey("UI.LookAndFeelType")) {
            return;
        }

        switch (resource.getString("UI.LookAndFeelType")) {
        case "CrossPlatform":
            // クロスプラットフォームのルックアンドフィールを設定する。
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            return;

        case "System":
            // システムのルックアンドフィールを設定する。
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            return;

        case "Custom":
            // ルックアンドフィール名が設定されていない場合、処理を終了する。
            if (!resource.containsKey("UI.LookAndFeelName")) {
                return;
            }

            // 設定ファイルから取得したルックアンドフィールが使用可能であれば設定する。
            String lookAndFeelName = resource.getString("UI.LookAndFeelName");

            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (lookAndFeelName.equals(info.getName()) || lookAndFeelName.equals(info.getClassName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }

            break;

        default:
            break;
        }

    }
}
