package org.example;

import java.sql.*;

public class DatabaseClientSample {

    private static final String sqlDriverName = "com.mysql.cj.jdbc.Driver";

    // SQLサーバの指定
    private static final String url = "jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp";
    private static final String sqlServerPort = "13308";

    // 以下は班ごとに違うことに注意
    private static final String sqlDatabaseName = "db_xxxxxx";
    private static final String sqlUserId   = "xxxxxx";
    private static final String sqlPassword = "xxxxxx";

    public static void main(String[] args) {
        DatabaseClientSample dbSample = new DatabaseClientSample();
        dbSample.sample();
    }

    DatabaseClientSample (){
        try {
            Class.forName(sqlDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sample() {
        try {
            // 接続先の文字列->jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp:13307/データベース名
            String target = url + ":" + sqlServerPort + "/" + sqlDatabaseName;
            System.out.println("target: " + target);

            // 接続先情報と"MySQLへログインするための"ユーザIDとパスワードから接続
            Connection connection = DriverManager.getConnection(target, sqlUserId, sqlPassword);

            // MySQLに問い合わせるためのStatementオブジェクトを構築する
            Statement stmt = connection.createStatement();

            // 実際にMySQLデータベースサーバに問い合わせるときのクエリメッセージを作成
            // Statementオブジェクトとクエリメッセージを使い，実際に問い合わせて結果を取得
            // ここはやりたい処理によって大きく変わることに注意
            String queryString;
            int result;

            // データベースの操作
            queryString = "show databases"; // データベースの一覧を表示
            ResultSet rs  = stmt.executeQuery(queryString);
            // 得られた結果の集合から必要なデータを取り出す
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }

            // テーブルの操作
            queryString = "show tables"; // テーブル一覧を表示
            rs  = stmt.executeQuery(queryString);
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }

            // 以下の操作はコメントアウトされています。参考までにご覧ください。
//            // テーブルの操作
//            queryString = "create table sampleTable (id INT)"; // 新しいテーブルを作成
//            result = stmt.executeUpdate(queryString);  // executeUpdate() を使用
//            System.out.println("Table created or affected rows: " + result);
//
//            queryString = "drop table sampleTable"; // 指定したテーブルを削除
//            result = stmt.executeUpdate(queryString);  // executeUpdate() を使用
//            System.out.println("Table created or affected rows: " + result);
//
//
//            // データの操作
//            queryString = "select * from sampleTable"; // テーブルからデータを選択
//            rs = stmt.executeQuery(queryString);  // executeUpdate() を使用
//            while (rs.next()) {
//                System.out.println(rs.getString(1));
//            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
    }
}