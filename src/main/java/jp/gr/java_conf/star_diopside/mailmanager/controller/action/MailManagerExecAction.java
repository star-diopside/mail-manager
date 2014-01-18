package jp.gr.java_conf.star_diopside.mailmanager.controller.action;

import jp.gr.java_conf.star_diopside.mailmanager.controller.model.MailManagerModel;
import jp.gr.java_conf.star_diopside.mailmanager.controller.support.ActionModelDriven;
import jp.gr.java_conf.star_diopside.mailmanager.controller.support.ParentComponentAccessor;

/**
 * MailManager処理実行イベントインタフェース
 */
public interface MailManagerExecAction extends ActionModelDriven<MailManagerModel>, ParentComponentAccessor {

}
