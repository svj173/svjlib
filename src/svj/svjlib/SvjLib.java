package svj.svjlib;

import svj.svjlib.svjlibs.SLCons;
import svj.svjlib.svjlibs.SLPar;
import svj.svjlib.tools.DialogTools;
import svj.svjlib.tools.GuiTools;

import java.awt.*;
import java.io.File;


/**
 * <BR/>
 * <BR/> 2022-09-16
 */
public class SvjLib implements Runnable {

    public SvjLib ()
    {
        // System.getProperty("java.version")

        Runtime.getRuntime().addShutdownHook ( new SvjLibShutdown() );
        

        Par.GM = new GeneralManager();
    }

    /**
     * Запускается в SwingUtilities.invokeLater ( mk ); -- ?
     */
    public void run ()
    {
        try
        {
            // Установить имя потока, в котoром будет крутиться EmsGUI приложение
            Thread.currentThread().setName("main");

            // запускаем процесс чтения конфига - если он есть
            WEdit6InitDialog initDialog = new WEdit6InitDialog ( "Инициализация" );
            initDialog.showDialog();

            // получить распарсенную инфу о книгах - если етсь аткой файл. елси работа еще идет,
            // в этом вызове возникнет пауза.
            // здесь результат нам не нужен, т.к. он занесся по ходу работы
            initDialog.getResult();

            Log.l.debug ( "SvjLib.run: run init SvjLib." );


            Par.GM.getFrame().init();
            Par.GM.getFrame().pack();

            // центрируем Фрейм
            GuiTools.setFrameScreenCenterPosition ( Par.GM.getFrame() );

            // Флаг что редактор поднят
            //Par.WEDIT_STARTED = true;

            // Открыть фрейм
            Par.GM.getFrame().setVisible ( true );

        } catch ( Exception e )         {
            //System.err.println ( "SvjLib.run() Error = " + e.getMessage() );
            Log.l.error ( "SvjLib.run() Error = " + e.getMessage(), e );
            //e.printStackTrace();
            DialogTools.showError("SvjLib.run() Error = " + e.getMessage(), "Ошибка инициализации" );
        }
    }

    public static void main ( String[] args )
    {
        String      str;
        SvjLib mk;

        System.setErr ( System.out );
        //if ( args.length > 0 )  configFile  = args[0];

        try
        {
            Thread.currentThread().setName ( "main" );

            Log.l.info ( "\n----------------------------------------------------------------------------------" );

            // установка общих параметров
            setCommonPar();

            // todo если логгер не прописан - инициировать руками
            /*
            str     = FileTools.createFileName ( "conf/config.txt" );
            Log.l.debug ( "Config dir = ", str );
            config  = new Properties ();
            config.load ( new FileInputStream( str ) );
            */

            // Модуль. Создать и инициализировать
            mk      = new SvjLib();

            // Запустить в отдельной самостоятельной нити
            javax.swing.SwingUtilities.invokeLater ( mk );
            //System.out.println ( "MK. finish " );

        } catch ( Throwable e ) {
            // Обработать  ошибку и закрыть.
            str = "SVJLib.main ERROR: " + e.getMessage();
            Log.l.error(str, e);
            System.err.println ( str );
            e.printStackTrace();
            System.exit ( 12 );
        }
    }

    private static void setCommonPar() {

        Par.MODULE_HOME     = System.getProperty ( "module.home" );

        // Внимание!!! Мониторов может быть два - тогда путаница.
        Par.SCREEN_SIZE     = Toolkit.getDefaultToolkit().getScreenSize();

        // - Определить домашнюю директорию пользователя
        // Попытка выяснить что за операционная система - по параметру хранения логина пользователя.
        // - Linux   - параметр USER
        // - Windows - параметр USERNAME

        // USER_LOGIN  - только для изменяемых параметров Редактора (dynamic)
        String str = System.getenv ( "USERNAME" ); // for Windows
        Log.l.debug ( "USERNAME = {}", str );
        if ( str != null )
        {
            Par.USER_LOGIN  = str;
        }
        else
        {
            str = System.getenv ( "USER" ); // for Linux
            if ( str != null )  Par.USER_LOGIN  = str;
        }
        Log.l.debug ( "User = '{}'", str );

        // HOME - домашняя директория пользователя. Именно в ней будет лежать конфиг пользователя. В директории '.svjlib'
        str = System.getenv ( "HOME" ); // for Windows
        Log.l.debug ( "HOME = {}", str );
        if ( str != null ) {
            Par.USER_HOME_DIR  = str;
            SLPar.CONF_DIR = Par.USER_HOME_DIR + File.separator + SLCons.CONFIG_DIR_NAME;
            // дефолтные значения
            SLPar.EXPORT_DIR = SLPar.CONF_DIR;
            SLPar.LIBS_INFO_DIR = SLPar.CONF_DIR;
        }

        // Загрузить конфиг-файл - если есть. Заменить дефолтные значения
        try {
            SLCons.CONFIG_MANAGERS.load();
            SLCons.CONFIG_MANAGERS.update();
        } catch (Exception e) {
            Log.file.error("Load config-file (" + SLCons.CONFIG_FILE + ") error", e);
        }
        Log.l.info("[T] SLCons.CONFIG_MANAGERS = {}", SLCons.CONFIG_MANAGERS);
    }

}
