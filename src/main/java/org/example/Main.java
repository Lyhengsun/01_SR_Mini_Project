package org.example;

import java.util.Scanner;

public class Main {
    static ProductView pv = new ProductView();
    static ProductModelImplement pmi = new ProductModelImplement();
    static ProductController productController = new ProductController(pv, pmi);
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        initializedMenuOption();
        menuLoop(sc);
        sc.close();
    }

    public static void menuLoop(Scanner sc) {
        while (true) {
            productController.printPaginationView();
            System.out.println(Menu.getMenuTable());
            System.out.print(ConsoleColor.ANSI_YELLOW + "=> Choose an option: " + ConsoleColor.ANSI_RESET);
            String optionInput = sc.nextLine().trim().toLowerCase();

            if (Menu.menuOptionMap.containsKey(optionInput)) {
                Menu.menuOptionMap.get(optionInput).execute();
            } else {
                System.out.println("Invalid Option. Please try again \n");
            }
            if (optionInput.equals("e"))
                break;
        }
    }

    public static void initializedMenuOption() {
        Menu.addMenuOption("W", "Write", () -> {
        });
        Menu.addMenuOption("R", "Read (id)", () -> {
        });
        Menu.addMenuOption("U", "Update", () -> {
            productController.updateProduct();
        });
        Menu.addMenuOption("D", "Delete", () -> {
        });
        Menu.addMenuOption("S", "Search (name)", () -> {
        });
        Menu.addMenuOption("Se", "Set rows", () -> {
        });
        Menu.addMenuOption("Sa", "Save", () -> {
            productController.savedUpdate();
        });
        Menu.addMenuOption("Un", "Unsaved", () -> {
           productController.unsavedUpdate();
        });
        Menu.addMenuOption("Ba", "Backup", () -> {
        });
        Menu.addMenuOption("Re", "Restore", () -> {
        });
        Menu.addMenuOption("E", "Exit", () -> {
        });
    }
}