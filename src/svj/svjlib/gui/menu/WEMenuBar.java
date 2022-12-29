package svj.svjlib.gui.menu;

import svj.svjlib.svjlibs.tools.SLTools;
import svj.svjlib.tools.DialogTools;
import svj.svjlib.tools.GuiTools;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Расширили JMenuBar добавив возможность поиска вложенного обьекта по его ИД.
 * <BR/> А также - добавление меню в конкретную область. Если область отсутствует - создается (рекурсивно)
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 15.07.2011 16:51:09
 */
public class WEMenuBar extends JMenuBar
{
    public WEMenuBar ()
    {
        super();

        WEMenu menu;

        // ---- наполнить меню для SvjLib ----

        // Библиотека
        menu    = SLTools.createLibMenu();
        add ( menu );

        // Настройки
        menu    = SLTools.createSettingsMenu();
        add ( menu );

        // Статистика
        menu    = SLTools.createStatisticMenu();
        add ( menu );


        // help -- не реализовано в java
        setHelpMenu ( new JMenu("Помощь") );
    }

    public WEMenu getMenu ( String id )
    {
        String idc;
        Component comp;
        int ncomponents = this.getComponentCount();
        Component[] component = this.getComponents();
        for ( int i = 0; i < ncomponents; i++ )
        {
            comp = component[i];
            if (comp instanceof WEMenu)
            {
                WEMenu mi = (WEMenu) comp;
                idc = mi.getId();
                if ( idc.equals ( id ) )   return mi;
            }
        }
        return null;
    }

    public void rewrite ()
    {
        GuiTools.rewriteComponents ( this );
        /*
        Component comp;
        int ncomponents = this.getComponentCount();
        Component[] component = this.getComponents();
        for ( int i = 0; i < ncomponents; i++ )
        {
            comp = component[i];
            if (comp instanceof WEMenu)
            {
                WEMenu mi = (WEMenu) comp;
                mi.rewrite();
            }
        }
        */
    }

    // НЕ исп.
    public WEMenuBar clone ( String id )
    {
        Component   comp;
        WEMenuBar   result;
        WEMenu      mi, newmi;
        JSeparator  sep1, sep2;
        JComboBox   box1, box2;

        result  = new WEMenuBar ();
        int ncomponents = this.getComponentCount();
        Component[] component = this.getComponents();
        for ( int i = 0; i < ncomponents; i++ )
        {
            comp = component[i];
            if (comp instanceof WEMenu)
            {
                mi      = (WEMenu) comp;
                newmi   = mi.clone(id);
                result.add ( newmi );
                continue;
            }
            if (comp instanceof JSeparator)
            {
                sep1    = (JSeparator) comp;
                sep2 = new JSeparator (sep1.getOrientation());
                result.add ( sep2 );
                continue;
            }
            if ( comp instanceof JComboBox )
            {
                box1    = (JComboBox) comp;
                box2    = new JComboBox (box1.getModel());
                result.add ( box2 );
                //continue;
            }
        }
        return result;
    }


    /* Попытка создать help меню, расположенное справа. Не получилось. (Взято от SUN). */
    public void setHelpMenu ( JMenu menu )
    {
        JMenu       helpMenu;
        JMenuItem   menuItem;

        // пропуск по горизонтали. т.е. сдвинуть данное меню максимально вправо.
        add ( Box.createHorizontalGlue() );

        helpMenu = new JMenu ( "Помощь" );
        add ( helpMenu );

        menuItem = new JMenuItem("О программе"); // Инструкция пользователя с функцией - скинуть в файл (PDF).
        menuItem.setComponentOrientation ( ComponentOrientation.RIGHT_TO_LEFT );
        helpMenu.add ( menuItem );

        // Об авторе
        menuItem = new JMenuItem("Об авторе");
        menuItem.setComponentOrientation ( ComponentOrientation.RIGHT_TO_LEFT );
        menuItem.addActionListener ( new ActionListener ()
        {
            @Override
            public void actionPerformed ( ActionEvent event )
            {
                DialogTools.showMessage ( "Об авторе",
                        "Сергей Афанасьев\n- s_afa@yahoo.com\n- https://www.litres.ru/sergey-afanasev-23644756"
                                + "\n- http://samlib.ru/a/afanasxew_s/" );
            }
        } );
        helpMenu.add ( menuItem );
    }

