package com.example.chatapp.model;

import java.time.LocalDateTime;

public class ChatMessage {
    private long id;
    private long roomId;
    private long userId;
    private String content;
    private LocalDateTime timestamp;

    // 表示用（ユーザー名など）※DBには含まれない
    private String displayName;

    public ChatMessage() {}

    public ChatMessage(long id, long roomId, long userId, String content, LocalDateTime timestamp) {
        this.id = id;
        this.roomId = roomId;
        this.userId = userId;
        this.content = content;
        this.timestamp = timestamp;
    }

    // ゲッター・セッター
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

