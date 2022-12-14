package svj.svjlib.gui.button;

import svj.svjlib.tools.GuiTools;

import javax.swing.*;

/**
 * <BR/>
 */
public class WButton  extends JButton
{
    public  WButton  ( String name, String toolTip, String iconPath )
    {
        super(name);

        //defaultToolTipText = toolTip;

        Icon icon;

        //setAllowed ( true );
        if ( iconPath != null )
        {
            icon   = GuiTools.createImageByFile ( iconPath );
            setIcon ( icon );
        }
        setText ( name );
        setName ( name );
        if ( toolTip != null ) setToolTipText ( toolTip );
        //setPreferredSize ( new Dimension(100, GCons.BUTTON_HEIGHT) );
        //setCursor ( new Cursor ( Cursor.HAND_CURSOR ) );
        setFocusable ( false );
        setHorizontalTextPosition ( SwingConstants.RIGHT );
        setHorizontalAlignment ( SwingConstants.LEADING);
        setVerticalTextPosition ( SwingConstants.BOTTOM );
    }

}
