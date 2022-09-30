package svj.svjlib.svjlibs.listener;

import svj.svjlib.gui.dialog.WidgetsDialog;
import svj.svjlib.gui.widget.FileWidget;
import svj.svjlib.gui.widget.StringFieldWidget;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Загрузить новую библиотеку.
 * <BR/> Эти данные хранятся в SLPar.LIBS
 * <BR/>
 */
public class LoadLibListListener implements ActionListener {

    private FileWidget dirPath;
    private StringFieldWidget myLibName;

    @Override
    public void actionPerformed(ActionEvent event) {
        // todo

        // Диалог ввода данных
        //     -- рутовый пароль - если нет прав дсотупа
        //    -- путь до директории - там будет арзличать - файл fb2, зип-файл, зип-зип файл - различеат сам, но можно ввести и тип архива библиотеки
        //    -- свое название библиотеки -- уникальное среди всех названий

        // Проверяем - есть ли у нас такой путь - т.е. массив загруженных библиотек - без их привязки к книгам
        //if (Par.LIBS != null )

        //String msg = "<html><body><h1>Не реализовано</h1></body></html>";

        //DialogTools.showHtml("Добавить библиотеку", msg);

        // виджеты
        // StringFieldWidget (String titleName, String value, boolean hasEmpty )
        dirPath = new FileWidget("Директория библиотеки", false);

        myLibName = new StringFieldWidget("Задайте имя библиотеки", "Моя библиотека", false);

        WidgetsDialog dialog = new WidgetsDialog("Добавить библиотеку");
        dialog.addWidget(dirPath);
        dialog.addWidget(myLibName);
        dialog.setTitleWidth(350);
        dialog.setValueWidth(250);

        dialog.showDialog();

        if (dialog.isOK()) {

            // Проверяем парва дсотупа к директории. Если что - запрашиваем логин-пароль на доступ.

        }

    }
    
}
