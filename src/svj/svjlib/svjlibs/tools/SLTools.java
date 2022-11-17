package svj.svjlib.svjlibs.tools;

import svj.svjlib.gui.menu.WEMenu;
import svj.svjlib.gui.menu.WEMenuItem;
import svj.svjlib.svjlibs.listener.*;
import svj.svjlib.svjlibs.obj.Genre;

import java.util.*;

/**
 * <BR/>
 */
public class SLTools {

    public static String getGenresAsStr (Collection<String> list)
    {
        String result;
        StringBuilder sb = new StringBuilder( 128 );
        if ( list == null  ) return "";
        if ( list.isEmpty()  ) return "";

        for ( String name : list )
        {
            sb.append ( Genre.getTitle(name) );
            sb.append ( ", " );
        }
        // удалить последнюю запятую и пробел
        result = sb.toString();
        result = result.substring ( 0, result.length() - 2 );
        return result;
    }

    public static WEMenu createLibMenu() {
        WEMenu     result;
        WEMenuItem menu;

        result  = new WEMenu ( "Библиотека" );

        menu    = new WEMenuItem("Список подсоединенных библиотек");
        menu.addActionListener(new ViewLibsListListener());
        result.add ( menu );

        menu    = new WEMenuItem("Добавить библиотеку");
        menu.addActionListener(new LoadLibListListener());
        result.add ( menu );

        return result;
    }

    /*
    public static WEMenu createSearchMenu() {
        WEMenu     result;
        WEMenuItem menu;

        result  = new WEMenu ( "Поиск" );

        menu    = new WEMenuItem("По книг");
        menu.addActionListener(new SearchByAuthorListener());
        result.add ( menu );


        return result;
    }
*/
    public static WEMenu createStatisticMenu() {
        WEMenu     result;
        WEMenuItem menu;

        result  = new WEMenu ( "Статистика" );

        menu    = new WEMenuItem("Количество книг");
        menu.addActionListener(new StatisticListener());
        result.add ( menu );

        /*
        menu    = new WEMenuItem("По названию");
        menu.addActionListener(...);
        result.add ( menu );
        */

        return result;
    }


}
