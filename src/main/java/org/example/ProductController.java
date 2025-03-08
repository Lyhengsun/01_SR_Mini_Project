package org.example;

import java.util.Scanner;

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
    public void deleteProduct() {
        int productID;
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.print("Please Enter ID to Delete the record: ");

            if (scan.hasNextInt()) {
                productID = scan.nextInt();

                if (productID > 0) {
                    break;
                } else {
                    System.out.println("Product ID cannot be 0 or negative. Please enter a valid number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number for Product ID.");
                scan.next();
            }
        }

        ProductModel product = pmi.getProductByID(productID);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("Are you sure you want to delete product ID: " + productID + "? (y/n)");
        String confirmation = scan.next();

        if (confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes")) {
            boolean isDeleted = pmi.deleteProduct(productID);
            if (isDeleted) {
                System.out.println("Deleted successfully.");
            } else {
                System.out.println("Error: Unable to delete product.");
            }
        } else {
            System.out.println("Deletion canceled.");
        }
    }
}
