package school.sorokin.javacore.basics;

import java.util.Scanner;

public class ContactTrackingSystem {
    private static final int MAX_COUNT_CONTACTS = 10;
    private static int noteCounter = 0;
    private static final Scanner scanner = new Scanner(System.in);
    private static final String[] commands = new String[]{
            "NEW",
            "SHOW ALL",
            "SEARCH",
            "DELETE",
            "EXIT"
    };
    private static final String[] names = new String[MAX_COUNT_CONTACTS];
    private static final String[] phones = new String[MAX_COUNT_CONTACTS];

    public static void main(String[] args) {
        initializeArrays();
        do {
            printMenu();
            int commandId = 0;
            try {
                commandId = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored) {
                // not matter
            }
            switch (commandId) {
                case 1:
                    addContact();
                    break;
                case 2:
                    getContacts();
                    break;
                case 3:
                    operationContact(false);
                    break;
                case 4:
                    operationContact(true);
                    break;
                case 5:
                    closeApplication();
                    return;
                default:
                    noSuchCommand();
                    break;
            }
        } while (true);
    }

    private static void initializeArrays() {
        for (int i = 0; i < MAX_COUNT_CONTACTS; i++) {
            names[i] = "";
            phones[i] = "";
        }
    }

    private static void addContact() {
        if (noteCounter == MAX_COUNT_CONTACTS) {
            System.out.println("Sorry, there is no place in the list of contacts.");
            return;
        }

        System.out.print("Enter name:");
        String name = getString();

        System.out.print("Enter phone:");
        String phone = getString();
        for (int i = 0; i < MAX_COUNT_CONTACTS; i++) {
            if (names[i].isBlank()) {
                names[i] = name;
                phones[i] = phone;
                System.out.printf("Successfully save contact (%s - %s) by number %d.%n-%n", name, phone, ++noteCounter);
                return;
            }
        }
    }

    private static void getContacts() {
        for (int i = 0; i < MAX_COUNT_CONTACTS; i++) {
            if (!names[i].isBlank()) {
                System.out.printf("Name:%s / Phone:%s%n", names[i], phones[i]);
            }
        }
    }

    private static void operationContact(boolean isRemoved) {
        System.out.printf("Enter name for %s... ", isRemoved ? "remove" : "search");
        String searchName = getString();

        for (int i = 0; i < MAX_COUNT_CONTACTS; i++) {
            boolean findName = names[i].equals(searchName);
            if (findName) {
                System.out.printf("[%d] %s - %s%n", i + 1, names[i], phones[i]);
                if (isRemoved) {
                    for (int k = i + 1; k < MAX_COUNT_CONTACTS - 1; k++) {
                        names[i] = names[k];
                        phones[i] = phones[k];
                    }
                    noteCounter--;
                    System.out.printf("Name %s removed%n", searchName);
                    return;
                }
            }
        }
        System.out.println("No such name found.");
    }

    private static void closeApplication() {
        System.out.println("Closing the application.");
    }

    private static void noSuchCommand() {
        System.out.println("No such command found.");
    }

    private static String getString() {
        String name = "";
        while (name.isBlank()) {
            name = scanner.nextLine();
        }
        return name;
    }

    private static void printMenu() {
        System.out.println("MENU - CONTACT TRACKING SYSTEM");
        for (int i = 0; i < commands.length; i++) {
            System.out.printf("[%s] - [%s]%n", i + 1, commands[i]);
        }
    }
}
