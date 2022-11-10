package svj.svjlib.gui.listener;

import svj.svjlib.gui.panel.ReloadPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Акция - обновляет ReloadPanel, котоаря сама читает свои фильтры, получает даныне и обнволяет свои панельки
 * <BR/>
 */
public class ReloadActionListener implements ActionListener {

    private final ReloadPanel reloadPanel;

    public ReloadActionListener(ReloadPanel reloadPanel) {
        this.reloadPanel = reloadPanel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        reloadPanel.reload();
    }
}
