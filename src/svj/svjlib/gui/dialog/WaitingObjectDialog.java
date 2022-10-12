package svj.svjlib.gui.dialog;


import svj.svjlib.GCons;
import svj.svjlib.Log;
import svj.svjlib.Par;
import svj.svjlib.exc.WEditException;
import svj.svjlib.gui.panel.WPanel;
import svj.svjlib.handler.CloseHandler;
import svj.svjlib.obj.ICancel;
import svj.svjlib.obj.ResponseObject;
import svj.svjlib.tools.GuiTools;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.CancellationException;


/**
 * Диалог отображения ожидания. (WAIT-режим)
 * <BR/> Есть возможность закрыть его по крестику (прервать процесс).
 * <BR/> Бесконечный бегунок, в середине которого отображаются секунды.
 * <BR/> Закрывается по окончанию процесса.
 * <BR/> При ошибках в работе - предварительно выводится окно с сообщением.
 * <BR/> Результат работы - обьектом. Если ошибка работы - в обьекте содержится исключение.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 27.04.2012 13:42:30
 */
public class WaitingObjectDialog extends JDialog implements CloseHandler, WindowListener {
    /**
     * Применяем свой Executor, т.к. по дефолту вызываемый Executor почему-то ограничивает число поочередно (и друг
     * за другом) запущенных задач.
     * Пример: Запускаем CPE-диалог дерево параметров, грузим в диалоге какой-нибудь параметр - и все это только 4
     * раза, а на 5 - гуи мертво зависает в ожидании запуска процесса  CommandSwingWorker
     */
    private static TestThreadPoolExecutor poolExecutor = new TestThreadPoolExecutor("WaitingDialogWorker", 4);
    /* Макс время, в сек. Может отсутствовать (меньше 0). */
    private final int MaxTimeout;
    /* Время старта - чтобы отображать реальное время. */
    private final long startTime = System.currentTimeMillis();
    /* Генератор секунд ожидания */
    private Timer timer;
    /* Счетчик секунд ожидания */
    private int ic;
    /* Флаг служит для того чтобы функция Отмены операции не дергалась второй раз -- т.е. это флаг применения
    операции Отмена. */
    //private boolean         worked;
    private JProgressBar progressBar;
    //private static ExecutorService          threadPool      = Executors.newFixedThreadPool ( 4 );
    /* Обработчик, окончания работы которого мы и ожидаем. Может быть null. */
    private SwingWorker worker;
    private boolean canceled = false;


    public WaitingObjectDialog(SwingWorker swingWorker, int maxTimeout, WPanel panel, String dialogTitle)  {
        super(Par.GM.getFrame(), dialogTitle, true);

        Border border;
        JButton cancelButton;

        this.MaxTimeout = maxTimeout;

        worker = swingWorker;

        try {
            setName(dialogTitle);

            getContentPane().setLayout(new BorderLayout());

            /*
            setPreferredSize(
                    new Dimension(500, 220));       // h: 80 - один бегунок, без кнопки Отмена. 120 - с кнопкой Отмена.
            setSize(500, 120);       // h: 80 - один бегунок, без кнопки Отмена. 120 - с кнопкой Отмена.
            */

            // блокируем крестик (по нажатию ничего не происходит)
            // Разрешаем работу крестика. Чтобы при гуи-глюках винды (пропадает кнопка Отмена) можно было отменять
            // работу по нажатию крестика.
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            // Какое-то внешнее ГУИ
            if (panel != null) {
                getContentPane().add(panel, BorderLayout.NORTH);
            }

            progressBar = new JProgressBar(0, 2000);

            // рисуем бесконечный бегунок вправо-влево
            progressBar.setIndeterminate(true);

            progressBar.setValue(0);
            progressBar.setStringPainted(true);

            // пустой бордюр - т.е. просто отступы по краям.
            // бордюр вокруг бегунка - c текстом
            border = BorderFactory.createTitledBorder("В работе ...");

            progressBar.setBorder(border);

            getContentPane().add(progressBar, BorderLayout.CENTER);

            // кнопка Отмена
            cancelButton = GuiTools.createButton("Отмена", "Прервать работу", GCons.IMG_CANCEL_BUTTON);
            cancelButton.addActionListener(e -> doCancel());

            JPanel internalButtonPanel;
            WPanel buttonPanel = new WPanel();
            buttonPanel.setInsets(5, 5, 5, 5);
            buttonPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
            buttonPanel.setLayout(new BorderLayout(5, 5));
            internalButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
            internalButtonPanel.add(cancelButton);
            buttonPanel.add(internalButtonPanel, BorderLayout.CENTER);

            getContentPane().add(buttonPanel, BorderLayout.SOUTH);   // NORTH - для отладки

            addWindowListener(this);

            pack();

            // -------------------- Таймер для бегунка. -----------------------
            ic = 0;
            // Create a timer. Прим 100 мсек - чтобы акции, которрые работают 200 мсек не тянулись бы целую секунду.
            timer = new Timer(100, new ActionListener() {
                private int stepCounter = 0;

                /**
                 *
                 * @param evt   cmd=null; source=Timer
                 */
                @Override
                public void actionPerformed(ActionEvent evt) {

                    long time = (System.currentTimeMillis() - startTime) / 1000;
                    progressBar.setString(time + " sec.");

                    if (MaxTimeout < 0) {
                        progressBar.setString(time + " sec.");
                    } else {
                        progressBar.setString(time + '/' + MaxTimeout + " sec.");
                    }

                    // вычисляем кол-во пройденных секунд.
                    stepCounter++;
                    if (stepCounter >= 10) {
                        // прошла секунда
                        ic++;
                        stepCounter = 0;
                    }
                    if (worker != null) {
                        if (worker.isDone()) {
                            Log.l.debug("WaitingObjectDialog (" + getName() +
                                    ").TIMER: actionPerformed. worker.isDone");
                            // закрыть диалог
                            close();
                        }
                    }
                }
            });
        } catch (Exception e) {
            // Иногда почему-то появляется диалог без кнопки Отмена. Проблемы при создании?
            Log.l.error("WaitingObjectDialog (" + getName() + "): error. title = " + dialogTitle, e);
        }
    }

