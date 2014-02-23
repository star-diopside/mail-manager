package jp.gr.java_conf.star_diopside.mailmanager.gui.support;

import java.awt.Container;
import java.util.Collection;

import javax.swing.JFrame;

import org.springframework.beans.factory.FactoryBean;

public class JFrameAssociateBean implements FactoryBean<JFrame> {

    private JFrame frame;
    private Collection<ContainerComponent> containerComponents;

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void setContainerComponents(Collection<ContainerComponent> containerComponents) {
        this.containerComponents = containerComponents;
    }

    @Override
    public JFrame getObject() throws Exception {
        Container contentPane = frame.getContentPane();
        for (ContainerComponent comp : containerComponents) {
            comp.addTo(contentPane);
        }
        frame.pack();
        return frame;
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
