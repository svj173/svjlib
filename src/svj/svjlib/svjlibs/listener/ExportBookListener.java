package svj.svjlib.svjlibs.listener;

import svj.svjlib.obj.BookTitle;
import svj.svjlib.svjlibs.SLCons;
import svj.svjlib.svjlibs.obj.Author;
import svj.svjlib.svjlibs.table.BookTablePanel;
import svj.svjlib.tools.DialogTools;
import svj.svjlib.tools.DumpTools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;

/**
 * Экспорт выбранных книг из ZIP формата.
 * <BR/>
 */
public class ExportBookListener implements ActionListener {

    private final BookTablePanel bookTablePanel;

    public ExportBookListener(BookTablePanel bookTablePanel) {
        this.bookTablePanel = bookTablePanel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        Collection<BookTitle> books = bookTablePanel.getSelectedItems();

        if (books.isEmpty()) return;

        // Стартова проверка. Только для Удаленной работы - на предмет прописки IP компа, диретории в компе, логин-пароль.
        checkStart();

        // Взять параметр - рабочая директория для Экспорта
        String exportDir = SLCons.DIR_PATH + File.separator + "export";

        String authorDir;
        Collection<String> dirs = new ArrayList<>();
        for (BookTitle book : books) {

            // Создаем путь до диреткории Автора
            authorDir = createAuthDir(exportDir, book);
            dirs.add(authorDir);


            // Прогоняем путь на предмет создания остутсвующих поддиректорий

            // Раззипуем книгу - для удаленной рабоыт этот метод переписывается

            // Сохраняем книгу в директории автора


            // Складываем в  журнал
        }

        //

        //DialogTools.showMessage("Выбраны книги", DumpTools.printBookTitles(books));
        DialogTools.showMessage("Директории", DumpTools.printCollection(dirs));

    }

    private String createAuthDir(String exportDir, BookTitle book) {

        // Взять первого автора
        Author author = getFirstAuthor(book);

        String authorFirstSymbol = "Others";
        String authorFullName = "Unknown";
        if (author != null) {
            // Берем первую букву первого автора
            authorFirstSymbol = getAuthorFirstSymbol(author);
            authorFullName = author.getSimple();
        }

        String authDir = exportDir + File.separator + authorFirstSymbol + File.separator + authorFullName;

        return authDir;
    }

    private Author getFirstAuthor(BookTitle book) {
        if (book.getAuthors().isEmpty())  return null;
        for (Author author: book.getAuthors()) {
            return author;
        }
        return null;
    }

    private String getAuthorFirstSymbol(Author author) {
        String result;

        String name = author.getSimple();
        char ch = name.charAt(0);
        char[] chb = new char[1];
        chb[0] = ch;
        result = new String(chb);

        return result;
    }

    /**
     * Стартова проверка. Только для Удаленной работы - на предмет прописки IP компа, диретории в компе, логин-пароль.
     */
    private void checkStart() {
    }

}
