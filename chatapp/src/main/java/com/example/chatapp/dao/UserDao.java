package com.example.chatapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.chatapp.model.User;

public class UserDao {

	private final String JDBC_URL ="jdbc:h2:~/desktop/DB/chatapp";
    private static final String DB_USER ="sa";
    private static final String DB_PASS = ""; // ← 本番では環境変数管理推奨
    
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
    }

    // ユーザー認証（ログイン）
    public User findByUsernameAndPassword(String username, String password) {
    	try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException ("JDBCドライバを読み込めませんでした");
			}
    	    	
    	String sql = "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
        	PreparedStatement stmt = conn.prepareStatement(sql)) {
        		stmt.setString(1, username);
        		stmt.setString(2, password);  // 本番ではパスワードはハッシュ化すべき！

        		try (ResultSet rs = stmt.executeQuery()) {
        			if (rs.next()) {
        				return extractUser(rs);
        			}
        		}

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    // IDでユーザー取得（表示名用など）
    public User getUserById(int id) {
    	try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException ("JDBCドライバを読み込めませんでした");
			}
    	    
        String sql = "SELECT * FROM USERS WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractUser(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public User findByUsername(String username) {
        String sql = "SELECT * FROM USERS WHERE USERNAME = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setUsername(rs.getString("USERNAME"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setDisplayName(rs.getString("DISPLAY_NAME"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // 見つからなかった場合
    }


    // 結果セットから User オブジェクトを生成
    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("ID"));
        user.setUsername(rs.getString("USERNAME"));
        user.setPassword(rs.getString("PASSWORD"));
//        user.setDisplayName(rs.getString("display_name"));
        return user;
    }

    public boolean insertUser(User user) {
        String sql = "INSERT INTO USERS (USERNAME, PASSWORD, DISPLAY_NAME) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getDisplayName());

            int result = stmt.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    
//    // ユーザー登録処理
//    public void save(User user) {
//        String sql = "INSERT INTO USERS (USERNAME, PASSWORD) VALUES (?, ?)";
//
//        try (Connection conn = getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setString(1, user.getUsername());
//            stmt.setString(2, user.getPassword());
//            stmt.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
//    


    
