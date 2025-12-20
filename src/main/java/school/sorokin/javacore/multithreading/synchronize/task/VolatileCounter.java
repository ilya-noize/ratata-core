package school.sorokin.javacore.multithreading.synchronize.task;

public class VolatileCounter implements SiteVisitCounter {
    private volatile int counter = 0;

    @Override
    public void incrementVisitCounter() {
        counter++;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getVisitCount() {
        return counter;
    }
}
