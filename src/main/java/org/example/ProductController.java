package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ProductController {
    private final ProductView pv;
    private final ProductModelImplement pmi;
    private ArrayList<ProductModel> unsavedWrite = new ArrayList<>();
    private int rows = 3;
    private int startCount = 0;
    private ArrayList<ProductModel> unsavedUpdate = new ArrayList<>();

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
        this.startCount = Math.min(this.startCount + this.rows, ((this.getTotalPage() - 1) * this.rows));
    }

    public void decrementPage() {
        this.startCount = Math.max(this.startCount - this.rows, 0);
    }

    public int getTotalPage() {
        ArrayList<ProductModel> products = pmi.getAllProducts();
        return ((products.size() / this.rows) + (products.size() % this.rows == 0 ? 0 : 1));
    }

    public void setPageByPageNumber(int pageNumber) {
        if (pageNumber <= getTotalPage() && pageNumber > 0)
            this.startCount = (pageNumber - 1) * this.rows;
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
        while (true) {
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
                            if (pmi.insertNewProductWithoutSync(product))
                                System.out.println(ConsoleColor.ANSI_GREEN + "New product '" + product.getName() + "' have been successfully added" + ConsoleColor.ANSI_RESET);
                            else
                                System.out.println(ConsoleColor.ANSI_RED + "New product '" + product.getName() + "' failed to get added" + ConsoleColor.ANSI_RESET);
                        }
                        pmi.syncData();
                        this.unsavedWrite = new ArrayList<>();
                    } else {
                        System.out.println(ConsoleColor.ANSI_RED + "Save Write Cancelled" + ConsoleColor.ANSI_RESET);
                    }
                    exit = true;
                }
                case "su" -> {
                    this.savedUpdate();
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
                    this.unsavedUpdateOperation();
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

    public void updateProduct() {
        Scanner sc = new Scanner(System.in);
        boolean foundId;
        ProductModelImplement product = new ProductModelImplement();
        int inputId;
        do {
            Helper.inputMessage("Input ID to update: ");
            String tempInput = sc.nextLine();
            tempInput = Helper.inputValidation(tempInput, Helper.TYPE.UPDATEID);
            inputId = Integer.parseInt(tempInput);
            foundId = product.getProductByIDBool((inputId));
        } while (!foundId);

        ProductModel productFound = product.getProductByID(inputId);
        unsavedUpdate.removeIf(p -> p.getId() == productFound.getId());
        int choice = pv.updateProductView(productFound);
        do {
            switch (choice) {
                case 1 -> {
                    Helper.inputMessage("Enter name: ");
                    String name = sc.nextLine();
                    productFound.setName(name);
                    unsavedUpdate.add(productFound);
                    Helper.successMessage("Successfully updated" + name);

                }
                case 2 -> {
                    Helper.inputMessage("Enter unit price: ");
                    String temp = sc.nextLine();
                    temp = Helper.inputValidation(temp, Helper.TYPE.PRICE);
                    productFound.setUnitPrice(Double.parseDouble(temp));
                    unsavedUpdate.add(productFound);
                    Helper.successMessage("Successfully updated " + temp);

                }
                case 3 -> {
                    Helper.inputMessage("Enter quantity: ");
                    String temp = sc.nextLine();
                    temp = Helper.inputValidation(temp, Helper.TYPE.QUANTITY);
                    productFound.setQty(Integer.parseInt(temp));
                    unsavedUpdate.add(productFound);
                    Helper.successMessage("Successfully updated " + temp);

                }
                case 4 -> {
                    Helper.inputMessage("Enter name: ");
                    String name = sc.nextLine();
                    productFound.setName(name);
                    Helper.inputMessage("Enter unit price: ");
                    String priceTemp = sc.nextLine();
                    priceTemp = Helper.inputValidation(priceTemp, Helper.TYPE.PRICE);
                    productFound.setUnitPrice(Double.parseDouble(priceTemp));
                    Helper.inputMessage("Enter quantity: ");
                    String qtyTemp = sc.nextLine();
                    qtyTemp = Helper.inputValidation(qtyTemp, Helper.TYPE.QUANTITY);
                    productFound.setQty(Integer.parseInt(qtyTemp));
                    unsavedUpdate.add(productFound);
                    Helper.successMessage("Successfully updated.");
                }
                case 5 -> {
                    return;
                }
            }
        } while (Helper.continueInput());
    }

    public void savedUpdate() {
        for (ProductModel p : unsavedUpdate) {
            if (pmi.updateProducts(p)) {
                Helper.successMessage("Product " + p.getId() + " successfully updated");
            } else {
                Helper.errorMessage("Product " + p.getId() + " unsuccessfully updated");
            }
        }
        pmi.syncData();
        unsavedUpdate = new ArrayList<>();
    }

    public void unsavedUpdateOperation() {
        pv.unsavedUpdateView(this.unsavedUpdate);
    }

    // Read Product by ID
    public void readProductById(Scanner sc) {
        while (true) {
            System.out.print("Enter Product ID: ");
            String input = sc.nextLine().trim();

            if (!isValidId(input)) {
                System.out.println(ConsoleColor.ANSI_RED +
                        "Invalid ID format. Please enter only numbers without spaces or special characters." +
                        ConsoleColor.ANSI_RESET);
                continue;
            }

            int productId = Integer.parseInt(input);
            ProductModel product = pmi.getProductByID(productId);

            if (product != null) {
                pv.displayProduct(product);
            } else {
                System.out.println(ConsoleColor.ANSI_RED + "Product not found!" + ConsoleColor.ANSI_RESET);
            }

            // Ask user if they want to return to the menu
            while (true) {
                System.out.print(ConsoleColor.ANSI_YELLOW + "Do you want to go back to the menu? (y/n): " + ConsoleColor.ANSI_RESET);
                String choice = sc.nextLine().trim().toLowerCase();

                if (choice.equals("y")) {
                    return;
                } else if (choice.equals("n")) {
                    System.out.println(ConsoleColor.ANSI_BLUE + "Please enter the product ID for display again." + ConsoleColor.ANSI_RESET);
                    break;
                } else {
                    System.out.println(ConsoleColor.ANSI_YELLOW + "Invalid choice! Please enter 'y' or 'n'." + ConsoleColor.ANSI_RESET);
                }
            }
        }
    }

    public void deleteProduct() {
        Scanner scan = new Scanner(System.in);
        int productID;
        while (true) {
            ProductModel product = null;
            while (true) {
                System.out.print(ConsoleColor.ANSI_YELLOW + "Please Enter ID to Delete the record: " + ConsoleColor.ANSI_RESET);

                if (scan.hasNextInt()) {
                    productID = scan.nextInt();

                    if (productID > 0) {
                        product = pmi.getProductByID(productID);

                        if (product != null) {
                            break;
                        } else {
                            System.out.println(ConsoleColor.ANSI_YELLOW + "Product not found. Please enter a valid Product ID." + ConsoleColor.ANSI_RESET);
                        }
                    } else {
                        System.out.println(ConsoleColor.ANSI_YELLOW + "Product ID cannot be 0 or negative. Please enter a valid number." + ConsoleColor.ANSI_RESET);
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid number for Product ID.");
                    scan.next();
                }
            }
            System.out.println(pv.productsView(List.of(product)));
            System.out.println("Are you sure you want to delete product ID: " + productID + "? (y/n)");
            String confirmation = scan.next();

            if (confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes")) {
                boolean isDeleted = pmi.deleteProduct(productID);
                if (isDeleted) {
                    System.out.println(ConsoleColor.ANSI_GREEN + "Deleted successfully." + ConsoleColor.ANSI_RESET);
                    System.out.println("Press Enter to continue...");
                    scan.nextLine();
                    scan.nextLine();
                    break;
                } else {
                    System.out.println(ConsoleColor.ANSI_RED + "Error: Unable to delete product." + ConsoleColor.ANSI_RESET);
                }
            } else {
                System.out.println("Deletion canceled.");
                break;
            }
        }
    }

    // Method to validate numeric ID input
    private boolean isValidId(String input) {
        return input.matches("^[0-9]+$");
    }
}