    public void close() {
        setCursor(null); //turn off the wait cursor

        // остановить процесс, если он еще не завершен
        if ((worker != null) && (!worker.isDone())) {
            if (worker instanceof ICancel) {
                // Здесь выполняется полноценное прерывание работы, с отправкой команды о Прерывании на сервер
                ICancel c = (ICancel) worker;
                c.cancel();
            } else {
                worker.cancel(true);
            }
        }

        timer.stop();

        // выкинуть себя из стека
        //Par.JDIALOG_STACK.remove(this);

        setVisible(false);
        dispose();

        // error - для отладки. Потом перевести на debug.
        Log.l.info("WaitingObjectDialog.close ({}): Finish. dialog size = {} / {}", getName(), getPreferredSize(), getSize());
    }

    /*
    public void start() throws EltexException {
        // добавить себя в массив диалогов - для возможностей оперативного закрытия
        Par.JDIALOG_STACK.add(this);
        start(null);
    }
    */
    public void start(Container container) throws WEditException {
        try {
            GuiTools.setDialogScreenCenterPosition(this, container);

            if (worker != null) {
                if (poolExecutor.getPoolSize() > 4) {
                    Log.l.error("WaitingObjectDialog.start: poolExecutor pool size greate then 4 = {}" +
                                    "; poolExecutor info = {}", poolExecutor.getPoolSize(), poolExecutor);
                }
                if (poolExecutor.getQueue().size() > 2) {
                    Log.l
                            .error("WaitingObjectDialog.start: poolExecutor queue size greate then 2 = {}" +
                                    "; poolExecutor info = {}", poolExecutor.getQueue().size(), poolExecutor);
                }
                poolExecutor.execute(worker);
            }
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            timer.start();

        } catch (Exception e) {
            Log.l.error("WaitingObjectDialog (" + getName() + ").start: Error", e);
            close();
            throw new WEditException("Ошибка выполнения :\n " + e.getMessage(), e);
        }

        setVisible(true);
    }

    public ResponseObject getResultMsg() {
        Object obj;
        ResponseObject result;

        result = new ResponseObject();
        try {
            if (canceled) {
                throw new CancellationException();
            }
            if (worker != null) {
                obj = worker.get(); // должен вернуть ResponseObject
                if (obj != null) {
                    if (obj instanceof ResponseObject) {
                        result = (ResponseObject) obj;
                    }
                } else {
                    result.setException(new WEditException(null, "Get NULL response."));
                }
            }
        } catch (CancellationException ce) {
            // Работа прервана пользователем - в точке worker.get().
            result.setObject("Прервано пользователем");
            Log.l.error("WaitingObjectDialog ({}).getResultMsg: Interrupt.", getName());
        } catch (Exception e) {
            result.setException(
                    new WEditException(e,  "Ошибка : " + e.toString()));
            Log.l.error("WaitingObjectDialog (" + getName() + ").getResultMsg: System error.", e);
        }

        return result;
    }

    @Override
    public void doClose(int closeType) {
    }

    /**
     * Нажата кнопка "Прерывание".
     */
    @Override
    public void doCancel() {
        // запрещаем перерисовку фрейма в любом случае т.к. нигде никаких изменений не произошло.
        /*
        if (!Par.STATE.isError()) {
            Par.STATE = GCons.SystemState.CANCEL;
        }
        */
        canceled = true;
        // Закрыть диалог. Отправка команды на сервер - в воркере при его закрытии - по состоянию, если он еще в работе.
        close();
    }

    @Override
    public void doOk() {
    }

    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        doCancel();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
