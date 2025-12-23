package school.sorokin.javacore.collections.event;

import school.sorokin.javacore.collections.Contact;

import java.util.List;

import static school.sorokin.javacore.collections.StarterListener.MANAGER;
import static school.sorokin.javacore.collections.StarterListener.getParameter;

public class SearchContactHandler implements EventHandler {

    @Override
    public void processing() {
        System.out.println("Укажите параметры для поиска контакта");

        Contact template = buildContact();
        List<Contact> contacts = MANAGER.findContact(template);
        if (contacts.isEmpty()) {
            System.out.printf("Не найден контакт с такими параметрами: %s", template);
            return;
        }
        contacts.forEach(System.out::println);
    }

    @Override
    public String description() {
        return "Найти контакт";
    }

    private static Contact buildContact() {
        String name = getParameter("имя: ", true);
        String phone = getParameter("телефон: ", true);
        String email = getParameter("email: ", true);
        String group = getParameter("группу: ", true);

        return new Contact(name, phone, email, group);
    }
}
