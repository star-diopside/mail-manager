package jp.gr.java_conf.star_diopside.mailmanager.gui.support;

import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.AbstractButton;

import org.springframework.beans.factory.FactoryBean;

public class ActionListenerAssociateBean implements FactoryBean<AbstractButton> {

    private AbstractButton button;
    private Collection<ActionListener> actionListeners;

    public void setButton(AbstractButton button) {
        this.button = button;
    }

    public void setActionListeners(Collection<ActionListener> actionListeners) {
        this.actionListeners = actionListeners;
    }

    @Override
    public AbstractButton getObject() throws Exception {
        for (ActionListener listener : actionListeners) {
            button.addActionListener(listener);
        }
        return button;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
