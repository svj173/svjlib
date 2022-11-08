package svj.svjlib.gui.table;

import svj.svjlib.gui.panel.WPanel;
import svj.svjlib.obj.BookTitle;
import svj.svjlib.svjlibs.listener.BookSelectionListener;
import svj.svjlib.svjlibs.obj.Author;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.*;

/**
 * <BR/>
 */
public class TableModelTest extends WPanel {

    private DefaultTableModel tableModel;
    private JTable table;
    /* Список найдненых атворов и их книг*/
    private Map<Author, Collection<BookTitle>> authorList = null;

    private JLabel topLabel = new JLabel();
	private JLabel bookInfoPanel;

	public TableModelTest(JLabel bookInfoPanel) {

    	setLayout(new BorderLayout());

    	add(topLabel, BorderLayout.NORTH);



        // Создание стандартной модели
      		tableModel = new DefaultTableModel();

      		// Определение стоблцов
        // Заголовки столбцов
		/*
				columnsHeader.add("Название");
		columnsHeader.add("Серия");
		columnsHeader.add("Индекс в серии");
		columnsHeader.add("Язык");
		columnsHeader.add("Дата написания");
		columnsHeader.add("Размер книги");

		 */
        	Object[] columnsHeader = new String[] {"Наименование", "Серия", "Индекс в серии", "Язык", "Дата написания", "Размер книги"};
      		tableModel.setColumnIdentifiers(columnsHeader);

      		/*
      		// Наполнение модели данными
      		for (int i = 0; i < array.length; i++) {
				tableModel.addRow(array[i]);          // addRow(Vector<?> rowData)
			}
		    */
      		// Создание таблицы на основании модели данных
      		table = new JTable(tableModel);

        table.setRowHeight(24);
		add(new JScrollPane(table), BorderLayout.CENTER);

		ListSelectionModel selModel = table.getSelectionModel();
		selModel.addListSelectionListener(new BookSelectionListener(table, bookInfoPanel));

    }

	public void initData(Author author) {

    	if (author == null) {
    		topLabel.setText("Не задан автор");
    		return;
		}

    	if (authorList == null) {
			topLabel.setText("Отсутствует список найденных книг");
 			return;
		}
    	// берем книги указанного автора
		//SLCons.BOOKS_MANAGERS.getBooksByAuthor(author);

		topLabel.setText("Автор: " + author.getSimple());

		Collection<BookTitle> books = authorList.get(author);

		Vector v;
    	// создаем вектор дат
		Vector<Vector> dataVector = new Vector<>();
		for (BookTitle book: books) {
			v = new Vector();
			v.add(book.getBookTitle());
			v.add(book.getSerialName());
			v.add(book.getSerialIndex());
			v.add(book.getLang());
			v.add(book.getDate());
			v.add(book.getBookSize());
			dataVector.add(v);
		}

		// создаем вектор названий колонок
		Vector columnsHeader = new Vector();
		columnsHeader.add("Название");
		columnsHeader.add("Серия");
		columnsHeader.add("Индекс в серии");
		columnsHeader.add("Язык");
		columnsHeader.add("Дата написания");
		columnsHeader.add("Размер книги");

		tableModel.setDataVector(dataVector, columnsHeader);

    	/*
public void setDataVector(Vector<? extends Vector> dataVector,
                              Vector<?> columnIdentifiers) {
    	 */



	}

	public void setAuthorList(Map<Author, Collection<BookTitle>> list) {
		authorList = list;
	}

}
