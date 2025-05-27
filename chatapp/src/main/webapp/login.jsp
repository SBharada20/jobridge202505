<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ログイン</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
    <div class="container">
        <div class="content-wrapper">
            <!-- 共通ヘッダーロゴをinclude -->
            <jsp:include page="/WEB-INF/views/header-logo.jsp" />
            
            <h2>ログイン</h2>
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
            <div class="form-container">
                <form action="login" method="post">
                    <div class="form-group">
                        <label>ユーザー名:</label>
                        <input type="text" name="username" required />
                    </div>
                    <div class="form-group">
                        <label>パスワード:</label>
                        <input type="password" name="password" required />
                    </div>
                    <button type="submit" class="btn btn-primary">ログイン</button>
                </form>
            </div>
            <p style="margin-top: 20px;"><a href="register">新規登録はこちら</a></p>
        </div>       
    </div>
</body>
</html>