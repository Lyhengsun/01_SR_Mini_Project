package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class ProductModelImplement {
    private String dbName = "stock_management_db";
    private String user = "postgres";
    private String password = "";
    private ArrayList<ProductModel> products;

    public ProductModelImplement() {
        this.products = this.getAllProductsFromDB();
    }

    private Connection getDBConnection() {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost/" + dbName;
            Properties props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", password);
            props.setProperty("ssl", "false");

            conn = DriverManager.getConnection(url, props);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        if (conn == null)
            throw new NullPointerException("DB Connection is null");

        return conn;
    }

    private ArrayList<ProductModel> getAllProductsFromDB() {
        ArrayList<ProductModel> productList = new ArrayList<>();
        try (Connection conn = getDBConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM products;")) {

            while (rs.next()) {
                ProductModel product = new ProductModel(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getDouble("product_unit_price"),
                        rs.getInt("quantity"),
                        rs.getDate("imported_date")
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
        }
        return productList;
    }

    public ArrayList<ProductModel> getAllProducts() {
        return products;
    }

    // Read product by ID from ArrayList instead of database
    public ProductModel getProductByID(int id) {
        for (ProductModel product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }
}
