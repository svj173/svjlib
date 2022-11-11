package svj.svjlib.gui.table;


import svj.svjlib.GCons;
import svj.svjlib.Log;
import svj.svjlib.WCons;
import svj.svjlib.exc.WEditException;
import svj.svjlib.gui.widget.AbstractWidget;
import svj.svjlib.obj.IName;
import svj.svjlib.obj.IWidthField;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.*;


/**
 * Интерфейс работы с панелью таблицы.
 * <BR/> Используется в функционале смены полей таблицы - для получения полного списка имен полей и задания выборочного.
 * <BR/> Прикручена работа с профилем пользователя по сохранению структуры колонок.
 * <BR/> Обобщения:
 * <BR/> - F - объекты, описывающие поле таблицы (строка имени либо что-то более сложное).
 * <BR/> - D - объекты данных
 * <BR/> - M - Класс модели. Например: AlertsActiveTableModel
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.09.2010 10:36:11
 */
public abstract class TablePanel<F extends IName, D, M extends ATableModel<D, F>> extends CommonPanel {

    /* Флаг - установлен или нет режим Выделить все записи для данной таблицы - актуально только для Таблицы с нашим
    скроллингом. */
    private boolean selectAll = true;

    // для доступа по время инициализации - например отключить прогресс барр
    //private TableDataReloadActionListener reloadActionListener = null;

    private boolean useScrollToVisible = false;

    // перелистнуть JScrollPane на верхнюю строчку
    private boolean doUpScroll = false;

    protected TablePanel(String objFullType, String tabName) {
        this(objFullType, tabName, null, null);
    }

    protected TablePanel(String objFullType, String tabName, M tableModel) {
        this(objFullType, tabName, tableModel, null);
    }

    protected TablePanel(String objFullType, String tabName, M tableModel, Vector<F> columns) {
        super(objFullType, tabName);

        setName(tabName);
        this.tabName = tabName;

        setLayout(new BorderLayout());

        if (tableModel != null) {
            // Создать обьекты Table и TableModel
            initTable(tableModel);

            /*
            // Взять список колонок по-умолчанию
            if (columns == null) {
                columns = createDefaultTableColumnNames();
            }
            */
            // Наполнить колонками модель
            ((ATableModel) mainTable.getModel()).setColumnFields(columns);

            // Создать собственно таблицу. Здесь же создастся модель колонок - на основе данных из модели данных.
            // - НЕ рабоатет с AbstractDataModel - вернули все обратно.
            //mainTable       = new EltexTable ( this.tableModel );
            //mainTable.setRowHeight ( GCons.TABLE_ROW_HEIGHT );


            /*
            setDefaultRenderer(Date.class, new EltexDateRenderer());
            setDefaultRenderer(Integer.class, new EltexLeftRenderer());
            setDefaultRenderer(Long.class, new EltexLeftRenderer());
            setDefaultRenderer(Double.class, new EltexLeftRenderer());
            */

            // Устанавливаем высоту шапки по-умолчанию - здесь, т.к. используется Table.
            //setHeaderHeight ( GCons.TABLE_ROW_HEIGHT ); // НЕЛЬЗЯ использовать, это портит перерисовку заголовка
            // при горизонтальном скролле!
        }
    }

    /* Валидация значений параметров в объекте данных таблицы. Ошибки - исключениями (возможно, накопительные по полям).
     * -- НЕ исп, т.к. перешли на редактирвоание в отдельном диалоге. Виджеты диалога сами валидируют значения.*/
    //public      abstract void       validateData ( D tableItem ) throws WEditException;

    /* Создать пустой объект данных */
    public abstract D createNewDataObject();

