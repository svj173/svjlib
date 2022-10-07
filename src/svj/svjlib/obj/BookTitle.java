package svj.svjlib.obj;

import svj.svjlib.WCons;
import svj.svjlib.svjlibs.obj.Author;

import javax.swing.*;

import java.util.*;

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
    private Collection<Author> authors = new LinkedList<>();   // составной - ФИО, а аткже соавторы

    private String annotation;

    private String serialName;
    private int serialIndex = 0;

    // Жанр - может быть несколкьо
    private Collection<String> genres = new ArrayList<>();

    private String lang;

    // титульная картинка книги если есть  -- идет полсе тега /description - надо ли это?
    private Icon titleIcon;

    // если есть аткая возможность - иначе приблизительный пересчет из ZIP размера.
    private long bookSize;

    public String info() {
        StringBuilder sb = new StringBuilder(128);
        sb.append(bookTitle);
        sb.append(WCons.SEP_1);

        if (authors.size() > 0)  {
            List<Author> l = new ArrayList<>(authors);
            Author author = l.get(0);
            sb.append(author.getLastName());
            sb.append(WCons.SEP_SPACE);
            sb.append(author.getFirstName());
            sb.append(WCons.SEP_SPACE);
        }

        sb.append(getLang());
        sb.append(WCons.SEP_SPACE);

        sb.append("size:");
        sb.append(getBookSize());
        sb.append(WCons.SEP_SPACE);

        if (getSerialName() != null) {
            sb.append("серия:");
            sb.append(getSerialName());
            sb.append(WCons.SEP_COLON);
            sb.append(getSerialIndex());
            sb.append(WCons.SEP_SPACE);
        }

        if (genres.size() > 0) {
            for (String str : genres) {
                sb.append(str);
                sb.append(WCons.SEP_COLON);
            }
            sb.append(WCons.SEP_SPACE);
        }

        sb.append(getArchiveDirName());
        sb.append(WCons.SEP_1);
        sb.append(getArchiveName());
        sb.append(WCons.SEP_1);
        sb.append(getFileName());
        sb.append(WCons.SEP_1);

        return sb.toString();
    }

    public String getArchiveDirName() {
        return archiveDirName;
    }

    public void setArchiveDirName(String archiveDirName) {
        this.archiveDirName = archiveDirName;
    }

    public String getArchiveName() {
        return archiveName;
    }

    public void setArchiveName(String archiveName) {
        this.archiveName = archiveName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Collection<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Collection<Author> authors) {
        this.authors = authors;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getSerialName() {
        return serialName;
    }

    public void setSerialName(String serialName) {
        this.serialName = serialName;
    }

    public int getSerialIndex() {
        return serialIndex;
    }

    public void setSerialIndex(int serialIndex) {
        this.serialIndex = serialIndex;
    }

    public Collection<String> getGenres() {
        return genres;
    }

    public Icon getTitleIcon() {
        return titleIcon;
    }

    public void setTitleIcon(Icon titleIcon) {
        this.titleIcon = titleIcon;
    }

    public long getBookSize() {
        return bookSize;
    }

    public void setBookSize(long bookSize) {
        this.bookSize = bookSize;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void addGenre(String value) {
        genres.add(value);
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    @Override
    public String toString() {
        return "BookTitle{" +
                "archiveDirName='" + archiveDirName + '\'' +
                ", archiveName='" + archiveName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", authors=" + authors +
                ", annotation.size='" + (annotation != null ? annotation.length() : "null") + '\'' +
                ", serialName='" + serialName + '\'' +
                ", serialIndex=" + serialIndex +
                ", genres=" + genres +
                ", lang='" + lang + '\'' +
                ", titleIcon=" + titleIcon +
                ", bookSize=" + bookSize +
                '}';
    }
}
