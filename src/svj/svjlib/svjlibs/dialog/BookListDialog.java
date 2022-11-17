package svj.svjlib.svjlibs.dialog;

import svj.svjlib.Par;
import svj.svjlib.exc.WEditException;
import svj.svjlib.gui.dialog.WDialog;
import svj.svjlib.gui.label.WLabel;
import svj.svjlib.gui.panel.WPanel;
import svj.svjlib.gui.table.TableModelTest;
import svj.svjlib.obj.BookTitle;
import svj.svjlib.svjlibs.obj.Author;

import javax.swing.*;

import java.awt.*;
import java.util.*;

/**
 * <BR/>
 */
public class BookListDialog extends WDialog<Map<Author, Collection<BookTitle>>, Void> {

    // сиксок найденных авторов и кол-во их книг - левай панель
    private final WPanel authorListPanel;
    // список книг выбранного автора
    private final TableModelTest bookListPanel;
    // аннотация выбраннйо книги
    private final JTextArea bookInfoPanel;

    public BookListDialog() {
        super(Par.GM.getFrame(), "Книги (не более 50 авторов)");

        // в первую очередь
        bookInfoPanel = new JTextArea();
        bookListPanel = new TableModelTest(bookInfoPanel);

        authorListPanel = createAuthorListPanel();

        addToWest(new JScrollPane(authorListPanel));

        //WPanel centerPanel = new WPanel();
        //centerPanel.setLayout(new BorderLayout());


        Box contents = new Box(BoxLayout.Y_AXIS);
      	//contents.add(new JScrollPane(bookListPanel));
      	contents.add(bookListPanel);
      	contents.add(new JScrollPane(bookInfoPanel));



        //centerPanel.add(BorderLayout.CENTER, contents);
        //centerPanel.add(BorderLayout.SOUTH, bookInfoPanel);

        //addToCenter(new JScrollPane(authorListPanel));
        addToCenter(contents);


    }


    private JLabel createBookInfoPanel() {
        JLabel panel = new JLabel();

        return panel;
    }

    @Override
    public void init(Map<Author, Collection<BookTitle>> initObject)  {

        // левая панель - список авторов (simpleName) и кол-во книг у них
        if (initObject == null) return;

        WLabel label;
        for (Map.Entry<Author, Collection<BookTitle>> entry : initObject.entrySet()) {
            label = new WLabel(entry.getKey().getSimple(), entry.getKey());
            authorListPanel.add(label);
            label = new WLabel(Integer.toString(entry.getValue().size()), entry.getKey());
            authorListPanel.add(label);
        }
        bookListPanel.setAuthorList(initObject);

        // - панель в центре вверху - список книг выраного автора
        // todo Формируем таблицу с галочками в первой позиции и с возможностью натсройки полей
        // - убирать лишние, переставлять местами, сортирвоки по полям.

        // todo Кнопки - Выход, Экспорт отмеченных
        // - по умочланию экспортирует в рабочую директорию Редактора, в поддиректорию books - в алфавитном порядке

        // панель в центре внизу - подробно про автора - анотация,

        // Формирование интерфейса

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

        //AuthorListMouseListener action = new AuthorListMouseListener(panel, bookListPanel);
        //panel.addMouseListener(action);


        return panel;
    }


    // здесь это не нужно
    @Override
    public Void getResult() throws WEditException {
        return null;
    }
}
