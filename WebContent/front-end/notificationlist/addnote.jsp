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
	var MyPoint2 = "/NotificationWebsocket/addnotification";
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL2 = "ws://" + window.location.host + webCtx + MyPoint2;
	                 //ws://localhost:8081/WebSocketChatWeb/TogerWS/james
	var webSocket2;
	function connect(){
		webSocket2 = new WebSocket(endPointURL2);
		webSocket2.onopen = function(event) {
			
		};
	}
	function sendMessage() {
		var inputMember = document.getElementById("member_no");
		var Member = inputMember.value.trim();
		var numcategory =$("#category_no").val();
		switch(numcategory){
		case "CA0001":
			category = "開課通知"
		 break;
		case "CA0002":
			category = "揪團通知"
		 break;
		case "CA0003":
			category = "追蹤教練通知"
		 break;
		case "CA0004":
			category = "追蹤團主通知"
		 break;
		case "CA0005":
			category = "商品通知"
		 break;
		case "CA0006":
			category = "團主通知"
		 break;
		case "CA0007":
			category = "揪團公告"
		 break;
		case "CA0008":
			category = "註冊通知"
		 break;
		case "CA0009":
			category = "餘額通知"
		 break;
		case "CA00010":
			category = "優惠通知"
		 break;
		case "CA00011":
			category = "聊天室通知"
		 break;
		case "CA0012":
			category = "狀態通知"
		 break;
		}
		var inputnotecontent = document.getElementById("note_content");
		var notecontent = inputnotecontent.value.trim();
		
		$.ajax({
			url: '<%=request.getContextPath()%>/notificationlist/notificationlist.do',
					type : "POST",
					data : {
						action : 'addnoteajax',
						member_no : Member,
						category_no : numcategory,
						note_content : notecontent,

					},
					dataType : "text",
					error : function(xhr) {
						console.log(xhr);
					},
					success : function(data) {
						console.log(data);
						swal({
							  title: "完成",
							  text: "訊息已送出",
							  icon: "success",
							  button: "Aww yiss!",
							});
					}
				});

		
		webSocket2.onmessage = function(event) {
			
		}
		var jsonObj = {
			"member_no" : Member,
			"category_no" : category,
			"note_content" : notecontent
		};
		webSocket2.send(JSON.stringify(jsonObj));

	}
</script>
</html>