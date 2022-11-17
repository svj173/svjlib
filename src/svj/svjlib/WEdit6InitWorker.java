package svj.svjlib;

import svj.svjlib.exc.WEditException;
import svj.svjlib.gui.panel.WPanel;
import svj.svjlib.svjlibs.SLCons;
import svj.svjlib.svjlibs.obj.LibInfo;
import svj.svjlib.tools.DialogTools;

import javax.swing.*;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Старт Редактора.
 * Если есть инфа о книгах - запускается xml-парсер и вытаскивает эти книги.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 21.09.2022 19:00
 */
public class WEdit6InitWorker    extends SwingWorker<Void,String>
{
    private final WEdit6InitDialog  dialog;
    //private BookTitles bookTitles;


    public WEdit6InitWorker ( WEdit6InitDialog dialog )
    {
        this.dialog = dialog;
    }

    @Override
    protected Void doInBackground () throws Exception
    {
        init();
        return null;
    }

    private void init () throws WEditException
    {
        //WEditShutdown       shutdown;
        GeneralManager      gm;
        FunctionManager     fm;
        ContentFrame        content;
        int ic;

        // Здесь загружаем инфу о книгах - если она есть (т.е. библиотеки были добавлены)
        // - лезем в конфиг-директорию проги (home/.svjlib/books.xml)
        // Заносим в Par.BOOKS


        // А также загрузить Инфу о загруженных библиотеках

        try {
            publish("Старт");

            // есть такой файл - парсим его и инфу о книгах библиотек
            publish("Есть добавленные библиотеки");
            publish("Загружаем информацию о библиотеках");
            //Thread.sleep(2000);

            // Загружаем инфу о библиотеках и получаем массив их ИД.
            // - если нет библиотек - пустой список
            Collection<LibInfo> libs = SLCons.LIBS_MANAGER.loadLibs();

            if (libs != null) {

                publish("Всего используется библиотек : " + libs.size());
                //Thread.sleep(2000);
                Log.file.info("libs size = {}", libs.size());

                Log.file.info("start books = {}", SLCons.BOOKS_MANAGERS.bookSize());

                long startTime, workTime;
                for (LibInfo lib : libs) {
                    startTime = System.currentTimeMillis();
                    Log.file.info("load libs = {}", lib);
                    publish("-- Загружаем библиотеку : " + lib.getName());
                    // загружаем инфу о книгах указанной библиотеки - каталог книг
                    ic = SLCons.BOOKS_MANAGERS.loadBooksInfo(lib.getId());
                    publish("---- Книг : " + ic);
                    workTime = (System.currentTimeMillis() - startTime) / 1000;
                    publish("---- Время, в сек : " + workTime);
                }

                Log.file.info("total books = {}", SLCons.BOOKS_MANAGERS.bookSize());

                publish("Финиш");
                //Thread.sleep(5000);

                // Итоговый диалог. Скачано:
                // - всего библиотек
                // - всего книг
                showTotalProcessDialog(SLCons.LIBS_MANAGER.libSize(), SLCons.BOOKS_MANAGERS.bookSize());
            }

        } catch (WEditException we) {
            throw we;
        } catch (Exception e) {
            Log.l.error("Init Library error", e);
            throw new WEditException("Ошибка инициализации", e);
            //DialogTools.showError(dialog, "Error: " + e.getMessage(), "Ошибка инициализации");
        }
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

    private void showTotalProcessDialog(int libSize, int bookSize) {
        WPanel panel = new WPanel();

        panel.setLayout ( new GridLayout( 2, 2, 5, 5 ) );


        panel.add(new JLabel("Всего библиотек"));
        panel.add(new JLabel(Integer.toString(libSize)));
        panel.add(new JLabel("Всего файлов"));
        panel.add(new JLabel(Integer.toString(bookSize)));

        DialogTools.showMessage(Par.GM.getFrame(), panel, "Результат инициализации библиотек");

    }


}
