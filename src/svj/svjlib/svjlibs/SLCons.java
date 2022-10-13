package svj.svjlib.svjlibs;

import svj.svjlib.svjlibs.manager.BooksManager;
import svj.svjlib.svjlibs.manager.LibsManager;

/**
 * <BR/>
 */
public interface SLCons {

    // ----------------- Обьекты ------------------------

    LibsManager LIBS_MANAGER = new LibsManager();
    BooksManager BOOKS_MANAGERS = new BooksManager();

    // ---------------- Параметры ---------------

    // директория для конфиг-файлов
    String LIBS_DIR_NAME = "svjlib";

    // имена конфиг-файлов
    String LIBS_FILE_NAME = "libs.xml";
    // каждая библиотека имеет свой файл книг - books_(libId).xml - ?
    String BOOKS_FILE_NAME_PREFIX = "books";

}
