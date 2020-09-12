<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.members.model.*"%>
<%@ page import="com.groupgo.model.*"%>
<%  String member_no = (String)session.getAttribute("member_no");
	MembersService membersSvc = new MembersService();
	pageContext.setAttribute("membersSvc",membersSvc); 
	MembersVO membersVO = membersSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO",membersVO); 
%>
<jsp:useBean id="groupgoSvc" scope="page" class="com.groupgo.model.GroupgoService" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>揪團列表</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/lobibox/dist/css/lobibox.css" />
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<style>


/* table{ */
/* width:100%; */
/* } */


img#group_pic{
	width:600px;
	margin:1rem;
}

 table#near_table{ 
	margin:0 auto 5rem auto; 	
	background-color:#E0DBDB;
 } 
 
 h2#near_text{
 position:relative;
 left:25%;
 top:20%;
 margin:1rem;
 }


div.row{
margin-top:5px;
}


div#match:hover{
cursor: pointer;
z-index:100;
}

div#myGroup,div#match,div#addGroupgo,div#groupgoIndex{

margin-top:1.1rem;
max-width:7rem;
padding-left: 0.6rem;
padding-top:0.3rem;
height: 2.8rem;
background-color:#E0DBDB;
display:block;

}


p#myGroupText,p#addGroupgoText,p#matchText,p#groupText{
font-size:1.2rem;
color:black;
margin-left:0.4rem;
margin-top:0.4rem;
font-weight:bold;
}


div#left{
width:8rem;
display:inline-block;
float:left;
position:relative;
top:10%;
left:3%;
}

img.groupgo_picture{
width:25rem;
height:16rem;
}

table.onegroup{
min-width:460px;
}

div#groupgo{
margin:1rem;
padding:1rem;

}


</style>

</head>
<body onload='connect();' onunload="disconnect();">
<%@ include file="/front-end/homepage/nav.file"%>

<div id="listall">


<div id="left">
<div id="groupgoIndex">	
       		<a href="<%=request.getContextPath()%>/front-end/groupgo/listAllGroupgo.jsp"><p id="groupText">揪團首頁</p></a>
</div>

<div id="addGroupgo">		
       		<a href="<%=request.getContextPath()%>/front-end/groupgo/addGroupgo.jsp"><p id="addGroupgoText">新增揪團</p></a>
</div>

<div id="myGroup">	
       		<a href="<%=request.getContextPath()%>/mygroup/mygroup.do?action=getMemberAttend"><p id="myGroupText">管理揪團</p></a>
</div>

<div id="match">    	
       	<p id="matchText">隨機配對</p>
</div>

</div>
<%-- <a href="<%=request.getContextPath()%>/groupgo/groupgo.do?action=getOne_For_Display&groupgo_id=G000001" class="match">隨機配對</a></div> --%>


<div class="container" id="listallgroup">

<ul class="nav nav-tabs" id="myTab" role="tablist">
  <li class="nav-item">
    <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">揪團中</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">即將開始</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" id="contact-tab" data-toggle="tab" href="#contact" role="tab" aria-controls="contact" aria-selected="false">已結束</a>
  </li>
</ul>
<div class="tab-content" id="myTabContent">
 <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
  <div class="row justify-content-center no-gutters">  
 	 <%@ include file="page1.file"%>
  </div>
<div class="row justify-content-center no-gutters"> 
	<c:forEach var="groupgoVO" items="${groupgoSvc.ing}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" >
 
  
    	<div class="col-5 col-md-5 col-xl-5" id="groupgo">
      		<img src="<%=request.getContextPath() %>/showgroupgo.do?groupgo_id=${groupgoVO.groupgo_id}" class="groupgo_picture">
     			<table class="onegroup">
     				<tr>
     					<th colspan="2">${groupgoVO.group_name}</th>
     				</tr>
     				<tr>
     					<td>主辦人: ${membersSvc.getOneMembers(groupgoVO.master_id).known}</td>
     					<td>時間: <fmt:formatDate value="${groupgoVO.group_date}" type="date"/></td>
     				</tr>
					<tr>
						<td colspan="2">地點: ${groupgoVO.place}
						<a href="<%=request.getContextPath()%>/front-end/groupgo/listOneGroup.jsp?groupgo_id=${groupgoVO.groupgo_id}">
        					<input type="submit" value="more..." class="getMore btn btn-outline-secondary">
						</a>
						</td>
					</tr>
				</table>		
    	</div>
    </c:forEach>
  </div>
  <div  class="row justify-content-center no-gutters">
  <%@ include file="page2.file"%>
  </div>
  </div>
  <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
	<div class="row justify-content-center no-gutters">  
	<c:forEach var="groupgoVO" items="${groupgoSvc.soon}" >

  		<div class="col-5 col-md-5 col-xl-5" id="groupgo">
      		<img src="<%=request.getContextPath() %>/showgroupgo.do?groupgo_id=${groupgoVO.groupgo_id}" class="groupgo_picture">
     			<table class="onegroup">
     				<tr>
     					<th colspan="2">${groupgoVO.group_name}</th>
     				</tr>
     				<tr>
     					<td>主辦人: ${membersSvc.getOneMembers(groupgoVO.master_id).known}</td>
     					<td>時間: <fmt:formatDate value="${groupgoVO.group_date}" type="date"/></td>
     				</tr>
					<tr>
						<td colspan="2">地點: ${groupgoVO.place}
						<a href="<%=request.getContextPath()%>/front-end/groupgo/listOneGroup.jsp?groupgo_id=${groupgoVO.groupgo_id}">
        					<input type="submit" value="more..." class="getMore btn-outline-secondary">
						</a>
						</td>
					</tr>
				</table>		
    	</div>
		
    </c:forEach>
    </div>  
