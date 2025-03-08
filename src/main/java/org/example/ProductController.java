package org.example;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.ArrayList;

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
    public void productSearchByName() {
        ArrayList<ProductModel> products = pmi.getAllProducts();
        Table table = new Table(5, BorderStyle.UNICODE_ROUND_BOX, ShownBorders.SURROUND_HEADER_AND_COLUMNS);
        CellStyle cellStyle = new CellStyle();
        String search = pv.input("Enter product name to search ");
        boolean checkInvalidName = true; int index = 0;
        search.toLowerCase();
        for (ProductModel productSearch : products) {
            if (productSearch.getName().contains(search)) {
                index++;
                if (index == 1){
                    table.addCell(" ".repeat(3) + "ID" + " ".repeat(3), cellStyle);
                    table.addCell(" ".repeat(5) + "Name" + " ".repeat(5), cellStyle);
                    table.addCell(" ".repeat(3) + "Unit Price" + " ".repeat(3), cellStyle);
                    table.addCell(" ".repeat(3) + "Qty" + " ".repeat(3), cellStyle);
                    table.addCell(" ".repeat(3) + "Import Date" + " ".repeat(3), cellStyle);
                }
                table.addCell(" ".repeat(3)+productSearch.getId()+" ".repeat(3),cellStyle);
                table.addCell(" ".repeat(5)+productSearch.getName()+" ".repeat(5),cellStyle);
                table.addCell(" ".repeat(3)+productSearch.getUnitPrice()+" ".repeat(3),cellStyle);
                table.addCell(" ".repeat(3)+productSearch.getQty()+" ".repeat(3),cellStyle);
                table.addCell(" ".repeat(3)+productSearch.getImportDate()+" ".repeat(3),cellStyle);
                checkInvalidName = false;
            }
        }
        System.out.println(table.render());
        if (checkInvalidName) {
            System.out.println(ConsoleColor.ANSI_RED+"Invalid product name ????? "+ConsoleColor.ANSI_RESET);
        }
        pv.input("Please continue.......");

    }
}
