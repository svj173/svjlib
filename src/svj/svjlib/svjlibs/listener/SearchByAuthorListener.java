package svj.svjlib.svjlibs.listener;

import svj.svjlib.gui.dialog.WidgetsDialog;
import svj.svjlib.gui.widget.StringFieldWidget;
import svj.svjlib.obj.BookTitle;
import svj.svjlib.svjlibs.SLCons;
import svj.svjlib.svjlibs.obj.Author;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * <BR/>
 */
public class SearchByAuthorListener implements ActionListener {

    private StringFieldWidget authorWidget;

    @Override
    public void actionPerformed(ActionEvent event) {

        authorWidget = new StringFieldWidget("Имя автора", "Если начинается с прописной буквы - то ищется начало", false);

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
            String author = authorWidget.getValue();

            boolean byFirst = false;

            // выясняем режим поиска - вхождение или начало
            String firstSymbol = author.substring(0,1);
            String str = firstSymbol.toUpperCase();
            if (str.equals(firstSymbol)) {
                // это большая буква
                byFirst = true;
            }

            Collection<BookTitle> result = new ArrayList<>();

            int ic = 0;
            for (BookTitle book : SLCons.BOOKS_MANAGERS.getBooks()) {
                if (findBook(book.getAuthors(), author, byFirst)) {
                    result.add(book);
                    ic++;
                    if (ic > 100) break;
                }
            }

            // открываем диалог со списком найденных книг
        }
    }

    private boolean findBook(Collection<Author> authors, String authorName, boolean byFirst) {
        boolean b;
        for (Author author : authors) {
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
        }
        return false;
    }
}
