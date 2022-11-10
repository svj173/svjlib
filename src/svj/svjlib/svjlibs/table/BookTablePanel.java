package svj.svjlib.svjlibs.table;

import svj.svjlib.gui.table.ATableModel;
import svj.svjlib.gui.table.TablePanel;
import svj.svjlib.obj.BookTitle;

import java.util.*;

/**
 * <BR/>
 */
public class BookTablePanel extends TablePanel<BookField, BookTitle, ATableModel<BookTitle, BookField>> {

    public BookTablePanel(String objFullType, String tabName) {
        super(objFullType, tabName, new BookTableModel());
    }

    @Override
    public BookTitle createNewDataObject() {
        return new BookTitle();
    }

    @Override
    public Vector<?> getFullFields() {
        return BookField.getFields();
    }

    // Изменение занчения полей - пока не надо
    @Override
    protected void updateFields() {

    }

    /*
    private ManageApplicRenderer booleanRenderer;

    public StbAppTablePanel(FullNameObj fullObjType, String tabName) {
        super(fullObjType.getFullName(), tabName, new StbAppTableModel());

        booleanEditor = new ManageApplicEditor();
        booleanRenderer = new ManageApplicRenderer();
    }

    @Override
    public Vector<StbAppField> getFullFields() {
        return StbAppField.getFields();
    }

    @Override
    protected void updateFields() {
        TableColumnModel columnModel;
        int ic;
        TableColumn tableColumn;
        JTable mainTable;
        EltexTableModel<StbAppItem, StbAppField> tableModel;

        mainTable = getTable();
        tableModel = getTableModel();

        // --------------- Редактирование -----------------------
        columnModel = mainTable.getColumnModel();

        // - UPDATE
        ic = tableModel.getColumnNumber(StbAppField.UPDATE);
        if (ic >= 0) {
            tableColumn = columnModel.getColumn(ic);
            Logger.getInstance().debug("StbAppTablePanel.updateFields: UPDATE tableColumn = ", tableColumn);
            if (tableColumn != null) {
                tableColumn.setCellEditor(booleanEditor);
                tableColumn.setCellRenderer(booleanRenderer);
            }
        }
        // - INSTALL
        ic = tableModel.getColumnNumber(StbAppField.INSTALL);
        if (ic >= 0) {
            tableColumn = columnModel.getColumn(ic);
            Logger.getInstance().debug("StbAppTablePanel.installFields: INSTALL tableColumn = ", tableColumn);
            if (tableColumn != null) {
                tableColumn.setCellEditor(booleanEditor);
                tableColumn.setCellRenderer(booleanRenderer);
            }
        }
    }

    @Override
    public StbAppItem createNewDataObject() {
        return new StbAppItem();
    }

     */
}
