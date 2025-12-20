package school.sorokin.javacore.multithreading.synchronize.task;

import java.util.HashMap;
import java.util.Map;

public class MultithreadingSiteVisitor {
    private final SiteVisitCounter siteVisitCounter;
    private final Map<Integer, StatThreadPoint> statisticPoints = new HashMap<>();
    private int numOfThreads = 0;

    public MultithreadingSiteVisitor(SiteVisitCounter siteVisitCounter) {
        this.siteVisitCounter = siteVisitCounter;
    }

    public void visitMultithread(int numOfThreads) {
        this.numOfThreads = numOfThreads;
        for (int i = 0; i < numOfThreads; i++) {
            Thread thread = new Thread(siteVisitCounter::incrementVisitCounter);
            statisticPoints.put(i, new StatThreadPoint(thread));
            thread.start();
        }
    }

    public void waitUntilAllVisited() {
        statisticPoints.keySet().stream()
                .mapToInt(i -> i)
                .mapToObj(statisticPoints::get)
                .forEach(statThreadPoint -> {
                    try {
                        statThreadPoint.thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    statThreadPoint.setEnd(System.currentTimeMillis());
                });
    }

    public void getTotalTimeOfHandling() {
        System.out.println("Статистика по потокам класса " + siteVisitCounter.getClass().getSimpleName());
        long start = statisticPoints.get(0).start;
        long end = statisticPoints.get(numOfThreads - 1).end;
        long difference = end - start;
        System.out.println("difference = " + difference);
    }

    private static class StatThreadPoint {
        private final Thread thread;
        private final long start;
        private long end;

        public StatThreadPoint(Thread thread) {
            this.thread = thread;
            this.start = System.currentTimeMillis();
            this.end = 0L;
        }

        public void setEnd(long end) {
            if (end < start) {
                throw new IllegalArgumentException("End before start.");
            }
            this.end = end;
        }

        private long diffTime() {
            return end - start;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;

            StatThreadPoint that = (StatThreadPoint) o;
            return start == that.start && thread.equals(that.thread);
        }

        @Override
        public int hashCode() {
            int result = thread.hashCode();
            result = 31 * result + Long.hashCode(start);
            return result;
        }

        @Override
        public String toString() {
            return "Thread " + thread.getName() +
                    ", start=" + start +
                    ", end=" + end +
                    ". duration=" + diffTime();
        }
    }
}
