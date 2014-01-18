package jp.gr.java_conf.star_diopside.mailmanager.exception;

/**
 * 業務例外クラス
 */
public class BusinessException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     */
    public BusinessException() {
        super();
    }

    /**
     * コンストラクタ
     * 
     * @param message 詳細メッセージ
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * コンストラクタ
     * 
     * @param cause 原因例外
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }

    /**
     * コンストラクタ
     * 
     * @param message 詳細メッセージ
     * @param cause 原因例外
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
