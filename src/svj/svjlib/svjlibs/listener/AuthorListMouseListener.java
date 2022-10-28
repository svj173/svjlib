package svj.svjlib.svjlibs.listener;

import svj.svjlib.Log;
import svj.svjlib.gui.label.WLabel;
import svj.svjlib.gui.panel.WPanel;
import svj.svjlib.gui.table.TableModelTest;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * <BR/>
 */
public class AuthorListMouseListener implements MouseListener {

    private final WPanel panel;
    private final TableModelTest bookListPanel;

    public AuthorListMouseListener(WPanel panel, TableModelTest bookListPanel) {

        this.panel = panel;
        this.bookListPanel = bookListPanel;
    }

    /*

координаты относительно левого верхнего угла экрана, то это можно сделать через функцию
MouseEvent.getLocationOnScreen().

     */

    // нажал и отпустил
    @Override
    public void mouseClicked(MouseEvent event) {

        Point screenPoint = event.getLocationOnScreen();
        Log.l.info("screenPoint = {}", screenPoint);

        Point point = event.getPoint();
        Log.l.info("point = {}", point);

        Component c;
        //c = panel.getComponentAt(screenPoint);
        //Log.l.info("screenPoint component = {}", c);

        // +++++
        c = panel.getComponentAt(point);
        Log.l.info("point component = {}", c);

        if (c instanceof WLabel) {

            WLabel label = (WLabel) c;
            Object obj = label.getObject();
            //DialogTools.showMessage("Выбран автор", obj);
            // инициализация страницы с книгами  - получить спсико книг для автора
            bookListPanel.init();
        }

        //SwingUtilities.convertPointToScreen();
    }

    @Override
    public void mousePressed(MouseEvent event) {

    }

    @Override
    public void mouseReleased(MouseEvent event) {

    }

    @Override
    public void mouseEntered(MouseEvent event) {

    }

    @Override
    public void mouseExited(MouseEvent event) {

    }
}
