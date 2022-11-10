package svj.svjlib.svjlibs.table;

import svj.svjlib.obj.IWidthField;

import java.util.*;

/**
 * Описание колонок для таблицы отображения списка книг.
 * <BR/>
 */
public enum BookField implements IWidthField {

//         	Object[] columnsHeader = new String[] {"Наименование", "Серия", "Индекс в серии", "Язык", "Дата написания",
//         	"Размер книги"};
    NAME("Наименование", String.class, 255, 0.3f),
    SERIAL("Серия", String.class, 255, 0.2f),
    INDEX("Индекс в серии", Integer.class, 255, 0.2f),
    LANG("Язык", String.class, 255, 0.2f),
    DATE("Дата написания", String.class, 255, 0.2f),
    SIZE("Размер книги", Long.class, 255, 0.2f),
    ;


    /* Русское название поля. Название БД поля - само значение данного объекта. */
    private String name;
    /* Тип данных этого поля. Необходим при сортировках в таблице */
    private Class fieldClass;
    /* Максимальная длина строки для текстового поля. */
    private int strSize;

    private float relWidth;


    BookField(String name, Class fieldClass, int strSize, float relWidth) {
        this.name = name;
        this.fieldClass = fieldClass;
        this.strSize = strSize;
        this.relWidth = relWidth;
    }

    public static Vector<BookField> getFields() {
        Vector<BookField> result;
        BookField[] fields;

        fields = values();
        result = new Vector<>(fields.length);
        result.addAll(Arrays.asList(fields));

        return result;
    }

/*


    NAME("Наименование", String.class, 255, 0.3f),
    // Name	-- имя параметра
    UPDATE("Update", Boolean.class, 255, 0.3f),
    //
    INSTALL("Install", Boolean.class, 255, 0.3f),
    //
    STATUS("Статус", String.class, 255, 0.3f),
    // Status	[read only]	Not installed	Off
    DESCRIPTION("Описание", String.class, 255, 0.3f),
    // Description	[read only]
    PACKAGE_NAME("Пакет", String.class, 255, 0.3f),
    // PackageName	[read only]	ru.cn.tv	Off
    VERSION_AVAILABLE("VersionAvailable", String.class, 255, 0.3f),
    // VersionAvailable	[read only]	1	Off
    VERSION_INSTALLED("VersionInstalled", String.class, 255, 0.3f),
    // VersionInstalled	[read only]		Off
    ;

    private boolean reinitFromLanguage = false;


    StbAppField(String name, Class fieldClass, int strSize, float relWidth) {
        this.name = name;
        this.fieldClass = fieldClass;
        this.strSize = strSize;
        this.relWidth = relWidth;
    }

    public static Vector<StbAppField> getFields() {
        Vector<StbAppField> result;
        StbAppField[] fields;

        fields = values();
        result = new Vector<>(fields.length);
        result.addAll(Arrays.asList(fields));

        return result;
    }

    @Override
    public float getWidth() {
        return relWidth;
    }

    @Override
    public String getName() {
        if (!reinitFromLanguage) {
            setNameFromLang();
        }
        return name;
    }

    public void setNameFromLang() {
        if (reinitFromLanguage) {
            return; // на всякий случай проверяем
        }
        reinitFromLanguage = true;
        String key = LanguageTools.getClassItemKey(this.getClass(), name(), LanguageConst.NAME);
        String value = Msg.getMessage(key);
        if (!key.equals(value)) {
            name = value;
        }
    }


 */
    public int getStrSize() {
        return strSize;
    }

    public Class getFieldClass() {
        return fieldClass;
    }

    @Override
    public float getWidth() {
        return relWidth;
    }

    @Override
    public String getName() {
        return name;
    }

}
