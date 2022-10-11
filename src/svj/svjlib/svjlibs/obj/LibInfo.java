package svj.svjlib.svjlibs.obj;

/**
 * Инфа о библиотеке
 * <BR/>
 */
public class LibInfo {

    /**
     * Номер загрузки библиотеки по-порядку. Фактически это ее ИД.
     */
    private final int index;

    /**
     * Полный путь до директории, где лежат файлы библиотеки.
     */
    private final String libDir;

    /**
     * Название библиотеки, заданное пользователем при ее добавлении.
     */
    private final String name;

    public LibInfo(int index, String libDir, String name) {
        this.index = index;
        this.libDir = libDir;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getLibDir() {
        return libDir;
    }

    public String getName() {
        return name;
    }

}