    /* Создать начальные поля  - исходная ситуация. На тот случай когда в БД ничего нет, или изменений мало по
    сравнению с умолчанием.
     * В связи с добавлением функционала IWidthField, можно сделать все хорошо в общем методе.
     * Переопределение в конкретных панелях для устройств надо удалить, иначе будет работать только ширина, но не
     * вкл-выкл колонок! */
    /*
    protected Vector<F> createDefaultTableColumnNames() {
        TableUserProfile userProfileLocal;
        String paramNamePrefix;
        int ic;
        Vector<F> result;
        Vector<F> all;
        boolean enableColumn;
        boolean enableColumnByWidth;
        IWidthField widthField;

        result = new Vector<>();
        all = new Vector<>((Collection<? extends F>) getFullFields());
        ic = 0;
        userProfileLocal = getUserProfile();
        paramNamePrefix = userProfileLocal.getParamNamePrefix();

        Log.l.debug("TablePanel (", getName(), ").createDefaultTableColumnNames: all fields = ", all);

        userProfileLocal.clear();  // на всякий случай, т.к. это - начало нашей работы и мы наполняем профиль исходными
        // значениями по-умолчанию.

        for (F field : all) {
            if (field instanceof IWidthField) {
                widthField = (IWidthField) field;
                enableColumnByWidth = widthField.getWidth() > 0;
                //widthField.setNameFromLang(); // изменить на название из языковых ресурсов - теперь делается в
                // самом IWidthField'е. см. org.eltex.ems.web.common.alert.AlertField.getName()
            } else {
                enableColumnByWidth = true;
            }

            enableColumn =
                    addUserProfileParam(userProfileLocal, paramNamePrefix, field, enableColumnByWidth, ic++);   // c 1

            if (enableColumn || enableColumnByWidth) {
                result.add(field);
            }
        }
        // если ничего не нашли, добавляем все!

        if (result.isEmpty()) {
            result.addAll(all);
        }

        userProfileLocal.setDefaultSorter(getDefaultRowSorterColumn(), getDefaultRowSorterOrder().toString());

        return result;
    }
    */

    /* Инициализация внешним обьектом */
    public void panelInit(Object obj) throws WEditException {
    }

    /* Дергается в ReloadPanel после всех загрузок. Описана как пустышка. Кому надо - перезапишет. */
    public void afterReload() throws WEditException {
    }

    public void beforeReload() throws WEditException {
    }

    public D getSelectedItem() {
        int realRow;
        int selectedRow;
        D result;

        result = null;
        selectedRow = mainTable.getSelectedRow();

        if (selectedRow >= 0) {
            realRow = mainTable.getRowSorter().convertRowIndexToModel(selectedRow);
            result = (D) ((ATableModel) mainTable.getModel()).getItem(realRow);
        }

        return result;
    }

    /**
     * Выбрать в таблице указанный обьект
     *
     * @param info обьект данных
     */
    public void setSelectedItem(D info) {
        int ic;
        int inum;
        int iselect;

        if (info == null) {
            return;
        }

        // варианты
        // - генерим событие с нашим обьектом на выделение и дергаем соответствующий листенер
        // - ищем руками обьект, находим его номер, выделяем -  +++

        ic = 0;
        inum = -1;
        iselect = -1;

        for (D item : getTableModel().getData()) {
            if ((item instanceof Comparable) && (info instanceof Comparable)) {
                if (((Comparable) item).compareTo(info) == 0) {
                    inum = ic;
                    break;
                }
            } else if (item == info) {
                inum = ic;
                break;
            }
            ic++;
        }

        if (inum >= 0) {
            // нашли
            try {
                // - здесь иногда выскакивает искл - ArrayIndexOutOfBoundsException. возможно тогда просто очищать
                // выделенное? Либо inum не правильно подсчитался?
                iselect = getTable().getRowSorter().convertRowIndexToView(inum);
                if (iselect >= 0) {
                    // Выделяем обьект в таблице
                    getTable().changeSelection(iselect, 0, false, false);
                }
            } catch (Exception e) {
                Log.l
                        .debug("TablePanel (", getName(), ").setSelectedItem: inum = ", inum, "; iselect = ", iselect,
                                "; item = ", info);
            }
        }
    }

    public Collection<D> getSelectedItems() {
        int[] selectedRows;
        int realRow;
        Collection<D> result;

        result = new ArrayList<>();
        selectedRows = mainTable.getSelectedRows();

        if (selectedRows.length > 0) {
            for (int i : selectedRows) {
                realRow = mainTable.getRowSorter().convertRowIndexToModel(i);
                result.add((D) ((ATableModel) mainTable.getModel()).getItem(realRow));
            }
        }

        return result;
    }

