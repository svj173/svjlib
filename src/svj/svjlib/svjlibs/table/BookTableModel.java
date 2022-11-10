package svj.svjlib.svjlibs.table;

import svj.svjlib.GCons;
import svj.svjlib.Log;
import svj.svjlib.gui.table.ATableModel;
import svj.svjlib.obj.BookTitle;

import java.util.*;

/**
 * <BR/>
 */
public class BookTableModel extends ATableModel<BookTitle, BookField> {

    @Override
    public void setFields(Collection fieldEnables) {
        Vector<BookField> result = null;
        BookField field;

        Log.l.debug("fieldEnables = {}", fieldEnables);

        /*
        result = new Vector<>(fieldEnables.size());
        for (TableColumnParam column : fieldEnables) {
            // заносим только TRUE !!!
            if (column.isEnable()) {
                field = BookField.valueOf(column.getFieldName());
                result.add(field);
            }
        }
        */

        result = BookField.getFields();
        Log.l.debug("new fields = {}", result);

        setColumnFields(result);
    }

    @Override
    public boolean isOkSorterField(BookField field) {
        return field != null;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BookField field;
        BookTitle rowInfo;

        rowInfo = getItem(rowIndex);
        field = getField(columnIndex);
        if (field == null || rowInfo == null) {
            return GCons.UNKNOWN_TABLE_VALUE;
        }

        return rowInfo.getValue(field);
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        Log.l.debug("setValueAt: aValue = {}; row = {}; column = {}", aValue, row, column);
        /*
        BookField field;
        BookTitle info;
        Boolean value;

        field = getFields().get(column);
        Log.l.debug("setValueAt: field = ", field);
        info = getItem(row);

        switch (field) {
            case UPDATE:
                value = (Boolean) aValue;
                info.setUpdate(value);
                break;

            case INSTALL:
                value = (Boolean) aValue;
                info.setInstall(value);
                if (!value) {
                    info.setUpdate(false);
                }
                break;
        }
        */
    }

    // исп при сортировке колонок
    @Override
    public Class getColumnClass(int column) {
        BookField field;

        field = getField(column);
        return field.getFieldClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        /*
        BookField stbappField;     // вызывается каждый клик
        StbAppItem rowInfo;

        stbappField = getFields().get(columnIndex);
        rowInfo = getItem(rowIndex);

        // установленная программа не удаляемая - редактирование запретить
        if (stbappField == BookField.INSTALL) {
            //если установлена и не удаляема - уставновку запретить
            if (rowInfo.getInstallInit() && !rowInfo.getDeletable()) {
                return false;
            }
            // одновременно install и update устанавливать нет смысла 0^ 1 || 1 0^ (i u) то запретить редактирование
            if (!rowInfo.getInstallInit() && rowInfo.getUpdate()) {
                return false;
            }
            // если не устанавливаемая и не установлена - установку запретить
            if (!rowInfo.getInstallInit() && !rowInfo.getInstallable()) {
                return false;
            }
            return true;
        }

        if (stbappField == BookField.UPDATE) {
            if (!rowInfo.getInstallInit() && !rowInfo.getUpdate() && !rowInfo.getInstalled()) {
                return false;
            }
            if (!rowInfo.getInstallable()) {
                return false; // если не устанавливаемая - обновление запретить
            }
            if (rowInfo.getStatus().startsWith("Installing")) {
                return false;
            }
            //  только если установленная версия меньше устанавливаемой - разрешить кликать Update
            return (rowInfo.getVersionCodeInstalled() < rowInfo.getVersionCodeAvailable());
        }
        */

        return false;// will never got executed
    }

    @Override
    public String getColumnName(int columnIndex) {
        return getField(columnIndex).getName();
    }


}
