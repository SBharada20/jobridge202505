package com.example.chatapp.model;

import java.time.LocalDateTime;
import java.util.Date;

public class ChatMessage {
    private Long id;
    private Long roomId;
    private Long userId;
    private String content;
    private LocalDateTime timestamp;
    
    // JSP表示用のフィールド
    private String displayName;
    private Date formattedDate;
    private String formattedDateString;

    // Constructors
    public ChatMessage() {}

    public ChatMessage(Long roomId, Long userId, String content) {
        this.roomId = roomId;
        this.userId = userId;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

    // JSP表示用のgetters/setters
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Date getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(Date formattedDate) {
        this.formattedDate = formattedDate;
    }

    // 新しく追加：文字列形式の日時フィールド
    public String getFormattedDateString() {
        return formattedDateString;
    }

    public void setFormattedDateString(String formattedDateString) {
        this.formattedDateString = formattedDateString;
    }

    // JSPで使いやすいヘルパーメソッド
    public String getFormattedTimestamp() {
        if (timestamp != null) {
            java.time.format.DateTimeFormatter formatter = 
                java.time.format.DateTimeFormatter.ofPattern("MM/dd HH:mm");
            return timestamp.format(formatter);
        }
        return "";
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", roomId=" + roomId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}