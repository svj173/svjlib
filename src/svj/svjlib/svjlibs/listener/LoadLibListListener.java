package svj.svjlib.svjlibs.listener;

import svj.svjlib.Log;
import svj.svjlib.exc.WEditException;
import svj.svjlib.gui.dialog.WidgetsDialog;
import svj.svjlib.gui.widget.FileWidget;
import svj.svjlib.gui.widget.StringFieldWidget;
import svj.svjlib.obj.BookTitle;
import svj.svjlib.obj.ResponseObject;
import svj.svjlib.tools.DialogTools;
import svj.svjlib.tools.DumpTools;

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
                // сам процесс чтения
                // todo Здесь необходим бегунок с процентом рабоыт - по кол-ву файлов в библиотеке
                Collection<BookTitle> result = processLoad(libDir, libName);

                // todo сохранить инфу о новой библиотеке в конфиг-директории Проги

                Log.file.info("books = {}", DumpTools.printBookTitles(result));


                // показать диалог - закачано книг, в том числе - по жанрам
                // + инфа из LoadLibInfo

            } catch (Exception e) {
                Log.l.error("libDir = " + libDir, e);
                DialogTools.showError("Ошибка загрузки библиотеки '" + libDir + "' :\n " + e.getMessage() + "\n",
                        "Ошибка загрузки");
            }
        }
    }

    private Collection<BookTitle> processLoad(String libDir, String libName) throws WEditException {

        LoadLibWorker worker = new LoadLibWorker(libDir, libName);

        ResponseObject response = DialogTools.doWithProgress(worker);

        if (response.getObject() instanceof String) {
            throw new WEditException(response.getObject().toString());
        }

        return (Collection<BookTitle>) response.getObject();
    }


}
