package org.example;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ProductController {
    private final ProductView pv;
    private final ProductModelImplement pmi;

    ProductController(ProductView pv, ProductModelImplement pmi) {
        this.pv = pv;
        this.pmi = pmi;
    }

    public void printPaginationView() {
        System.out.println(pv.getProductPage(pmi.getAllProducts()));
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

    // Method to validate numeric ID input
    private boolean isValidId(String input) {
        return input.matches("^[0-9]+$");
    }
}