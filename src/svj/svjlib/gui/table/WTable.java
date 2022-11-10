package svj.svjlib.gui.table;


import svj.svjlib.Log;
import svj.svjlib.WCons;
import svj.svjlib.tools.Convert;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;


/**
 * Свои изменения внутри обьекта JTable.
 * <BR/> - Увеличение функциональности рендерера (черезстрочная расскраска и т.д.).
 * <BR/> - Добавлена возможность навешивать листенеры выборки записей в таблице.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 14.06.2011 15:15:13
 */
public class WTable extends JTable {
    /* Список слушателей на изменение выборки записи в таблице. */
    private final Collection<ListSelectionListener> selectListeners;
    private ATableModel model;
    private boolean useSortBar = false; // использовать прогресс при сортировке
    private boolean sorted = true;

    /* Цвет для принудительной смены цвета строки таблицы. Применяется для выделения при поисках и т.д. */
    private Color changeColor;

    //Шаг смены цветов строк при черезстрочной раскраске.
    private int colorChangeStep = 1;


    public WTable(ATableModel tableModel) {
        this(tableModel, 1);

        //Log.l.debug ( "EltexTable::" );
    }

    public WTable(ATableModel tableModel, int colorChangeStep) {
        super(tableModel);

        model = tableModel;
        selectListeners = new ArrayList<>();
        changeColor = null;

        this.colorChangeStep = colorChangeStep;

        //Log.l.debug ( "EltexTable::" );
    }

    public void valueChanged(ListSelectionEvent event) {
        // Сначала отдаем события слушателям-подписчикам на обработку
        for (ListSelectionListener listener : selectListeners) {
            listener.valueChanged(event);
        }

        // Вызываем основной метод
        super.valueChanged(event);
    }

    public void addSelectListener(ListSelectionListener listener) {
        selectListeners.add(listener);
    }

    /**
     * Некоторые действия с рендерером для конкретной ячейки. Можно добавить что-то свое.
     * <BR/> Перекрашивает цвет фона, установленный в рендерерах конкретных столбцов (например, подсветка Статусов
     * событий).
     * <BR/>
     * <BR/> 1) Добавляет в стандартный рендерер ячейки таблицы через-строчную подсветку
     * <BR/>
     * <BR/> Алгоритм
     * <BR/> 1)
     * <BR/>
     * <BR/>
     *
     * @param renderer рендерер
     * @param row      строка
     * @param column   столбец
     * @return новый рендерер
     */
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component component;
    //    EltexTableCellRenderer eltexRenderer;
        boolean changeBackground;
        boolean rowSelected;
        boolean useOdd;
        int columnSize;
        int rowSize;

        columnSize = -1;
        rowSize = -1;

        try {
            // Проверяем на граничные значения - строка, столбец.
            columnSize = getEltexTableModel().getColumnCount();
            if (column >= columnSize) {
                Log.l
                        .warn("EltexTable (%s).prepareRenderer: Wrong column number. column = %d; column size = %d",
                                getName(), column, columnSize);
                return new JLabel("(c)");
            }
            rowSize = getEltexTableModel().getRowCount();
            if (row >= rowSize) {
                Log.l
                        .warn("EltexTable (%s).prepareRenderer: Wrong row number. row = %d; row size = %d"
                                , getName(), row, rowSize);
                return new JLabel("(r)");
            }

            // Получить ячейку таблицы, уже обработанную рендерами (отрисовать ячейку).
            component = super.prepareRenderer(renderer, row, column);

            //Log.l.debug ( "-- EltexTable.prepareRenderer: row = ", row, ", column = ", column, ",
            // obj = ", component );

            // - Выбрана ли она
            rowSelected = isRowSelected(row);
            //Log.l.debug ( "---- EltexTable.prepareRenderer: rowSelected = ", rowSelected );

            // - Было ли изменение фона ячейки
            changeBackground = false;    // для всех остальных renderer's
            /*
            if (renderer instanceof EltexTableCellRenderer) {
                eltexRenderer = (EltexTableCellRenderer) renderer;
                changeBackground = eltexRenderer.isChangeBackground();
            }
            */
            //Log.l.debug ( "---- EltexTable.prepareRenderer: changeBackground = ", changeBackground );

            // Проверяем на принудительную подсветку - т.е. обязательно подсветить - но только если строка не выбрана
            // и цвет ячейки не был уже изменен.
            if (rowSelected || changeBackground) {
                useOdd = true;
            } else {
                useOdd = processNecessaryColor(component, row);
            }

            // Через-строчная подсветка
            if (useOdd) {
                paintOdd(component, row, rowSelected, changeBackground);
            }

            // Подсветка строки редактирования
            //paintEdit ( component, row, rowSelected, changeBackground, editableRow );

        } catch (Exception e) {
            Log.l.error("WTable ({}).prepareRenderer: error. row = {}; column = {}; columnSize = {}; rowSize = {}",
                    getName(), row, column, columnSize, rowSize);
            // Убрал (e) - т.к. Е почему-то отображается жирным шрифтом. И если изменение содержания таблицы
            // происходит когда половина таблицы уже отрисовалась, то во второй половинке - много строчек с Е. И
            // только потом - нормальная таблица.
            // - т.е. происходит мелькание какого-то жирного текста.
            //component = new JLabel ( "(e)" );
            component = new JLabel("");
        }

