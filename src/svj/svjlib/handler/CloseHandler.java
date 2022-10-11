package svj.svjlib.handler;

/**
 * Обработчик закрытия. Например, диалогового окна.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 27.07.2011 10:21:59
 */
public interface CloseHandler
{
    /* Вызывается в основном при физическом закрытии окна. Для окончательного (корреткного) завершения работы. */
    void close();


    /**
     * Вызывается из программы.
     *
     * @param closeType - тип закрытия. Это int-константы из ряда JOptionPane: YES_OPTION, NO_OPTION, CANCEL_OPTION,
     *                  OK_OPTION, CLOSED_OPTION.
     */
    void doClose(int closeType);

    // Отдельно выводим акции, т.к. для одного диалога Cancel это doClose(1), а для другого - doClose(2)
    void doCancel();

    void doOk();


}