    public void clearSelection() {
        mainTable.clearSelection();
    }

    /* Выдать кол-во выбранных записей. */
    public int getSelectedSize() {
        return mainTable.getSelectedRowCount();
    }

    public int getSelectedIndex() {
        int realRow;
        int selectedRow;

        realRow = WCons.INT_EMPTY;
        selectedRow = mainTable.getSelectedRow();

        if (selectedRow >= 0) {
            realRow = mainTable.getRowSorter().convertRowIndexToModel(selectedRow);
        }

        return realRow;
    }

    /* Отметить запись по ее номеру (выбрать). Пока не получилось. */
    public void setSelectedIndex(int index) {
        Log.l.debug("TablePanel (", getName(), ").setSelectedIndex : Start. index = ", index);
        mainTable.setRowSelectionInterval(index, index);
        Log.l.debug("TablePanel (", getName(), ").setSelectedIndex : Finish. index = ", index);
    }

    /**
     * Получить обьект по выбранному номеру, с учетом сортировки.
     *
     * @param rowIndex Номер выбранной записи таблицы.
     * @return Реальный обьект
     */
    public D getItem(int rowIndex) {
        int realRow;

        realRow = mainTable.getRowSorter().convertRowIndexToModel(rowIndex);

        return (D) ((ATableModel) mainTable.getModel()).getItem(realRow);
    }

    public Vector<F> getCurrentFields() {
        M tableModel1;

        tableModel1 = getTableModel();
        return tableModel1.getFields();
    }

    /* Занести используемые колонки - с диалога изменения колонок. */
    public void setFields(Vector<F> fields)  {
        M tableModel1;
        RowSorter sorter;

        tableModel1 = getTableModel();

        // убить сортировку
        sorter = getTable().getRowSorter();
        if (sorter != null)    sorter.setSortKeys(null);

        tableModel1.setColumnFields(fields);
        updateFields();

        // сохранить изменения в профиле пользователя и в БД
        //saveColumnEnablesUserProfile(fields);
    }

    /* Создать колонку 'по-умолчанию' (начало работы) */
    /*
    protected boolean addUserProfileParam(TableUserProfile userProfile, String paramNamePrefix, F field,
                                          boolean enableDefault, int numberDefault) {
        String id;
        TableColumnParam column;

        if (!isUseUserProfile()) {
            return false;
        }

        column = new TableColumnParam(field.toString(), field.getName());
        // enable
        id = paramNamePrefix + TableUserProfile.ENABLE + field.toString();
        column.setValueEnable(id, enableDefault);
        // number
        if (numberDefault >= 0) {
            id = paramNamePrefix + TableUserProfile.NUMBER + field.toString();
            column.setValueNumber(id, numberDefault);
        }

        userProfile.addColumn(field.toString(), column);

        firstHandleColumn(column, field);

        return column.isEnable();
    }
    */

    /**
     * Первичная обработка колонки - при создании колонки как обьекта.
     *
     * @param column Обьект колонки.
     * @param field  Имя колонки.
     */
    /*
    protected void firstHandleColumn(TableColumnParam column, F field) {
    }
    */

    /**
     * Cохранить изменения в профиле пользователя и в БД
     *
     * @ param fields Список полей, полученных из диалога настроек полей - это включенные поля.
     * @throws WEditException Ошибки сохранения
     */
    /*
    protected void saveColumnEnablesUserProfile(Vector<F> fields) throws WEditException {
        TableUserProfile userProfile1;

        if (!isUseUserProfile()) {
            return;
        }

        // Занести новые значения columnEnable в профиль. Сохранить их в БД если были изменения.
        if (!isUserProfileExist()) {
            throw new WEditException("Нет возможности сохранить профиль пользователя по причине отсутствия профиля.");
        }

        userProfile1 = getUserProfile();
        // выключить все колонки
        userProfile1.setAllEnables(false);
        // включить те что в списке
        for (F field : fields) {
            userProfile1.updateEnableColumns(field.toString(), field.getName(), true);
        }

        // сохраняем
        userProfile1.saveEnables();
    }
    */

