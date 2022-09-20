package svj.svjlib;

import java.awt.*;
import java.io.FileInputStream;
import java.util.*;

/**
 * <BR/>
 * <BR/> 2022-09-16
 */
public class SvjLib implements Runnable {

    public static void main ( String[] args )
    {
        String      str;
        SvjLib mk;
        MkApplet    applet;
        Properties config;

        System.setErr ( System.out );
        //if ( args.length > 0 )  configFile  = args[0];

        try
        {
            MkPar.MODULE_HOME     = System.getProperty ( "module.home" );

            Thread.currentThread().setName ( "main" );

            // ----------- Инициализируем логгер   ----------------------
            //str = System.getProperty ( "log4j" );
            str     = FileTools.createFileName ( "conf/logger.txt" );
            System.out.println ( "log_file = '"+str+"'" );
            Log.init ( str );
            Log.l.info ( "\n----------------------------------------------------------------------------------" );

            str     = FileTools.createFileName ( "conf/config.txt" );
            Log.l.debug ( "Config dir = ", str );
            config  = new Properties ();
            config.load ( new FileInputStream( str ) );

            // Создать и инициализировать
            mk      = new SvjLib();
            applet  = new MkApplet();
            applet.init();

            GM.getInstance().addMsg ( "START" );

            // Цепляем сом-порт
            GM.getInstance().initCom ( config );

            // Запустить в отдельной самостоятельной нити
            javax.swing.SwingUtilities.invokeLater ( mk );
            //System.out.println ( "MK. finish " );

        } catch ( Throwable e ) {
            // Обработать  ошибку и закрыть киоск.
            str = "MK.main ERROR: " + e.getMessage();
            System.err.println ( str );
            e.printStackTrace();
            System.exit ( 12 );
        }
    }


    public SvjLib ()
    {
        // Внимание!!! Мониторов может быть два - тогда путаница.
        Par.SCREEN_SIZE     = Toolkit.getDefaultToolkit().getScreenSize();
        // System.getProperty("java.version")
    }

    public void run ()
    {
        try
        {
            // Установить имя потока, в котoром будет крутиться EmsGUI приложение
            Thread.currentThread().setName("svjlib");

            //content = gm.getContent();
            Par.GM.getFrame().init();
            Par.GM.getInstance().getFrame().pack ();

            // Флаг что редактор поднят
            Par.WEDIT_STARTED = true;

            // Открыть фрейм
            Par.GM.getFrame().setVisible ( true );

        } catch ( Exception e )         {
            System.err.println ( "SvjLib.run() Error = " + e.getMessage() );
            e.printStackTrace();
        }
    }

}
