package svj.svjlib.gui.dialog;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created with IntelliJ IDEA.
 * User: mikhail
 * Date: 4/4/14
 * Time: 9:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestThreadFactory implements ThreadFactory {
    final ThreadFactory defaultFactory = Executors.defaultThreadFactory();
    private int num = 0;
    private String threadName = "test";

    public TestThreadFactory(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = defaultFactory.newThread(r);
        thread.setName(String.format("%s-%d", threadName, num++));
        thread.setDaemon(true);
        return thread;
    }
}
