package org.example;

import java.util.ArrayList;
import java.util.List;

public class ProductController {
    private final ProductView pv;
    private final ProductModelImplement pmi;
    private ArrayList<ProductModel> unsavedWrite = new ArrayList<>();
    private int rows = 3;
    private int startCount = 0;

    ProductController(ProductView pv, ProductModelImplement pmi) {
        this.pv = pv;
        this.pmi = pmi;
    }

    public void printPaginationView() {
        System.out.println(pv.getProductPage(pmi.getAllProducts(), this.startCount, this.rows));
    }

    public void setStartCount(int startCount) {
        this.startCount = startCount;
    }

    public void incrementPage() {
        this.startCount = Math.min(this.startCount + this.rows, ((this.getTotalPage()-1) * this.rows));
    }
    public void decrementPage() {
        this.startCount = Math.max(this.startCount - this.rows, 0);
    }
    public int getTotalPage(){
        ArrayList<ProductModel> products = pmi.getAllProducts();
        return ((products.size() / this.rows) + (products.size() % this.rows == 0 ? 0 : 1));
    }
    public void setPageByPageNumber(int pageNumber) {
        if (pageNumber <= getTotalPage() && pageNumber > 0)
            this.startCount = (pageNumber -1) * this.rows;
    }
    public void setRows(int rows) {
        this.rows = rows;
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

    public void setRowsOperation() {
        while (true){
            int rowsSizeInput = Integer.parseInt(pv.inputLoopWithRegexValidation("Enter a rows size", "^[0-9]+$", "Invalid Input. Rows size need to be an integer and bigger than 0"));
            if (rowsSizeInput > 0) {
                this.setRows(rowsSizeInput);
                break;
            }
            System.out.println("Invalid Input. Rows size need to be an integer and bigger than 0\n");
        }
    }

    public void saveOperation() {
        while (true) {
            System.out.println("'si' to save writes and 'su' to save update and 'b' to go back to the menu");
            String unsavedInput = pv.input("Enter your input :").toLowerCase();

            boolean exit = false;
            switch (unsavedInput) {
                case "si" -> {
                    if (unsavedWrite.isEmpty()) {
                        System.out.println("There are no unsaved writes to save");
                        exit = true;
                        break;
                    }
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
                    exit = true;
                }
                case "su" -> {
                    System.out.println();
                    exit = true;
                }
            }
            if (unsavedInput.equals("b") || exit) {
                pv.input("Press Enter to go back to menu...");
                System.out.println();
                break;
            }
            System.out.println("Invalid Input. Please try again \n");
        }
    }

    public void unsavedOperation() {
        while (true) {
            System.out.println("'ui' for unsaved insert and 'uu' for unsaved update and 'b' to go back to the menu");
            String unsavedInput = pv.input("Enter your input").toLowerCase();

            boolean exit = false;
            switch (unsavedInput) {
                case "ui" -> {
                    if (unsavedWrite.isEmpty()) {
                        System.out.println("There are no unsaved writes to display");
                        exit = true;
                        break;
                    }
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
