package school.sorokin.javacore.collections.event;

import school.sorokin.javacore.collections.Contact;

import java.util.List;

import static school.sorokin.javacore.collections.StarterListener.MANAGER;
import static school.sorokin.javacore.collections.StarterListener.getParameter;

public class PrintGroupContractsHandler implements EventHandler {

    @Override
    public void processing() {
        System.out.printf("Группы: %s%n", MANAGER.getContactGroups().toString());

        String groupName = getParameter("название группы: ", true);
        List<Contact> byGroup = MANAGER.getByGroup(groupName);
        if (byGroup.isEmpty()) {
            System.out.printf("Группа %s пуста!%n", groupName);
            return;
        }
        System.out.printf("--- Контакты в группе \"%s\": ---%n", groupName);
        byGroup.forEach(System.out::println);
    }

    @Override
    public String description() {
        return "Просмотреть контакты по группе";
    }
}
