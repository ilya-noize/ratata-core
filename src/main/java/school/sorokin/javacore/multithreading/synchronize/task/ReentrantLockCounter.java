package school.sorokin.javacore.multithreading.synchronize.task;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCounter implements SiteVisitCounter {
    private int counter = 0;
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public void incrementVisitCounter(){
        lock.lock();
        try {
            counter++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getVisitCount() {
        return counter;
    }
}
