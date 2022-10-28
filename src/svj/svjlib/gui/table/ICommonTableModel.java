package svj.svjlib.gui.table;

import java.util.*;

public interface ICommonTableModel<D, F> {
    /**
     * Привести параметры профиля в массив колонок, применяемый в данной модели.
     */
    void setFields(Collection fieldEnables);

    /**
     * Returns the db name for column number <code>column</code>.
     */
    String getColumn(int column);

    D getItem(int selectedRow);

    void setColumnFields(Vector<F> cFields);
}
