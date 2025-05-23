<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.example.chatapp.model.User" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login");
        return;
    }
%>

<!DOCTYPE html>
<html>
	<head>
    	<meta charset="UTF-8">
    	<title>チャットルーム - ${room.name}</title>
    	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
	</head>
	<body>
		<div class="content-wrapper">
        	<div class="chat-header">
            	<div style="display: flex; align-items: center; justify-content: space-between;">
            		<div style="display: flex; align-items: center;">
            			<svg viewBox="0 0 800 200" xmlns="http://www.w3.org/2000/svg" style="max-width: 150px; margin-right: 20px;">
            				<!-- Simplified logo for chat header -->
            				<g transform="translate(20, 50)">
                        		<path d="M0 20 L0 0 L40 0 L60 20 L60 80 L40 80 L20 60 L20 40 L0 20 Z" 
                        		fill="none" stroke="white" stroke-width="6" stroke-linejoin="round"/>
                        		<text x="80" y="35" font-size="32" fill="white" font-family="Arial, sans-serif" font-weight="bold">jobridge</text>
                        		<text x="80" y="65" font-size="24" fill="white" font-family="Arial, sans-serif">chat</text>
                        	</g>
                        </svg>
                    </div>
                </div>
            </div>

    		<h2>チャットルーム: ${room.name}</h2>

    		<div class="info">
    			<div style="font-size: 14px; opacity: 0.9;">
        		ようこそ、<strong><%= user.getDisplayName() %></strong> さん！
				</div>
    		</div>
    		<div id="chat-messages" class="message">
				<c:forEach var="message" items="${messages}">
    			<div class="message">
        			<div class="message-header">
            			<span class="username">${message.displayName}</span>
            			<span class="timestamp">${message.formattedDateString}</span>
        			</div>
        			<div class="message-content">
            			<c:out value="${message.content}" />
        			</div>
    			</div>
				</c:forEach>
    		</div>
			<div style="text-align: right; font-size: 14px;">
    			<form class="message-form" action="chat" method="post">
        			<input type="hidden" name="roomId" value="${roomId}" />
        			<input type="text" name="content" placeholder="メッセージを入力..." required />
        			<input type="submit" value="送信" />
    			</form>
    			<p style="margin-top: 20px;"><a href="rooms" >← チャットルーム一覧に戻る</a></p>
			</div>
 		</div>
	</body>
</html>
