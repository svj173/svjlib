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
        super(objFullType, tabName, new BookTableModel(), BookField.getFields());
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

}
