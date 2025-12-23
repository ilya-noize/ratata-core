package school.sorokin.javacore.collections;

import school.sorokin.javacore.collections.event.AddContactHandler;
import school.sorokin.javacore.collections.event.EventHandler;
import school.sorokin.javacore.collections.event.NoSuchHandler;
import school.sorokin.javacore.collections.event.PrintContactsHandler;
import school.sorokin.javacore.collections.event.PrintGroupContractsHandler;
import school.sorokin.javacore.collections.event.RemoveContactHandler;
import school.sorokin.javacore.collections.event.SearchContactHandler;
import school.sorokin.javacore.collections.event.ShutdownHandler;

import java.util.List;
import java.util.Scanner;

/**
 * Включение DEMO-режима
 * new ContactManager(true);
 */
public class StarterListener {
    public static final ContactManager MANAGER = new ContactManager(false);
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final List<EventHandler> HANDLERS = List.of(
            new NoSuchHandler(), // always first

            new AddContactHandler(),
            new RemoveContactHandler(),
            new PrintContactsHandler(),
            new SearchContactHandler(),
            new PrintGroupContractsHandler(),

            new ShutdownHandler() // always last
    );

    public static void main(String[] args) {
        int commandId = 0;
        while (commandId < HANDLERS.size()) {
            printMenu();
            try {
                commandId = Integer.parseInt(SCANNER.nextLine());
                if (commandId >= HANDLERS.size() || commandId < 0) {
                    commandId = 0;
                }
            } catch (NumberFormatException ignored) {
                commandId = 0;
            }
            HANDLERS.get(commandId).processing();
        }
    }

    public static String getParameter(String parameterName, boolean isNull) {
        System.out.printf("Введите %s", parameterName);
        String input = " ";
        while (input.isBlank()) {
            input = SCANNER.nextLine();
            if (isNull) {
                return input;
            }
        }
        return input;
    }

    private static void printMenu() {
        System.out.println("МЕНЮ УПРАВЛЕНИЯ КОНТАКТАМИ.");
        for (int i = 1; i < HANDLERS.size(); i++) {
            System.out.printf("%s. - [%s]%n", i, HANDLERS.get(i).description());
        }
        System.out.println("Введите номер команды:");
    }
}
