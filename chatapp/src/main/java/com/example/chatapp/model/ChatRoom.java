package com.example.chatapp.model;

public class ChatRoom {
    private int id;
    private String name;

    // デフォルトコンストラクタ
    public ChatRoom() {}

    // コンストラクタ（オプション）
    public ChatRoom(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // ゲッターとセッター
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
