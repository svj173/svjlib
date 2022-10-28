package svj.svjlib.gui.table;

import svj.svjlib.gui.panel.WPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * <BR/>
 */
public class TableModelTest extends WPanel {

    private DefaultTableModel tableModel;
    private JTable table;

    public TableModelTest() {

        // Создание стандартной модели
      		tableModel = new DefaultTableModel();

      		// Определение стоблцов
        // Заголовки столбцов
        	Object[] columnsHeader = new String[] {"Наименование", "Ед.измерения", "Количество"};
      		tableModel.setColumnIdentifiers(columnsHeader);

      		// Наполнение модели данными
      		for (int i = 0; i < array.length; i++)
      			tableModel.addRow(array[i]);

      		// Создание таблицы на основании модели данных
      		table = new JTable(tableModel);

        table.setRowHeight(24);

    }
    
}
