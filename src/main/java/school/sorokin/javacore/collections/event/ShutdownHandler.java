package school.sorokin.javacore.collections.event;

public class ShutdownHandler implements EventHandler {
    @Override
    public void processing() {
        System.out.println("Завершение работы.");
    }

    @Override
    public String description() {
        return "Выйти";
    }
}
