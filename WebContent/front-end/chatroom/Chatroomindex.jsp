<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.members.model.*"%>
 <% 
 	MembersService memberSvc = new MembersService();
 	List<MembersVO> list = memberSvc.getAll();
 	pageContext.setAttribute("list", list);
 %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Chat Room</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/lobibox/dist/css/lobibox.css" />
<link rel="stylesheet" href="css/styles.css" type="text/css" />
</head>
<body onload="connect();" onunload="disconnect();">
<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.1.0.min.js"></script>
<script src="<%=request.getContextPath()%>/js/lobibox/dist/js/lobibox.min.js"></script>
<script src="<%=request.getContextPath()%>/js/lobibox/dist/js/notifications.min.js"></script>
<h1 style="visibility:hidden" id ="memberno1">${membersVO.mem_name}</h1>
<div class="fixed">
<FORM name="form1">
<table>
<%--  <jsp:useBean id="memberSvc" scope="page" class="com.members.model.MembersService" /> --%>
	<tr>
		<td>會員姓名:</td>
				<select size="1" name="member_no" id="memberno2" >
         		<c:forEach var="memberVO" items="${memberSvc.all}" > 
         		<option value="${memberVO.member_no}">${memberVO.mem_name}
         </c:forEach>   
       </select>
	</tr>
			
</table>
</FORM>
 <input type="button" value="選擇好友" onclick ="history()">
</div>	
	<div class="container">
        <div class="row pt-3">
            <div class="chat-main">
                <div class="col-md-12 chat-header rounded-top bg-primary text-white">
                    <div class="row">
                        <div class="col-md-6 username pl-2">
                            <i class="fa fa-circle text-success" aria-hidden="true"></i>
                            <h6 class="m-0">聊天室</h6>
                        </div>
                        <div class="col-md-6 options text-right pr-2">
                            <i class="fa fa-plus mr-2" aria-hidden="true"></i>
                            <i class="fa fa-video-camera" aria-hidden="true"></i>
                            <i class="fa fa-circle text-success live-video mr-1" aria-hidden="true"></i>
                            <i class="fa fa-phone mr-2" aria-hidden="true"></i>
                            <i class="fa fa-cog mr-2" aria-hidden="true"></i>
                            <i class="fa fa-times hide-chat-box" aria-hidden="true"></i>
                        </div>
                    </div>
                </div>
                <div class="chat-content">
                    <div class="col-md-12 chats border">
                     <ul class="p-0">
                     <div id="messagesArea"></div>
                       </ul>
					</div>
                    <div class="col-md-12 message-box border pl-2 pr-2 border-top-0">
                        <input id="message" type="text" class="pl-0 pr-0 w-100" placeholder="輸入訊息" onkeydown="if (event.keyCode == 13) sendMessage();"/>
                        <div class="tools">
                            <i class="fa fa-picture-o" aria-hidden="true"></i>
                            <i class="fa fa-telegram" aria-hidden="true"></i>
                            <i class="fa fa-bell" aria-hidden="true"></i>
                            <i class="fa fa-meh-o" aria-hidden="true"></i>
                            <i class="fa fa-paperclip" aria-hidden="true"></i>
                            <i class="fa fa-gamepad" aria-hidden="true"></i>
                            <i class="fa fa-camera" aria-hidden="true"></i>
                            <i class="fa fa-folder" aria-hidden="true"></i>
                            <i class="fa fa-thumbs-o-up m-0" aria-hidden="true"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
<script type="text/javascript">
var memberno1 =$("#memberno1").text();
$("#memberno2").change(function(){
	messagesArea.innerHTML = "";
	 
	 memberno2 = document.getElementById("memberno2").value.trim();
	
	});
var inputMember = document.getElementById("memberno2");
var memberno2 = inputMember.value.trim();

var MyPoint = "/Chatroom";
var host = window.location.host;
var path = window.location.pathname;
var webCtx = path.substring(0, path.indexOf('/', 1));
var endPointURL = "ws://" + window.location.host + webCtx + MyPoint+"/"+memberno1;
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
        if(jsonObj.type === "history"){
        	
        	var messagestr = JSON.parse(jsonObj.chat_content);
        	messagesArea.innerHTML = "";
            for (i = 0; i < messagestr.length; i++) {
            	var messageobj = JSON.parse(messagestr[i]);
            	var memberno =messageobj.member_no1;
                var message =messageobj.message;
                if(messageobj.member_no1 == memberno1){
               	 inside = " <li class='pl-2 pr-2 bg-primary rounded text-white text-center send-msg mb-1'>"
               		 + memberno +":"+"<br>"+ message
                       + "</li>";
                       
               	 messagesArea.innerHTML = messagesArea.innerHTML + inside.replace(/\n/gi, "<br>") + "<br>";
               	messagesArea.scrollTop = messagesArea.scrollHeight;
                       }else{
                    inside = "<li class='p-1 rounded mb-1'><div class='receive-msg'>"+
                    "<img src='http://nicesnippets.com/demo/image1.jpg'>"+
                    "<div class='receive-msg-desc  text-center mt-1 ml-1 pl-2 pr-2'><p class='pl-2 pr-2 rounded'>"
                   	 + memberno +":"+"<br>"+ message
                       + "</p></div></div></li>";    	
                      
                   messagesArea.innerHTML = messagesArea.innerHTML + inside.replace(/\n/gi, "<br>") + "<br>";
                   messagesArea.scrollTop = messagesArea.scrollHeight;
                       }
       		}
       	}else if(jsonObj.type === "onload"){
       		var memberno = jsonObj.member_no1;
			var message = jsonObj.message;
			if(jsonObj.member_no1 == memberno1){
              	 inside = " <li class='pl-2 pr-2 bg-primary rounded text-white text-center send-msg mb-1'>"
              		 + memberno +":"+"<br>"+ message
                      + "</li>";
              	 messagesArea.innerHTML = messagesArea.innerHTML + inside.replace(/\n/gi, "<br>") + "<br>";
              	messagesArea.scrollTop = messagesArea.scrollHeight;
                      }else{
                   inside = "<li class='p-1 rounded mb-1'><div class='receive-msg'>"+
                   "<img src='http://nicesnippets.com/demo/image1.jpg'>"+
                   "<div class='receive-msg-desc  text-center mt-1 ml-1 pl-2 pr-2'><p class='pl-2 pr-2 rounded'>"
                  	 + memberno +":"+"<br>"+ message
                      + "</p></div></div></li>";    	
                  messagesArea.innerHTML = messagesArea.innerHTML + inside.replace(/\n/gi, "<br>") + "<br>";
                  messagesArea.scrollTop = messagesArea.scrollHeight;
                      }
   		}else{
   			var row = jsonObj.row;
   			Lobibox.notify('default', {
				continueDelayOnInactiveTab : true,
				msg : "您有"+row+"筆訊息"
			});
   		}
        	
		messagesArea.scrollTop = messagesArea.scrollHeight;
	};

	webSocket.onclose = function(event) {
	};
}
function history(){
	
	var jsonObj = {"type":'history',"member_no1":memberno1,"member_no2" : memberno2, "message" :""};
	webSocket.send(JSON.stringify(jsonObj));
}

function sendMessage() {
    var inputMessage = document.getElementById("message");
    var message = inputMessage.value.trim();
    
    if (message === ""){
        alert ("訊息請勿空白!");
        inputMessage.focus();	
    }else{
        var jsonObj = {"type":'onload',"member_no1":memberno1,"member_no2" : memberno2, "message" : message};
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