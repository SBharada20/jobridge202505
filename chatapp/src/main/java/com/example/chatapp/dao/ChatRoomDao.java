package com.example.chatapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.chatapp.model.ChatRoom;

public class ChatRoomDao {

    private static final String JDBC_URL = "jdbc:h2:~/desktop/DB/chatapp";
    private static final String DB_USER = "sa";
    private static final String DB_PASS = "";

    private static final String SQL_FIND_ALL = "SELECT * FROM CHAT_ROOMS";
    private static final String SQL_INSERT = "INSERT INTO CHAT_ROOMS (NAME) VALUES (?)";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM CHAT_ROOMS WHERE ID = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM CHAT_ROOMS WHERE ID = ?";

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
    }

    /** 全チャットルームを取得 */
    public List<ChatRoom> findAll() {
        List<ChatRoom> rooms = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ChatRoom room = new ChatRoom();
                room.setId(rs.getLong("ID"));
                room.setName(rs.getString("NAME"));
                rooms.add(room);
            }

        } catch (SQLException e) {
            System.err.println("Error in findAll: " + e.getMessage());
            e.printStackTrace();
        }

        return rooms;
    }

    /** 新しいチャットルームを保存 */
    public void save(ChatRoom room) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {

            stmt.setString(1, room.getName());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error in save: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** ID指定でチャットルームを検索 */
    public ChatRoom findById(Long roomId) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_ID)) {

            stmt.setLong(1, roomId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ChatRoom room = new ChatRoom();
                    room.setId(rs.getLong("ID"));
                    room.setName(rs.getString("NAME"));
                    return room;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error in findById: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
    // IDによるチャットルーム削除
    public boolean deleteById(long id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_BY_ID)) {

            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
