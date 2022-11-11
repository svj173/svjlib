package svj.svjlib.svjlibs.panel;

import svj.svjlib.Log;
import svj.svjlib.gui.button.WButton;
import svj.svjlib.gui.listener.ReloadActionListener;
import svj.svjlib.gui.panel.ReloadPanel;
import svj.svjlib.gui.panel.WPanel;
import svj.svjlib.gui.widget.StringFieldWidget;
import svj.svjlib.listener.WKeyAdapter;
import svj.svjlib.obj.BookTitle;
import svj.svjlib.svjlibs.SLCons;
import svj.svjlib.tools.*;

import javax.swing.*;

import java.awt.*;
import java.util.*;

/**
 * Поиск только по Названиям книг
 * Состав: Две горизонт панели
 * 1) две верт панели
 * - Фитры (Название, RU) + Обнвоить
 * - Список авторов + кол-во книг
 * 2) BooksPanel
 *
 * <BR/>
 */
public class BookNamesListPanel extends ReloadPanel {

    // сиксок найденных авторов и кол-во их книг - левай панель
    //private  WPanel authorListPanel;
    // список книг выбранного автора
    private  final BooksPanel bookListPanel = new BooksPanel();
    // аннотация выбраннйо книги
    //private  JLabel bookInfoPanel;

    private StringFieldWidget bookNameWidget;
    private StringFieldWidget bookLangWidget;


    public BookNamesListPanel() {

        String name = "BookNamesPanel";
        setName ( name );
        setId ( name );

        setLayout ( new BorderLayout(5,5) );

        // 1) Верхняя панель - фильтры
        WPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);


        // 2) Нижняя панель - список найденных книг
        add(bookListPanel, BorderLayout.CENTER);
    }

    private WPanel createTopPanel() {

        WPanel result = new WPanel();
        result.setLayout(new BorderLayout());

        // --------------- 1) фильтры: Автор, Язык, ------------------
        JPanel authorFilterPanel = new JPanel();
        result.add(authorFilterPanel, BorderLayout.NORTH);

        authorFilterPanel.setLayout ( new FlowLayout ( FlowLayout.LEFT,0,0) );
        authorFilterPanel.setBorder ( BorderFactory.createEtchedBorder() );

        bookNameWidget = new StringFieldWidget("Название", "", false);
        String toolTip = "Если начинается с прописной буквы - то ищется подобное начало, иначе - вхождение";
        bookNameWidget.setToolTip(toolTip);
        bookNameWidget.setValueWidth(250);
        authorFilterPanel.add(bookNameWidget);

        bookLangWidget = new StringFieldWidget("Язык книги", "ru", false);
        toolTip = "ru - русский, en - английский, и пр. Если отсутствует - любой язык";
        bookLangWidget.setToolTip(toolTip);
        bookLangWidget.setValueWidth(25);
        authorFilterPanel.add(bookLangWidget);

        // кнопка Обновить
        WButton reloadButton = GuiTools.createButton ( "Обновить", null, "reload.png" );
        reloadButton.setActionCommand ( "reload" );
        reloadButton.addActionListener ( new ReloadActionListener(this));
        // привзяка к Enter
        //reloadButton.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "pressed");
        //reloadButton.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
        reloadButton.addKeyListener(new WKeyAdapter(this));

        authorFilterPanel.add ( reloadButton );


        return result;
    }

    @Override
    public void reload() {

        //String msg = "author: '" + authorWidget.getValue() + "'; lang = '" + authorLangWidget.getValue() + "'";
        //DialogTools.showMessage("Reload", msg);

        // - Из диалога
        String lang = bookLangWidget.getValue();
        String authorStr = bookNameWidget.getValue();
        if (StringTools.isEmpty(authorStr)) {
            DialogTools.showError("Название книги должно быть задано", "Ошибка");
            return;
        }

        try {
            boolean byFirst = false;

            // выясняем режим поиска - вхождение или начало
            String firstSymbol = authorStr.substring(0,1);
            String str = firstSymbol.toUpperCase();
            if (str.equals(firstSymbol)) {
                // это большая буква
                byFirst = true;
            }

            Log.l.info("Search author text = '{}'; byFirst = {}", authorStr, byFirst);

            // TreeMap - сортировка по алфавиту названий
            Collection<BookTitle> result = new TreeSet<>();

            for (BookTitle book : SLCons.BOOKS_MANAGERS.getBooks()) {
                if (findBook(book.getBookTitle(), authorStr, byFirst) && checkLang(lang, book)) {
                    result.add(book);
                    //  ограничение на кол-во
                    if (result.size() > 50) break;
                }
            }

            // Обновляем список книг
            initBooks(result);

        } catch (Exception e) {
            DialogTools.showError(e.getMessage(), "Ошибка");
        }
    }

    private boolean checkLang(String lang, BookTitle book) {
        if (StringTools.isEmpty(lang)) return true;

        return Utils.compareToWithNull(lang, book.getLang()) == 0;
    }

    // здесь лучше Автора сразу приводить к полной строке - ФИО и с ней уже работать - чем
    // анализирвоать возможность пропусков.
    private boolean findBook(String bookTitle, String authorName, boolean byFirst) {
        boolean b;
        if (bookTitle == null) return false;

        if (byFirst) {
            b = bookTitle.startsWith(authorName);
            if (b) return true;
        } else {
            b = bookTitle.contains(authorName);
            if (b) return true;
        }
        return false;
    }

    public void initBooks(Collection<BookTitle> initObject)  {

        // левая панель - список авторов (simpleName) и кол-во книг у них

        if (initObject == null) return;

        bookListPanel.initData(initObject);

    }

}
