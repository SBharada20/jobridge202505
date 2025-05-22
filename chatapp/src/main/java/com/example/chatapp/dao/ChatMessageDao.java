package com.example.chatapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.chatapp.model.ChatMessage;

public class ChatMessageDao {

    private static final String JDBC_URL = "jdbc:h2:~/desktop/DB/chatapp";
    private static final String DB_USER = "sa";
    private static final String DB_PASS = "";

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

    // チャットメッセージ保存
    public void save(ChatMessage message) {
        String sql = "INSERT INTO MESSAGES (ROOM_ID, USER_ID, CONTENT, CREATED_AT) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, message.getRoomId());
            stmt.setLong(2, message.getUserId());
            stmt.setString(3, message.getContent());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(message.getTimestamp()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ルームIDでメッセージ取得（投稿日時昇順）
    public List<ChatMessage> findByRoomId(long roomId) {
        List<ChatMessage> messages = new ArrayList<>();
        String sql = "SELECT * FROM MESSAGES WHERE ROOM_ID = ? ORDER BY CREATED_AT ASC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, roomId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChatMessage message = new ChatMessage();
                    message.setId(rs.getLong("ID"));
                    message.setRoomId(rs.getLong("ROOM_ID"));
                    message.setUserId(rs.getLong("USER_ID"));
                    message.setContent(rs.getString("CONTENT"));
                    message.setTimestamp(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                    messages.add(message);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }
}
