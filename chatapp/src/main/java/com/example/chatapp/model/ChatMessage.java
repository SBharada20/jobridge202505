package com.example.chatapp.model;

import java.time.LocalDateTime;

public class ChatMessage {
    private int id;
    private int roomId;
    private int userId;
    private String message;
    private LocalDateTime timestamp;

    // 表示用（ユーザー名など）※DBには含まれない
    private String displayName;

    public ChatMessage() {}

    public ChatMessage(int id, int roomId, int userId, String message, LocalDateTime timestamp) {
        this.id = id;
        this.roomId = roomId;
        this.userId = userId;
        this.message = message;
        this.timestamp = timestamp;
    }

    // ゲッター・セッター
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}

