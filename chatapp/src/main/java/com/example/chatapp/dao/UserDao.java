package com.example.chatapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.chatapp.model.User;

public class UserDao {
    
    private static final Logger LOGGER = Logger.getLogger(UserDao.class.getName());
    
    private static final String JDBC_URL = "jdbc:h2:~/desktop/DB/chatapp";
    private static final String DB_USER = "sa";
    private static final String DB_PASS = ""; // 本番では環境変数から取得推奨
    
    // JDBCドライバのロード（共通メソッド）
    private void loadDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
        	LOGGER.log(Level.SEVERE, "JDBCドライバを読み込めませんでした", e);
            throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
        }
    }
    
    /**
     * ユーザー認証（ログイン）
     * @param username ユーザー名
     * @param password パスワード（本番ではハッシュ化されたものを使用）
     * @return 認証されたユーザー、見つからない場合はnull
     */
    public User findByUsernameAndPassword(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        
        loadDriver();

        String sql = "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, password); // 本番ではパスワードはハッシュ化すべき！
            LOGGER.info("認証クエリ実行: " + stmt);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                	LOGGER.info("ユーザー認証成功: " + username);
                    return extractUser(rs);
                }
                LOGGER.info("ユーザー認証失敗: " + username);
                return null; // ユーザーが見つからない場合
            }
        } catch (SQLException e) {
        	LOGGER.log(Level.SEVERE, "ユーザー認証中にSQLエラーが発生しました", e);
            return null;
        }
    }
    
    /**
     * ユーザー名でユーザーを検索
     * @param username ユーザー名
     * @return 見つかったユーザー、見つからない場合はnull
     * @throws SQLException データベースエラーが発生した場合
     */
    public User findByUsername(String username) throws SQLException {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        
        loadDriver();
           
        String sql = "SELECT * FROM USERS WHERE USERNAME = ?";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            LOGGER.info("ユーザー検索クエリ実行: " + stmt);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractUser(rs);
                }
                return null; // 見つからなかった場合
            }
        } catch (SQLException e) {
        	LOGGER.log(Level.SEVERE, "ユーザー検索中にSQLエラーが発生しました", e);
            throw e; // SQLExceptionを再スロー
        }
    }
    
    /**
     * IDでユーザー取得（表示名用など）
     * @param id ユーザーID
     * @return 見つかったユーザー、見つからない場合はnull
     */
    public User getUserById(long id) {
        if (id <= 0) {
            return null;
        }
        
        loadDriver();
        
        String sql = "SELECT * FROM USERS WHERE ID = ?";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            LOGGER.info("ID検索クエリ実行: " + stmt);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractUser(rs);
                }
                return null;
            }
        } catch (SQLException e) {
        	LOGGER.log(Level.SEVERE, "ID検索中にSQLエラーが発生しました", e);
            return null;
        }
    }

    /**
     * 結果セットから User オブジェクトを生成
     * @param rs 結果セット
     * @return Userオブジェクト
     * @throws SQLException 結果セットの読み取りエラー
     */
    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("ID"));
        user.setUsername(rs.getString("USERNAME"));
        user.setPassword(rs.getString("PASSWORD"));
        user.setDisplayName(rs.getString("DISPLAY_NAME"));
        return user;
    }

    /**
     * 新しいユーザーを登録
     * @param user 登録するユーザー
     * @return 登録成功時true、失敗時false
     */
    public boolean insertUser(User user) {
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
        	LOGGER.warning("無効なユーザーデータです");
            return false;
        }
        
        loadDriver();
        
        String sql = "INSERT INTO USERS (USERNAME, PASSWORD, DISPLAY_NAME) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getDisplayName());
            
            LOGGER.info("ユーザー登録クエリ実行: " + stmt);
            
            int result = stmt.executeUpdate();
            boolean success = result == 1;
            
            if (success) {
            	LOGGER.info("ユーザー登録成功: " + user.getUsername());
            } else {
            	LOGGER.warning("ユーザー登録失敗: " + user.getUsername());
            }
            
            return success;

        } catch (SQLException e) {
        	LOGGER.log(Level.SEVERE, "ユーザー登録中にSQLエラーが発生しました", e);
            return false;
        }
    }
}