    /**
     * Gets the help menu for the menu bar.  This method is not yet
     * implemented and will throw an exception.
     * Gets the help menu for the menu bar.
     *
     * @return the <code>JMenu</code> that delivers help to the user
     */
    /*
    public JMenu getHelpMenu ()
    {
        //throw new Error ( "getHelpMenu() not yet implemented." );
        JMenu help = null;
        if ( helpMenu != null )
        {
            JMenu tmphelp = helpMenu.get ();
            if ( tmphelp != null && tmphelp.getParent () == this )
                help = tmphelp;
        }

        if ( helpMenu != null && help == null )
            helpMenu = null;
        return help;
    }
    */

    /*
    public void addMenu ( JComponent newMenu )
    {
        if ( newMenu == null )  return;

        if ( newMenu instanceof WEMenu )
        {
            addMenu ( (WEMenu) newMenu );
        }
        else if ( newMenu instanceof WEMenuItem )
        {
            addMenuItem ( (WEMenuItem) newMenu );
        }
        else
        {
            add (newMenu);
        }
    }

    public void addMenu ( WEMenu newMenu )
    {
        String  menuPath;
        WEMenu  menu;

        Log.l.debug ( "--- Start. newMenu = ", newMenu );
        if ( newMenu == null )  return;

        //  взять из меню - полный путь до точки расположения этого меню (другое меню). Взять номер группы в точке (меню).
        menuPath    = newMenu.getMenuPath();
        Log.l.debug ( "--- menuPath = ", menuPath );

        menu    = getMenuByPath ( menuPath );
        Log.l.debug ( "menu = ", menu );
        if ( menu != null ) menu.add ( newMenu, newMenu.getGroup() );
    }

    public void addMenuItem ( WEMenuItem menuItem )
    {
        String  menuPath;
        WEMenu  menu;

        Log.l.debug ( "--- Start. newMenu = ", menuItem );
        if ( menuItem == null )  return;

        //  взять из меню - полный путь до точки расположения этого меню (другое меню). Взять номер группы в точке (меню).
        menuPath    = menuItem.getMenuPath();
        Log.l.debug ( "--- menuPath = ", menuPath );

        menu        = getMenuByPath ( menuPath );
        Log.l.debug ( "menu = ", menu );
        if ( menu != null ) menu.add ( menuItem, menuItem.getGroup() );
    }

    private WEMenu getMenuByPath ( String menuPath )
    {
        int         i;
        WEMenu      menu, menu1;

        Log.l.debug ( "Start. menuPath = ", menuPath );

        if ( menuPath == null )  return null;

        // Разобрать строку пути Меню
        String[] smenu    = menuPath.split ( "/" );
        if ( smenu.length == 0 )
        {
            // TODO По идее, такие ошибки надо запоминать где-то и потом вывести полный список пользователю в красном окне.
            Log.l.error ( "Menu path is NULL" );
            return null;
        }
        Log.l.debug ( "menuPath size = ", smenu.length );

        // Если элементы пути отсутствуют - создать  -- todo рекурсивно

        // - Если отсутствует корневой элемент меню - создать
        menu    = getMenu ( smenu[0] );
        Log.l.debug ( "parent menu = ", menu );
        if ( menu == null )
        {
            menu    = new WEMenu ( smenu[0] );
            //menu.setMenuPath ( smenu[0] );
            add ( menu );
        }

        for ( i=1; i<smenu.length; i++ )
        {
            menu1    = menu.getMenu ( smenu[i] );
            Log.l.debug ( i, ". name = ", smenu[i], ", menu = '", menu1, "'" );
            if ( menu1 == null )
            {
                menu1 = new WEMenu ( smenu[i] );
                menu.add ( menu1 );
                Log.l.debug ( i, ". Create new menu. name = ", smenu[i], ", menuPath = ", menuPath );
            }
            menu    = menu1;
        }

        return menu;
    }
    */

}

