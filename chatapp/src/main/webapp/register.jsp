<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ユーザー登録</title>
    <!-- スタイルは省略 -->
</head>
<body>
<div class="register-container">
    <h2>ユーザー登録</h2>

    <!-- 成功メッセージ -->
    <c:if test="${not empty message}">
        <div style="color: green; margin-bottom:1em;">
            ${message}
        </div>
    </c:if>

    <!-- エラーメッセージ -->
    <c:if test="${not empty error}">
        <div style="color: red; margin-bottom:1em;">
            ${error}
        </div>
    </c:if>

    <form action="register" method="post">
        <label for="username">ユーザー名:</label>
        <input type="text" name="username" id="username" required />

        <label for="password">パスワード:</label>
        <input type="password" name="password" id="password" required />

        <label for="displayName">表示名:</label>
        <input type="text" name="displayName" id="displayName" required />

        <input type="submit" value="登録" />
    </form>

    <p><a href="login">ログインはこちら</a></p>
</div>
</body>
</html>
