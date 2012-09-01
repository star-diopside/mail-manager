package jp.mailmanager.main;

import java.util.ResourceBundle;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import jp.mailmanager.exception.SwingExceptionHandler;
import jp.mailmanager.gui.MailManagerFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * アプリケーション実行クラス
 */
public class Launcher implements Runnable {

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    /** リターンコード：エラー終了 */
    public static final int RETURN_ERROR = 1;

    /**
     * エントリーポイント
     * 
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        // イベントディスパッチスレッドでメイン処理を実行する。
        SwingUtilities.invokeLater(new Launcher());
    }

    /**
     * メイン処理
     * 
     * @param args コマンドライン引数
     * @return 終了ステータスコード
     */
    public void run() {

        try {
            // ルックアンドフィールの設定を行う。
            updateLookAndFeel();

            // 例外ハンドラを設定する。
            Thread.currentThread().setUncaughtExceptionHandler(new SwingExceptionHandler());

            // メインウィンドウを表示する。
            MailManagerFrame frame = new MailManagerFrame();
            frame.init();

        } catch (Throwable t) {
            // エラーログを出力する。
            LOGGER.error(t.getMessage(), t);

            // エラー終了のリターンコードを返す。
            System.exit(RETURN_ERROR);
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
