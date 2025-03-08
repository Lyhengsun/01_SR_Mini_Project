package org.example;

import java.util.ArrayList;

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

    }
}
