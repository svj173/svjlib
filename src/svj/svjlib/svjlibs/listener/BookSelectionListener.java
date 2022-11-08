package svj.svjlib.svjlibs.listener;

import svj.svjlib.obj.BookTitle;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

/**
 * Акция на выделении книги в таблице - выводит внизу аннотацию книги и другую инфу
 * <BR/>
 */
public class BookSelectionListener implements ListSelectionListener {

    private JTable table;
    private JLabel bookInfoPanel;

    public BookSelectionListener(JTable table, JLabel bookInfoPanel) {
        this.table = table;
        this.bookInfoPanel = bookInfoPanel;

    }

    @Override
    public void valueChanged(ListSelectionEvent event) {

        String result = "22";
        int[] selectedRows = table.getSelectedRows();
        for (int i = 0; i < selectedRows.length; i++) {
            int selIndex = selectedRows[i];
            TableModel model = table.getModel();
            Object value = model.getValueAt(selIndex, 0);
            if (value instanceof BookTitle) {
                BookTitle book = (BookTitle) value;
                result = book.getAnnotation() + "\n\n- file: " + book.getFileName();
                break;
            } else {
                result = "Выбран не BookTitle";   // !!! - почемуто здеь
            }
            //result = result + value;
            /*
            if (i != selectedRows.length —1){
                result += ", ";
            }
            */
        }
        if (result == null || result.isEmpty())  result = "Аннотация оистутсвует";
        bookInfoPanel.setText(result);

    }
}
