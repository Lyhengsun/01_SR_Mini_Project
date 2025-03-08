package org.example;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductView {
    public String getProductPage(ArrayList<ProductModel> products) {
        Table productsTable = new Table(5, BorderStyle.UNICODE_ROUND_BOX, ShownBorders.ALL);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);
        productsTable.addCell(" ".repeat(3) + "ID" + " ".repeat(3), cellStyle);
        productsTable.addCell(" ".repeat(5) + "Name" + " ".repeat(5), cellStyle);
        productsTable.addCell(" ".repeat(3) + "Unit Price" + " ".repeat(3), cellStyle);
        productsTable.addCell(" ".repeat(3) + "Qty" + " ".repeat(3), cellStyle);
        productsTable.addCell(" ".repeat(3) + "Import Date" + " ".repeat(3), cellStyle);

        for (ProductModel product : products) {
            productsTable.addCell(String.valueOf(product.getId()), cellStyle);
            productsTable.addCell(" ".repeat(3) + product.getName() + " ".repeat(3), cellStyle);
            productsTable.addCell(String.format("%.2f", product.getUnitPrice()), cellStyle);
            productsTable.addCell(String.valueOf(product.getQty()), cellStyle);
            productsTable.addCell(new SimpleDateFormat("yyyy-MM-dd").format(product.getImportDate()), cellStyle);
        }
        productsTable.addCell("Page 1", cellStyle, 2);
        productsTable.addCell("Total Records : " + products.size(), cellStyle, 3);
        return productsTable.render();
    }

    public String input(String question) {
        Scanner sc = new Scanner(System.in);
        System.out.print(question + ":");
        return sc.nextLine();
    }

    public void displayProduct(ProductModel product) {
        Table productTable = new Table(5, BorderStyle.UNICODE_ROUND_BOX, ShownBorders.ALL);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);


        productTable.addCell(ConsoleColor.ANSI_PURPLE + " Read Product By ID " + ConsoleColor.ANSI_RESET, cellStyle, 5);

        productTable.addCell(ConsoleColor.ANSI_BLUE + " ".repeat(3) + "ID" + " ".repeat(3)  + ConsoleColor.ANSI_RESET, cellStyle);
        productTable.addCell(ConsoleColor.ANSI_BLUE+ " ".repeat(5) + "Name" + " ".repeat(5)  + ConsoleColor.ANSI_RESET, cellStyle);
        productTable.addCell(ConsoleColor.ANSI_BLUE + " ".repeat(3) + "Unit Price" + " ".repeat(3)  + ConsoleColor.ANSI_RESET, cellStyle);
        productTable.addCell(ConsoleColor.ANSI_BLUE + " ".repeat(3) + "Qty" + " ".repeat(3)  + ConsoleColor.ANSI_RESET, cellStyle);
        productTable.addCell(ConsoleColor.ANSI_BLUE + " ".repeat(3) + "Import Date" + " ".repeat(3)  + ConsoleColor.ANSI_RESET, cellStyle);

        // Product Data
        productTable.addCell(String.valueOf(product.getId()), cellStyle);
        productTable.addCell(" " .repeat(3) + product.getName()  + " ".repeat(3), cellStyle);
        productTable.addCell(String.format("$%.2f", product.getUnitPrice()), cellStyle);
        productTable.addCell(String.valueOf(product.getQty()), cellStyle);
        productTable.addCell(new SimpleDateFormat("yyyy-MM-dd").format(product.getImportDate()), cellStyle);


        System.out.println(productTable.render());
    }
}