package com.example.chatapp.controller;

import com.example.chatapp.dao.UserDao;
import com.example.chatapp.model.User;

public class main {
	public static void main(String[] args) {
	    UserDao dao = new UserDao();
	    User user = new User();
	    user.setUsername("test");
	    user.setPassword("1234");
	    user.setDisplayName("表示名");

	    if (dao.insertUser(user)) {
	        System.out.println("登録成功");
	    }

	    User found = dao.findByUsername("test");
	    if (found != null) {
	        System.out.println("取得成功: " + found.getDisplayName());
	    } else {
	        System.out.println("取得失敗");
	    }
	}

}
