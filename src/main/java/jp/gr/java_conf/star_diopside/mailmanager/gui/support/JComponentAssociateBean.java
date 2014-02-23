package jp.gr.java_conf.star_diopside.mailmanager.gui.support;

import java.util.Collection;

import javax.swing.JComponent;

import org.springframework.beans.factory.FactoryBean;

public class JComponentAssociateBean implements FactoryBean<JComponent> {

    private JComponent component;
    private Collection<ContainerComponent> containerComponents;

    public void setComponent(JComponent component) {
        this.component = component;
    }

    public void setContainerComponents(Collection<ContainerComponent> containerComponents) {
        this.containerComponents = containerComponents;
    }

    @Override
    public JComponent getObject() throws Exception {
        for (ContainerComponent comp : containerComponents) {
            comp.addTo(component);
        }
        return component;
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
