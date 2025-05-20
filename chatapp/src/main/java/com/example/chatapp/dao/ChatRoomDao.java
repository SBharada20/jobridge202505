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

	private final String JDBC_URL ="jdbc:h2:~/desktop/DB/chatapp";
    private static final String DB_USER ="sa";
    private static final String DB_PASS = ""; // ← 本番では環境変数管理推奨

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
    }

    // 全チャットルーム取得
    public List<ChatRoom> findAll() {
        List<ChatRoom> rooms = new ArrayList<>();
        String sql = "SELECT * FROM CHAT_ROOMS";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ChatRoom room = new ChatRoom();
                room.setId((int) rs.getLong("ID"));
                room.setName(rs.getString("NAME"));
                rooms.add(room);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }
    
    public void save(ChatRoom room) {
        String sql = "INSERT INTO CHAT_ROOMS (NAME) VALUES (?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, room.getName());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // IDによるチャットルーム検索
    public ChatRoom findById(Long id) {
        String sql = "SELECT * FROM CHAT_ROOMS WHERE ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ChatRoom room = new ChatRoom();
                room.setId((int) rs.getLong("id"));
                room.setName(rs.getString("name"));
                return room;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
