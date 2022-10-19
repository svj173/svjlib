package svj.svjlib.svjlibs.listener;

import svj.svjlib.Log;
import svj.svjlib.Par;
import svj.svjlib.exc.WEditException;
import svj.svjlib.gui.dialog.WidgetsDialog;
import svj.svjlib.gui.panel.WPanel;
import svj.svjlib.gui.widget.FileWidget;
import svj.svjlib.gui.widget.StringFieldWidget;
import svj.svjlib.obj.BookTitle;
import svj.svjlib.obj.ResponseObject;
import svj.svjlib.svjlibs.SLCons;
import svj.svjlib.svjlibs.obj.LibInfo;
import svj.svjlib.svjlibs.obj.LoadLibInfo;
import svj.svjlib.tools.Convert;
import svj.svjlib.tools.DialogTools;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Загрузить новую библиотеку.
 * <BR/> Эти данные хранятся в SLPar.LIBS
 * <BR/>
 */
public class LoadLibListListener implements ActionListener {

    private FileWidget dirPath;
    private StringFieldWidget myLibName;
    //private  final        String endTag = "</description>";


    @Override
    public void actionPerformed(ActionEvent event) {

        // Диалог ввода данных
        //     -- рутовый пароль - если нет прав дсотупа
        //    -- путь до директории - там будет арзличать - файл fb2, зип-файл, зип-зип файл - различеат сам, но можно ввести и тип архива библиотеки
        //    -- свое название библиотеки -- уникальное среди всех названий

        // Проверяем - есть ли у нас такой путь - т.е. массив загруженных библиотек - без их привязки к книгам
        //if (Par.LIBS != null )

        // виджеты
        // дефолтная директория - берется из конфиг-файла Редактора - директория полсденей загруженной библиотеки
        String defDir = "/home/svj/Serg/Libruks/Архивы Либрусек";
        dirPath = new FileWidget("Директория библиотеки", false, defDir);

        myLibName = new StringFieldWidget("Задайте имя библиотеки", "Моя библиотека", false);

        WidgetsDialog dialog = new WidgetsDialog("Добавить библиотеку");
        dialog.addWidget(dirPath);
        dialog.addWidget(myLibName);
        dialog.setTitleWidth(350);
        dialog.setValueWidth(250);
        dialog.pack();

        dialog.showDialog();

        if (dialog.isOK()) {

            // - Из диалога
            String libName = myLibName.getValue();
            // Берем директорию
            String libDir = dirPath.getValue();

            // todo Проверяем права дсотупа к директории. Если что - запрашиваем логин-пароль на доступ.

            // Par.BOOKS - список всех книг - туда добавить если есть данные

            try {
                long startTime = System.currentTimeMillis();
                // сам процесс чтения
                // - Здесь необходим бегунок с процентом рабоыт - по кол-ву файлов в библиотеке
                ResponseObject response = processLoad(libDir, libName);
                Collection<BookTitle> result = (Collection<BookTitle>) response.getObject();

                Log.file.info("book size = {}", result.size());

                long totalTime = System.currentTimeMillis() - startTime;
                Log.file.info("total time = {}", Convert.sec2str(totalTime, Convert.Format.HH_MM_SS));

                //Log.file.info("books = {}", DumpTools.printBookTitles(result));


                // todo показать диалог - закачано книг, в том числе - по жанрам
                // + инфа из LoadLibInfo
                LoadLibInfo libInfo = (LoadLibInfo) response.getObject2();
                showTotalProcessDialog(libInfo);

            } catch (Exception e) {
                Log.file.error("libDir = " + libDir, e);
                DialogTools.showError("Ошибка загрузки библиотеки '" + libDir + "' :\n " + e.getMessage() + "\n",
                        "Ошибка загрузки");
            }
        }
    }

    private void showTotalProcessDialog(LoadLibInfo libInfo) {
        WPanel panel = new WPanel();

        panel.setLayout ( new GridLayout( 7, 2, 5, 5 ) );

        /*
- Директория
- Всего архивных файлов:
- Всего книг:
Ошибки загрузки
- Загрузки книг - подробный путь - диреткория, зип-архив книг, зип-файл книги
 Прочие ошибки - ?
         */

        panel.add(new JLabel("Директория"));
        panel.add(new JLabel(libInfo.getLibDir()));
        panel.add(new JLabel("Всего архивных файлов"));
        panel.add(new JLabel(Integer.toString(libInfo.getSourceArchiveSize())));
        panel.add(new JLabel("Всего книг"));
        panel.add(new JLabel(Integer.toString(libInfo.getSourceBookSize())));

        // Ошибки
        panel.add(new JLabel("Ошибки"));
        panel.add(new JLabel("-------"));
        panel.add(new JLabel("Ошибки получения кодировки текста"));
        panel.add(new JLabel(Integer.toString(libInfo.getBadCodeText())));
        panel.add(new JLabel("Книга - не зип-файл"));
        panel.add(new JLabel(Integer.toString(libInfo.getBookNoneZip())));
        panel.add(new JLabel("Ошибки извлечения книги"));
        panel.add(new JLabel(Integer.toString(libInfo.getParseError())));

        DialogTools.showMessage(Par.GM.getFrame(), panel, "Результат загрузки библиотеки");

    }


    private ResponseObject processLoad(String libDir, String libName) throws WEditException {

        LibInfo lib = new LibInfo(libDir, libName);

        // проверка возможности добавления
        SLCons.LIBS_MANAGER.checkLib(lib);

        WPanel panel = new WPanel();
        panel.setLayout ( new GridLayout( 3, 2, 5, 5 ) );

        /*
   Всего архивных файлов: 2854
   Загружено архивных файлов:  12
   Всего книг: 234

         */
        //JLabel totalZipTitle, loadZipTitle, totalBooksTitle;
        JLabel totalZipValue, loadZipValue, totalBooksValue;

        totalZipValue = new JLabel("0");
        loadZipValue = new JLabel("0");
        totalBooksValue = new JLabel("0");

        panel.add(new JLabel("Всего архивных файлов"));
        panel.add(totalZipValue);
        panel.add(new JLabel("Загружено архивных файлов"));
        panel.add(loadZipValue);
        panel.add(new JLabel("Всего книг"));
        panel.add(totalBooksValue);

        LoadLibWorker worker = new LoadLibWorker(lib, totalZipValue, loadZipValue, totalBooksValue);

        ResponseObject response = DialogTools.doWithProgress(worker, panel);

        if (response.getObject() instanceof String) {
            throw new WEditException(response.getObject().toString());
        }

        return response;
    }


}
