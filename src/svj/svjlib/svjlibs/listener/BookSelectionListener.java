package svj.svjlib.svjlibs.listener;

import svj.svjlib.obj.BookTitle;
import svj.svjlib.svjlibs.table.BookTablePanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Акция на выделении книги в таблице - выводит внизу аннотацию книги и другую инфу
 * <BR/>
 */
public class BookSelectionListener implements ListSelectionListener {

    private BookTablePanel bookTablePanel;
    private JTextArea bookInfoPanel;

    public BookSelectionListener(BookTablePanel bookTablePanel, JTextArea bookInfoPanel) {
        this.bookTablePanel = bookTablePanel;
        this.bookInfoPanel = bookInfoPanel;

    }

    @Override
    public void valueChanged(ListSelectionEvent event) {

        String result;

        BookTitle book = bookTablePanel.getSelectedItem();
        //result = book.getAnnotation() + "\n\n- file: " + book.getFileName() + "\n";
        if (book == null) {
            result = "";
        } else {
            result = book.fullInfo();
        }

        //if (StringTools.isEmpty(result))  result = "Аннотация отсутствует";

        bookInfoPanel.setText(result);

    }
}
