package jp.gr.java_conf.star_diopside.mailmanager.gui.support;

import java.awt.GridBagConstraints;
import java.awt.Insets;

@SuppressWarnings("serial")
public class GridBagConstraintsSupport extends GridBagConstraints {

    public void setGridx(int gridx) {
        this.gridx = gridx;
    }

    public void setGridy(int gridy) {
        this.gridy = gridy;
    }

    public void setGridwidth(int gridwidth) {
        this.gridwidth = gridwidth;
    }

    public void setGridheight(int gridheight) {
        this.gridheight = gridheight;
    }

    public void setWeightx(double weightx) {
        this.weightx = weightx;
    }

    public void setWeighty(double weighty) {
        this.weighty = weighty;
    }

    public void setAnchor(int anchor) {
        this.anchor = anchor;
    }

    public void setFill(int fill) {
        this.fill = fill;
    }

    public void setInsets(Insets insets) {
        this.insets = insets;
    }

    public void setIpadx(int ipadx) {
        this.ipadx = ipadx;
    }

    public void setIpady(int ipady) {
        this.ipady = ipady;
    }
}
