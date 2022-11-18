package svj.svjlib;

import svj.svjlib.gui.WComponent;
import svj.svjlib.gui.listener.WEditWindowAdapter;
import svj.svjlib.gui.menu.WEMenuBar;
import svj.svjlib.gui.panel.ServicePanel;
import svj.svjlib.svjlibs.SLCons;

import javax.swing.*;

import java.awt.*;

/**
 * Главный фрейм
 * <BR/>
 */
public class ContentFrame extends JFrame implements WComponent {

    // иконки
    private WEMenuBar       menuBar;

    // панель дял служебных сообщений - memoryLabel, statusMsg, javaVersion
    private ServicePanel servicePanel;


    public ContentFrame() {
        // todo + version / build / create date
        // LibRusEk - SvjLib .....
        super(WCons.PROGRAMM_TITLE);

        // Установить точку вывода
        setLocation ( 200, 100 );
        setDefaultCloseOperation ( WindowConstants.EXIT_ON_CLOSE );

        Dimension   d;
        int         x, y;
        d   = Toolkit.getDefaultToolkit().getScreenSize();
        x   = d.width - (d.width / 4);
        y   = d.height - 100;
        setSize ( x,y );
        Dimension size  = new Dimension ( x,y );
        setPreferredSize(size);


        // иконка
        //Image icon = GuiTools.createImage ( "img/editor/we6_title.png" );
        //if ( icon != null )  setIconImage ( icon );

        // Вешаем листенер на крестик фрейма
        addWindowListener ( new WEditWindowAdapter() );

        // создаем все ГУИ элементы

        // 1. тулбар
        menuBar = new WEMenuBar();
        getRootPane().setJMenuBar ( menuBar );


        // Повесить на крестик фрейма функцию закрытия
        addWindowListener ( new WEditWindowAdapter() );

    }

    public void init ()
    {
        Log.l.debug ( "ContentFrame.init: Start." );

        // Добавляем ГУИ индививдуальные элементы
        SLCons.SL_GUI_MANAGER.initFrame(this);

        // todo Добавляем инфу о книга

        // todo создаем табик - книги по жанрам

        // Перерисовать фрейм согласно установленному языку
        rewrite();

        // Перерисовать меню согласно установленному языку
        rewriteMenu();

        Log.l.debug ( "ContentFrame.init: Finish." );
    }

    @Override
    public void rewrite() {
        /*
        Log.l.debug ( "ContentFrame.rewrite: Start. Par.NEED_REWRITE = %b; Par.WEDIT_STARTED = %b", Par.NEED_REWRITE, Par.WEDIT_STARTED );

        if ( Par.NEED_REWRITE && Par.WEDIT_STARTED )
        {
            GuiTools.rewriteComponents ( this );

            menuBar.rewrite();
            //rewriteMenu();

            // перерисовка рабочих панелей
            projectsPanel.rewrite();
            booksPanel.rewrite();
            textsPanel.rewrite();

            servicePanel.rewrite();

            toolbar.rewrite();

            textAdditionalPanel.rewrite();

            // true - всегда взводим флаг что необходима перерисовка. Функции где это не требуется должны сбрасывать этот флаг.
            //Par.NEED_REWRITE = false;
        }

        if ( ! Par.NEED_REWRITE )  Par.NEED_REWRITE = true;
        */


        Log.l.debug ( "ContentFrame.rewrite: Finish" );

    }

    /**
     * Перерисовать согласно установленному языку -- по идее, вес это выхватывается в rewrite
     */
    public void rewriteMenu()
    {
        // todo Взять меню
        //WEMenuBar   menu    = (WEMenuBar) getRootPane().getJMenuBar();
        //menu.rewrite();
        // Оглавление
        //contentPanel.rewrite ();
    }

    public void setMenuBar ( WEMenuBar menuBar )
    {
        this.menuBar = menuBar;
    }

    public ServicePanel getServicePanel() {
        return servicePanel;
    }
}