    @Override
    protected void setWidth() {
        TableColumnModel columnModel;
        Enumeration<TableColumn> en;
        TableColumn column;
    //    TableColumnParam columnParam;
        String ruName;
        Integer columnWidth;
        Vector<F> fields;
        TreeMap<String, Float> widths;
        IWidthField widthField;
        Float iwidth;

        Log.l.debug("TablePanel (", getName(), ").setWidth: Start");

        widths = new TreeMap<>();
        fields = (Vector<F>) getFullFields();

        for (F field : fields) {
            if (field instanceof IWidthField) {
                widthField = (IWidthField) field;
                if ((widthField.getWidth() > 0) && (widthField.getWidth() < 1)) {
                    widths.put(widthField.getName(), widthField.getWidth());
                }
            } else {
                break;
            }
        }

        Log.l
                .debug("TablePanel (", getName(), ").setWidth: widths size = ", widths.size(), ". isUseUserProfile = ",
                        isUseUserProfile());

        if (!isUseUserProfile() && widths.isEmpty()) {
            return;
        }

        columnModel = mainTable.getColumnModel();
        en = columnModel.getColumns();
        while (en.hasMoreElements()) {
            column = en.nextElement();
            // Взять текст заголовка. По нему можно найти объект колонки в профиле колонок.
            ruName = (String) column.getHeaderValue();

            iwidth = widths.get(ruName);
            /*
            // взять нашу колонку для этого заголовка
            columnParam = userProfile.getColumn(ruName);
            if ((columnParam == null) && (iwidth == null)) {
                continue;  // здесь мы не можем создать объект тк отсутствует имя_англ. (можно поискать через
            }
            // взять ширину
            if (columnParam != null) {
                columnWidth = columnParam.getWidth();
                if (columnWidth != null) {
                    column.setPreferredWidth(columnWidth);   // setWidth  setMaxWidth
                } else if (iwidth != null) {
                    column.setPreferredWidth((int) (iwidth * getWidth()));
                }

            } else if (iwidth != null) {
                column.setPreferredWidth((int) (iwidth * getWidth()));
            }
            */

            column.setPreferredWidth((int) (iwidth * getWidth()));
        }
        Log.l.debug("TablePanel (", getName(), ").setWidth: Finish");
    }

    public void initTable(M tableModel) {
        mainTable = new WTable(tableModel);
        mainTable.setRowHeight(GCons.TABLE_ROW_HEIGHT);
        mainTable.setName(getName());

        JTableHeader header = mainTable.getTableHeader();
        final TableCellRenderer tcrOs = header.getDefaultRenderer();
    //    header.setDefaultRenderer(new TableHeaderCellRenderer(tcrOs));

        header.setBackground(WCons.GREEN_2);

        mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        RowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        mainTable.setRowSorter(sorter);

    //    mainTable.addMouseListener(new SimpleTableReselectListener(mainTable));

        // cтандартные табличные операции
    //    mainTable.addMouseListener(new SimpleTablePopupListener(this));

        // в том классе ничего не делается
        //header.getColumnModel().addColumnModelListener ( new ColumnChangeListener(mainTable) );
        add ( new JScrollPane(mainTable), BorderLayout.CENTER );

        // режим изменения ширины всех колонок в случае изменения ширины одной колонки - см. programmer_guid
        //mainTable.setAutoResizeMode ( JTable.AUTO_RESIZE_OFF );

        //ColumnChangeListener columnChangeListener = new ColumnChangeListener();
        //mainTable.getColumnModel().addColumnModelListener ( columnChangeListener );
    }

    public WTable getEltexTable() {
        return (WTable) mainTable;
    }

    public M getTableModel() {
        return (M) mainTable.getModel();
    }

    /*
    public boolean isUserProfileExist() {
        return userProfile != null;
    }
    */

