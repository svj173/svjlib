package svj.svjlib.listener;

import svj.svjlib.gui.panel.ReloadPanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Отрабатывает нажатие клавиш ESC, ENTER в диалоговых окнах.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 27.07.2011 10:24:46
 */
public class WKeyAdapter extends KeyAdapter
{
    private final ReloadPanel reloadPanel;

    public WKeyAdapter(ReloadPanel reloadPanel )
    {
        this.reloadPanel = reloadPanel;
    }

    //public void keyTyped( KeyEvent e) {}       // клавиша нажата и отпущена
    //public void keyReleased(KeyEvent e) {}     // отжата

    /* клавиша нажата */
        @Override
    public void keyPressed ( KeyEvent event )
    {
        //Log.l.info ( "[B] event = %s", event );
        int keyCode = event.getKeyCode ();

        if (keyCode == KeyEvent.VK_ENTER)  reloadPanel.reload ();

        /*
        switch ( keyCode )
        {
            case KeyEvent.VK_ESCAPE:
                //Log.l.debug ( "press ESC" );
                break;

            case KeyEvent.VK_ENTER:
                //Log.l.debug ( "press ENTER" );
                reloadPanel.reload ();
                break;
        }
        */
    }

}
