package svj.svjlib.svjlibs.listener;

import svj.svjlib.Log;
import svj.svjlib.gui.label.WLabel;
import svj.svjlib.gui.panel.WPanel;
import svj.svjlib.obj.BookTitle;
import svj.svjlib.svjlibs.panel.BooksPanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

/**
 * Выводит спсиок  книг указанного автора - в списке найденных результатов - в виде таблицы.
 * <BR/>
 */
public class GenreListMouseListener implements MouseListener {

    private final WPanel panel;
    private final BooksPanel bookListPanel;

    public GenreListMouseListener(WPanel panel, BooksPanel bookListPanel) {

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
            Collection<BookTitle> bookList = (Collection<BookTitle>) label.getObject();
            //Log.l.info("select genre = {}", genre);
            //DialogTools.showMessage("Выбран автор", author);
            // инициализация страницы с книгами  - получить спсико книг для автора
            bookListPanel.initData(bookList);
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
