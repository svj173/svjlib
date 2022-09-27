package svj.svjlib;

import svj.svjlib.gui.WComponent;
import svj.svjlib.gui.listener.WEditWindowAdapter;
import svj.svjlib.obj.BookTitles;

import javax.swing.*;

/**
 * Главный фрейм
 * <BR/>
 */
public class ContentFrame extends JFrame implements WComponent {

    public void init (BookTitles bookTitles)
    {
        Log.l.debug ( "ContentFrame.init: Start." );

        // Повесить на крестик фрейма функцию закрытия
        addWindowListener ( new WEditWindowAdapter() );

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


}