</div>

  <div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="contact-tab">
  	<div class="row justify-content-center no-gutters">  
	<c:forEach var="groupgoVO" items="${groupgoSvc.ed}">

 <div class="col-5 col-md-5 col-xl-5" id="groupgo">
      		<img src="<%=request.getContextPath() %>/showgroupgo.do?groupgo_id=${groupgoVO.groupgo_id}" class="groupgo_picture">
     			<table class="onegroup">
     				<tr>
     					<th>${groupgoVO.group_name}</th>
     					<td>評價: <fmt:formatNumber type="number" value='${groupgoVO.score_sum div (groupgoSvc.getOneGroup(groupgoVO.groupgo_id).current_peo-1)}' maxFractionDigits="1" /></td>
     				</tr>
     				<tr>
     					<td>主辦人: ${membersSvc.getOneMembers(groupgoVO.master_id).known}</td>
     					<td>時間: <fmt:formatDate value="${groupgoVO.group_date}" type="date"/></td>
     				</tr>
					<tr>
						<td colspan="2">地點: ${groupgoVO.place}
						<a href="<%=request.getContextPath()%>/front-end/groupgo/listOneGroup.jsp?groupgo_id=${groupgoVO.groupgo_id}">
        					<input type="submit" value="more..." class="getMore btn-outline-secondary">
						</a>
						</td>
					</tr>
				</table>		
    	</div>
    	
    </c:forEach>
    </div>

  </div>
</div>


  

</div>


</div>
<%@ include file="/front-end/homepage/footer.file"%>

<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>

<script src="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.js" type="text/javascript"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.css" />
 <script src="<%=request.getContextPath()%>/js/lobibox/dist/js/lobibox.min.js"></script>
  <script src="<%=request.getContextPath()%>/js/lobibox/dist/js/notifications.min.js"></script>


<script>

$("#match").click(function(){
	
	if(window.navigator.geolocation == undefined){
		alert("can't get location!");
		return;
	}
	navigator.geolocation.getCurrentPosition(successCallback,errorCallback,{
		enableHighAcuracy: true,
		timeout: 5000, // 指定获取地理位置的超时时间，默认不限时，单位为毫秒
		 maximumAge: 3000// 最长有效期，在重复获取地理位置时，此参数指定多久再次获取位置。
	});
	 function successCallback(e){
	
		 var lon = e.coords.longitude;
		 var lat = e.coords.latitude;
		
		 $.ajax({
				type: "POST",
				url: "<%=request.getContextPath()%>/groupgo/groupgo.do",
				data: "action=getNear_For_Display&lon="+lon+"&lat="+lat,
				datatype : "json",
				success: function(data){
					$('#listallgroup').empty();
					data = JSON.parse(data);
					group = data[0];

					var deadline = group.deadline.substr(0,16);
					var group_date = group.group_date.substr(0,16);
					
										
					$('#listallgroup').append("<h2 id='near_text'>這個怎麼樣呢?</h2><table id='near_table'><tr><td colspan='3'>"
							+"<img src='<%=request.getContextPath() %>/showgroupgo.do?groupgo_id="+group.groupgo_id+"' id='group_pic'></td></tr>"
							+"<th>揪團編號 : </th><td>"+group.groupgo_id+"</td><td><a href='<%=request.getContextPath()%>/groupgo/groupgo.do?action=getOne_For_Display&groupgo_id="+group.groupgo_id+"'><input type='submit' value='more...'></a></td></tr>"
							+"<tr><th>揪團名稱 : </th><td colspan='2'>"+group.group_name +"</td></tr><tr><th>活動日期 : </th><td colspan='2'>"+group_date
							+"</td></tr><tr><th>活動地點 : </th><td colspan='2'>"+group.place+"</td></tr></table>");		
			}
			});
			
	}
	function errorCallback(e){
		 alert("沒辦法偵測您的位置, 隨機配對囉");
		 
		 var lon = 0;
		 var lat = 0;
		
		 $.ajax({
				type: "POST",
				url: "<%=request.getContextPath()%>/groupgo/groupgo.do",
				data: "action=getNear_For_Display&lon="+lon+"&lat="+lat,
				datatype : "json",
				success: function(data){
					$('#listallgroup').empty();
					data = JSON.parse(data);
					group = data[0];

					var deadline = group.deadline.substr(0,16);
					var group_date = group.group_date.substr(0,16);
					
										
					$('#listallgroup').append("<h2 id='near_text'>這個怎麼樣呢?</h2><table id='near_table'><tr><td colspan='3'>"
							+"<img src='<%=request.getContextPath() %>/showgroupgo.do?groupgo_id="+group.groupgo_id+"' id='group_pic'></td></tr>"
							+"<th>揪團編號 : </th><td>"+group.groupgo_id+"</td><td><a href='<%=request.getContextPath()%>/groupgo/groupgo.do?action=getOne_For_Display&groupgo_id="+group.groupgo_id+"'><input type='submit' value='more...'></a></td></tr>"
							+"<tr><th>揪團名稱 : </th><td colspan='2'>"+group.group_name +"</td></tr><tr><th>活動日期 : </th><td colspan='2'>"+group_date
							+"</td></tr><tr><th>活動地點 : </th><td colspan='2'>"+group.place+"</td></tr></table>");	
			}
			});
	}

});		

var memberno = "${member_no}"; 

		var MyPoint = "/NotificationWebsocket/";
		var host = window.location.host;
		var path = window.location.pathname;
		var webCtx = path.substring(0, path.indexOf('/', 1));
		var endPointURL = "ws://" + window.location.host + webCtx + MyPoint+ memberno;
		//ws://localhost:8081/WebSocketChatWeb/TogerWS/james
		var webSocket;
		

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
			console.log("websocket離線");
		}

</script>
</body>
</html>