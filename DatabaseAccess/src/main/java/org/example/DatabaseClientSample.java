package org.example;

import java.sql.*;

public class DatabaseClientSample {

    private static final String sqlDriverName = "com.mysql.cj.jdbc.Driver";

    // SQLサーバの指定
    private static final String url = "jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp";
    private static final String sqlServerPort = "13308";

    // 以下は班ごとに違うことに注意
    private static final String sqlDatabaseName = "db_group_c";
    private static final String sqlUserId   = "group_c";
    private static final String sqlPassword = "group_c";

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

             //以下の操作はコメントアウトされています。参考までにご覧ください。
            // テーブルの操作
            String tableName = "student_records_tmp"; // 一時的なテーブル名

            // 1. テーブル作成 (学籍番号, 名前, 学科 の3カラム)
            try {
                queryString = "CREATE TABLE " + tableName + " (student_id INT PRIMARY KEY, name VARCHAR(50), major VARCHAR(50))";
                result = stmt.executeUpdate(queryString);
                System.out.println("\n--- 1. テーブル作成 (" + tableName + ") ---");
                System.out.println("作成結果 (0=成功): " + result);
            } catch (SQLException e) {
                // テーブルが既に存在する場合に DROP TABLE を実行
                System.out.println("テーブル作成中にエラー: " + e.getMessage() + " -> 既存のテーブルを削除します。");
                stmt.executeUpdate("DROP TABLE " + tableName);
                // 再度作成を試みる
                queryString = "CREATE TABLE " + tableName + " (student_id INT PRIMARY KEY, name VARCHAR(50), major VARCHAR(50))";
                stmt.executeUpdate(queryString);
                System.out.println("テーブルを再作成しました。");
            }


            // 2. データ挿入 (INSERT)
            queryString = "INSERT INTO " + tableName + " (student_id, name, major) VALUES (1001, '田中 太郎', '情報工学科')";
            stmt.executeUpdate(queryString);
            queryString = "INSERT INTO " + tableName + " (student_id, name, major) VALUES (1002, '佐藤 花子', '機械工学科')";
            stmt.executeUpdate(queryString);
            queryString = "INSERT INTO " + tableName + " (student_id, name, major) VALUES (1003, '山田 次郎', '電気工学科')";
            result = stmt.executeUpdate(queryString);
            System.out.println("--- 2. データ挿入 ---");
            System.out.println("挿入された行数 (最後のINSERT): " + result);


            // 3. データ選択 (SELECT) と出力
            queryString = "SELECT student_id, name, major FROM " + tableName;
            rs = stmt.executeQuery(queryString);
            System.out.println("\n--- 3. データ閲覧 (SELECT) 結果 ---");
            System.out.println("----------------------------------------------");
            System.out.println("ID   | 名前       | 学科");
            System.out.println("----------------------------------------------");

            while (rs.next()) {
                // カラム名またはインデックスで値を取得
                int id = rs.getInt("student_id");
                String name = rs.getString("name");
                String major = rs.getString("major");

                // 整形して出力
                System.out.printf("%4d | %-8s | %s\n", id, name, major);
            }
            System.out.println("----------------------------------------------");


            // 4. テーブル削除 (クリーンアップ)
            queryString = "DROP TABLE " + tableName;
            result = stmt.executeUpdate(queryString);
            System.out.println("\n--- 4. テーブル削除 (クリーンアップ) ---");
            System.out.println("削除結果 (0=成功): " + result);


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // connection, stmt, rs の close 処理をここに入れるのが理想的です
        }
    }
}