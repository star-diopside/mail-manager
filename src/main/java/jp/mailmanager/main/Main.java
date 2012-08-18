package jp.mailmanager.main;

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
}
