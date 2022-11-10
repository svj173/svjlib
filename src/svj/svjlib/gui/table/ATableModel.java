package svj.svjlib.gui.table;


import svj.svjlib.Log;
import svj.svjlib.obj.IName;

import javax.swing.table.DefaultTableModel;

import java.util.*;


/**
 * Общая модель отображения табличных данных.
 * <BR/> - D - объекты табличных данных (например, SsidMacInfo).
 * <BR/> - F - объекты, описывающие поля таблицы (например: SsidMacField).
 * <BR/>
 * <BR/> Замечание:
 * <BR/> AbstractTableModel не прошла, хотя относительно DefaultTableModel здесь у нас переписаны Vector<D> data и
 * Vector<F> columnFields.
 * <BR/> Проявление ошибки - когда выключаются поля из таблицы - в таблице в модели колонок не происходит изменения и
 * метод getValueAt разваливается
 * (разсинхронизация кол-ва колонок и соответствия полей и их номеров). svj, 2012-12-18.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 27.10.2010 10:44:44
 */
// AbstractTableModel
public abstract class ATableModel<D, F extends IName> extends DefaultTableModel implements ICommonTableModel<D, F> {
    /* Объекты имен полей таблицы. Здесь содержатся только те колонки, которые отображены на экране (т.е. не полный
    список). НЕ NULL. */
    private final Vector<F> columnFields = new Vector<>();
    /**
     * массив данных. один объект - одна строка.
     * создаем сразу, а не в конструкторе - тк обращение getRowCount идет у конструктора уже в super().
     * - НЕТ, все не так. getRowCount вызывается super конструкторе, а к этому времени массив data еще не успел
     * создасться.
     * - final - нельзя!!! т.к. при обращении в конструкторе к data, который еще не успел содасться, возникает
     * конфликт на уровне java.
     */
    private Vector<D> data = new Vector<>();
    /**
     * Индивидуальное имя. Как правило это имя родительского класса.
     */
    private String name;


    public ATableModel() {
        this(null);
    }
    /*
    // todo Попытка универсализировать, а то сплошной очень похожий копи-паст у всех классов-наследников. svj,
    2013-03-07
    // todo Доделать по возможности.g
    public void setFields ( Collection<TableColumnParam> fieldEnables )
    {
        Vector<F> result;
        F         field;

        Logger.getInstance ().debug ( "JournalTableModel.setFields: Start. fieldEnables = ", fieldEnables );

        result = new Vector<F>(fieldEnables.size());
        for ( TableColumnParam column : fieldEnables )
        {
            // заносим только TRUE !!!
            if ( column.isEnable() )
            {
                field   = F.valueOf ( column.getFieldName() );    // ????
                result.add ( field );
            }
        }
        Logger.getInstance().debug ( "JournalTableModel.setFields: new fields = ", result );

        setColumnFields ( result );
    }
    */

    public ATableModel(Vector<F> names) {
        super(names, 0);

        setColumnFields(names);
    }

    /* Привести параметры профиля в массив колонок, применяемый в данной модели. */
    //public abstract void setFields(Collection<TableColumnParam> fieldEnables);



    @Override
    public String getColumn(int column) {
        return null;
    }

    public ATableModel(Vector<D> data, Vector<F> names) {
        //super(data, names);
        super(names, 0);

        setColumnFields(names);

        setData(data);
    }

    /* Проверяем, можно ли данное поле сортировать. */
    public abstract boolean isOkSorterField(F field);

    public Vector<D> getData() {
        return data;
    }

    // todo Здесь описан универсальный механизм извлечения данных. Перенести его в общий abstract class. И тогда
    // многочисленные getValueAt у классов удалятся. (svj)
    // todo D,F - наследовать от IInfo и IWidthField
    /*
    @Override
    public Object getValueAt ( int rowIndex, int columnIndex )
    {
        IWidthField field;        // F
        IInfo       rowInfo;      // D
        Object      result;

        result  = "???";
        rowInfo = getItem ( rowIndex );
        if ( rowInfo != null )
        {
            field = getField ( columnIndex );
            if ( field != null )
            {
                result = rowInfo.getValue ( field );
            }
        }

        return result;
    }
    */

