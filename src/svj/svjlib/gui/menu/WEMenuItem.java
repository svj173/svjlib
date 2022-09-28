package svj.svjlib.gui.menu;

import svj.svjlib.Log;
import svj.svjlib.gui.WMenuComponent;
import svj.svjlib.obj.BookObj;
import svj.svjlib.tools.GuiTools;

import javax.swing.*;

import java.awt.event.ActionListener;

/**
 * <BR/>
 */
public class WEMenuItem  extends JMenuItem implements WMenuComponent
{
    /** Идентификатор */
    private String  id;
    /** Какой-то обьект, который связан с данным пунктом меню. */
    private Object  object;


    public WEMenuItem ( String id )
    {
        super ( id );
        this.id  = id;
        //this.key = key;
        object   = null;
    }

    public WEMenuItem ( String id, String iconPath )
    {
        super(id);

        Icon icon;

        this.id  = id;
        //this.key = key;
        if ( iconPath != null )
        {
            try
            {
                icon   = GuiTools.createImageByFile ( iconPath );
                setIcon ( icon );
            } catch ( Exception e )            {
                Log.l.error ( "Ошибка получения иконки для пункта меню '" + id + "'", e);
            }
        }
    }

    public String getId ()
    {
        return id;
    }

    public void rewrite ()
    {
        //logger.debug ( "id = " + id + ", key = " + key );
        //setText ( Msg.getMsg(key) );
    }


    public WEMenuItem clone ( String frameId )
    {
        WEMenuItem  result;
        String      cmd;
        KeyStroke   ks;

        result  = new WEMenuItem ( id );
        //logger.debug ( "id = " + id + ", key = " + key );
        //setText ( Msg.getMsg(key) );
        ActionListener[] al;
        al  = this.getActionListeners ();
        for ( int i=0; i<al.length; i++ )
            result.addActionListener ( al[i]);
        // Взять команду, навешенную функцией и поставить вперед ИД окна части.
        cmd = getActionCommand ();
        result.setActionCommand ( frameId + cmd );
        //logger.debug ( "clone menu item = " + result );
        ks  = this.getAccelerator ();
        if ( ks != null ) result.setAccelerator ( ks );
        return result;
    }

    @Override
    public void init ( BookObj obj )
    {
    }

    @Override
    public BookObj getObj ()
    {
        return null;
    }

    public Object getObject ()
    {
        return object;
    }

    public void setObject ( Object object )
    {
        this.object = object;
    }

}
