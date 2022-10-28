package svj.svjlib.gui.table;

import svj.svjlib.Log;
import svj.svjlib.exc.WEditException;

import javax.swing.*;

import java.util.*;

/**
 * Общий интерфейс работы с панелью таблицы.
 */
public abstract class CommonPanel extends JPanel {

    /* Имя вкладки - на русском */
    protected String tabName;
    protected JTable mainTable;
    /* Профиль пользователя для данной таблицы. Профиль стартово загружается из БД только при обращении к данной
странице.
   - final - нельзя переопределять */
    //protected final TableUserProfile userProfile;
    /* Флаг - надо ли использовать профиль пользователя для данной таблицы. */
    private boolean useUserProfile = true;
    //private SwitchHorizonScrollWidget switchHorizonScrollWidget = null;

    CommonPanel(String objFullType, String tabName) {
        //userProfile = new TableUserProfile(objFullType, tabName);
    }

    public boolean isUseUserProfile() {
        return useUserProfile;
    }

    public void setUseUserProfile(boolean useUserProfile) {
        this.useUserProfile = useUserProfile;
    }

    /**
     * Получить максимально полный список используемых в таблице полей.
     *
     * @return Список всех полей.
     */
    public abstract Vector<?> getFullFields();

    /**
     * Вызывается при занесении обьектов полей в модель. Для возможности каких-то индивидуальных изменений (см.
     * CpeListAllTablePanel).
     */
    protected abstract void updateFields();

    /**
     * Метод дергается при каждом изменении в дереве. Т.е. при каждом выборе нового объекта в дереве вызывается
     * данный метод.
     * <BR/> Здесь только работа с профилем пользователя по этой таблице.
     * <BR/> Фактически это beforeReload.
     *
     * @throws WEditException исключение при работе с профилем по этой таблице.
     */
    public void init() throws WEditException {
        List<? extends RowSorter.SortKey> sortKeys;
        //Collection<TableColumnParam> enables;
        RowSorter sorter;
        boolean loaded;
        boolean loadEnables;
        boolean useHorizontalScroll;

        Log.l.debug("CommonPanel (", getName(), ").init: Start. tabName = '", "", "'; isUseUserProfile() = ",
                        isUseUserProfile());


        Log.l.debug("CommonPanel (", getName(), ").init: Finish");
    }

    public JTable getTable() {
        return mainTable;
    }

    public void setUseHorizontalScroll(boolean useHorizontalScroll) {
        if (useHorizontalScroll) {
            mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        } else {
            mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        }
    }


    /**
     * Проверить изменения ширины колонки
     */
    protected abstract void setWidth();


    protected int getDefaultRowSorterColumn() {
        return 0;
    }

    protected SortOrder getDefaultRowSorterOrder() {
        return SortOrder.ASCENDING;
    }
}
