package svj.svjlib.svjlibs;

import svj.svjlib.Par;
import svj.svjlib.handler.ConfigManager;
import svj.svjlib.svjlibs.manager.BooksManager;
import svj.svjlib.svjlibs.manager.LibsManager;
import svj.svjlib.svjlibs.manager.SlGuiManager;

import java.io.File;

/**
 * <BR/>
 */
public interface SLCons {

    // ---------------- Параметры ---------------

    // директория для конфиг-файлов
    String LIBS_DIR_NAME = "svjlib";

    String DIR_PATH = Par.USER_HOME_DIR + File.separator + SLCons.LIBS_DIR_NAME;
    String CONFIG_FILE = SLCons.DIR_PATH + File.separator + SLCons.CONFIG_FILE_NAME;


    // имена конфиг-файлов
    String CONFIG_FILE_NAME = "svjlib_conf.txt";
    String LIBS_FILE_NAME = "libs.xml";
    // каждая библиотека имеет свой файл книг - books_(libId).xml - ?
    String BOOKS_FILE_NAME_PREFIX = "books";

    String GENRE_SEP = ";";

    // ----------------- Обьекты ------------------------

    SlGuiManager SL_GUI_MANAGER = new SlGuiManager();
    LibsManager LIBS_MANAGER = new LibsManager();
    BooksManager BOOKS_MANAGERS = new BooksManager();
    ConfigManager CONFIG_MANAGERS = new ConfigManager(SLCons.CONFIG_FILE);


}
