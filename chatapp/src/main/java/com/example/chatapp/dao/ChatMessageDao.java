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
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.chatapp.model.ChatMessage;

public class ChatMessageDao {
	
	private static final Logger LOGGER = Logger.getLogger(ChatMessageDao.class.getName());

    private static final String JDBC_URL = "jdbc:h2:~/desktop/DB/chatapp";
    private static final String DB_USER  = "sa";
    private static final String DB_PASS  = "";
    
    private static final String SQL_INSERT =
        "INSERT INTO MESSAGES (ROOM_ID, USER_ID, CONTENT, TIMESTAMP) VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_ROOM =
        "SELECT ID, ROOM_ID, USER_ID, CONTENT, TIMESTAMP FROM MESSAGES WHERE ROOM_ID = ? ORDER BY TIMESTAMP ASC";
    private static final String SQL_DELETE_BY_ID = 
    	    "DELETE FROM MESSAGES WHERE ID = ? AND USER_ID = ?";
    
    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
        	LOGGER.log(Level.SEVERE, "JDBCドライバを読み込めませんでした", e);
            throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
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
            	LOGGER.warning("No rows inserted for chat message.");
            
            }

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    message.setId(keys.getLong(1));
                }
            }

        } catch (SQLException e) {
        	LOGGER.log(Level.SEVERE, "No rows inserted for chat message.", e);
        }
    }

    /** ルームIDでメッセージ取得 */
    public List<ChatMessage> findByRoomId(long roomId) {
        List<ChatMessage> messages = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ROOM)) {

            stmt.setLong(1, roomId);
            LOGGER.info("認証クエリ実行: " + stmt);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChatMessage msg = new ChatMessage();
                    msg.setId(rs.getLong("ID"));
                    msg.setRoomId(rs.getLong("ROOM_ID"));
                    msg.setUserId(rs.getLong("USER_ID"));
                    msg.setContent(rs.getString("CONTENT"));
                    Timestamp ts = rs.getTimestamp("TIMESTAMP");
                    if (ts != null) {
                        msg.setTimestamp(ts.toLocalDateTime());
                    }
                    messages.add(msg);
                }
            }

        } catch (SQLException e) {
        	LOGGER.log(Level.SEVERE, "メッセージ取得中にSQLエラーが発生しました", e);
        }
        return messages;
    }
    /**
     * メッセージ削除（自分のメッセージのみ）
     */
    public boolean deleteMessage(long messageId, long userId) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_BY_ID)) {
            
            stmt.setLong(1, messageId);
            stmt.setLong(2, userId);
            
            int affected = stmt.executeUpdate();
            if (affected > 0) {
                LOGGER.info("Message deleted successfully: ID=" + messageId + ", User=" + userId);
                return true;
            } else {
                LOGGER.warning("No message deleted - either message not found or not owned by user: ID=" + messageId + ", User=" + userId);
                return false;
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting message: ID=" + messageId + ", User=" + userId, e);
            return false;
        }
    }
    
    
}
