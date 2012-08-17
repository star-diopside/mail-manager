package jp.mailmanager.main;

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
public class Main {

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
        Main obj = context.getBean(Main.class);

        // メイン処理を実行する。
        System.exit(obj.execute(args));
    }

    /**
     * メイン処理
     * 
     * @param args コマンドライン引数
     * @return 終了ステータスコード
     */
    public int execute(String[] args) {

        try {
            // 処理実装クラスのメソッドを呼び出す。
            mailFileManager.execute();

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
