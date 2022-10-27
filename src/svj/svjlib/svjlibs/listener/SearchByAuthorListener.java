package svj.svjlib.svjlibs.listener;

import svj.svjlib.Log;
import svj.svjlib.gui.dialog.WidgetsDialog;
import svj.svjlib.gui.widget.StringFieldWidget;
import svj.svjlib.obj.BookTitle;
import svj.svjlib.svjlibs.SLCons;
import svj.svjlib.svjlibs.dialog.BookListDialog;
import svj.svjlib.svjlibs.obj.Author;
import svj.svjlib.tools.DumpTools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 *
 * Поиск - с бегунком
 *
 * Поиск книг - поля
 * - Автор
 * - Название
 * - язык книги
 * - Название серии
 * ...
 * Причем, если текст поиска наичнается с большйо буквы, то ищем как startWith, иначе - contain
 * <BR/>
 */
public class SearchByAuthorListener implements ActionListener {

    private StringFieldWidget authorWidget;

    @Override
    public void actionPerformed(ActionEvent event) {

        Log.l.info("Start Search author");

        authorWidget = new StringFieldWidget("Имя автора", "", false);

        String toolTip = "Если начинается с прописной буквы - то ищется начало, иначе - вхождение";
        authorWidget.setToolTip(toolTip);

        // todo Либо сразу открывает диалог BookListDialog
        // - и там есть и виджет поиска и Экспорт отмеченных.
        // - и тогад лучше сюда же добавить виджет названия книг

        WidgetsDialog dialog = new WidgetsDialog("Поиск книг по автору");
        dialog.addWidget(authorWidget);
        dialog.setTitleWidth(350);
        dialog.setValueWidth(250);
        dialog.pack();

        dialog.showDialog();

        if (dialog.isOK()) {

            // - Из диалога
            String authorStr = authorWidget.getValue();

            boolean byFirst = false;

            // выясняем режим поиска - вхождение или начало
            String firstSymbol = authorStr.substring(0,1);
            String str = firstSymbol.toUpperCase();
            if (str.equals(firstSymbol)) {
                // это большая буква
                byFirst = true;
            }

            Log.l.info("Search author text = '{}'; byFirst = {}", authorStr, byFirst);

            // todo сортировка по алфавиту авторов
            //Map<Author, Collection<BookTitle>> result = new HashMap<>();
            Map<Author, Collection<BookTitle>> result = new TreeMap<>();

            int ic = 0;
            Author author;
            for (BookTitle book : SLCons.BOOKS_MANAGERS.getBooks()) {
                author = findBook(book.getAuthors(), authorStr, byFirst);
                if (author != null) {
                    addBook(result, author, book);
                    // todo  ограничение на кол-во авторов
                    if (result.size() > 50) break;
                }
            }

            Log.l.info("Search author text = '{}'; find size = {}", authorStr, result.size());
            Log.l.info("result = {}", DumpTools.printMap(result, null));

            // открываем диалог со списком найденных книг
            // - список - Автор - кол-во книг
            BookListDialog bookListDialog = new BookListDialog();
            bookListDialog.init(result);
            bookListDialog.showDialog();
            //DialogTools.showMessage("Автор: " + authorStr, DumpTools.printBookTitles(result));
        }
    }

    private void addBook(Map<Author, Collection<BookTitle>> result, Author author, BookTitle book) {
        Collection<BookTitle> bookList = result.get(author);
        if (bookList == null) {
            bookList = new ArrayList<>();
            result.put(author, bookList);
        }
        bookList.add(book);
    }

    // todo здесь лучше Автора сразу приводить к полной строке - ФИО и с ней уже работать - чем анализирвоать возможность пропусков.
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
            /*
            if (author.getLastName() != null) {
                if (byFirst) {
                    b = author.getLastName().startsWith(authorName);
                    if (b) return true;
                } else {
                    b = author.getLastName().contains(authorName);
                    if (b) return true;
                }

            } else {
                if (author.getMiddleName() != null) {
                    if (byFirst) {
                        b = author.getMiddleName().startsWith(authorName);
                        if (b) return true;
                    } else {
                        b = author.getMiddleName().contains(authorName);
                        if (b) return true;
                    }
                } else {
                    if (author.getFirstName() != null) {
                        if (byFirst) {
                            b = author.getFirstName().startsWith(authorName);
                            if (b) return true;
                        } else {
                            b = author.getFirstName().contains(authorName);
                            if (b) return true;
                        }
                    }
                }
            }
            */
        }
        return null;
    }
}
