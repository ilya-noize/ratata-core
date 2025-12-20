package school.sorokin.javacore.multithreading.synchronize.task;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerCounter implements SiteVisitCounter {
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void incrementVisitCounter() {
        counter.incrementAndGet();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getVisitCount() {
        return counter.get();
    }
}
