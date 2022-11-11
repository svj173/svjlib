package svj.svjlib.obj;

import svj.svjlib.GCons;
import svj.svjlib.WCons;
import svj.svjlib.svjlibs.obj.Author;
import svj.svjlib.svjlibs.table.BookField;
import svj.svjlib.svjlibs.tools.SLTools;
import svj.svjlib.tools.Convert;
import svj.svjlib.tools.StringTools;
import svj.svjlib.tools.Utils;

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
public class BookTitle implements Comparable<BookTitle> {

    // ИД библиотеки
    private long libId;

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
    private String date;

    // титульная картинка книги если есть  -- идет полсе тега /description - надо ли это?
    private Icon titleIcon;

    // если есть аткая возможность - иначе приблизительный пересчет из ZIP размера.
    private long bookSize;

    public String getSimpleInfo() {
        StringBuilder sb = new StringBuilder(128);
        sb.append(bookTitle);
        sb.append(WCons.SEP_1);

        if (authors.size() > 0) {
            List<Author> l = new ArrayList<>(authors);
            Author author = l.get(0);
            sb.append(author.getLastName());
            sb.append(WCons.SEP_SPACE);
            sb.append(author.getFirstName());
        }
        return sb.toString();
    }

    public String getFirstAuthor() {
        StringBuilder sb = new StringBuilder(128);

        if (authors.size() > 0) {
            List<Author> l = new ArrayList<>(authors);
            Author author = l.get(0);
            sb.append(author.getSimple());
        }
        return sb.toString();
    }

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

        if (getDate() != null) {
            sb.append("date:");
            sb.append(getDate());
            sb.append(WCons.SEP_SPACE);
        }

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

        sb.append(getLibId());
        sb.append(WCons.SEP_1);
        sb.append(getArchiveName());
        sb.append(WCons.SEP_1);
        sb.append(getFileName());

        return sb.toString();
    }

    public String fullInfo() {
        StringBuilder sb = new StringBuilder(128);
        sb.append(bookTitle);
        sb.append(WCons.END_LINE);
        sb.append(WCons.END_LINE);

        if (authors.size() > 0)  {
            sb.append("Автор: ");
            List<Author> l = new ArrayList<>(authors);
            Author author = l.get(0);
            sb.append(author.getSimple());
            sb.append(WCons.END_LINE);
        }

        if (! StringTools.isEmpty(getAnnotation())) {
            sb.append(WCons.END_LINE);
            sb.append("Аннотация: ");
            sb.append(getAnnotation());
            sb.append(WCons.END_LINE);
            sb.append(WCons.END_LINE);
        }

        sb.append("Язык: ");
        sb.append(getLang());
        sb.append(WCons.END_LINE);

        if (getDate() != null) {
            sb.append("Дата: ");
            sb.append(getDate());
            sb.append(WCons.END_LINE);
        }

        sb.append("Размер: ");
        sb.append(getBookSize());
        sb.append(WCons.END_LINE);

        if (getSerialName() != null) {
            sb.append("Серия: ");
            sb.append(getSerialName());
            sb.append(" - ");
            sb.append(getSerialIndex());
            sb.append(WCons.END_LINE);
        }

        if (genres.size() > 0) {
            sb.append("Жанр: ");
            sb.append(SLTools.getGenresAsStr(getGenres()));
            sb.append(WCons.END_LINE);
        }

        sb.append(WCons.END_LINE);

        sb.append("ИД библиотеки: ");
        sb.append(getLibId());
        sb.append(WCons.END_LINE);

        sb.append("Имя файла архива: ");
        sb.append(getArchiveName());
        sb.append(WCons.END_LINE);

        sb.append("Имя файла книги в архиве: ");
        sb.append(getFileName());
        sb.append(WCons.END_LINE);

        return sb.toString();
    }

    // для отображения в таблице списка книг
    public Object getValue(IWidthField field) {
        if (!(field instanceof BookField)) {
            return GCons.UNKNOWN_TABLE_VALUE;
        }
        switch ((BookField) field) {
            case NAME:
                return getBookTitle();
            case AUTHOR:
                return getFirstAuthor();
            case GENRE:
                return getGenresStr();
            case SERIAL:
                return getSerialName();
            case INDEX:
                return getSerialIndex();
            case SIZE:
                return getBookSize();
            case DATE:
                return getDate();
            case LANG:
                return getLang();
            default:
                return GCons.UNKNOWN_TABLE_VALUE;
        }
    }

    public long getLibId() {
        return libId;
    }

    public void setLibId(long libId) {
        this.libId = libId;
    }

    public void setLibId(String libIdStr) {
        this.libId = Convert.str2long(libIdStr, 0);
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

    public void setSerialIndex(String serialIndexStr) {
        this.serialIndex = Convert.getInt(serialIndexStr, 0);
    }

    public Collection<String> getGenres() {
        return genres;
    }

    public String getGenresStr() {
        return SLTools.getGenresAsStr(getGenres());
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

    public void setBookSize(String bookSizeStr) {
        this.bookSize = Convert.str2long(bookSizeStr, 0);
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "BookTitle{" +
                "archiveDirName='" + libId + '\'' +
                ", archiveName='" + archiveName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", date='" + date + '\'' +
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

    @Override
    public int compareTo(BookTitle bookTitle) {

        if ( bookTitle == null )  return 1;

        return Utils.compareToWithNull ( getBookTitle(), bookTitle.getBookTitle() );
    }

    public boolean equals(Object obj) {
        if ( obj == null )  return false;
        if ( obj instanceof BookTitle )
        {
            BookTitle bookTitle = (BookTitle) obj;
            return compareTo ( bookTitle ) == 0;
        }
        return false;
    }

}
