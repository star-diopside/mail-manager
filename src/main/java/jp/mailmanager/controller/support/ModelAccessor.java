package jp.mailmanager.controller.support;

/**
 * モデルアクセスインタフェース
 * 
 * @param <T> モデル型
 */
public interface ModelAccessor<T> {

    /**
     * モデルを取得する。
     * 
     * @return モデル
     */
    T getModel();

    /**
     * モデルを設定する。
     * 
     * @param model モデル
     */
    void setModel(T model);

}
