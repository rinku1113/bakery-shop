package com.example.shopping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MYSQLTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3307/web_shop?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "R!9vX2@eLq7#dA";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("MySQLと接続できました！");
            conn.close();
        } catch (SQLException e) {
            System.out.println("接続に失敗しました...");
            e.printStackTrace();
        }
    }
}

