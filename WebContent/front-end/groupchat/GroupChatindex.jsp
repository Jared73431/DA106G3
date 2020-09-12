<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.members.model.*"%>
<%@ page import="com.groupgo.model.*"%>
<%
 MembersService memberSvc = new MembersService();
 String member_no = (String)session.getAttribute("member_no");
 MembersVO membersVO = memberSvc.getOneMembers(member_no);
 pageContext.setAttribute("membersVO", membersVO);
%>
<%
String groupid = request.getParameter("groupgoid");
%>
<jsp:useBean id="groupSvc" scope="page" class="com.groupgo.model.GroupgoService" />
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Chat Room</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/js/bootstrap.min.js">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="css/styles.css" type="text/css" />
</head>
<body onload="connect();" onunload="disconnect();" >
<%@ include file="/front-end/homepage/nav.file"%>
<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.1.0.min.js"></script>
<div class="container">
  <div class="row justify-content-md-center">
    <div class="col col-lg-10">
     <div class = "stylex">
	<h1 style="visibility:hidden" id ="groupgoid">${param.groupgoid}</h1>
	<h1 style="visibility:hidden" id ="memberno">${membersVO.mem_name}</h1>
	
	<h3 id="statusOutput" class="statusOutput">
	        <c:forEach var="groupVO" items="${groupSvc.all}">
                    <c:if test="${param.groupgoid==groupVO.groupgo_id}">
	                   	 【${groupVO.group_name}】
                    </c:if>
             </c:forEach>
     </h3>
	<div id="messagesArea"></div>
	<div class="panel input-area">
		<input id="message" class="text-field"  type="text" placeholder="Message" onkeydown="if (event.keyCode == 13) sendMessage();" /> 
		<input type="submit" id="sendMessage" class="button" value="Send" onclick="sendMessage();" /> 
	</div>
</div>
</div>
</div>
</div>
	<%@ include file="/front-end/homepage/footer.file"%>
</body>
<script type="text/javascript">
var memberno =$("#memberno").text();
var groupgoid = $("#groupgoid").text();
var MyPoint = "/GroupChatsocket";
var host = window.location.host;
var path = window.location.pathname;
var webCtx = path.substring(0, path.indexOf('/', 1));
var endPointURL = "ws://" + window.location.host + webCtx + MyPoint+"/"+memberno+"/"+groupgoid;
//var endPointURL = "ws://localhost:8081/IBM_WebSocket1_ChatB/MyEchoServer/peter/309";

var statusOutput = document.getElementById("statusOutput");
var webSocket;

function connect() {
	// 建立 websocket 物件
	webSocket = new WebSocket(endPointURL);
	
	webSocket.onopen = function(event) {
		
	};

	webSocket.onmessage = function(event) {
		var messagesArea = document.getElementById("messagesArea");
        var jsonObj = JSON.parse(event.data);
        console.log(messageobj );
        if(jsonObj.action === "history"){
        	var messagestr = JSON.parse(jsonObj.message);
            for (i = 0; i < messagestr.length; i++) {
            	var messageobj = JSON.parse(messagestr[i]);
            	var membernos =messageobj.memberno;
                var message =messageobj.message;
                if(messageobj.memberno != memberno){
            	 inside = "<div class='talk-bubbleright tri-right left-top'><div class='talktextleft'><p>"
            		 + membernos +":"+"<br>"+ message
                    + "</p></div></div>";
            	 messagesArea.innerHTML = messagesArea.innerHTML + inside.replace(/\n/gi, "<br>") + "<br>";
                    }else{
                 inside = "<div class='talk-bubbleleft tri-left right-top'><div class='talktextright'><p>"
                	 + membernos +":"+"<br>"+ message
                    + "</p></div></div>";    	
                messagesArea.innerHTML = messagesArea.innerHTML + inside.replace(/\n/gi, "<br>") + "<br>";
                    }
                  
                    
       		}
       	}else{
			var membernos = jsonObj.memberno;
			var message = jsonObj.message;
			if(jsonObj.memberno != memberno){
           	 inside = "<div class='talk-bubbleright tri-right left-top'><div class='talktextleft'><p>"
           		 + membernos +":"+"<br>"+ message
                   + "</p></div></div>";
           	 messagesArea.innerHTML = messagesArea.innerHTML + inside.replace(/\n/gi, "<br>") + "<br>";
                   }else{
                inside = "<div class='talk-bubbleleft tri-left right-top'><div class='talktextright'><p>"
               	 + membernos +":"+"<br>"+ message
                   + "</p></div></div>";    	
               messagesArea.innerHTML = messagesArea.innerHTML + inside.replace(/\n/gi, "<br>") + "<br>";
                   }
   		}
        	
		messagesArea.scrollTop = messagesArea.scrollHeight;
	};

	webSocket.onclose = function(event) {
		updateStatus("WebSocket 已離線");
	};
}


function sendMessage() {
   
    
    var inputMessage = document.getElementById("message");
    var message = inputMessage.value.trim();
    
    if (message === ""){
        alert ("訊息請勿空白!");
        inputMessage.focus();	
    }else{
        var jsonObj = {"memberno" : memberno, "message" : message};
        webSocket.send(JSON.stringify(jsonObj));
        inputMessage.value = "";
        inputMessage.focus();
    }
}


function disconnect () {
	webSocket.close();
}

</script>

</html>