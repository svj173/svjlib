package svj.svjlib.gui.dialog;


import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: mikhail
 * Date: 4/4/14
 * Time: 9:22 AM
 */
public class TestThreadPoolExecutor extends ThreadPoolExecutor {

    private String threadName;
    /**
     * Последний запущенный процесс. Для информации.
     */
    private Runnable process = null;

    public TestThreadPoolExecutor(String threadName, int max) {
        super(max, max, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<>(), new TestThreadFactory(threadName));
        this.threadName = threadName;
    }

    /**
     * Кешированный, т.е. при ненадобности потоки самоустраняются
     *
     * @param threadName         имя потоков
     * @param max                максимальный размер
     * @param keepAliveInSeconds сколько будет жить поток бездействуя в секундах
     */
    public TestThreadPoolExecutor(String threadName, int max, long keepAliveInSeconds) {
        super(0, max, keepAliveInSeconds, TimeUnit.SECONDS, new SynchronousQueue<>(),
                new TestThreadFactory(threadName));
        this.threadName = threadName;
    }

    public void execute(Runnable process) {
        this.process = process;
        super.execute(process);
    }

    public StringBuilder getExecuteInfo() {
        int top = 0.8 * getCorePoolSize() < 1 ? 1 : (int) (0.8 * getCorePoolSize());

        StringBuilder result = new StringBuilder(threadName);
        result.append(" executor state:");

        result.append("\n\tActive: ").append(getActiveCount());
        result.append("\n\tCope pool size: ").append(getCorePoolSize());
        result.append("\n\tMaximum pool size: ").append(getMaximumPoolSize());
        result.append("\n\tCurrent pool size: ").append(getPoolSize());
        result.append("\n\tCurrent queue size: ").append(getQueue().size());
        result.append("\n\tState : ");
        if (!getQueue().isEmpty()) {
            result.append("overloaded (tasks are queued)");
        } else if (getActiveCount() < top) {
            result.append("normal (active size less than 80% of maximum)");
        } else {
            result.append("warning (active size greater than 80% of maximum)");
        }
        result.append("\n\tTotal completed tasks: ").append(getCompletedTaskCount());
        result.append("\n\tLast start process: ").append(process);

        return result;
    }
}
