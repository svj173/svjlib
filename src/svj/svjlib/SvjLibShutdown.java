package svj.svjlib;


/**
 * Аварийный останов Редактора по системному вызову - Ctrl/C, сброс питания, вызову System.exit().
 * <BR/>
 * <BR/> User: Zhiganov
 * <BR/> Date: 29.08.2007
 * <BR/> Time: 16:39:21
 */
public class SvjLibShutdown extends Thread
{
    public SvjLibShutdown()
    {
        Log.l.info ( "Create Shutdown function" );
        setName ( "Shutdown" );
    }

    /**
     * Запуск процесса аварийного останова Редактора
     */
    public void run ()
    {
        Log.l.info ( "\n\n\t-------------- Start shutdown process. Waiting for real work is finished. --------------------" );

        // todo сохранение в конфиг-файле каких-то измененых данных

        Log.l.info ( "Finish shutdown\n------------------------------------ FINISH ------------------------------------------\n" );
    }

}
