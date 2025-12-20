package school.sorokin.javacore.multithreading.synchronize.task;

import java.util.List;

public class Starter {
    public static void main(String[] args) {
        List<SiteVisitCounter> visitCounters = List.of(
                new UnsychronizedCounter(),
                new VolatileCounter(),
                new AtomicIntegerCounter(),
                new SychronizedBlockCounter(),
                new ReentrantLockCounter()
        );
        int numOfThreads = 10;

        for(SiteVisitCounter visitCounter: visitCounters) {
            MultithreadingSiteVisitor visitor = new MultithreadingSiteVisitor(visitCounter);
            visitor.visitMultithread(numOfThreads);
            visitor.waitUntilAllVisited();
            visitor.getTotalTimeOfHandling();
        }
    }
}
