package school.sorokin.javacore.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ContactManager {
    private final Set<Contact> contacts = new HashSet<>();
    private final Map<String, List<Contact>> groups = new HashMap<>();

    public ContactManager(boolean demoMode) {
        if(demoMode) {
            for (int i = 0; i < 10; i++) {
                String s = String.valueOf(i);
                String gr = String.valueOf(i % 2);
                save(new Contact(s, s, s, gr));
            }
        }
    }

    public boolean save(Contact contact) {
        if (contacts.contains(contact)) {
            return false;
        }

        contacts.add(contact);
        groups.computeIfAbsent(contact.group(), key -> new ArrayList<>())
                .add(contact);

        return true;
    }

    public List<Contact> findContact(Contact template) {
        Set<Contact> result = new HashSet<>();
        for (Contact contact : contacts) {
            if (contact.name().contains(template.name()) && !template.name().isBlank()) {
                result.add(contact);
            }
            if (contact.phone().contains(template.phone()) && !template.phone().isBlank()) {
                result.add(contact);
            }
            if (contact.email().contains(template.email()) && !template.email().isBlank()) {
                result.add(contact);
            }
            if (contact.group().contains(template.group()) && !template.group().isBlank()) {
                result.add(contact);
            }
        }
        return result.isEmpty() ? List.of() : new ArrayList<>(result);
    }

    public List<Contact> getAllContacts() {
        if (contacts.isEmpty()) {
            return List.of();
        }
        return new ArrayList<>(contacts);
    }

    public List<Contact> getByGroup(String group) {
        if (groups.isEmpty()) {
            return List.of();
        }
        List<Contact> result = new ArrayList<>();
        for (String grouping : groups.keySet()) {
            if (grouping.contains(group)) {
                result.addAll(groups.get(grouping));
            }
        }
        return result.isEmpty() ? List.of() : result;
    }

    public Set<String> getContactGroups() {
        return groups.keySet();
    }

    public void remove(Contact contact) {
        contacts.remove(contact);
        groups.get(contact.group()).remove(contact);
    }

    public void removeAll(List<Contact> contacts) {
        contacts.forEach(this::remove);
    }
}
