package svj.svjlib.gui.widget;


import svj.svjlib.WCons;
import svj.svjlib.exc.WEditException;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseListener;


/**
 * Виджет строковых многострочных значений.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 04.02.2014 14:00:18
 */
public class TextWidget extends AbstractWidget<String>
{
    private final JTextArea textField;

    // -- Попытка проявить ползунок - не прошла.
    //private JPanel panel;

    public TextWidget (String titleName, boolean hasEmpty, int rows )
    {
        super ( titleName, hasEmpty, ""  );

        //Dimension size;

        textField   = new JTextArea();

        //size        = new Dimension ( width, WCons.BUTTON_HEIGHT );
        //textField.setPreferredSize ( size );

        //setBorder ( BorderFactory.createEtchedBorder() );
        //textField.setBackground ( Color.RED );

        textField.setLineWrap ( true );
        textField.setWrapStyleWord ( false );
        textField.setRows ( rows );
        textField.setWrapStyleWord ( true );
        textField.setBorder ( BorderFactory.createEtchedBorder() );
        //textField.setColumns ( maxSize );   // выкл - делает почему-то шире чем задано символов (svj, 2010-10-12)

        // todo Проблема - нет ползунка у Скролла. Не смог его проявить.
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setEnabled ( true );
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);

        /*
        // -- Попытка проявить ползунок - не прошла.
        panel = new JPanel(new BorderLayout());
        panel.add(textField, BorderLayout.CENTER);
        scrollPane.setViewportView ( panel );
        //*/

        scrollPane.setViewportView ( textField );

        add ( scrollPane );
        /*
        if (rows == 0)
            add ( new JScrollPane(textField) );
        else
            add ( textField );
        */
    }

    public TextWidget (String titleName, String value, int rows )
    {
        this ( titleName, true, rows );

        setValue ( value );
    }

    public TextWidget (String titleName, int rows )
    {
        this ( titleName, true, rows );
    }


    public void addMouseListener ( MouseListener mouseListener )
    {
        textField.addMouseListener ( mouseListener );
    }

    @Override
    public void setEditable ( boolean value )
    {
        textField.setEditable ( value );
    }

    @Override
    public void setValueWidth ( int width )
    {
        int         height;
        Dimension dim;

        if ( textField != null )
        {
            height  = textField.getHeight();
            if ( height < WCons.BUTTON_HEIGHT )  height = WCons.BUTTON_HEIGHT;
            dim     = new Dimension( width, height );
            textField.setPreferredSize ( dim );
        }
    }

    @Override
    public JComponent getGuiComponent ()
    {
        return textField;
    }

    @Override
    protected String validateValue () throws WEditException
    {
        String str, result, msg;

        str = textField.getText();
        if ( str != null )
        {
            str = str.trim();
            if ( str.length() == 0 ) str = null;
        }

        result = str;

        if ( str == null )
        {
            if ( hasEmpty() )   result = null;
            else
            {
                msg = getTitleName();
                if ( msg != null )  msg = msg + ": ";
                msg = msg + "Значение не может быть пустым";
                throw new WEditException ( msg );
            }
        }
        /*
        else
        {
            if ( str.length() > maxSize )
            {
                msg = getTitleName();
                if ( msg != null )  msg = msg + ": ";
                msg = Convert.concatObj ( msg, "Введено символов '", str.length(), "', что больше максимально допустимого '", maxSize, "'" );
                throw new WEditException ( msg );
            }
            result = str;
        }
        */
        return result;
    }

    @Override
    public String getValue () //throws WEditException
    {
        /*
        String result;

        try
        {
            result = validateValue();
        } catch ( Exception e )         {
            result = null;
            Log.l.error ( e, "Ошибка получения строкового параметра из виджета." );
        }
        return result;
        */
        return textField.getText();
    }

    @Override
    public void setValue ( String value ) // throws WEditException
    {
        /*
        if ( value == null )
        {
            if ( hasEmpty() )  value = getEmptyValue();
            else  throw new WEditException ( null, getTitleName(), ": Введено неверное значение" );
        }
        */

        //if ( (value != null) && (value.length() > maxSize) )  value = value.substring ( 0, maxSize-1 );
        textField.setText ( value );

        /*
        // -- Попытка проявить ползунок - не прошла.
        final Dimension d = new Dimension(panel.getWidth(), panel.getHeight() + 2);
        panel.setPreferredSize(d);

        panel.revalidate();
        panel.repaint();
        */
    }

}
