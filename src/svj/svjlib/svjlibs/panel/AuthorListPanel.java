package svj.svjlib.svjlibs.panel;

import svj.svjlib.Log;
import svj.svjlib.gui.button.WButton;
import svj.svjlib.gui.label.WLabel;
import svj.svjlib.gui.listener.ReloadActionListener;
import svj.svjlib.gui.panel.ReloadPanel;
import svj.svjlib.gui.panel.WPanel;
import svj.svjlib.gui.widget.StringFieldWidget;
import svj.svjlib.listener.WKeyAdapter;
import svj.svjlib.obj.BookTitle;
import svj.svjlib.svjlibs.SLCons;
import svj.svjlib.svjlibs.listener.AuthorListMouseListener;
import svj.svjlib.svjlibs.obj.Author;
import svj.svjlib.tools.*;

import javax.swing.*;

import java.awt.*;
import java.util.*;

/**
 * Поиск только по авторам
 * Состав: Две горизонт панели
 * 1) две верт панели
 * - Фитры (Автор, RU) + Обнвоить
 * - Список авторов + кол-во книг
 * 2) BooksPanel
 *
 * <BR/>
 */
public class AuthorListPanel extends ReloadPanel {

    // список найденных авторов и кол-во их книг - левая панель
    private  WPanel authorListPanel;
    // список книг выбранного автора
    private  final BooksPanel bookListPanel = new BooksPanel();
    // аннотация выбраннйо книги
    //private  JLabel bookInfoPanel;

    private StringFieldWidget authorWidget;
    private StringFieldWidget authorLangWidget;


    public AuthorListPanel() {

        String name = "AuthorsPanel";
        setName ( name );
        setId ( name );

        setLayout ( new BorderLayout(5,5) );

        // 1) Левая панель - фильтры и список авторов + кол-во книг
        WPanel leftPanel = createLeftPanel();
        add(leftPanel, BorderLayout.WEST);


        // 2) Правая панель - список книг выбранного атвора
        add(bookListPanel, BorderLayout.CENTER);
    }

    private WPanel createLeftPanel() {

        WPanel result = new WPanel();
        result.setLayout(new BorderLayout());

        // --------------- 1) фильтры: Автор, Язык, ------------------
        JPanel authorFilterPanel = new JPanel();
        result.add(authorFilterPanel, BorderLayout.NORTH);

        authorFilterPanel.setLayout ( new FlowLayout ( FlowLayout.LEFT,0,0) );
        authorFilterPanel.setBorder ( BorderFactory.createEtchedBorder() );

        authorWidget = new StringFieldWidget("Имя автора", "", false);
        String toolTip = "Если начинается с прописной буквы - то ищется подобное начало, иначе - вхождение";
        authorWidget.setToolTip(toolTip);
        authorWidget.setValueWidth(250);
        authorFilterPanel.add(authorWidget);

        authorLangWidget = new StringFieldWidget("Язык книги", "ru", false);
        toolTip = "ru - русский, en - английский, и пр. Если отсутствует - любой язык";
        authorLangWidget.setToolTip(toolTip);
        authorLangWidget.setValueWidth(25);
        authorFilterPanel.add(authorLangWidget);

        // кнопка Обновить
        WButton reloadButton = GuiTools.createButton ( "Обновить", null, "reload.png" );
        reloadButton.setActionCommand ( "reload" );
        reloadButton.addActionListener ( new ReloadActionListener(this));
        // привзяка к Enter
        //reloadButton.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "pressed");
        //reloadButton.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
        reloadButton.addKeyListener(new WKeyAdapter(this));

        authorFilterPanel.add ( reloadButton );

        // список авторов
        authorListPanel = createAuthorListPanel();
        result.add(new JScrollPane(authorListPanel), BorderLayout.CENTER);

        return result;
    }

    @Override
    public void reload() {

        //String msg = "author: '" + authorWidget.getValue() + "'; lang = '" + authorLangWidget.getValue() + "'";
        //DialogTools.showMessage("Reload", msg);

        // - Из диалога
        String lang = authorLangWidget.getValue();
        String authorStr = authorWidget.getValue();
        if (StringTools.isEmpty(authorStr)) {
            DialogTools.showError("Автор должен быть задан", "Ошибка");
            return;
        }

        boolean byFirst = false;

        // выясняем режим поиска - вхождение или начало
        String firstSymbol = authorStr.substring(0,1);
        String str = firstSymbol.toUpperCase();
        if (str.equals(firstSymbol)) {
            // это большая буква
            byFirst = true;
        }

        Log.l.info("Search author text = '{}'; byFirst = {}", authorStr, byFirst);

        // TreeMap - сортировка по алфавиту авторов
        Map<Author, Collection<BookTitle>> result = new TreeMap<>();

        Author author;
        for (BookTitle book : SLCons.BOOKS_MANAGERS.getBooks()) {
            author = findBook(book.getAuthors(), authorStr, byFirst);
            if (author != null && checkLang(lang, book)) {
                addBook(result, author, book);
                // todo  ограничение на кол-во авторов
                if (result.size() > 50) break;
            }
        }

        // Обновляем список авторов
        initAuthors(result);

        // Очищаем список книг
    }

    private boolean checkLang(String lang, BookTitle book) {
        if (StringTools.isEmpty(lang)) return true;

        return Utils.compareToWithNull(lang, book.getLang()) == 0;
    }

    private void addBook(Map<Author, Collection<BookTitle>> result, Author author, BookTitle book) {
        Collection<BookTitle> bookList = result.get(author);
        if (bookList == null) {
            bookList = new ArrayList<>();
            result.put(author, bookList);
        }
        bookList.add(book);
    }

    // здесь лучше Автора сразу приводить к полной строке - ФИО и с ней уже работать - чем
    // анализирвоать возможность пропусков.
    private Author findBook(Collection<Author> authors, String authorName, boolean byFirst) {
        boolean b;
        String str;
        for (Author author : authors) {
            str = author.getSimple();
            if (byFirst) {
                b = str.startsWith(authorName);
                if (b) return author;
            } else {
                b = str.contains(authorName);
                if (b) return author;
            }
        }
        return null;
    }

    private WPanel createAuthorListPanel() {
        WPanel panel = new WPanel();

        panel.setLayout ( new GridLayout( 0, 2, 5, 5 ) );

        AuthorListMouseListener action = new AuthorListMouseListener(panel, bookListPanel);
        panel.addMouseListener(action);

        return panel;
    }

    public void initAuthors(Map<Author, Collection<BookTitle>> initObject)  {

        // левая панель - список авторов (simpleName) и кол-во книг у них

        // - очистить старые данные по авторам
        authorListPanel.removeAll();

        if (initObject == null) return;

        WLabel label;
        /*
        for (Map.Entry<Author, Collection<BookTitle>> entry : initObject.entrySet()) {
            label = new WLabel(entry.getKey().getSimple(), entry.getKey());
            authorListPanel.add(label);
            label = new WLabel(Integer.toString(entry.getValue().size()), entry.getKey());
            authorListPanel.add(label);
        }
        */
        Collection<BookTitle> books;
        // по ключу - чтобы в резалте авторы расположились по алфавиту
        for (Author author : initObject.keySet()) {
            books = initObject.get(author);
            // имя автора
            label = new WLabel(author.getSimple(), author);
            authorListPanel.add(label);
            // кол-во его книг
            label = new WLabel(Integer.toString(books.size()), author);
            authorListPanel.add(label);
        }

        bookListPanel.setAuthorList(initObject);

        authorListPanel.repaint();
        authorListPanel.revalidate();
    }

}
