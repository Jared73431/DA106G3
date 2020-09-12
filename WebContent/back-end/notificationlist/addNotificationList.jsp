<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.notificationlist.model.*"%>
<%@ page import="java.util.*"%>
<%
	NotificationListVO notificationlistVO = (NotificationListVO) request.getAttribute("notificationlistVO");
%>
<%
String feature = "F0007";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>
<html>
<head>
<link rel="stylesheet"href="<%=request.getContextPath()%>/js/lobibox/dist/css/lobibox.css" />
<link rel="stylesheet"href="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.css" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>���u��Ʒs�W - addNotificationList.jsp</title>

<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}
</style>

<style>
table {
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 0px solid #CCCCFF;
}

th, td {
	padding: 1px;
}
</style>

</head>
<body bgcolor='white' onload='connect()'>
	<script
		src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.1.0.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.js"></script>
	

	<h3>�q���C��s�W:</h3>

	<%-- ���~��C --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">�Эץ��H�U���~:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM name="form1">
		<table>
		 <jsp:useBean id="memberSvc" scope="page" class="com.members.model.MembersService" />
			<tr>
				<td>�|���m�W:</td>
				<td><select size="1" name="member_no" id="member_no" >
         		<c:forEach var="memberVO" items="${memberSvc.all}" > 
         		<option value="${memberVO.member_no}">${memberVO.mem_name}
         </c:forEach>   
       </select>
       </td>
			</tr>
			<jsp:useBean id="cateSvc" scope="page"
				class="com.categorys.model.CategorysService" />
			<tr>
				<td>�q�����O:<font color=red><b>*</b></font></td>
				<td><select size="1" name="category_no" id="category_no">
						<c:forEach var="categorysVO" items="${cateSvc.all}">
							<option value="${categorysVO.category_no}"
								${(notificationlistVO.category_no==categorysVO.category_no)? 'selected':'' }>${categorysVO.category_name}
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>�q�����e</td>
				<td><input type="TEXT" name="note_content" size="45"
					id="note_content"
					value="<%=(notificationlistVO == null) ? "�q�����e" : notificationlistVO.getNote_content()%>" /></td>
			</tr>

		</table>
		<br> <input type="button" value="�e�X�s�W" onclick="sendMessage();">
		
		<b id="msg"></b>>
	</FORM>

	


</body>
<script>
	var MyPoint = "/NotificationWebsocket/addnotification";
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
	                 //ws://localhost:8081/WebSocketChatWeb/TogerWS/james
	var webSocket;
	function connect(){
		webSocket = new WebSocket(endPointURL);
		webSocket.onopen = function(event) {
			
		};
	}
	function sendMessage() {
		var inputMember = document.getElementById("member_no");
		var Member = inputMember.value.trim();
		var numcategory =$("#category_no").val();
		switch(numcategory){
		case "CA0001":
			category = "�}��"
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
							  title: "����",
							  text: "�T���w�e�X",
							  icon: "success",
							  button: "Aww yiss!",
							});
					}
				});

		
		webSocket.onmessage = function(event) {
			
		}
		var jsonObj = {
			"member_no" : Member,
			"category_no" : category,
			"note_content" : notecontent
		};
		webSocket.send(JSON.stringify(jsonObj));

	}
</script>
</html>