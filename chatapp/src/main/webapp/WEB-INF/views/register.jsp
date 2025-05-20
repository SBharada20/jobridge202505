<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>ユーザー登録</title></head>
<body>
    <h2>ユーザー登録</h2>
    <form method="post" action="register">
        ユーザー名: <input type="text" name="username" required /><br/>
        パスワード: <input type="password" name="password" required /><br/>
        <input type="submit" value="登録" />
    </form>
</body>
</html>
