<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.members.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
<script>
var memberno = "${member_no}"; 

		var MyPoint = "/NotificationWebsocket/";
		var host = window.location.host;
		var path = window.location.pathname;
		var webCtx = path.substring(0, path.indexOf('/', 1));
		var endPointURL = "ws://" + window.location.host + webCtx + MyPoint+ memberno;
		//ws://localhost:8081/WebSocketChatWeb/TogerWS/james
		var webSocket;
		var webSocket2;

		function connect() {
			// create a websocket
			webSocket = new WebSocket(endPointURL);
			webSocket.onopen = function(event) {
			console.log("websocket連線");
			};
			webSocket.onmessage = function(event) {
console.log(event.data);
				var jsonObj = JSON.parse(event.data);
				var message = jsonObj.member_no + ": " + jsonObj.note_content
						+ jsonObj.category_no + "\r\n";
				var category = jsonObj.category_no;
				var message = jsonObj.note_content;
				Lobibox.notify('default', {
					continueDelayOnInactiveTab : true,
					title : category,
					msg : message
				});
			};
		}
		
		
		function disconnect() {
			webSocket.close();
			console.log("websocket斷線");
		}

</script>
</html>