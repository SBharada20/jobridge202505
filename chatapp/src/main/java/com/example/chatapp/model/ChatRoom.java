package com.example.chatapp.model;

public class ChatRoom {
    private long id;
    private String name;

    // デフォルトコンストラクタ
    public ChatRoom() {}

    // コンストラクタ（オプション）
    public ChatRoom(long id, String name) {
        this.id = id;
        this.name = name;
    }

    // ゲッターとセッター
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
    	return "ChatRoom{id=" + id + ", name='" + name + "'}";
    }
}
