package com.example.shopping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductViewer {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3307/web_shop?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "root";
        String password = "R!9vX2@eLq7#dA";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                String category = rs.getString("category");
                int stock = rs.getInt("stock");

                System.out.println("ID: " + id);
                System.out.println("商品名: " + name);
                System.out.println("説明: " + description);
                System.out.println("価格: " + price);
                System.out.println("カテゴリ: " + category);
                System.out.println("在庫: " + stock);
                System.out.println("---------------------------");
            }

        } catch (SQLException e) {
            System.out.println("データベース接続またはクエリに失敗しました！");
            e.printStackTrace();
        }
    }
}

