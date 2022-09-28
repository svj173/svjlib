package svj.svjlib;

import svj.svjlib.tools.Convert;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 21.09.22 18:57
 */
public class GeneralManager {

    //private FunctionManager fm;
    private ContentFrame    frame;
    //private ConfigManager   config;


    public GeneralManager() {
        frame = new ContentFrame();
    }

    public ContentFrame getFrame ()
    {
        return frame;
    }


    public void close ()
    {
        // Сохраняем параметры пользователя - НЕТ - сохораняем и сразу же, при доабвлениях и изменениях.
    }

    public void setStatus ( Object ... msg )
    {
        setStatus ( Convert.concatObj ( msg ) );
    }

    /* Занести инфу по статусу операции.
       Для многократных изменений в одной акции - необходимо и акцию и изменения производить в
       отдельном потоке - особенность Swing* - сделано в statusPanel */
    public void setStatus ( String statusMsg )
    {
        //Logger.getInstance().debug ( "GeneralManager.setStatus: Finish. statusMsg = " + statusMsg );
        if ( statusMsg != null )
        {
            // удалить все символы возврата каретки
            statusMsg   = statusMsg.replace ( '\n', ' ' );
            statusMsg   = statusMsg.replace ( '\r', ' ' );
            getFrame().getServicePanel().setStatusText ( statusMsg );
        }
        //Logger.getInstance().debug ( "GeneralManager.setStatus: Finish" );
    }

    /* Время выполнения операции - для статус панели */
    public void setTime ( long timeMsec )
    {

    }

    /* Перерисовать весь фрейм */
    public void rewrite ()
    {
        Log.l.debug ( "Start" );

        frame.rewrite();

        Log.l.debug ( "Finish" );
    }


}
