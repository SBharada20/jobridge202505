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
//	private final String JDBC_URL ="jdbc:h2:~/desktop/DB/chatapp";
//	private final String DB_USER ="sa";
//	private final String DB_PASS ="1234";
	

	private final String JDBC_URL ="jdbc:h2:~/desktop/DB/chatapp";
    private static final String DB_USER ="sa";
    private static final String DB_PASS = ""; // ← 本番では環境変数管理推奨

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

    // メッセージ保存
    public void save(ChatMessage message) {
        String sql = "INSERT INTO MESSAGE (ROOM_ID, USER_ID, CONTENT, CREATED_AT) VALUES (?, ?, ?, NOW())";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, message.getRoomId());
            stmt.setLong(2, message.getUserId());
            stmt.setString(3, message.getContent());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 指定ルームのメッセージ一覧取得
    public List<ChatMessage> findByRoomId(Long roomId) {
        List<ChatMessage> messages = new ArrayList<>();
        String sql = "SELECT * FROM MESSAGE WHERE ROOM_ID = ? ORDER BY CREATED_AT ASC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, roomId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ChatMessage message = new ChatMessage();
                message.setId((int) rs.getLong("id"));
                message.setRoomId((int) rs.getLong("ROOM_ID"));
                message.setUserId((int) rs.getLong("USER_ID"));
                message.setContent(rs.getString("CONTENT"));
                message.setTimestamp(rs.getTimestamp("CREATED_AT").toLocalDateTime());

                messages.add(message);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }
}

