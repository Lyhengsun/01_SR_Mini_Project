package org.example;
import java.util.Date;

public class ProductModel {
    private final int id;
    private String name;
    private double unitPrice;
    private int qty;
    private Date importDate;

    public ProductModel(int id, String name, double unitPrice, int qty, Date importDate) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.importDate = importDate;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getUnitPrice() {
        return unitPrice;
    }
    public int getQty() {
        return qty;
    }
    public Date getImportDate() {
        return importDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}

