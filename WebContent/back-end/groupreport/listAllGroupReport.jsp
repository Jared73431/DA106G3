<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.groupreport.model.*"%>

<%
String feature = "F0003";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>

<%
    GroupReportService groupreportsvc = new GroupReportService();
    List<GroupReportVO> list = groupreportsvc.getAll();
    pageContext.setAttribute("list",list);
%>
<jsp:useBean id="groupgoSvc" scope="page" class="com.groupgo.model.GroupgoService" />
<jsp:useBean id="groupreportSvc" scope="page" class="com.groupreport.model.GroupReportService" />

<!DOCTYPE html>
<html>
<head>
<title>檢舉列表</title>

<link rel="stylesheet"href="<%=request.getContextPath()%>/js/lobibox/dist/css/lobibox.css" />
<link rel="stylesheet"href="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.css" />
<style>
 #reportcontainer{
 	font-family: microsoft jhengHei;
 	margin-top:3rem;
 }
 
</style>

</head>
<body onload="connect();" onunload="disconnect();">    
<div class="container" id="reportcontainer">
	
<ul class="nav nav-tabs" id="myTab" role="tablist">
  <li class="nav-item">
    <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">未處理</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">審核通過</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" id="contact-tab" data-toggle="tab" href="#contact" role="tab" aria-controls="contact" aria-selected="false">已下架</a>
  </li>
</ul>
<div class="tab-content" id="myTabContent">
  <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
  	<div class="container">
  
 		 <table class="table">
    		<thead class="thead-light">
		      <tr>
				<th>揪團名稱</th>
				<th>揪團狀態</th>
				<th>檢舉編號</th>
				<th>檢舉時間</th>
				<th>檢舉內容</th>
				<th>處理狀態</th>
				<th>更新</th>
				<th>取消揪團</th>
		      </tr>
		    </thead>
    		<tbody>    
				<c:forEach var="groupreportVO" items="${groupreportSvc.wait}">  
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/groupreport/groupreport.do">			
					<tr>
						<td><a href="<%=request.getContextPath()%>/groupgo/groupgo.do?action=getOne_For_Display&groupgo_id=${groupreportVO.groupgo_id}" target=_blank>${groupgoSvc.getOneGroup(groupreportVO.groupgo_id).group_name}</a></td>
						<td>${groupgoStatus[groupgoSvc.getOneGroup(groupreportVO.groupgo_id).group_status]}</td>
						<td>${groupreportVO.member_no}</td>
						<td>${groupreportVO.report_date}</td>
						<td>${groupreportVO.report_content}</td>
						<td>
							<select name="report_status" value="${groupreportVO.report_status}">
								<c:forEach var="report_status" items="${reportStatus}">
									<option value=${report_status.key} ${(groupreportVO.report_status==report_status.key)?'selected':''}>${report_status.value}</option>
								</c:forEach>
							</select>
						</td>
					<td>	
						<input type="submit" value="更新處理狀態"> 
						<input type="hidden" name="groupgo_id" value="${groupreportVO.groupgo_id}"> 
						<input type="hidden" name="member_no" value="${groupreportVO.member_no}"> 
						<input type="hidden" name="report_date" value="${groupreportVO.report_date}"> 
						<input type="hidden" name="report_content" value="${groupreportVO.report_content}"> 
						<input type="hidden" name="old_status" value="${groupreportVO.report_status}"> 
						<input type="hidden" name="action" value="update">
					</td>
				</FORM>	
				
				<td>
					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/groupgo/groupgo.do" id="form">
						<input type="button" value="下架揪團" id="delete-groupgo"> 
						<input type="hidden" name="groupgo_id" value="${groupreportVO.groupgo_id}"  id="delete-groupgo_id"> 
						<input type="hidden" name="group_status" value="5"> 
						<input type="hidden" name="report_status" value="3">
						<input type="hidden" name="action" value="update_status_report">
					</FORM>
				</td>
					</tr>		
				</c:forEach>
			</tbody>
		</table>
	</div>
 </div>
  <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
  	 	<div class="container">
  
 		 <table class="table">
    		<thead class="thead-light">
		      <tr>
				<th>揪團名稱</th>
				<th>揪團狀態</th>
				<th>檢舉編號</th>
				<th>檢舉時間</th>
				<th>檢舉內容</th>
				<th>處理狀態</th>
				<th>更新</th>		
		      </tr>
		    </thead>
    		<tbody>    
				<c:forEach var="groupreportVO" items="${groupreportSvc.pass}">  
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/groupreport/groupreport.do">			
					<tr>
						<td><a href="<%=request.getContextPath()%>/groupgo/groupgo.do?action=getOne_For_Display&groupgo_id=${groupreportVO.groupgo_id}" target=_blank >${groupgoSvc.getOneGroup(groupreportVO.groupgo_id).group_name}</a></td>
						<td>${groupgoStatus[groupgoSvc.getOneGroup(groupreportVO.groupgo_id).group_status]}</td>
						<td>${groupreportVO.member_no}</td>
						<td>${groupreportVO.report_date}</td>
						<td>${groupreportVO.report_content}</td>
						<td>
							<select name="report_status" value="${groupreportVO.report_status}">
								<c:forEach var="report_status" items="${reportStatus}">
									<option value=${report_status.key} ${(groupreportVO.report_status==report_status.key)?'selected':''}>${report_status.value}</option>
								</c:forEach>
							</select>
						</td>
					<td>	
						<input type="submit" value="更新處理狀態"> 
						<input type="hidden" name="groupgo_id" value="${groupreportVO.groupgo_id}"> 
						<input type="hidden" name="member_no" value="${groupreportVO.member_no}"> 
						<input type="hidden" name="report_date" value="${groupreportVO.report_date}"> 
						<input type="hidden" name="report_content" value="${groupreportVO.report_content}"> 
						<input type="hidden" name="old_status" value="${groupreportVO.report_status}"> 
						<input type="hidden" name="action" value="update">
					</td>
				</FORM>	
				
				
					</tr>		
				</c:forEach>
			</tbody>
		</table>
	</div>
  </div>
  <div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="contact-tab">
  	 	<div class="container">
  
 		 <table class="table">
    		<thead class="thead-light">
		      <tr>
				<th>揪團名稱</th>
				<th>揪團狀態</th>
				<th>檢舉編號</th>
				<th>檢舉時間</th>
				<th>檢舉內容</th>
				<th>處理狀態</th>
				<th>更新</th>
		      </tr>
		    </thead>
    		<tbody>    
				<c:forEach var="groupreportVO" items="${groupreportSvc.finish}">  
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/groupreport/groupreport.do">			
					<tr>
						<td><a href="<%=request.getContextPath()%>/groupgo/groupgo.do?action=getOne_For_Display&groupgo_id=${groupreportVO.groupgo_id}" target=_blank>${groupgoSvc.getOneGroup(groupreportVO.groupgo_id).group_name}</a></td>
						<td>${groupgoStatus[groupgoSvc.getOneGroup(groupreportVO.groupgo_id).group_status]}</td>
						<td>${groupreportVO.member_no}</td>
						<td>${groupreportVO.report_date}</td>
						<td>${groupreportVO.report_content}</td>
						<td>
							<select name="report_status" value="${groupreportVO.report_status}">
								<c:forEach var="report_status" items="${reportStatus}">
									<option value=${report_status.key} ${(groupreportVO.report_status==report_status.key)?'selected':''}>${report_status.value}</option>
								</c:forEach>
							</select>
						</td>
					<td>	
						<input type="submit" value="更新處理狀態"> 
						<input type="hidden" name="groupgo_id" value="${groupreportVO.groupgo_id}"> 
						<input type="hidden" name="member_no" value="${groupreportVO.member_no}"> 
						<input type="hidden" name="report_date" value="${groupreportVO.report_date}"> 
						<input type="hidden" name="report_content" value="${groupreportVO.report_content}"> 
						<input type="hidden" name="action" value="update">
					</td>
				</FORM>	
				
					</tr>		
				</c:forEach>
			</tbody>
		</table>
	</div>
  </div>
