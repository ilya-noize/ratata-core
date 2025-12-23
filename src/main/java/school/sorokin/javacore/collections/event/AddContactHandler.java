package school.sorokin.javacore.collections.event;

import school.sorokin.javacore.collections.Contact;

import static school.sorokin.javacore.collections.StarterListener.MANAGER;
import static school.sorokin.javacore.collections.StarterListener.getParameter;

public class AddContactHandler implements EventHandler {

    @Override
    public void processing() {
        System.out.println("Укажите параметры для нового контакта");
        String message = MANAGER.save(buildContact()) ? "Контакт добавлен." : "Такой контакт уже существует!";
        System.out.println(message);
    }

    @Override
    public String description() {
        return "Добавить контакт";
    }

    private static Contact buildContact() {
        String name = getParameter("имя: ", false);
        String phone = getParameter("телефон: ", false);
        String email = getParameter("email: ", true);
        String group = getParameter("группу: ", true);

        return new Contact(name, phone, email, group);
    }
}
