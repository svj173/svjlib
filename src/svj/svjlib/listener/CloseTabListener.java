package svj.svjlib.listener;

import svj.svjlib.exc.WEditException;

import java.awt.event.ActionEvent;

/**
 * <BR/>
 */
public class CloseTabListener extends WActionListener
{

    public CloseTabListener(String name) {
        super(name);
    }

    /*
        private Function closeFunction;

        public CloseTabListener ( Function closeFunction )
        {
            super ( "CloseTabListener" );

            this.closeFunction = closeFunction;
        }
        */
    @Override
    public void handleAction ( ActionEvent event ) throws WEditException
    {
        //if ( closeFunction != null )  closeFunction.handle ( event );
    }

}
