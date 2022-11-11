package svj.svjlib.svjlibs.table;

import svj.svjlib.obj.IWidthField;

import java.util.*;

/**
 * Описание колонок для таблицы отображения списка книг.
 * <BR/>
 */
public enum BookField implements IWidthField {

    NAME("Наименование", String.class, 255, 0.3f),
    GENRE("Жанр", String.class, 255, 0.3f),
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
