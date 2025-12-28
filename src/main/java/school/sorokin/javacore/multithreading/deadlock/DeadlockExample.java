package school.sorokin.javacore.multithreading.deadlock;

public class DeadlockExample {
    private static final int TRIES = 100;

    public static void main(String[] args) {
        Object firstSync = new Object();
        Object secondSync = new Object();

        Thread firstThread = new Thread(() -> {
            for (int i = 0; i < TRIES; i++) {
                synchronized (firstSync) {
                    System.out.println("Первый поток захватил первый монитор");
                    synchronized (secondSync) {
                        System.out.println("Первый поток захватил второй монитор");
                        System.out.println("---");
                    }
                }
            }
        });
        Thread secondThread = new Thread(() -> {
            for (int i = 0; i < TRIES; i++) {
                synchronized (firstSync) {
                    System.out.println("Второй поток захватил первый монитор");
                    synchronized (secondSync) {
                        System.out.println("Второй поток захватил второй монитор");
                        System.out.println("---");
                    }
                }
            }
        });

        firstThread.start();
        secondThread.start();
    }
}