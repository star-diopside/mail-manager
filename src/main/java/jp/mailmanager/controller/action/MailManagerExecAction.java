package jp.mailmanager.controller.action;

import jp.mailmanager.controller.model.MailManagerModel;
import jp.mailmanager.controller.support.ActionModelDriven;
import jp.mailmanager.controller.support.ParentComponentAccessor;

/**
 * MailManager処理実行イベントインタフェース
 */
public interface MailManagerExecAction extends ActionModelDriven<MailManagerModel>, ParentComponentAccessor {

}
