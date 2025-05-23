package com.example.chatapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.example.chatapp.model.ChatMessage;

public class ChatMessageDao {

    private static final String JDBC_URL           = "jdbc:h2:~/desktop/DB/chatapp";
    private static final String DB_USER            = "sa";
    private static final String DB_PASS            = "";

    private static final String SQL_INSERT         =
        "INSERT INTO MESSAGES (ROOM_ID, USER_ID, CONTENT, CREATED_AT) VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_ROOM =
        "SELECT ID, ROOM_ID, USER_ID, CONTENT, CREATED_AT FROM MESSAGES WHERE ROOM_ID = ? ORDER BY CREATED_AT ASC";

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("H2 JDBC Driver not found", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
    }

    /** メッセージ保存 */
    public void save(ChatMessage message) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, message.getRoomId());
            stmt.setLong(2, message.getUserId());
            stmt.setString(3, message.getContent());
            stmt.setTimestamp(4, Timestamp.valueOf(message.getTimestamp()));

            int affected = stmt.executeUpdate();
            if (affected == 0) {
                System.err.println("Warning: No rows inserted for chat message.");
            }

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    message.setId(keys.getLong(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error saving chat message:");
            e.printStackTrace();
        }
    }

    /** ルームIDでメッセージ取得 */
    public List<ChatMessage> findByRoomId(long roomId) {
        List<ChatMessage> messages = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ROOM)) {

            stmt.setLong(1, roomId);
            System.out.println("Executing: " + stmt);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChatMessage msg = new ChatMessage();
                    msg.setId(rs.getLong("ID"));
                    msg.setRoomId(rs.getLong("ROOM_ID"));
                    msg.setUserId(rs.getLong("USER_ID"));
                    msg.setContent(rs.getString("CONTENT"));
                    Timestamp ts = rs.getTimestamp("CREATED_AT");
                    if (ts != null) {
                        msg.setTimestamp(ts.toLocalDateTime());
                    }
                    messages.add(msg);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving messages for room " + roomId + ":");
            e.printStackTrace();
        }
        return messages;
    }
}
