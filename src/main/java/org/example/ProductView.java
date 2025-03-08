package org.example;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ProductView {
    public  <T extends Collection<ProductModel>> String getProductPage(T products) {
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
        productTable.addCell("Page 1", cellStyle, 2);
        productTable.addCell("Total Records : " + products.size(), cellStyle, 3);
        return productTable.render();
    }

    public String input(String question) {
        Scanner sc = new Scanner(System.in);
        System.out.print(question + " :");
        return sc.nextLine();
    }

    public String inputLoopWithRegexValidation(String question, String regex, String failedMessage) {
        while (true) {
            String inputString = input(question);
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
        String productNameInput = inputLoopWithRegexValidation("Input Product Name", "^[a-zA-Z\\s]+$", "Invalid Format for name. Please try again");
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
}
