package svj.svjlib.svjlibs.tools;

import svj.svjlib.gui.menu.WEMenu;
import svj.svjlib.gui.menu.WEMenuItem;
import svj.svjlib.svjlibs.listener.ViewLibsListListener;

/**
 * <BR/>
 */
public class SLTools {

    public static WEMenu createLibMenu() {
        WEMenu     result;
        WEMenuItem menu;

        result  = new WEMenu ( "Библиотека" );

        menu    = new WEMenuItem("Список подсоединенных библиотек");
        menu.addActionListener(new ViewLibsListListener());
        result.add ( menu );

        menu    = new WEMenuItem("Добавить библиотеку");
        result.add ( menu );

        return result;
    }


}
