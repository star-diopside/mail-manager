package jp.mailmanager.exception;

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Swingアプリケーションの例外ハンドラ
 */
public class SwingExceptionHandler implements UncaughtExceptionHandler {

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(SwingExceptionHandler.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {

        // エラーログを出力する。
        LOGGER.error(e.getMessage(), e);

        // エラーメッセージを表示する。
        if (SwingUtilities.isEventDispatchThread()) {
            // イベントディスパッチスレッドで実行している場合、メッセージ表示処理を行う。
            showMessage();

        } else {
            // イベントディスパッチスレッドでメッセージ表示処理を行う。
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        showMessage();
                    }
                });

            } catch (InvocationTargetException | InterruptedException ex) {
                // エラーログを出力する。
                LOGGER.error(ex.getMessage(), ex);
            }
        }
    }

    /**
     * エラーメッセージの表示を行う。
     */
    private void showMessage() {
        JOptionPane.showMessageDialog(null, "エラーが発生しました。詳細はエラーログを参照してください。", "エラー", JOptionPane.ERROR_MESSAGE);
    }
}
