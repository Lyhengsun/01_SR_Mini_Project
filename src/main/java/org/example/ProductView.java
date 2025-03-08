package org.example;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

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
    public String input(String question){
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

    public ProductModel writeProductView() {

    }
}
