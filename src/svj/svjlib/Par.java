package svj.svjlib;

import svj.svjlib.obj.BookTitles;

import java.awt.*;

/**
 * <BR/>
 */
public class Par {

    /* Размер экрана пользователя */
    public static Dimension SCREEN_SIZE;

    public static GeneralManager GM;

    /** Рабочая директория Редактора (т.е. где он находится). */
    public static String MODULE_HOME    = "";

    /** Системный Логин пользователя. Если нет - пустая строка (не NULL).     */
    public static String USER_LOGIN     = "";

    /* Домашняя директория пользователя. */
    public static String USER_HOME_DIR  = "";

    /* Вид кодировки конфиг файлов Редактора*/
    public static String CODE_CONFIG    = "UTF-8";

    /* Вид кодировки книги*/
    public static String CODE_BOOK      = "UTF-8";

    /* Размеры иконок в рабочих кнопках. */
    public static int BUTTONS_ICON_SIZE = 24;

    public static BookTitles BOOKS = null;

}
