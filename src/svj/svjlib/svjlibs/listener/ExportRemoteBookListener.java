package svj.svjlib.svjlibs.listener;

import svj.svjlib.svjlibs.table.BookTablePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Экспорт удаленный (из внешней библиотеки)
 * - хранит: пути до внешней библиотеки, логин-пароль
 * - выполняет внешнюю команду scp  (либо что-то похожее для Винды - задается в конфиге)
 * либо найти аналогичную java-библиотеку.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 14.11.22 10:44
 */
public class ExportRemoteBookListener implements ActionListener {

    private final BookTablePanel bookTablePanel;

    public ExportRemoteBookListener(BookTablePanel bookTablePanel) {
        this.bookTablePanel = bookTablePanel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
    }

}
