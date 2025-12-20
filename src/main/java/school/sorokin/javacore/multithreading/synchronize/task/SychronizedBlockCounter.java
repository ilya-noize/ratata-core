package school.sorokin.javacore.multithreading.synchronize.task;

import static java.lang.Thread.sleep;

public class SychronizedBlockCounter implements SiteVisitCounter {
    private int counter = 0;
    private final Object monitor = new Object();

    @Override
    public void incrementVisitCounter() {
        synchronized (monitor) {
            counter++;
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getVisitCount() {
        return counter;
    }
}