    // Null - при очистке таблицы.
    public void setData(Vector<D> dataList) {
        data.clear();
        if (dataList != null) {
            data.addAll(dataList);
        }
        processData(data);
        //this.fireTableDataChanged();
    }

    public Vector<F> getFields() {
        return columnFields;
    }


    @Override
    public void setColumnFields(Vector cFields) {
        columnFields.clear();

        if (cFields != null) {
            columnFields.addAll(cFields);
        }

        setColumnIdentifiers(cFields);
    }

    /* Предварительная (перед их отображением) обработка только что полученных данных. Например, изменить состав
    полей в таблице "Статистика состава ОНт по типам"
       (выкинуть неиспользуемые типы из колонок). svj, 2011-06-10 */
    public void processData(Vector<D> data) {
    }

    @Override
    public int getRowCount() {
        // Обязательно проверяем на NULL. Хоть и final, но  getRowCount вызывается super конструкторе, а к этому
        // времени масив data еще не успел создасться.
        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }

    @Override
    public int getColumnCount() {
        return columnFields.size();
    }

    public F getField(int column) {
        // проверка на границы
        if ((column < 0) || (column >= columnFields.size())) {
            return null;
        } else {
            // учитывать ситуацию когда колонка была только что перенесена на другое место здесь невозможно, т.к. за
            // это отвечает TableColumnModel
            // Поэтому сюда надо передавать уже правильный номер колонки
            // - логи нельзя - stackOverflow
            //Logger.getInstance().debug ( "-- EltexTableModel.getField: column number = " + column );
            return columnFields.get(column);
        }
    }

    public int getColumnNumber(F field) {
        return columnFields.indexOf(field);
    }

    // для отладок
    public Vector<F> getColumns() {
        return columnFields;
    }

    /* Найти колонку по имени идентификатора (англ). */
    public F getFieldByIdName(String fieldId) {
        for (F field : columnFields) {
            if (field.toString().equalsIgnoreCase(fieldId)) {
                return field;
            }
        }
        return null;
    }

    /* Найти номер колонки по имени идентификатора (англ). */
    public int getColumnNumberByIdName(String fieldId) {
        int ic = 0;
        //Logger.getInstance().debug ( "-- EltexTableModel.getColumnNumberByIdName: fieldId = ", fieldId );
        for (F field : columnFields) {
            //Logger.getInstance().debug ( "---- EltexTableModel.getColumnNumberByIdName: field = ", field );
            if (field.toString().equalsIgnoreCase(fieldId)) {
                return ic;
            }
            ic++;
        }
        return -1;
    }

    /* Если в таблице используется сортировки то этот индекс необходимо сначала преобразовать в модели сортировки к
    реальному индексу объекта.
     * - Так ли это? Проверить.
     * -- Это так, но в TablePanel.getItem уже предварителньо происходит вычисление реального индекса,
     * а потом уже идет обращение к модели - model.getItem(selectedRow) -- svj, 2012-11-26. */
    public D getItem(int selectedRow) {
        if (selectedRow < 0) {
            return null;
        }

        if (selectedRow < data.size()) {
            return data.get(selectedRow);
        } else {
            return null;
        }

        /*
        try
        {
            if ( (data == null) || data.isEmpty() )
            {
                return null;
            }
            else
            {
                if ( selectedRow < data.size() )
                    return data.get ( selectedRow );
                else
                    return null;
            }
        } catch ( Exception e ) {
            // ArrayIndexOutOfBoundsException
            Logger.getInstance().error ( e, "EltexTableModel.getItem (",getName(),"): Get Null item for selectedRow =
             ", selectedRow, "; data vector = ", data );
            return null;
        }
        */
    }

    public void addItem(D item) {
        data.add(item);
    }

    public void addItems(Collection<D> items) {
        data.addAll(items);
    }

    public void setItem(int selectedRow, D item) {
        if (!data.isEmpty()) {
            data.set(selectedRow, item);
        }
    }

    public void removeItem(int index) {
        if ((!data.isEmpty()) && (index < data.size())) {
            data.remove(index);
        }
    }

    public void removeItem(D item) {
        Log.l.debug("removeItem: item = {}", item);
        data.remove(item);
    }

    public void removeItems(Collection<D> items) {
        Log.l.debug("removeItems: Start");

        data.removeAll(items);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void clear() {
        data.clear();
    }

}
