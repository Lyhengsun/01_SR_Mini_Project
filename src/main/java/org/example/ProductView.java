package org.example;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import javax.sound.sampled.Port;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class ProductView {
    static Scanner sc = new Scanner(System.in);
    public String getProductPage(ArrayList<ProductModel> products, int startCount, int rows) {
        int endCount = Math.min(startCount + rows, products.size());
        Table productTable = new Table(5, BorderStyle.UNICODE_ROUND_BOX, ShownBorders.ALL);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);
        productTable.addCell(" ".repeat(3) + "ID" + " ".repeat(3), cellStyle);
        productTable.addCell(" ".repeat(5) + "Name" + " ".repeat(5), cellStyle);
        productTable.addCell(" ".repeat(3) + "Unit Price" + " ".repeat(3), cellStyle);
        productTable.addCell(" ".repeat(3) + "Qty" + " ".repeat(3), cellStyle);
        productTable.addCell(" ".repeat(3) + "Import Date" + " ".repeat(3), cellStyle);

        List<ProductModel> shownProducts = IntStream.range(startCount, endCount).mapToObj(products::get).toList();
        for (ProductModel product : shownProducts) {
            productTable.addCell(String.valueOf(product.getId()), cellStyle);
            productTable.addCell(" ".repeat(3) + product.getName() + " ".repeat(3), cellStyle);
            productTable.addCell(String.format("%.2f", product.getUnitPrice()), cellStyle);
            productTable.addCell(String.valueOf(product.getQty()), cellStyle);
            productTable.addCell(new SimpleDateFormat("yyyy-MM-dd").format(product.getImportDate()), cellStyle);
        }
        productTable.addCell("Page " +
                ((endCount / rows)  + (endCount % rows == 0 ? 0 : 1)) + "/" +
                ((products.size() / rows) + (products.size() % rows == 0 ? 0 : 1)), cellStyle, 2);
        productTable.addCell("Total Records : " + products.size(), cellStyle, 3);
        return productTable.render();
    }

    public String input(String question) {
        System.out.print(question + " :");
        return sc.nextLine();
    }

    public String inputLoopWithRegexValidation(String question, String regex, String failedMessage) {
        while (true) {
            String inputString = input(question).trim();
            if (Pattern.matches(regex, inputString)) {
                return inputString;
            }
            System.out.println(failedMessage + "\n");
        }
    }

    public boolean inputYesNoLoopValidation(String question) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print(question);
            String inputString = sc.nextLine().trim().toLowerCase();
            if (inputString.equals("yes") || inputString.equals("y"))
                return true;
            if (inputString.equals("no") || inputString.equals("n"))
                return false;

            System.out.println("Invalid input. Please input y for yes and n for no.\n");
        }
    }

    public ProductModel writeProductView(int newId) {
        System.out.println("ID: " + newId);
        String productNameInput = inputLoopWithRegexValidation("Input Product Name", "^[a-zA-Z0-9\\s]+$", "Invalid Format for name. Please try again");
        double productPriceInput = Double.parseDouble(inputLoopWithRegexValidation("Enter price", "^[0-9.]+$", "Invalid Format for price. Please try again."));
        int productQtyInput = Integer.parseInt(inputLoopWithRegexValidation("Enter quantity", "^[0-9]{1,8}$", "Invalid Format for quantity and quantity can't be more than 8 digits. Please try again"));

        return new ProductModel(newId, productNameInput, productPriceInput, productQtyInput, new Date());
    }

    public <T extends Collection<ProductModel>> String productsView(T products) {
        Table productTable = new Table(5, BorderStyle.UNICODE_ROUND_BOX, ShownBorders.ALL);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);
        productTable.addCell(" ".repeat(3) + "ID" + " ".repeat(3), cellStyle);
        productTable.addCell(" ".repeat(5) + "Name" + " ".repeat(5), cellStyle);
        productTable.addCell(" ".repeat(3) + "Unit Price" + " ".repeat(3), cellStyle);
        productTable.addCell(" ".repeat(3) + "Qty" + " ".repeat(3), cellStyle);
        productTable.addCell(" ".repeat(3) + "Import Date" + " ".repeat(3), cellStyle);

        for (ProductModel product : products) {
            productTable.addCell(String.valueOf(product.getId()), cellStyle);
            productTable.addCell(" ".repeat(3) + product.getName() + " ".repeat(3), cellStyle);
            productTable.addCell(String.format("%.2f", product.getUnitPrice()), cellStyle);
            productTable.addCell(String.valueOf(product.getQty()), cellStyle);
            productTable.addCell(new SimpleDateFormat("yyyy-MM-dd").format(product.getImportDate()), cellStyle);
        }
        return productTable.render();
    }

    public int updateProductView(ProductModel p) {
        Table productsTable = new Table(5, BorderStyle.UNICODE_ROUND_BOX, ShownBorders.ALL);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);
        productsTable.addCell(" ".repeat(3) + "ID" + " ".repeat(3), cellStyle);
        productsTable.addCell(" ".repeat(5) + "Name" + " ".repeat(5), cellStyle);
        productsTable.addCell(" ".repeat(3) + "Unit Price" + " ".repeat(3), cellStyle);
        productsTable.addCell(" ".repeat(3) + "Qty" + " ".repeat(3), cellStyle);
        productsTable.addCell(" ".repeat(3) + "Import Date" + " ".repeat(3), cellStyle);

        productsTable.addCell(String.valueOf(p.getId()), cellStyle);
        productsTable.addCell(" ".repeat(3) + p.getName() + " ".repeat(3), cellStyle);
        productsTable.addCell(String.format("%.2f", p.getUnitPrice()), cellStyle);
        productsTable.addCell(String.valueOf(p.getQty()), cellStyle);
        productsTable.addCell(new SimpleDateFormat("yyyy-MM-dd").format(p.getImportDate()), cellStyle);
        System.out.println(productsTable.render());
        System.out.println("1. Name\t2. Unit Price\t3. Qty\t4. All Field\t5. Exit ");
        Helper.inputMessage("Choose an option to update: ");
        String temp = sc.nextLine();
        Helper.inputValidation(temp, Helper.TYPE.NUM);
        return Integer.parseInt(temp);
    }

    public void unsavedUpdateView(ArrayList<ProductModel> p) {
        Table productsTable = new Table(5, BorderStyle.UNICODE_ROUND_BOX, ShownBorders.ALL);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);
        productsTable.addCell(" ".repeat(3) + "ID" + " ".repeat(3), cellStyle);
        productsTable.addCell(" ".repeat(5) + "Name" + " ".repeat(5), cellStyle);
        productsTable.addCell(" ".repeat(3) + "Unit Price" + " ".repeat(3), cellStyle);
        productsTable.addCell(" ".repeat(3) + "Qty" + " ".repeat(3), cellStyle);
        productsTable.addCell(" ".repeat(3) + "Import Date" + " ".repeat(3), cellStyle);

       for (ProductModel item : p) {
           productsTable.addCell(String.valueOf(item.getId()), cellStyle);
           productsTable.addCell(" ".repeat(3) + item.getName() + " ".repeat(3), cellStyle);
           productsTable.addCell(String.format("%.2f", item.getUnitPrice()), cellStyle);
           productsTable.addCell(String.valueOf(item.getQty()), cellStyle);
           productsTable.addCell(new SimpleDateFormat("yyyy-MM-dd").format(item.getImportDate()), cellStyle);
       }
        System.out.println(productsTable.render());
       Helper.enterInput();
    }
}
