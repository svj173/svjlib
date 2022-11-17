package svj.svjlib.svjlibs.listener;

import svj.svjlib.WCons;
import svj.svjlib.svjlibs.SLCons;
import svj.svjlib.tools.DialogTools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Статистика кол-ва - библиотек, книг
 * <BR/>
 */
public class StatisticListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent event) {
        String libs = SLCons.LIBS_MANAGER.getLibsInfo();
        String books = SLCons.BOOKS_MANAGERS.getBooksInfo();

        StringBuilder sb = new StringBuilder(128);

        sb.append(libs);
        sb.append(WCons.END_LINE);
        sb.append(books);
        sb.append(WCons.END_LINE);

        DialogTools.showMessage("Количество", sb);
    }

}
