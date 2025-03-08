package org.example;
import java.util.Date;

public class ProductModel {
    private int id;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getQty() {
        return qty;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public Date getImportDate() {
        return importDate;
    }
}

