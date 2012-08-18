package jp.mailmanager.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ApplicationContextユーティリティクラス
 */
public class ApplicationContextUtils {

    private static final ApplicationContext APPLICATION_CONTEXT = new ClassPathXmlApplicationContext(
            "applicationContext.xml");

    /**
     * プライベートコンストラクタ
     */
    private ApplicationContextUtils() {
    }

    /**
     * ApplicationContextのインスタンスを取得する。
     * 
     * @return ApplicationContextインスタンス
     */
    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }
}
