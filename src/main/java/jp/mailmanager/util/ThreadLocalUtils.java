package jp.mailmanager.util;

import java.text.SimpleDateFormat;

public class ThreadLocalUtils {

    private ThreadLocalUtils() {
    }

    public static final ThreadLocal<SimpleDateFormat> FORMATTER_YYYYMMDDHHMMSS = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmss");
        }
    };
}
