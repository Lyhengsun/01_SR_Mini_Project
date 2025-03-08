package org.example;

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

    public void writeProduct() {
        String inputString = pv.input("Input Word");
        System.out.println(inputString);
        pv.input("Enter to continue...");
    }
}
