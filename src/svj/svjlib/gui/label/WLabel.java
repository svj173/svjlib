package svj.svjlib.gui.label;

import javax.swing.*;

/**
 * <BR/>
 */
public class WLabel extends JLabel {

    private Object object;

    public WLabel(String text, Object object) {
        super(text);
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
