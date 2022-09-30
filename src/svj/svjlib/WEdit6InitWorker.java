package svj.svjlib;

import svj.svjlib.exc.WEditException;
import svj.svjlib.obj.BookTitles;

import javax.swing.*;

import java.io.File;
import java.util.*;

/**
 * - Void - the result type returned by this {@code SwingWorker's}  {@code doInBackground} and {@code get} methods
 * - String - the type used for carrying out intermediate results by this  {@code SwingWorker's} {@code publish} and {@code process} methods
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 21.09.22 19:00
 */
public class WEdit6InitWorker    extends SwingWorker<BookTitles,String>
{
    private final WEdit6InitDialog  dialog;
    private BookTitles bookTitles;


    public WEdit6InitWorker ( WEdit6InitDialog dialog )
    {
        this.dialog = dialog;
    }

    @Override
    protected BookTitles doInBackground () throws Exception
    {
        init();

        return bookTitles;
    }

    private void init () throws WEditException
    {
        //WEditShutdown       shutdown;
        GeneralManager      gm;
        FunctionManager     fm;
        ContentFrame        content;

        // Здесь загружаем инфу о книгах - если она есть (т.е. библиотеки были добавлены)
        // - лезем в конфиг-директорию проги (home/.svjlib/books.xml)
        // Заносим в Par.BOOKS

        String booksFile = Par.USER_HOME_DIR + "/.svjlib/books.xml";

        File file = new File(booksFile);
        if (file.isFile()) {
            // todo
        }

        // А также загрузить Инфу о загруженных библиотеках

        try {
            publish ( "Старт" );
            Thread.sleep(2000);

            // Загружается файл с информацией о книгах. Размер файла - ...

            publish ( "Шаг-1" );
            Thread.sleep(2000);

            publish ( "Шаг-2" );
            Thread.sleep(2000);

            publish ( "Шаг-3" );
            Thread.sleep(2000);

            publish ( "Финиш" );
            Thread.sleep(2000);

        } catch (Exception e) {


        }

        /*
        ConfigManager       config;
        UserParamsManager   upm;
        StringBuilder       errMsg;  // Сообщения об ошибках открытия.
        long                mTime, nTime;

        Log.l.debug ( "Start" );

        mTime   = System.currentTimeMillis();
        errMsg  = new StringBuilder ( 128 );

        try
        {
            // Создать пустой обьект GM
            gm  = new GeneralManager();
            // Занести
            Par.GM  = gm;

            publish ( "Старт" );
            // Загрузить конфиги -  Пользователя, Редактора -- Надо ли его хранить в памяти?
            config  = new ConfigManager();
            config.init();
            gm.setConfig ( config );
            nTime   = System.currentTimeMillis();
            publish ( "Загрузка конфига" + createWorkTime ( mTime ) );

            fm  = new FunctionManager();
            fm.init ();
            gm.setFm ( fm );
            mTime   = System.currentTimeMillis();
            publish ( "Инициализация функций" + createWorkTime ( nTime ) );
            // Подписать функции друг на друга.
            fm.signFunction ();
            nTime   = System.currentTimeMillis();
            publish ( "Подпись функций" + createWorkTime ( mTime ) );


            // Загрузить и инсталлировать в функции параметры пользователя -- наполнить фрейм toolBar
            upm = new UserParamsManager();
            bookTitles = upm.start ( errMsg );
            mTime   = System.currentTimeMillis ();
            publish ( "Загрузка параметров пользователя" + createWorkTime ( nTime ));

            // todo Взять номер версии и номер билда

            // Создать JFrame обьект Content - здесь уже исп элементы и функции
            content = new ContentFrame();
            gm.setContent ( content );
            nTime   = System.currentTimeMillis();
            publish ( "Создание фрейма" + createWorkTime ( mTime ) );

            // наполнить фрейм Главным меню
            createMenu ( content, fm, content );
            mTime   = System.currentTimeMillis();
            publish ( "Создать Главное меню" + createWorkTime ( nTime ) );

            // ---- Здесь все модули и функции подняты.
            // Стартануть функции - декоратор, reopen... Reopen - не должен открывать свой проект - это просто список.
            fm.startAll ( errMsg );
            nTime   = System.currentTimeMillis();
            publish ( "Запуск функций" + createWorkTime ( mTime ) );


            // Открыть ранее открытое - по bookTitles
            openProjects (bookTitles, errMsg );
            mTime   = System.currentTimeMillis();
            publish ( "Отрытие сборников и книг" + createWorkTime ( nTime ) );


            //shutdown    = new WEditShutdown ( function );
            shutdown    = new WEditShutdown ();
            Runtime.getRuntime().addShutdownHook ( shutdown );
            //nTime   = System.currentTimeMillis();
            publish ( "Создание Shutdown " + createWorkTime ( mTime ) );

            // Если были стартовые ошибки - вывести их отдельным диалогом.
            if ( errMsg.length () > 0 )
            {
                Log.l.error ( ">>>>>>>>>>>>>> Start WEdit6 error >>>>>>>>>>>>>> :\n%s", errMsg );
                DialogTools.showError ( errMsg, "Ошибки открытия редактора." );
            }

        } catch ( WEditException we )        {
            // Это уже фатальные ошибки открытия редактора.
            throw we;
        } catch ( Exception e )        {
            Log.l.error ( "WEdit.init:", e );
            throw new WEditException( "init.wedit.error.throwable", e );
        }
        */
    }

    private String createWorkTime ( long t )
    {
        StringBuilder sb;
        long          time;

        time = System.currentTimeMillis() - t;
        sb   = new StringBuilder ( 16 );
        sb.append ( "   (" );
        //sb.append ( time/1000 );
        sb.append ( time );
        sb.append ( ")" );

        return sb.toString();
    }

    protected void process ( List<String> list )
    {
        for ( String msg : list )
        {
            dialog.addText ( msg );
        }
    }



    @Override
    protected void done ()
    {
        /*
        try
        {
            get();
        } catch ( Exception ignore )      {
            Log.l.error ( ".done: error", ignore );
        }
        */
        dialog.setVisible ( false );
        Log.l.debug ( ".done: Finish." );
    }

}
