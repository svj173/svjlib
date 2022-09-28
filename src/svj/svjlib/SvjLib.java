package svj.svjlib;

import svj.svjlib.obj.BookTitles;

import java.awt.*;
import java.util.*;

/**
 * <BR/>
 * <BR/> 2022-09-16
 */
public class SvjLib implements Runnable {

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
            Thread.currentThread().setName("main");

            // todo запускаем процесс чтения конфига - если он есть
            WEdit6InitDialog initDialog = new WEdit6InitDialog ( "Инициализация" );
            initDialog.showDialog();

            // получить распарсенную инфу о книгах - если етсь аткой файл
            BookTitles bookTitles    = initDialog.getResult();

            Log.l.debug ( "WEdit.run: run init WEdit6." );


            Par.GM = new GeneralManager();

            Par.GM.getFrame().init(bookTitles);
            Par.GM.getFrame().pack();

            // Флаг что редактор поднят
            //Par.WEDIT_STARTED = true;

            // Открыть фрейм
            Par.GM.getFrame().setVisible ( true );

        } catch ( Exception e )         {
            System.err.println ( "SvjLib.run() Error = " + e.getMessage() );
            e.printStackTrace();
        }
    }

    public static void main ( String[] args )
    {
        String      str;
        SvjLib mk;
        Properties config;

        System.setErr ( System.out );
        //if ( args.length > 0 )  configFile  = args[0];

        try
        {
            Par.MODULE_HOME     = System.getProperty ( "module.home" );

            Thread.currentThread().setName ( "main" );

            Log.l.info ( "\n----------------------------------------------------------------------------------" );

            // - Определить домашнюю директорию пользователя
            // Попытка выяснить что за операционная система - по параметру хранения логина пользователя.
            // - Linux   - параметр USER
            // - Windows - параметр USERNAME

            // USER_LOGIN  - только для изменяемых параметров Редактора (dynamic)
            str = System.getenv ( "USERNAME" ); // for Windows
            Log.l.debug ( "USERNAME = %s", str );
            if ( str != null )
            {
                Par.USER_LOGIN  = str;
            }
            else
            {
                str = System.getenv ( "USER" ); // for Linux
                if ( str != null )  Par.USER_LOGIN  = str;
            }
            Log.l.debug ( "User = '%s'", str );

            // HOME - домашняя директория пользователя. Именно в ней будет лежать конфиг пользователя. В директории '.svjlib'
            str = System.getenv ( "HOME" ); // for Windows
            Log.l.debug ( "HOME = %s", str );
            if ( str != null )  Par.USER_HOME_DIR  = str;

            /*
            str     = FileTools.createFileName ( "conf/config.txt" );
            Log.l.debug ( "Config dir = ", str );
            config  = new Properties ();
            config.load ( new FileInputStream( str ) );
            */

            // Создать и инициализировать
            mk      = new SvjLib();

            // Запустить в отдельной самостоятельной нити
            javax.swing.SwingUtilities.invokeLater ( mk );
            //System.out.println ( "MK. finish " );

        } catch ( Throwable e ) {
            // Обработать  ошибку и закрыть киоск.
            str = "SVJLib.main ERROR: " + e.getMessage();
            System.err.println ( str );
            e.printStackTrace();
            System.exit ( 12 );
        }
    }

}
