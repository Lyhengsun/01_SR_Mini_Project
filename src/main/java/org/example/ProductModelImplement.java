package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class ProductModelImplement {
    private String dbName = "stock_management_db";
    private String user = "postgres";
    private String password = "";
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

    public ProductModel getProductByID(int id) {
        ProductModel product = null;
        for(ProductModel p : products) {
            if (p.getId() == id) {
                product = p;
                break;
            }
        }
        return product;
    }

    public boolean getProductByIDBool(int id) {
        boolean found = false;
        for(ProductModel p : products) {
            if (p.getId() == id) {
                found = true;
                break;
            }
        }
        return found;
    }

    public boolean updateProducts(ProductModel unsavedProduct) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;

        try {
            conn = getDBConnection();

            String sql = "UPDATE products SET product_name = ?, product_unit_price = ?, quantity = ? WHERE product_id = ?;";

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, unsavedProduct.getName());
            pstmt.setDouble(2, unsavedProduct.getUnitPrice());
            pstmt.setInt(3, unsavedProduct.getQty());
            pstmt.setInt(4, unsavedProduct.getId());

            pstmt.executeUpdate();
            success = true;
            conn.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println("Update Error SQL: " + e.getMessage());
        }

        return success;
    }


































}
