package svj.svjlib.gui.panel;


import svj.svjlib.Log;

import javax.swing.*;

import java.awt.*;


/**
 * Панель содержит дерево и список функций, которые можно перегружать (rewrite) - например, для изменения иконок функций.  
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 05.08.2011 16:34:34
 */
public class IconsPanel extends RewritePanel
{
    private int     currentIconSize;
    //private final Collection<Function> functions;


    public IconsPanel ( String name )
    {
        setName ( name );
        setLayout ( new FlowLayout( FlowLayout.LEFT,0,0) );
        setBorder ( BorderFactory.createEtchedBorder() );

        currentIconSize = 24;
        //currentIconSize = Par.PANEL_ICON_SIZE;
        //functions       = new ArrayList<Function>();
    }

    @Override
    public void rewrite ()
    {
        Log.l.debug ( "Start ({}).",getName() );
        /*
        // Перерисовываем иконки - только если изменился размер иконок.
        if ( currentIconSize != Par.PANEL_ICON_SIZE )
        {
            // Удаляем все иконки функций.
            removeAll();
            for ( Function function : functions )
            {
                Log.l.debug ( "(",getName(), ") --- function = ", function );
                addIcon ( function );
                //reinitIcon ( function );   // Перерисовать иконки
            }
            repaint();
            revalidate();
            currentIconSize = Par.PANEL_ICON_SIZE;
        }

        for ( Function function : functions )
        {
            function.rewrite ();
        }
        */
        Log.l.debug ( "Finish ({}).",getName() );
    }

    /*
     * Иметь ссылку на свою функцию чтобы можно было корректно перерисовать иконку.
     * Добавляем только если есть Иконка. Если нет иконки - берем unknow.png (16)
      */
    /*
    public void addFunction ( Function function )
    {
        if ( function == null )  return;

        functions.add ( function );
        addIcon ( function );
    }

    private void addIcon ( Function function )
    {
        JComponent comp;

        try
        {
            comp    = function.getGuiComponent ( Par.PANEL_ICON_SIZE );
            Log.l.debug ( "(%s)",getName(), " --- function = ", function, " --  add gui comp = ", comp );
            if ( comp != null )
            {
                comp.setName ( function.getName() );
                comp.setToolTipText ( function.getName() );
                add ( comp );
            }

        } catch ( Exception e )         {
            // Не добавилась иконка - не страшно.
            Log.l.error ( Convert.concatObj ( "Не добавилась иконка для функции '", function, "'." ), e);
        }
    }

    public Function getFunction ( FunctionId functionId )
    {
        if ( functionId != null )
        {
            for ( Function function : functions )
            {
                if ( function.getId() == functionId ) return function;
            }
        }
        return null;
    }
    */
}
