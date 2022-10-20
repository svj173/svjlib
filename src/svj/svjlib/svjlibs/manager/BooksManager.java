package svj.svjlib.svjlibs.manager;

import svj.svjlib.Par;
import svj.svjlib.WCons;
import svj.svjlib.exc.WEditException;
import svj.svjlib.obj.BookTitle;
import svj.svjlib.svjlibs.SLCons;
import svj.svjlib.svjlibs.SLPar;
import svj.svjlib.svjlibs.obj.Author;
import svj.svjlib.svjlibs.obj.Genre;
import svj.svjlib.svjlibs.obj.GlobalGenre;
import svj.svjlib.svjlibs.stax.BooksTitleStaxParser;
import svj.svjlib.tools.Convert;
import svj.svjlib.tools.FileTools;

import java.io.File;
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
public class BooksManager extends XmlHandler{

    private final Collection<BookTitle> books = new ArrayList<>();
    // мапы по жанрам
    private final Map<GlobalGenre,Map<Genre, Collection<BookTitle>>> bookMap = new HashMap<>();

    // массив не найденных жанров
    private final Collection<String> wrongGenre = new ArrayList<>();


    public void addBook(BookTitle book) {
        if (book != null) {
            books.add(book);
        }
    }

    public void addBooks(Collection<BookTitle> bookList) {
        if (bookList != null) {
            books.addAll(bookList);
        }
    }

    public Collection<BookTitle> getBooks() {
        return books;
    }

    /**
     * Сохранить изменения в конфиг-файл - libs.xml
     * @param bookList   Инфа о книгах библиотеки
     * @param libId      ИД библиотеки
     */
    public void saveFile(Collection<BookTitle> bookList, long libId) throws Exception {
        // Сформировать полный путь до файла
        //String fileName = SLPar.CONF_DIR + File.separator + SLCons.BOOKS_FILE_NAME_PREFIX +
        //        "_" + libId + ".xml";
        String fileName = getBookFileName(libId);
        //Log.file.info("Books file name = {}; libId = {}", fileName, libId);

        // Если нет промежуточных директорий - создать
        FileTools.createFolder(new File(SLPar.CONF_DIR));

        // Сохранить Map<Integer, LibInfo> libs в файле как XML.
        // - если был такой файл - перезаписать
        String text = getLibsAsXml(bookList);
        FileTools.save(fileName, text);
    }

    private String getLibsAsXml(Collection<BookTitle> bookList) {
        StringBuilder sb = new StringBuilder(512);

        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<books>\n");

        String prefixSp = "    ";

        for (BookTitle bookInfo : bookList) {
            sb.append("  <book>\n");

            sb.append(setTag(prefixSp, "size", bookInfo.getBookSize()));
            sb.append(setTag(prefixSp, "lang", bookInfo.getLang()));
            sb.append(setTag(prefixSp, "serialIndex", bookInfo.getSerialIndex()));
            sb.append(setTag(prefixSp, "serialName", bookInfo.getSerialName()));
            sb.append(setTag(prefixSp, "libId", bookInfo.getLibId()));
            sb.append(setTag(prefixSp, "annotation", bookInfo.getAnnotation()));
            sb.append(setTag(prefixSp, "archiveName", bookInfo.getArchiveName()));
            sb.append(setTag(prefixSp, "bookTitle", bookInfo.getBookTitle()));
            sb.append(setTag(prefixSp, "fileName", bookInfo.getFileName()));



            // авторы
            for (Author author : bookInfo.getAuthors()) {
                sb.append("    ");
                sb.append(author.getAsXml());
            }

            // жанры
            sb.append("    <genre>");
            sb.append(Convert.collectionToStr(bookInfo.getGenres(), SLCons.GENRE_SEP));
            sb.append("</genre>\n");

            sb.append("  </book>\n");

        }

        sb.append("</books>\n");

        return sb.toString();
    }

    private String setTag(String prefixSp, String tagName, String tagValue) {
        if (tagValue == null)  return WCons.SP;

        return prefixSp + "<" + tagName + ">" + tagValue + "</" + tagName + ">\n";
    }

    private String setTag(String prefixSp, String tagName, long tagValue) {
        return prefixSp + "<" + tagName + ">" + tagValue + "</" + tagName + ">\n";
    }

    private String setTag(String prefixSp, String tagName, int tagValue) {
        return prefixSp + "<" + tagName + ">" + tagValue + "</" + tagName + ">\n";
    }

    public int loadBooksInfo(Long libId) throws WEditException {
        // создаем имя полное файла
        String fileName = getBookFileName(libId);

        // парсим файл
        BooksTitleStaxParser parser = new BooksTitleStaxParser();
        Collection<BookTitle> booksList = parser.read(fileName, WCons.CODE_PAGE);

        books.addAll(booksList);

        return booksList.size();

    }

    public String getBookFileName(Long libId) {
        return Par.USER_HOME_DIR + File.separator + SLCons.LIBS_DIR_NAME + File.separator + SLCons.BOOKS_FILE_NAME_PREFIX +
                        "_" + libId + ".xml";
    }



    public int bookSize() {
        return books.size();
    }

    public void addBookToGenreMap(String genre, BookTitle bookTitle) {
        Genre g;
        if (genre == null) {
            g = Genre.unknown;
        } else {
            // ищем такой жанр у себя
            try {
                g = Genre.valueOf(genre);
            } catch (Exception e) {
                // неизветсный жанр
                /*
                if (!wrongGenre.contains(genre)) {
                    wrongGenre.add(genre);
                }
                */
                g = Genre.unknown;
            }
        }

        // берем Глобальный жанр
        GlobalGenre gg = g.getGlobalGenre();

        Map<Genre, Collection<BookTitle>> ggMap = bookMap.get(gg);
        if (ggMap == null) {
            ggMap = new HashMap<Genre, Collection<BookTitle>>();
            bookMap.put(gg, ggMap);
        }

        Collection<BookTitle> list = ggMap.get(g);
        if (list == null) {
            list = new ArrayList<>();
            ggMap.put(g, list);
        }

        list.add(bookTitle);

    }

    public Map<GlobalGenre, Map<Genre, Collection<BookTitle>>> getBookMap() {
        return bookMap;
    }

    public Collection<String> getWrongGenre() {
        return wrongGenre;
    }
}
