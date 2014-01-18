package jp.gr.java_conf.star_diopside.mailmanager.controller.support;

import java.awt.Component;

/**
 * 親コンポーネントアクセスインタフェース
 */
public interface ParentComponentAccessor {

    /**
     * 親コンポーネントを設定する。
     * 
     * @param parent 親コンポーネント
     */
    void setParent(Component parent);

}
