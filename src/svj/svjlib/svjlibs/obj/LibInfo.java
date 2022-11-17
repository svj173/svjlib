package svj.svjlib.svjlibs.obj;

/**
 * Инфа о библиотеке
 * <BR/>
 */
public class LibInfo {

    /**
     * Номер загрузки библиотеки по-порядку. Фактически это ее ИД.
     */
    private final long id;

    /**
     * Полный путь до директории, где лежат файлы библиотеки.
     */
    private final String libDir;

    /**
     * Название библиотеки, заданное пользователем при ее добавлении.
     */
    private final String name;

    public LibInfo(String libDir, String name) {
        id = System.currentTimeMillis();
        this.libDir = libDir;
        this.name = name;
    }

    public LibInfo(String id, String libDir, String name) {
        this.id = Long.parseLong(id);
        this.libDir = libDir;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getLibDir() {
        return libDir;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "LibInfo{" +
                "id=" + id +
                ", libDir='" + libDir + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
