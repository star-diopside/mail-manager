package jp.mailmanager.main;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import jp.mailmanager.service.MailFileManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

/**
 * メインクラス
 */
@Controller
public class Main implements Launcher {

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    /** リターンコード：正常終了 */
    public static final int RETURN_SUCCESS = 0;

    /** リターンコード：エラー終了 */
    public static final int RETURN_ERROR = 1;

    @Autowired
    private MailFileManager mailFileManager;

    /**
     * エントリーポイント
     * 
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {

        // メインクラスのインスタンスを取得する。
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Launcher launcher = context.getBean("main", Launcher.class);

        // メイン処理を実行する。
        System.exit(launcher.run(args));
    }

    /**
     * メイン処理
     * 
     * @param args コマンドライン引数
     * @return 終了ステータスコード
     */
    public int run(String[] args) {

        try {
            // 処理実装クラスのメソッドを呼び出す。
            LinkedHashMap<File, Exception> errors = mailFileManager.copyMailFiles(args[0], args[1]);

            for (Map.Entry<File, Exception> error : errors.entrySet()) {
                System.err.println("ファイル名：" + error.getKey());
                System.err.println("例外内容：" + error.getValue().getMessage());
                error.getValue().printStackTrace();
                System.err.println();
            }

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
