package school.sorokin.javacore.collections.event;

public class NoSuchHandler implements EventHandler {
    @Override
    public void processing() {
        System.out.println("Команда не поддерживается.");
    }

    @Override
    public String description() {
        return "";
    }
}