    /* Есть ли указанная колонка среди текущих */
    public boolean contains(F field) throws WEditException {
        Vector<F> fields = getCurrentFields();
        return fields.contains(field);
    }

    public String getTabName() {
        return tabName;
    }

    /**
     * Дать команду на завершение редактирования. Например, при нажатии кнопки 'Сохранить' в ситуации,
     * когда какая-то ячейка осталась с открытым редактором (т.е. введенное значение не перенесено в обьект и
     * находится пока только в
     * самом редакторе, и необходимо сначала дать команду, чтобы редактор перенес это значение в обьект).
     * <BR/> Заодно проверяется состояние редакторов - можно ли завершать редактирование. Если хотя бы один редактор
     * не может завершиться
     * (например, из-за ошибки валидации - т.к. редактор просто не может принять это значение, и следовательно, не
     * может переписать его в обьект),
     * - сообщать об этом наверх, чтобы, например, не запускать функцию 'Сохранения'.
     * <BR/> svj, 2010-12-24
     *
     * @return TRUE - успешно завершено редактирование всех ячеек. FALSE - какой-то редактор (или несколько)
     * обнаружил ошибку валидации.
     */
    /*
    public boolean stopEditing() {
        boolean result;
        boolean bb;
        TableColumnModel columnModel;
        TableColumn tableColumn;
        Enumeration<TableColumn> columns;
        TableCellEditor editor;
        EltexCellEditor eltexEditor;

        result = true;
        columnModel = getTable().getColumnModel();
        columns = columnModel.getColumns();

        while (columns.hasMoreElements()) {
            tableColumn = columns.nextElement();
            editor = tableColumn.getCellEditor();
            if (editor != null) {
                // todo Необходимо сначала проверять на активность редактора. Если он не активен, то в нем нет
                // никаких значений
                //   и валидация null объекта может сгенерить ошибку данных (not null)

                if (editor instanceof EltexCellEditor) {
                    // валидируем тихо, без ругани
                    eltexEditor = (EltexCellEditor) editor;
                    bb = eltexEditor.stopEditing(true);
                } else {
                    bb = editor.stopCellEditing();
                }
                // если хоть один редактор НЕ закрылся, значит ошибка валидации данных и Сохранять пока нельзя.
                if (!bb) {
                    result = false;
                }
            }
        }

        return result;
    }
    */

    public void validateString(String fieldName, String value, int strFieldSize, boolean notNull)
            throws WEditException {
        if (value != null) {
            value = value.trim();
        }

        if (value != null) {
            if (value.length() > strFieldSize) {
                throw new WEditException(null, fieldName, ": Длина строки '", value.length(),
                        "' больше максимально допустимой '", strFieldSize, "'");
            } else if (value.isEmpty() && notNull) {
                throw new WEditException(null, fieldName, ": Значение не может быть пустым");
            }
        } else {
            if (notNull) {
                throw new WEditException(null, fieldName, ": Значение не может быть пустым");
            }
        }
    }

    public boolean isSelectAll() {
        return selectAll;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }

    // todo сделать abstract - попытка универсализировать редактирование записей в таблицах отдельным универсальным
    // диалогом.
    public Map<F, AbstractWidget> getItemWidgets(D item) throws WEditException {
        return null;
    }

    public D createItem(Map<F, AbstractWidget> widgets) throws WEditException {
        return null;
    }

    /**
     * Для Bug #11102 При сортировке в таблицах нужно выводить прогресс.
     * Использовать для больших таблиц.
     */
    public void setUseSortBar(boolean val) {
        ((WTable) mainTable).setUseSortBar(val);
    }

    public void setTableItemsMouseListener(MouseListener popupListener) {
        mainTable.addMouseListener(popupListener);
    }

    public void setPopupMouseListener(MouseListener popupListener) {
        List<MouseListener> mlp = new ArrayList<>();
        /*
        for (MouseListener listener : mainTable.getMouseListeners()) {
            if (!(listener instanceof SimpleTablePopupListener)) {
                mlp.add(listener);
            }
        }
        */
        for (MouseListener listener : mainTable.getMouseListeners()) {
            mainTable.removeMouseListener(listener);
        }

        for (MouseListener listener : mlp) {
            mainTable.addMouseListener(listener);
        }

        mainTable.addMouseListener(popupListener);
    }

