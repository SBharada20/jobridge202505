package com.example.chatapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.chatapp.model.User;

public class UserDao {

    private final String JDBC_URL = "jdbc:h2:~/desktop/DB/chatapp";
    private final String DB_USER = "sa";
    private final String DB_PASS = ""; // ← 本番では環境変数管理推奨
    
    // JDBCドライバのロード（共通メソッド）
    private void loadDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
        }
    }
    
    // ユーザー認証（ログイン）
    public User findByUsernameAndPassword(String username, String password) {
        loadDriver();

        String sql = "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, password); // 本番ではパスワードはハッシュ化すべき！
            System.out.println(stmt); // test

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("成功" + stmt); // test
                    return extractUser(rs);
                }
                System.out.println("エラー" + stmt); // test
                return null; // ユーザーが見つからない場合
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public User findByUsername(String username) throws SQLException {
        loadDriver();
           
        String sql = "SELECT * FROM USERS WHERE USERNAME = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            System.out.println(stmt); // test
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractUser(rs);
                }
                return null; // 見つからなかった場合
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // SQLExceptionを再スロー
        }
    }
    
    // IDでユーザー取得（表示名用など）
    public User getUserById(long id) {
        loadDriver();
        
        String sql = "SELECT * FROM USERS WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractUser(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 結果セットから User オブジェクトを生成
    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("ID"));
        user.setUsername(rs.getString("USERNAME"));
        user.setPassword(rs.getString("PASSWORD"));
        user.setDisplayName(rs.getString("DISPLAY_NAME")); // 統一
        return user;
    }

    public boolean insertUser(User user) {
        loadDriver();
        
        String sql = "INSERT INTO USERS (USERNAME, PASSWORD, DISPLAY_NAME) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getDisplayName());
            System.out.println(stmt);//test
            int result = stmt.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}