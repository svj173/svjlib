package svj.svjlib.svjlibs.obj;

/**
 * Результат загрузки библиотеки.
 * <BR/> - Сколько книг загружено
 * <BR/> - Сколкьо азгружено с ошибками - + причины ошибок
 * <BR/> -- Ошибки загрузки: Не выделили кодировку текста, Книга не в зип-архиве
 */
public class LoadLibInfo {

    // всего архивных папок с книгами
    private int sourceArchiveSize = 0;
    // всего обработано книг
    private int sourceBookSize = 0;
    // благополучно загружено книг
    private int bookSize = 0;
    private int badCodeText = 0;
    private int bookNoneZip = 0;
    private int parseError = 0;
    private String libDir;

    public void incSourceArchive() {
        sourceArchiveSize++;
    }

    public void incSourceBook() {
        sourceBookSize++;
    }

    public void incBook() {
        bookSize++;
    }

    public void incBadCodeText() {
        badCodeText++;
    }

    public void incBookNoneZip() {
        bookNoneZip++;
    }

    public void incParseError() {
        parseError++;
    }

    @Override
    public String toString() {
        return "LoadLibInfo{libDir=" + libDir +
                ", sourceArchiveSize=" + sourceArchiveSize +
                ", sourceBookSize=" + sourceBookSize +
                ", bookSize=" + bookSize +
                ", badCodeText=" + badCodeText +
                ", bookNoneZip=" + bookNoneZip +
                ", parseError=" + parseError +
                '}';
    }

    public void setLibDir(String libDir) {
        this.libDir = libDir;
    }

    public String getLibDir() {
        return libDir;
    }

}
