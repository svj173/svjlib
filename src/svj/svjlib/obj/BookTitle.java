package svj.svjlib.obj;

import javax.swing.*;

/**
 * Полученыне данные - BookTitle
 * - метсоположение - fb2-27-439041-446105-RUSSIAN.zip / 442687.fb2.zip
 * - Навзание книги
 * - Автор
 * - Серия
 * - Индекс в серии
 * - Жанр
 * - Аннотация
 * - Титл картинка - ? - в уменьшенном виде
 * - Размер - ? - как из зип получить реальный размер без распаковки?
 * <BR/>
 */
public class BookTitle {

    // путь до директории библиотеки
    private String archiveDirName;

    // fb2-27-439041-446105-RUSSIAN.zip
    private String archiveName;

    // 442687.fb2.zip
    private String fileName;

    private String bookTitle;
    private String author;   // составной - ФИО, а аткже соавторы

    private String annotation;

    private String serialName;   // см у Брауна
    private int serialIndex;

    private String janr;   // может быть несколкьо? - см у Брауна

    // титл книги елси есть
    private Icon titleIcon;

    // если есть аткая возможность - приблизительный пересчет из ZIP размера.
    private long bookSize;


}
