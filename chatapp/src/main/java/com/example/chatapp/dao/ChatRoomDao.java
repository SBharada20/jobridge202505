package com.example.chatapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.chatapp.model.ChatRoom;

public class ChatRoomDao {
	
	private static final Logger logger = Logger.getLogger(ChatRoomDao.class.getName());

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
        	logger.log(Level.SEVERE, "JDBCドライバを読み込めませんでした", e);
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
        	logger.log(Level.SEVERE, "全チャットルームを取得中にSQLエラーが発生しました", e);
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
        	logger.log(Level.SEVERE, "チャットルーム登録中にSQLエラーが発生しました", e);
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
            logger.log(Level.SEVERE, "チャットルーム検索中にSQLエラーが発生しました", e);
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
        	logger.log(Level.SEVERE, "チャットルーム削除中にSQLエラーが発生しました", e);
        }

        return false;
    }
}
