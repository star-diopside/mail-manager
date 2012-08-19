package jp.mailmanager.controller.support;

import java.awt.event.ActionListener;

/**
 * アクションモデルインタフェース<br>
 * アクションリスナーとモデルアクセスを組み合わせたインタフェース
 * 
 * @param <T> モデル型
 */
public interface ActionModelDriven<T> extends ActionListener, ModelAccessor<T> {

}
