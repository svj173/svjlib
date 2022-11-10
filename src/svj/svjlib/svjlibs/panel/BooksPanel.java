package svj.svjlib.svjlibs.panel;

import svj.svjlib.gui.button.WButton;
import svj.svjlib.gui.panel.WPanel;
import svj.svjlib.obj.BookTitle;
import svj.svjlib.svjlibs.obj.Author;
import svj.svjlib.svjlibs.table.BookTablePanel;
import svj.svjlib.tools.GuiTools;

import javax.swing.*;

import java.awt.*;
import java.util.*;

/**
 * 3) BooksPanel
 * - список книг (таблица)
 * - аннотация выбранной книги
 * - кнпоки: Экспорт, Экспор удаленный
 * <BR/>
 */
public class BooksPanel extends WPanel {

    // таблица спсика книг
    private BookTablePanel bookTablePanel;

    // инфа о выбраннйо книге
    private final JTextArea bookInfo = new JTextArea();

    /* Список найдненых атворов и их книг*/
    private Map<Author, Collection<BookTitle>> authorList = null;


    public BooksPanel() {

        setLayout(new BorderLayout());

        bookTablePanel = new BookTablePanel("books", "BookTablePanel");
        add(bookTablePanel, BorderLayout.CENTER);


        WPanel buttonPanel = new WPanel();

        buttonPanel.setLayout ( new FlowLayout ( FlowLayout.LEFT,0,0) );
        // кнопка Экспорт
        WButton exportButton = GuiTools.createButton ( "Экспорт", null, "export.png" );
        //reloadButton.setActionCommand ( "reload" );
        //reloadButton.addActionListener ( new ReloadActionListener(this));
        buttonPanel.add ( exportButton );

        // кнопка Экспорт-внешний
        WButton exportButton2 = GuiTools.createButton ( "Экспорт-внешний", null, "export.png" );
        //reloadButton.setActionCommand ( "reload" );
        //reloadButton.addActionListener ( new ReloadActionListener(this));
        buttonPanel.add ( exportButton2 );

        Box contents = new Box(BoxLayout.Y_AXIS);
      	//contents.add(new JScrollPane(bookListPanel));
      	contents.add(bookInfo);
      	contents.add(buttonPanel);
        add(contents, BorderLayout.SOUTH);
    }

    public void initData(Object obj) {
        if (obj == null)  return;

        //ATableModel<BookTitle, BookField> model = bookTablePanel.getTableModel();
        //model.setData();  // Vector<BookTitle
    }

    public void setAuthorList(Map<Author, Collection<BookTitle>> list) {
        // получить нвоый обьект данных -- ???
        authorList = list;
    }
}
