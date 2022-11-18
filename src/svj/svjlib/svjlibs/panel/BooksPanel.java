package svj.svjlib.svjlibs.panel;

import svj.svjlib.WCons;
import svj.svjlib.gui.button.WButton;
import svj.svjlib.gui.panel.WPanel;
import svj.svjlib.gui.table.ATableModel;
import svj.svjlib.obj.BookTitle;
import svj.svjlib.svjlibs.listener.BookSelectionListener;
import svj.svjlib.svjlibs.listener.ExportBookListener;
import svj.svjlib.svjlibs.listener.ExportRemoteBookListener;
import svj.svjlib.svjlibs.obj.Author;
import svj.svjlib.svjlibs.obj.Genre;
import svj.svjlib.svjlibs.table.BookField;
import svj.svjlib.svjlibs.table.BookTablePanel;
import svj.svjlib.tools.DialogTools;
import svj.svjlib.tools.GuiTools;

import javax.swing.*;

import java.awt.*;
import java.util.*;

/**
 * BooksPanel
 * <BR/> - список книг (таблица)
 * <BR/> - аннотация выбранной книги
 * <BR/> - кнопки: Экспорт, Экспорт удаленный
 * <BR/>
 */
public class BooksPanel extends WPanel {

    // Верхняя строка - Имя выбранного автора
    private JLabel topPanel = new JLabel("|");

    // таблица списка книг
    private BookTablePanel bookTablePanel;

    // инфа о выбранной книге
    private final JTextArea bookInfo = new JTextArea();

    /* Список найдненых атворов и их книг*/
    private Map<Author, Collection<BookTitle>> authorList = null;


    public BooksPanel() {

        setLayout(new BorderLayout());

        // делаем метку прозрачной -  чтобы иметь возможность перерисовать ей фон.
        topPanel.setOpaque(true);
        topPanel.setBackground(WCons.BOTTOM_TABS);
        // расположить текст по середине
        topPanel.setHorizontalAlignment(SwingConstants.CENTER);
        add(topPanel, BorderLayout.NORTH);


        bookTablePanel = new BookTablePanel("books", "BookTablePanel");
        //bookTablePanel.setFields(BookField.getFields());
        add(bookTablePanel, BorderLayout.CENTER);


        // добавим акцию выборки книги - отображать внизу информацию о выбранной книге
        JTable table = bookTablePanel.getTable();
        ListSelectionModel selModel = table.getSelectionModel();
        selModel.addListSelectionListener(new BookSelectionListener(bookTablePanel, bookInfo));


        WPanel buttonPanel = new WPanel();

        buttonPanel.setLayout ( new FlowLayout ( FlowLayout.CENTER,10,5) );
        // кнопка Экспорт
        WButton exportButton = GuiTools.createButton ( "Экспорт", null, "export.png" );
        //reloadButton.setActionCommand ( "reload" );
        exportButton.addActionListener ( new ExportBookListener(bookTablePanel));
        buttonPanel.add ( exportButton );

        // кнопка Экспорт-внешний
        WButton exportButton2 = GuiTools.createButton ( "Экспорт-внешний", null, "export.png" );
        //reloadButton.setActionCommand ( "reload" );
        exportButton2.addActionListener ( new ExportRemoteBookListener(bookTablePanel));
        buttonPanel.add ( exportButton2 );

        bookInfo.setText("Поле для аннотаций\n\n");
        bookInfo.setLineWrap(true);
        bookInfo.setWrapStyleWord(true);   // при переносе не разрывать слова
        bookInfo.setBorder ( BorderFactory.createEtchedBorder() );

        Box contents = new Box(BoxLayout.Y_AXIS);
      	//contents.add(new JScrollPane(bookListPanel));
      	contents.add(bookInfo);
      	contents.add(buttonPanel);
        add(contents, BorderLayout.SOUTH);
    }

    // выбран автор
    public void initData(Collection<BookTitle> bookList) {
        if (bookList == null)  {
            DialogTools.showError("Не задано Название", "Ошибка");
            return;
        }


        //topPanel.setText();

        // заносим книги выбранного атвора в таблицу
        ATableModel<BookTitle, BookField> model = bookTablePanel.getTableModel();
        Vector<BookTitle> v = new Vector<>();
        v.addAll(bookList);
        model.setData(v);  // Vector<BookTitle
        model.fireTableDataChanged();

        bookTablePanel.getTable().repaint();
        bookTablePanel.getTable().revalidate();
    }

    // выбран автор
    public void initAuthorData(Object obj) {
        if (obj == null)  {
            DialogTools.showError("Не задан Автор", "Ошибка");
            return;
        }

        if (authorList == null) {
            DialogTools.showError("Не задан authorList", "Ошибка");
        }

        Author author = (Author) obj;
        topPanel.setText(author.getSimple());

        Collection<BookTitle> authorBooks = authorList.get(author);

        // заносим книги выбранного атвора в таблицу
        ATableModel<BookTitle, BookField> model = bookTablePanel.getTableModel();
        Vector<BookTitle> v = new Vector<>();
        v.addAll(authorBooks);
        model.setData(v);  // Vector<BookTitle
        model.fireTableDataChanged();

        bookTablePanel.getTable().repaint();
        bookTablePanel.getTable().revalidate();
    }

    public void setAuthorList(Map<Author, Collection<BookTitle>> list) {
        // получить нвоый обьект данных - Новый список авторов и их книг
        authorList = list;
        // очистить спсиок книг
        ATableModel<BookTitle, BookField> model = bookTablePanel.getTableModel();
        model.clear();
        model.fireTableDataChanged();
    }

    public void initGenreData(Genre genre) {
        throw new RuntimeException("Не реализован");
    }

    public void setHeaderTitle(String text) {
        topPanel.setText(text);
    }

}