    public void setDefaultRenderer(Class clazz, TableCellRenderer renderer) {
        getTable().setDefaultRenderer(clazz, renderer);
    }

    public void setChangeColor(Color color) {
        getEltexTable().setChangeColor(color);
    }

    /*
    public TableDataReloadActionListener getReloadActionListener() {
        return reloadActionListener;
    }

    public void setReloadActionListener(TableDataReloadActionListener reloadActionListener) {
        this.reloadActionListener = reloadActionListener;
    }
    */

    /**
     * Передвижение выделенной записи  таблицы в окно видимости - толкьо при наличии JScrollPane.
     * <br/> Здесь необходимо вычислить координаты выделенной записи, т.к. скролл - только по координатам области
     * (Rectangle).
     * <BR/>
     * <BR/> частично взято на http://stackoverflow.com/questions/853020/jtable-scrolling-to-a-specified-row-index
     * <BR/> но он передвигал так, что начало выделения в самом низу таблицы. хочется чтоб было наверху
     * <BR/>
     *
     * @param rowIndex  - view index
     * @param vColIndex
     * @param resetTop  - сбрасывать сначала вверх, так как переход вверх что-то глючит
     */
    public void scrollToVisible(int rowIndex, int vColIndex, boolean resetTop) {
        JTable table;
        JViewport viewport;
        Dimension dim;
        Dimension dimOne;
        Rectangle rectOne;
        Rectangle rect;

        table = getTable();

        if (table.getRowCount() < 1) {
            return;
        }
        if (!(table.getParent() instanceof JViewport)) {
            return;
        }

        viewport = (JViewport) table.getParent();

        //Первым делом сдвигаем наверх, а то автопрокрутка вверх глючит!
        if (resetTop) {
            viewport.setViewPosition(new Point(0, 0));
        }

        // The location of the viewport relative to the table
        dim = viewport.getExtentSize();
        // cell dimension
        dimOne = new Dimension(0, 0);

        // This rectangle is relative to the table where the  northwest corner of cell (0,0) is always (0,0).
        rect = table.getCellRect(rowIndex, vColIndex, true);
        if ((rowIndex + 1) < table.getRowCount()) {
            if ((vColIndex + 1) < table.getColumnCount()) {
                vColIndex++;
            }
            rectOne = table.getCellRect(rowIndex + 1, vColIndex, true);
            dimOne.width = rectOne.x - rect.x;
            dimOne.height = rectOne.y - rect.y;
        }

        // Translate the cell location so that it is relative
        // to the view, assuming the northwest corner of the
        // view is (0,0)
        // '+ veiw dimension - cell dimension' to set first selected row on the top

        rect.setLocation(rect.x + dim.width - dimOne.width, rect.y + dim.height - dimOne.height);
        Log.l
                .debug("--- TablePanel(", getName(), ").scrollToVisible: rowIndex = ", rowIndex, "; view rect = ",
                        rect);

        table.scrollRectToVisible(rect);
    }

    public boolean isUseScrollToVisible() {
        return useScrollToVisible;
    }

    public void setUseScrollToVisible(boolean useScrollToVisible) {
        this.useScrollToVisible = useScrollToVisible;
    }

    public boolean isDoUpScroll() {
        return doUpScroll;
    }

    public void setDoUpScroll(boolean doUpScroll) {
        this.doUpScroll = doUpScroll;
    }

    /*
    protected boolean isNodeSelected() {
        TreeObj obj = Par.GM.getCurrentObj();
        return obj != null && (obj instanceof DbObj);
    }
    */

    public boolean isUseHorizontalScroll() {
        return getTable().getAutoResizeMode() == JTable.AUTO_RESIZE_OFF;
    }

    public void setUseHorizontalScroll(boolean useHorizontalScroll) {
        if (useHorizontalScroll) {
            getTable().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        } else {
            getTable().setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        }
    }
}
