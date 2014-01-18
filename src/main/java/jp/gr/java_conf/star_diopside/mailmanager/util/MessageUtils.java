package jp.gr.java_conf.star_diopside.mailmanager.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.MessageSourceAccessor;

/**
 * Spring Framework 管理対象外オブジェクトからメッセージソースへアクセスするためのヘルパークラス
 */
public class MessageUtils {

    /**
     * プライベートコンストラクタ
     */
    private MessageUtils() {
    }

    /**
     * メッセージソースを取得する。
     * 
     * @return メッセージソースへアクセスするMessageSourceAccessor
     */
    public static MessageSourceAccessor getMessageSourceAccessor() {
        ApplicationContext context = ApplicationContextUtils.getApplicationContext();
        return context.getBean(MessageSourceAccessor.class);
    }
}
