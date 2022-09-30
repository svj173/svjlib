package svj.svjlib.obj;

import java.util.*;

/**
 * 3) Полученыне данные - BookTitle
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
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 21.09.22 19:10
 */
public class BookTitles {

    private final Collection<BookTitle> books = new ArrayList<>();

    public void addBook(BookTitle book) {
        books.add(book);
    }

    public Collection<BookTitle> getBooks() {
        return books;
    }
}
