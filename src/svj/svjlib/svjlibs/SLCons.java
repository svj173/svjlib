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

    // Имя директории для конфиг-файлов
    String CONFIG_DIR_NAME = "svjlib";

    // Полный путь до директории для конфиг-файлов
    // todo - ??? - USER_HOME_DIR ведь определяется во вреям рабоыт программы - может не успеть определится.
    // Брать все директории из DirTools ?
    String DIR_PATH = Par.USER_HOME_DIR + File.separator + SLCons.CONFIG_DIR_NAME;

    // Конфиг-файл. Изменять ни имя ни директорию нельзя. Это индивидуальный конфиг для пользователя
    String CONFIG_FILE = SLCons.DIR_PATH + File.separator + SLCons.CONFIG_FILE_NAME;


    // имена конфиг-файлов
    String CONFIG_FILE_NAME = "svjlib_conf.txt";

    // Имя файла с опсианием загруженных библиотек (директории, название...)
    String LIBS_FILE_NAME = "libs.xml";

    // Префикс имени библиотеки. Каждая библиотека имеет свой файл книг - books_(libId).xml
    String BOOKS_FILE_NAME_PREFIX = "books";

    // Разделитель в списке Жанров книги
    String GENRE_SEP = ";";

    // ----------------- Обьекты ------------------------

    SlGuiManager SL_GUI_MANAGER = new SlGuiManager();
    LibsManager LIBS_MANAGER = new LibsManager();
    BooksManager BOOKS_MANAGERS = new BooksManager();
    ConfigManager CONFIG_MANAGERS = new ConfigManager();


}
