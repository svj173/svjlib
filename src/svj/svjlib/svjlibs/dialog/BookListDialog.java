package svj.svjlib.svjlibs.dialog;

import svj.svjlib.Par;
import svj.svjlib.exc.WEditException;
import svj.svjlib.gui.dialog.WDialog;
import svj.svjlib.gui.panel.WPanel;
import svj.svjlib.obj.BookTitle;
import svj.svjlib.svjlibs.obj.Author;

import javax.swing.*;

import java.awt.*;
import java.util.*;

/**
 * <BR/>
 */
public class BookListDialog extends WDialog<Map<Author, Collection<BookTitle>>, Void> {

    private final WPanel authorListPanel;
    private final WPanel bookListPanel;
    private final WPanel bookInfoPanel;

    public BookListDialog() {
        super(Par.GM.getFrame(), "Книги (не более 50 авторов)");

        authorListPanel = createAuthorListPanel();

        addToWest(authorListPanel);

        WPanel centerPanel = new WPanel();
        centerPanel.setLayout(new BorderLayout());

        bookListPanel = createBookListPanel();
        bookInfoPanel = createBookInfoPanel();

        centerPanel.add(BorderLayout.CENTER, bookListPanel);
        centerPanel.add(BorderLayout.SOUTH, bookInfoPanel);

    }

    private WPanel createBookListPanel() {
        WPanel panel = new WPanel();

        return panel;
    }

    private WPanel createBookInfoPanel() {
        WPanel panel = new WPanel();

        return panel;
    }

    @Override
    public void init(Map<Author, Collection<BookTitle>> initObject)  {

        // левая панель - список авторов (simpleName) и кол-во книг у них
        if (initObject == null) return;

        for (Map.Entry<Author, Collection<BookTitle>> entry : initObject.entrySet()) {
            authorListPanel.add(new JLabel(entry.getKey().getSimple()));
            authorListPanel.add(new JLabel(Integer.toString(entry.getValue().size())));
        }

        // - панель в центре вверху - список книг выраного автора
        // todo Формируем таблицу с галочками в первой позиции и с возможностью натсройки полей
        // - убирать лишние, переставлять местами, сортирвоки по полям.

        // todo Кнопки - Выход, Экспорт отмеченных
        // - по умочланию экспортирует в рабочую директорию Редактора, в поддиректорию books - в алфавитном порядке

        // панель в центре внизу - подробно про автора - анотация,

    }

    private WPanel createAuthorListPanel() {
        WPanel panel = new WPanel();

        panel.setLayout ( new GridLayout( 0, 2, 5, 5 ) );

        // Автор (simpleName) - кол-во книг

        /*
        panel.add(new JLabel("Всего библиотек"));
        panel.add(new JLabel(Integer.toString(libSize)));
        panel.add(new JLabel("Всего файлов"));
        panel.add(new JLabel(Integer.toString(bookSize)));
        */

        return panel;
    }


    // здесь это не нужно
    @Override
    public Void getResult() throws WEditException {
        return null;
    }
}
