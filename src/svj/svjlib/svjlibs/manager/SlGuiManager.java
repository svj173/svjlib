package svj.svjlib.svjlibs.manager;

import svj.svjlib.ContentFrame;
import svj.svjlib.gui.panel.EditablePanel;
import svj.svjlib.gui.panel.SimpleEditablePanel;
import svj.svjlib.gui.panel.WPanel;
import svj.svjlib.gui.tabs.TabsPanel;
import svj.svjlib.svjlibs.panel.AuthorListPanel;

import javax.swing.*;

import java.awt.*;

/**
 * <BR/>
 */
public class SlGuiManager {

    public void initFrame(ContentFrame contentFrame) {

        final TabsPanel<WPanel> tabsPanel;
        tabsPanel = new TabsPanel<WPanel>();
        tabsPanel.setTabPlacement ( JTabbedPane.TOP );
        String tabsName    = "tabsPanel";
        tabsPanel.setName(tabsName);
        tabsPanel.getAccessibleContext().setAccessibleName (tabsName);
        // Другой цвет на всю нижнюю панель - чтобы отличалась от верхней.
        //tabsPanel.setBackground ( WCons.BOTTOM_TABS );

        String tabId;
        WPanel               tabPanel;

        tabId = "Genre";
        tabPanel = createGenrePanel (tabId);
        tabsPanel.addPanel ( tabPanel, tabId, "Жанры" );

        tabId = "Authors";
        tabPanel = createAuthorsPanel (tabId);
        tabsPanel.addPanel ( tabPanel, tabId, "Авторы" );

        tabId = "BookNames";
        tabPanel = createBookNamesPanel (tabId);
        tabsPanel.addPanel ( tabPanel, tabId, "Названия книг" );


        contentFrame.getContentPane().add ( tabsPanel, BorderLayout.CENTER );



    }

    /**
     * Состав: Три горизонт панели
     * 1) Полный список жанров
     * 2) Спсико авторов выбранного жанра (+ кол-во его книг) -- ? а книги этого автора других жанров?
     * - hd-фильтр по жанру
     * 3) BooksPanel
     * - список книг (таблица)
     * - аннотация выбранной книги
     * - кнпоки: Экспорт, Экспор удаленный
     *
     *
     * @param name
     * @return
     */
    private EditablePanel createGenrePanel(String name) {
        SimpleEditablePanel result = new SimpleEditablePanel();

        result.setName ( name );
        result.setId ( name );

        result.setLayout ( new BorderLayout(5,5) );

        // две горизонтальынх панели - Список жанров - Список книг выбранного жанра (по авторам)

        return result;
    }

    /**
     * Поиск только по авторам
     * Состав: Две горизонт панели
     * 1) две верт панели
     * - Фитры (Автор, RU) + Обнвоить
     * - Список авторов + кол-во книг
     * 2) BooksPanel
     *
     * @param name
     * @return
     */
    private WPanel createAuthorsPanel(String name) {
        WPanel result = new AuthorListPanel();

        return result;
    }

    /**
     * Поиск только по названиям книг
     * Состав: Одна панель - разбита на две верткиальных
     * 1) Фитры (Название, RU) + Обнвоить
     * 2) BooksPanel
     *
     * @param name
     * @return
     */
    private EditablePanel createBookNamesPanel(String name) {
        SimpleEditablePanel result = new SimpleEditablePanel();

        result.setName ( name );
        result.setId ( name );

        result.setLayout ( new BorderLayout(5,5) );

        // три вертикальных панели
        // - Кнопки Поиска, Экспорта - Список книг выбранного жанра (по авторам)

        return result;
    }


}
