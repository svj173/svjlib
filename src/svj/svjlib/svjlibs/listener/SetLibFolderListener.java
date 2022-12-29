package svj.svjlib.svjlibs.listener;

import svj.svjlib.Log;
import svj.svjlib.gui.dialog.WidgetsDialog;
import svj.svjlib.gui.widget.FileWidget;
import svj.svjlib.svjlibs.SLCons;
import svj.svjlib.svjlibs.SLPar;
import svj.svjlib.tools.DialogTools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Выводит на экран в диалоге - Задать - Директория, в которой находятся файлы описания книг и библиотек
 * <BR/> Эти данные хранятся в SLPar.LIBS
 * <BR/>
 */
public class SetLibFolderListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent event) {
        /*
        String msg = "<html><body><h1>Не реализовано</h1></body></html>";

        DialogTools.showHtml("Директория, в которой находятся файлы описания книг и библиотек", msg);
        */

        FileWidget libFolder;
        FileWidget exportFolder;

        WidgetsDialog dialog = new WidgetsDialog("Настроить рабочие директории");

        int titleWidth = 350;

        libFolder = new FileWidget("Директория, в которой находятся файлы описания книг и библиотек",
                false, SLPar.LIBS_INFO_DIR);
        libFolder.setTitleWidth(titleWidth);

        exportFolder = new FileWidget("Директория, в которую экспортируются книги из библиотеки",
                false, SLPar.EXPORT_DIR);
        exportFolder.setTitleWidth(titleWidth);

        dialog.addWidget(libFolder);
        dialog.addWidget(exportFolder);
        dialog.pack();

        dialog.showDialog();

        boolean change = false;
        if (dialog.isOK()) {
            if (libFolder.isChangeValue()) {
                SLCons.CONFIG_MANAGERS.setLibsDir(libFolder.getValue());
                change = true;
            }
            if (exportFolder.isChangeValue()) {
                SLCons.CONFIG_MANAGERS.setExportDir(exportFolder.getValue());
                change = true;
            }

            if (change) {
                try {
                    SLCons.CONFIG_MANAGERS.save();
                } catch (Exception e) {
                    Log.file.error("Save config-file (" + SLCons.CONFIG_FILE + ") error", e);
                    DialogTools.showError("Файл: " + SLCons.CONFIG_FILE + "\nОшибка : " + e.getMessage(),
                            "Ошибка сохранения конфиг-файла");
                }
            }
        }
    }
    
}
