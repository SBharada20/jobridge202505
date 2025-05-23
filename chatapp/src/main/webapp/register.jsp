<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
    	<meta charset="UTF-8">
    	<title>ユーザー登録</title>
    	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
	</head>
	<body>
		<div class="content-wrapper">
		<div class="form-container">
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
    		<div class="form-group">
        		<label for="username">ユーザー名:</label>
        		<input type="text" name="username" id="username" required />
			</div>
			<div class="form-group">
        		<label for="password">パスワード:</label>
        		<input type="password" name="password" id="password" required />
			</div>
			<div class="form-group">
	    		<label for="displayName">表示名:</label>
        		<input type="text" name="displayName" id="displayName" required />
        	</div>
			<button type="submit" class="btn btn-primary">登録</button>
    	</form>
		</div>
   		<p style="margin-top: 20px;"><a href="login">ログインはこちら</a></p>
		</div>
	</body>
</html>
