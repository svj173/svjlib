package svj.svjlib.svjlibs.listener;

import svj.svjlib.tools.DialogTools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Выводит на экран в диалоге список библиотек.
 * <BR/> Эти данные хранятся в SLPar.LIBS
 * <BR/>
 */
public class ViewLibsListListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent event) {
        // todo Показать
        String msg = "<html><body><h1>Не реализовано</h1></body></html>";

        DialogTools.showHtml("Список подсоединенных библиотек", msg);
    }
    
}
