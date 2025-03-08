package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class ProductModelImplement {
    private String dbName = "stock_management_db";
    private String user = "postgres";
    private String password = "password";
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
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        if (conn == null)
            throw new NullPointerException("DB Connection is null");

        return conn;
    }

    public void syncData() {
        this.products = this.getAllProductsFromDB();
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
        for (ProductModel p : products) {
            if (p.getId() == id) {
                product = p;
                break;
            }
        }
        return product;
    }

    public int getNewID() {
        return products.getLast().getId() + 1;
    }

    public boolean insertNewProductWithoutSync(ProductModel product) {
        Connection conn = getDBConnection();
        try {
            String sqlInsert = "INSERT INTO products (product_name, product_unit_price, quantity)VALUES(?, ?, ?)";
            PreparedStatement pstm = conn.prepareStatement(sqlInsert);
            pstm.setString(1, product.getName());
            pstm.setDouble(2, product.getUnitPrice());
            pstm.setInt(3, product.getQty());
            int stResult = pstm.executeUpdate();
            conn.close();
            pstm.close();
            return stResult > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error : " + e.getMessage());
        }
        return false;
    }
}
