package svj.svjlib.svjlibs.dialog;

import svj.svjlib.Par;
import svj.svjlib.exc.WEditException;
import svj.svjlib.gui.dialog.WDialog;
import svj.svjlib.obj.BookTitle;

import java.util.*;

/**
 * <BR/>
 */
public class BookListDialog extends WDialog<Collection<BookTitle>, Void> {

    public BookListDialog() {
        super(Par.GM.getFrame(), "Книги (не более 100)");
    }

    @Override
    public void init(Collection<BookTitle> initObject) throws WEditException {

        // todo Формируем таблицу с галочками в первой позиции и с возможностью натсройки полей
        // - убирать лишние, переставлять местами, сортирвоки по полям.

        // todo Кнопки - Выход, Экспорт отмеченных
        // - по умочланию экспортирует в рабочую директорию Редактора, в поддиректорию books - в алфавитном порядке

    }

    // здесь это не нужно
    @Override
    public Void getResult() throws WEditException {
        return null;
    }
}
