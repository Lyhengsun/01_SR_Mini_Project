package org.example;

import java.util.ArrayList;
import java.util.List;

public class ProductController {
    private final ProductView pv;
    private final ProductModelImplement pmi;
    private ArrayList<ProductModel> unsavedWrite = new ArrayList<>();

    ProductController(ProductView pv, ProductModelImplement pmi) {
        this.pv = pv;
        this.pmi = pmi;
    }

    public void printPaginationView() {
        System.out.println(pv.getProductPage(pmi.getAllProducts()));
    }

    public void writeProducts() {
        int newId;
        if (this.unsavedWrite.isEmpty())
            newId = pmi.getNewID();
        else
            newId = unsavedWrite.getLast().getId() + 1;
        ProductModel newProduct = pv.writeProductView(newId);
        if (newProduct != null) {
            System.out.println(pv.productsView(new ArrayList<>(List.of(newProduct))));
        }
        System.out.println("Product " + newId + ConsoleColor.ANSI_GREEN + " successfully added." + ConsoleColor.ANSI_RESET);
        this.unsavedWrite.add(newProduct);
        this.pv.input("Press Enter to continue...");
    }

    public void saveOperation() {
        while (true) {
            System.out.println("'si' to save insert and 'su' to save update and 'b' to go back to the menu");
            String unsavedInput = pv.input("Enter your input :").toLowerCase();

            boolean exit = false;
            switch (unsavedInput) {
                case "si" -> {
                    System.out.println(pv.productsView(unsavedWrite));
                    boolean confirmInsert = pv.inputYesNoLoopValidation(ConsoleColor.ANSI_YELLOW + "Are you sure to save this insert? (input y for yes and n for no) : " + ConsoleColor.ANSI_RESET);

                    if (confirmInsert) {
                        for (ProductModel product : this.unsavedWrite) {
                            if(pmi.insertNewProductWithoutSync(product))
                                System.out.println(ConsoleColor.ANSI_GREEN + "New product '"+ product.getName() +"' have been successfully added" + ConsoleColor.ANSI_RESET);
                            else
                                System.out.println(ConsoleColor.ANSI_RED + "New product '"+ product.getName() + "' failed to get added" + ConsoleColor.ANSI_RESET);
                        }
                        pmi.syncData();
                        this.unsavedWrite = new ArrayList<>();
                    } else {
                        System.out.println(ConsoleColor.ANSI_RED + "Save Write Cancelled" + ConsoleColor.ANSI_RESET);
                    }
                    pv.input("Press Enter to go back to menu...");
                    exit = true;
                }
                case "su" -> {
                    System.out.println();
                    pv.input("Press Enter to go back to menu...");
                    exit = true;
                }
            }
            if (unsavedInput.equals("b") || exit) {
                System.out.println();
                break;
            }
            System.out.println("Invalid Input. Please try again \n");
        }
    }

    public void unsavedOperation() {
        while (true) {
            System.out.println("'ui' for unsaved insert and 'uu' for unsaved update and 'b' to go back to the menu");
            String unsavedInput = pv.input("Enter your input :").toLowerCase();

            boolean exit = false;
            switch (unsavedInput) {
                case "ui" -> {
                    System.out.println(pv.productsView(unsavedWrite));
                    pv.input("Press Enter to go back to menu...");
                    exit = true;
                }
                case "uu" -> {
                    System.out.println();
                    pv.input("Press Enter to go back to menu...");
                    exit = true;
                }
            }
            if (unsavedInput.equals("b") || exit) {
                System.out.println();
                break;
            }
            System.out.println("Invalid Input. Please try again \n");
        }
    }
}
