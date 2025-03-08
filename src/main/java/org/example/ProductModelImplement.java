package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class ProductModelImplement {
    private String dbName = "postgres";
    private String user = "postgres";
    private String password = "123";
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
    public boolean deleteProduct (int productID){

        try {
            Connection con = getDBConnection();
            PreparedStatement pst = con.prepareStatement("DELETE from products where product_id = ?");

            pst.setInt(1,productID);
            int arrowAffect = pst.executeUpdate();
            this.products = this.getAllProductsFromDB();

            return arrowAffect == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
