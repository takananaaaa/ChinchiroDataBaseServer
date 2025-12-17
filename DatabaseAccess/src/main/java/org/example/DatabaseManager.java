package org.example;

import java.sql.*;

public class DatabaseManager {
    private static final String sqlDriverName = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp";
    private static final String sqlServerPort = "13308";
    private static final String sqlDatabaseName = "db_group_c";
    private static final String sqlUserId   = "group_c";
    private static final String sqlPassword = "group_c";

    public DatabaseManager() {
        try {
            Class.forName(sqlDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 接続を取得するメソッド
    public Connection getConnection() throws SQLException {
        String target = url + ":" + sqlServerPort + "/" + sqlDatabaseName;
        return DriverManager.getConnection(target, sqlUserId, sqlPassword);
    }

    // ユーザー管理用テーブルを作成するメソッド
    public void setupTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(50) NOT NULL UNIQUE, "
                + "password VARCHAR(50) NOT NULL"
                + ")";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("usersテーブルの準備が完了しました。");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}