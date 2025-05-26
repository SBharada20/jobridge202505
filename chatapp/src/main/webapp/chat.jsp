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
    	<style>
    		.message {
    			position: relative;
    		}
    		.delete-btn {
    			background: #ff4444;
    			color: white;
    			border: none;
    			padding: 4px 8px;
    			border-radius: 3px;
    			font-size: 12px;
    			cursor: pointer;
    			margin-left: 10px;
    			opacity: 0.7;
    			transition: opacity 0.2s;
    		}
    		.delete-btn:hover {
    			opacity: 1;
    			background: #cc0000;
    		}
    		.message-header {
    			display: flex;
    			align-items: center;
    			justify-content: space-between;
    		}
    		.message-info {
    			display: flex;
    			align-items: center;
    		}
    	</style>
    	<script>
    		function confirmDelete(messageId, roomId) {
    			if (confirm('このメッセージを削除しますか？')) {
    				// 削除リクエストを送信
    				var form = document.createElement('form');
    				form.method = 'POST';
    				form.action = 'chat';
    				
    				var actionInput = document.createElement('input');
    				actionInput.type = 'hidden';
    				actionInput.name = 'action';
    				actionInput.value = 'delete';
    				form.appendChild(actionInput);
    				
    				var messageIdInput = document.createElement('input');
    				messageIdInput.type = 'hidden';
    				messageIdInput.name = 'messageId';
    				messageIdInput.value = messageId;
    				form.appendChild(messageIdInput);
    				
    				var roomIdInput = document.createElement('input');
    				roomIdInput.type = 'hidden';
    				roomIdInput.name = 'roomId';
    				roomIdInput.value = roomId;
    				form.appendChild(roomIdInput);
    				
    				document.body.appendChild(form);
    				form.submit();
    			}
    		}
    	</script>
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
    		
    		<!-- エラーメッセージ表示 -->
    		<c:if test="${not empty error}">
    		    <div style="color: red; margin: 10px 0; padding: 10px; background: #ffeeee; border: 1px solid #ffcccc; border-radius: 5px;">
    		        ${error}
    		    </div>
    		</c:if>
    		
    		<!-- 成功メッセージ表示 -->
    		<c:if test="${not empty success}">
    		    <div style="color: green; margin: 10px 0; padding: 10px; background: #eeffee; border: 1px solid #ccffcc; border-radius: 5px;">
    		        ${success}
    		    </div>
    		</c:if>
    		
    		<div id="chat-messages">
				<c:forEach var="message" items="${messages}">
    				<!-- 自分の投稿かどうかを判定して異なるCSSクラスを適用 -->
    				<div class="message ${message.displayName == sessionScope.user.displayName ? 'my-message' : 'other-message'}">
        				<div class="message-header">
        					<div class="message-info">
            					<span class="username">
            						<!-- 自分の投稿の場合は「あなた」と表示 -->
            						<c:choose>
            							<c:when test="${message.displayName == sessionScope.user.displayName}">
            								あなた
            							</c:when>
            							<c:otherwise>
            								${message.displayName}
            							</c:otherwise>
            						</c:choose>
            					</span>
            					<span class="timestamp">${message.formattedDateString}</span>
            				</div>
            				<!-- 自分のメッセージの場合のみ削除ボタンを表示 -->
            				<c:if test="${message.userId == sessionScope.user.id}">
            					<button class="delete-btn" onclick="confirmDelete(${message.id}, ${roomId})">
            						削除
            					</button>
            				</c:if>
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