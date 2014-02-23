package jp.gr.java_conf.star_diopside.mailmanager.gui.support;

import java.awt.GridBagLayout;

@SuppressWarnings("serial")
public class GridBagLayoutSupport extends GridBagLayout {

    public void setColumnWidths(int[] columnWidths) {
        this.columnWidths = columnWidths;
    }

    public void setRowHeights(int[] rowHeights) {
        this.rowHeights = rowHeights;
    }

    public void setColumnWeights(double[] columnWeights) {
        this.columnWeights = columnWeights;
    }

    public void setRowWeights(double[] rowWeights) {
        this.rowWeights = rowWeights;
    }
}