</div>
</div>

</body>
<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.1.0.min.js"></script>
<script src="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.js"></script>


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

	$("body").on("click","#delete-groupgo",function(e){
		e.preventDefault();
		sendMessage($(this));
	});
	// $('#delete-groupgo').click(function(){
//	 	sendMessage($(this));
	// });	

	function sendMessage(btn) {
// 		var inputMember = ${memebr_no};
// 		var Member = inputMember.value.trim();
// 		var numcategory ="揪團通知";
// 		var notecontent = "揪團被取消囉";

 var groupgo_id =btn.next().val();

		$.ajax({
			url: '<%=request.getContextPath()%>/notificationlist/notificationlist.do',
					type : "POST",
					data : {
						action : 'addgroupnoteajax',
						groupgo_id : groupgo_id
						
					},
					dataType : "text",
					error : function(xhr) {
						console.log(xhr);
					},
					success : function(data) {
						
						var data = JSON.parse(data);						
						var member_no = data[0];
						var content = data[1];

						alert("給團員發取消通知了");
						for(let i =0; i < member_no.length; i++){
							var jsonObj = {
									"member_no" : member_no[i],
									"category_no" : "揪團通知",
									"note_content" : content.content
								};

								webSocket2.send(JSON.stringify(jsonObj));
							}	
// 						swal({
// 							  title: "完成",
// 							  text: "發給團員取消通知了",
// 							  icon: "success",
// 							  button: "Aww yiss!",
// 							});
						$("#form").submit();
					}
				});

		
		webSocket2.onmessage = function(event) {
			
		}
		
}
</script>
</html>