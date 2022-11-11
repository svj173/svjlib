package svj.svjlib.svjlibs.listener;

import svj.svjlib.Log;
import svj.svjlib.gui.label.WLabel;
import svj.svjlib.gui.panel.WPanel;
import svj.svjlib.svjlibs.obj.Author;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Выводит спсиок  книг указанного автора - в списке найденных результатов - в виде таблицы.
 * <BR/>
 */
public class AuthorListMouseListener implements MouseListener {

    private final WPanel panel;
    private final WPanel bookListPanel;

    public AuthorListMouseListener(WPanel panel, WPanel bookListPanel) {

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

        //Point screenPoint = event.getLocationOnScreen();
        //Log.l.info("screenPoint = {}", screenPoint);

        Point point = event.getPoint();
        //Log.l.info("point = {}", point);

        Component c;
        //c = panel.getComponentAt(screenPoint);
        //Log.l.info("screenPoint component = {}", c);

        // +++++
        c = panel.getComponentAt(point);
        Log.l.info("point component = {}", c);

        if (c instanceof WLabel) {

            WLabel label = (WLabel) c;
            Author author = (Author) label.getObject();
            Log.l.info("select author = {}", author);
            //DialogTools.showMessage("Выбран автор", author);
            // инициализация страницы с книгами  - получить спсико книг для автора
            bookListPanel.initAuthorData(author);
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