        return component;
    }

    /**
     * В самой таблице, а также в обьектах таблицы могут быть заданы параметры - изменить цвет записи в таблице
     * (например, необходимо выделить записи, удовлетворяющие условию поиска).
     * Проверяем это и при необходимости меняем цвет на заданный в обьекте записи.
     *
     * @param component Компонента отрисовки.
     * @param row       Отображаемая строка таблицы.
     * @return Отключить работу черезстрочной подсветки. false - цвет был изменен (чтобы далее этот цвет не
     * изменился, например, на цвет черезстрочный).
     */
    private boolean processNecessaryColor(Component component, int row) {
        Object obj;
        //IChangeColor chColor;
        boolean result;
        Color color;
        Color itemColor;

        result = true;
        /*
        color = getChangeColor();

        // Проверяем необходимость изменения цвета фона только если цвет для этого действия задан.

        // Берем обьект данной записи таблицы.
        // Зачем это сюда??? падает рендерер в Мониторинг системы ems - память java! (mgaidamak)
        if (getRowSorter() != null) {
            int[] realRows = GuiTools.createRealIndex(getRowSorter(), new int[] {row});
            obj = getEltexTableModel().getItem(realRows[0]);
        } else {
            obj = getEltexTableModel().getItem(row);
        }

        if (obj instanceof IChangeColor) {
            chColor = (IChangeColor) obj;
            itemColor = chColor.getChangeColor();

            if (chColor.needChangeColor()) {
                // меняем цвет и устанавливаем флаг - больше цвет не менять.
                if (color != null) {
                    component.setBackground(color);
                } else if (itemColor != null) {
                    component.setBackground(itemColor);
                }
                result = false;  // Отключить работу черезстрочной подсветки
            }
        }
        */

        return result;
    }


    private void paintOdd(Component component, int row, boolean rowSelected, boolean changeBackground) {
        // В глобальном методе getTableCellRendererComponent этот функционал уже используется. Но там исп цвет из
        // проперти Table.alternateRowColor.
        //  Надо его переустановить. -- В конструкторе EltexGeneralFrame -- Не помогло, здесь все равно возвращает
        // NULL. svj, 2011-06-16
        Color alternateColor;
        Color bgColor;

        //alternateColor = DefaultLookup.getColor(this, ui, "Table.alternateRowColor");
        alternateColor = WCons.GRAY_1;
        //Log.l.debug ( "---- EltexTable.prepareRenderer: alternateColor = ", alternateColor );

        // раз нет alternateColor, значит в глобальном методе getTableCellRendererComponent не была обработана
        // через-строчная подсветка - делаем здесь.
        if ((!rowSelected) && (alternateColor == null)) {
            if ((row / colorChangeStep) % 2 == 0) {
                bgColor = WCons.TABLE_EVEN_ROW_COLOR;
            } else {
                bgColor = WCons.TABLE_ODD_ROW_COLOR;
            }
            //Log.l.debug ( "------ EltexTable.paintOdd: Paint ODD. color = ", bgColor );

            if (!changeBackground) {
                // меняем фон всей ячейки
                component.setBackground(bgColor);
            }
        }
    }

    private void paintBorder(Component component, Color bgColor) {
        JComponent comp;

        // Если у ячейки уже изменили фон - отрисовать ей бордюр
        if (component instanceof JComponent) {
            comp = (JComponent) component;
            //comp.setBorder ( GCons.TABLE_ROW_EDIT_BORDER );
            comp.setBorder(new LineBorder(bgColor, 2));
            //Log.l.debug ( "------ EltexTable.paintEdit: Paint BORDER = ", bgColor );
        }
    }

    private void paintEdit(Component component, int row, boolean rowSelected, boolean changeBackground,
                           boolean editableRow) {
        // Только если это строка редактирования
        if (editableRow) {
            if (changeBackground) {
                // Если у редактируемой ячейки уже изменили фон - отрисовать ей бордюр
                paintBorder(component, WCons.TABLE_ROW_EDIT_COLOR);
            } else {
                // Фон не менялся - рисуем свой
                component.setBackground(WCons.TABLE_ROW_EDIT_COLOR);
                //Log.l.debug ( "------ EltexTable.paintEdit: Paint YELLOW Background" );
                // Также изменяем и цвет текста, т.к. у некоторых видов оформления он - белый.
                component.setForeground(Color.BLACK);
            }
        }
    }

    public ATableModel getEltexTableModel() {
        return model;
    }

    @Override
    public void sorterChanged(RowSorterEvent e) {
        /*
        WaitingDialogUnmodal waitingDialog;
        EltexTableSortWorker worker;

        Log.l.debug("EltexTable (", getName(), ").sorterChanged : Started ", e.getType());

        try {
            if (useSortBar && e.getType().equals(RowSorterEvent.Type.SORT_ORDER_CHANGED)) {
                this.sorted = false;
                worker = new EltexTableSortWorker(this);
                if (Par.GM.getFrame().isFocused()) {
                    // Отображать бегунок только если пользователь работает в этом окне
                    // иначе, незачем ему выскакивать
                    waitingDialog = new WaitingDialogUnmodal(worker,
                            Convert.concatObj(CommonLanguageLabel.SORT_IN_PROGRESS.getName(), "..."));
                    waitingDialog.start(this.getParent());
                }
            } else if (useSortBar && e.getType().equals(RowSorterEvent.Type.SORTED)) {
                this.sorted = true;
            }
        } catch (Exception ex) {
            Log.l.error(ex, "EltexTable (", getName(), ").sorterChanged : error. event = ", e);
        }
        */

        super.sorterChanged(e);

        Log.l.debug("EltexTable (", getName(), ").sorterChanged : Finished ", e.getType());
    }

    @Override
    public String getToolTipText(MouseEvent e) {
        String tip = null;
        Object otip = null;
        java.awt.Point p = e.getPoint();
        int hitRowIndex = rowAtPoint(p);
        int hitColumnIndex = columnAtPoint(p);

        if (hitColumnIndex != -1 && hitRowIndex != -1) {
            boolean mathWidth = true;
            TableCellRenderer renderer = getCellRenderer(hitRowIndex, hitColumnIndex);
            Component component = prepareRenderer(renderer, hitRowIndex, hitColumnIndex);
            Rectangle cellRect = getCellRect(hitRowIndex, hitColumnIndex, false);
            component.setBounds(cellRect);
            component.validate();
            component.doLayout();
            p.translate(-cellRect.x, -cellRect.y);
            Component comp = component.getComponentAt(p);
            if (comp instanceof JComponent) {
                Font font = component.getFont();
                FontMetrics fontMetrics = component.getFontMetrics(font);
                otip = getValueAt(hitRowIndex, hitColumnIndex);
                if (otip == null || otip instanceof JComponent) {
                    tip = null;
                } else if (otip instanceof Date) {
                    tip = Convert.getDateAsStr((Date) otip, WCons.DD_MM_YYYY_HH_MM_SS);
                    /*
                } else if (otip instanceof WifiMac) { //Для этого типа показывать подсказку вне зависимости от ширины
                    // столбца
                    tip = ((WifiMac) otip).getTooltip();
                    mathWidth = false;
                    */
                } else {
                    tip = otip.toString();
                }

                if (tip != null && mathWidth) {
                    int width = fontMetrics.stringWidth(tip);
                    int colWidth = getColumnModel().getColumn(hitColumnIndex).getWidth();
                    if ((width + 2) < colWidth) {
                        tip = null;
                    }
                }

            } // comp instanceof JComponent
        }

        // No tip from the renderer get our own tip
        if (tip == null) {
            tip = getToolTipText();
        }

        return tip;
    }

    public void setUseSortBar(boolean useBar) {
        this.useSortBar = useBar;
    }

    public boolean getSorted() {
        return this.sorted;
    }

    public Color getChangeColor() {
        return changeColor;
    }

    public void setChangeColor(Color changeColor) {
        this.changeColor = changeColor;
    }

    public int getColorChangeStep() {
        return colorChangeStep;
    }

    public void setColorChangeStep(int colorChangeStep) {
        this.colorChangeStep = colorChangeStep;
    }

    /*
    @Override
    public void tableChanged(TableModelEvent e) {
        try {
            super.tableChanged(e);
        } catch (NullPointerException ex) {
            Log.l.error(String
                    .format("EltexTable.tableChanged. event -%s, %s, %s, %s, %s.", e.getSource(), e.getColumn(),
                            e.getFirstRow(), e.getLastRow(), e.getType()), ex);
        }
        final JTableHeader header = getTableHeader();
        if (header != null && header instanceof EltexTableHeader) {
            ((EltexTableHeader) header).updateHeight();
        }
    }

    @Override
    protected EltexTableHeader createDefaultTableHeader() {
        return new EltexTableHeader(this);
    }
    */

}
