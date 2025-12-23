package school.sorokin.javacore.collections.event;

import school.sorokin.javacore.collections.Contact;

import java.util.List;

import static school.sorokin.javacore.collections.StarterListener.MANAGER;

public class PrintContactsHandler implements EventHandler {

    @Override
    public void processing() {
        List<Contact> contacts = MANAGER.getAllContacts();
        if(contacts.isEmpty()) {
            System.out.println("Список контактов пуст");
            return;
        }
        contacts.forEach(System.out::println);
    }

    @Override
    public String description() {
        return "Просмотреть все контакты";
    }
}
