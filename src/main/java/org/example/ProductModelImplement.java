package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class ProductModelImplement {
    private final String dbName = "stock_management_db";
    private final String user = "postgres";
    private final String password = "";
    ArrayList<ProductModel> products = new ArrayList<>();

    public ProductModelImplement() {
        this.products = this.getAllProductsFromDB();
    }

    private Connection getDBConnection() {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/" + dbName;
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
            ResultSet rs = st.executeQuery("SELECT * FROM products ORDER BY product_id");
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

    public boolean getProductByIDBool(int id) {
        boolean found = false;
        for (ProductModel p : products) {
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

            String sql = "UPDATE products SET product_name = ?, product_unit_price = ?, quantity = ? WHERE product_id = ?";

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

    public boolean deleteProduct(int productID) {

        try {
            Connection con = getDBConnection();
            PreparedStatement pst = con.prepareStatement("DELETE from products where product_id = ?");

            pst.setInt(1, productID);
            int arrowAffect = pst.executeUpdate();
            this.products = this.getAllProductsFromDB();

            return arrowAffect == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
