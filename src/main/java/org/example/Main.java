package org.example;

import java.util.*;

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
        List<String> pageOption = Arrays.asList(new String[]{"f", "p", "n", "l", "g"});
        while (true) {
            productController.printPaginationView();
            System.out.println("-".repeat(46) + " Menu " + "-".repeat(46));
            System.out.println("     F. First Page     P. Previous Page     N. Next Page     L. Last Page     G. Goto");
            System.out.println();
            System.out.println(Menu.getMenuTable());
            System.out.println("-".repeat(98));
            System.out.print(ConsoleColor.ANSI_YELLOW + "=> Choose an option: " + ConsoleColor.ANSI_RESET);
            String optionInput = sc.nextLine().trim().toLowerCase();

            if (Menu.menuOptionMap.containsKey(optionInput)) {
                Menu.menuOptionMap.get(optionInput).execute();
            } else if (pageOption.contains(optionInput)) {
                int totalPage = productController.getTotalPage();
                switch (optionInput) {
                    case "f" -> productController.setStartCount(0);
                    case "p" -> productController.decrementPage();
                    case "n" -> productController.incrementPage();
                    case "l" -> productController.setPageByPageNumber(totalPage);
                    case "g" -> {
                        while (true) {
                            int pageNumInput = Integer.parseInt(pv.inputLoopWithRegexValidation(ConsoleColor.ANSI_YELLOW + "Enter page number (1-" + totalPage + "): " + ConsoleColor.ANSI_RESET, "^[0-9+$", "Invalid Input. Please enter a valid page number"));
                            if (pageNumInput > 0 && pageNumInput <= totalPage ) {
                                productController.setPageByPageNumber(pageNumInput);
                                break;
                            }
                            else
                                System.out.println("Invalid Input. Please enter a valid page number\n");
                        }
                    }
                }
            } else {
                System.out.println("Invalid Option. Please try again \n");
            }
            if (optionInput.equals("e"))
                break;
        }
    }

    public static void initializedMenuOption() {
        Menu.addMenuOption("W", "Write", () -> productController.writeProducts());
        Menu.addMenuOption("R", "Read (id)", () -> productController.readProductById(new Scanner(System.in)));
        Menu.addMenuOption("U", "Update", () -> productController.updateProduct());
        Menu.addMenuOption("D", "Delete", () -> productController.deleteProduct());
        Menu.addMenuOption("S", "Search (name)", () -> {
        });
        Menu.addMenuOption("Se", "Set rows", () -> productController.setRowsOperation());
        Menu.addMenuOption("Sa", "Save", () -> productController.saveOperation());
        Menu.addMenuOption("Un", "Unsaved", () -> productController.unsavedOperation());
        Menu.addMenuOption("Ba", "Backup", () -> {
        });
        Menu.addMenuOption("Re", "Restore", () -> {
        });
        Menu.addMenuOption("E", "Exit", () -> {
        });
    }
}