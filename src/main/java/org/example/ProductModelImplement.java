package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class ProductModelImplement {
    private String dbName = "stock_management_db";
    private String user = "postgres";
    private String password = "11112222";
    ArrayList<ProductModel> products = new ArrayList<>();

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
        }
        catch (ClassNotFoundException | SQLException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        if (conn == null)
            throw new NullPointerException("DB Connection is null");

        return conn;
    }

    private ArrayList<ProductModel> getAllProductsFromDB() {
        ArrayList<ProductModel> productList = new ArrayList<>();
        try {
            Connection conn = getDBConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM products;");
            while (rs.next()) {
                ProductModel product = new ProductModel(rs.getInt("product_id"), rs.getString("product_name"), rs.getDouble("product_unit_price"), rs.getInt("quantity"), rs.getDate("imported_date"));
                productList.add(product);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
        }
        return productList;
    }

    public ArrayList<ProductModel> getAllProducts() {
        return products;
    }

    //Read product by ID
    public ProductModel getProductByID(int id) {
        ProductModel product = null;
        String query = "SELECT * FROM products WHERE product_id = ?";

        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                product = new ProductModel(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getDouble("product_unit_price"),
                        rs.getInt("quantity"),
                        rs.getDate("imported_date")
                );
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return product;
    }
}