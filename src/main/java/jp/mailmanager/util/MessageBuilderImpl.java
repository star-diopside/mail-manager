package jp.mailmanager.util;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

/**
 * メッセージビルダークラス
 */
@Component
@Scope(value = "thread", proxyMode = ScopedProxyMode.INTERFACES)
public class MessageBuilderImpl implements MessageBuilder {

    /** メッセージソース */
    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    /** メッセージコード */
    private String msg;

    /** 引数 */
    private ArrayList<Object> args = new ArrayList<>();

    @Override
    public void init() {
        this.msg = null;
        this.args.clear();
    }

    @Override
    public void setMessageCode(String code) {
        this.msg = code;
    }

    @Override
    public void addArgumentCode(String code) {
        this.args.add(this.messageSourceAccessor.getMessage(code));
    }

    @Override
    public void addArgument(Object arg) {
        this.args.add(arg);
    }

    @Override
    public String getMessage() {
        return this.messageSourceAccessor.getMessage(this.msg, this.args.toArray());
    }
}
