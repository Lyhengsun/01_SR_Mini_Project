package org.example;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ProductController {
    private final ProductView pv;
    private final ProductModelImplement pmi;
    private ArrayList<ProductModel> unsavedWrite = new ArrayList<>();
    private ArrayList<ProductModel> unsavedUpdate = new ArrayList<>();

    ProductController(ProductView pv, ProductModelImplement pmi) {
        this.pv = pv;
        this.pmi = pmi;
    }

    public void printPaginationView() {
        System.out.println(pv.getProductPage(pmi.getAllProducts()));
    }
    public void writeProducts() {

    }

    public void updateProduct() {
        Scanner sc = new Scanner(System.in);
        boolean foundId;
        ProductModelImplement product = new ProductModelImplement();
        int inputId;
        do{
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
        for(ProductModel p : unsavedUpdate) {
            if (pmi.updateProducts(p)) {
                Helper.successMessage("Product " + p.getId() + " successfully updated");
            } else {
                Helper.errorMessage("Product " + p.getId() + " unsuccessfully updated");
            }
        }
        unsavedUpdate = new ArrayList<>();
    }

    public void unsavedUpdate() {
        pv.unsavedUpdateView(unsavedUpdate);
    }
}
