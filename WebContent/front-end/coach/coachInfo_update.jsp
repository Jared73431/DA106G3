<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="java.io.*"%>

<%@ page import="com.members.model.*"%>

<%
	String member_no = (String) session.getAttribute("member_no");
	MembersService membersSvc = new MembersService();
	MembersVO membersVO = membersSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO", membersVO);
%>

<%
	CoachService coachSvc = new CoachService();
	CoachVO coachVO = coachSvc.getOneCoachByMember(membersVO.getMember_no());
	pageContext.setAttribute("coachVO", coachVO);
%>
<jsp:useBean id="membersSvc2" scope="page"
	class="com.members.model.MembersService" />
<html>
<head>
<title>修改教練個人資料 - coachInfo_update.jsp</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.css">
</head>
<body bgcolor='white'>
	<%@ include file="/front-end/homepage/nav.file"%>
	<div id="listall">
		<div class="container">

			<div class="table-responsive">
				<FORM METHOD="post"
			ACTION="<%=request.getContextPath()%>/coach/coach.do" name="form1" enctype="multipart/form-data">
				<table class="table">
					<tr class="table-primary">
						<th scope="row">教練簡介</th>
						<th>
							<div class="form-group">
								<textarea class="form-control" name="coa_info" rows="5">${coachVO.coa_info}</textarea>
							</div>
						</th>
					</tr>
					<tr class="table-primary">
						<th scope="row">服務地點</th>
						<td><input class="form-control" type="TEXT"
							name="service_area" size="10" value="${coachVO.service_area}" /></td>
					</tr>
					<tr class="table-primary">
						<th scope="row">教練專長1</th>
						<td><input class="form-control" type="TEXT" name="coa_skill1"
							size="10" value="${coachVO.coa_skill1}" /></td>
					</tr>
					<tr class="table-primary">
						<th scope="row">教練證照1</th>
						<td><img id="li1"
							src="<%=request.getContextPath()%>/DBGifReader4Coach?member_no=${coachVO.member_no}&licence=coa_licence1"
							width=120rem height=120rem> <input class="form-control-file" id="up1"
							type="FILE" name="coa_licence1" size="20" value="${coa_licence1}" /></td>
					</tr>
					<tr class="table-primary">
						<th scope="row">教練專長2</th>
						<td><input class="form-control" type="TEXT" name="coa_skill2"
							size="10" value="${coachVO.coa_skill2}" /></td>
					</tr>
					<tr class="table-primary">
						<th scope="row">教練證照2</th>
						<td><img id="li2"
							src="<%=request.getContextPath()%>/DBGifReader4Coach?member_no=${coachVO.member_no}&licence=coa_licence2"
							width=120rem height=120rem> <input class="form-control-file" id="up2"
							type="FILE" name="coa_licence2" size="20" value="${coa_licence2}" /></td>
					</tr>
					<tr class="table-primary">
						<th scope="row">教練專長3</th>
						<td><input class="form-control" type="TEXT" name="coa_skill3"
							size="10" value="${coachVO.coa_skill3}" /></td>
					</tr>
					<tr class="table-primary">
						<th scope="row">教練證照3</th>
						<td><img  id="li3"
							src="<%=request.getContextPath()%>/DBGifReader4Coach?member_no=${coachVO.member_no}&licence=coa_licence3"
							width=120rem height=120rem> <input class="form-control-file" id="up3"
							type="FILE" name="coa_licence3" size="20" value="${coa_licence3}" /></td>
					</tr>
				</table>
				<input type="hidden" name="action" value="update">
				<input type="hidden" name="member_no" value="${membersVO.member_no}">
				<input type="hidden" name="coa_no" value="${coachVO.coa_no}">
				<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
				<button class="btn btn-primary" type="submit" style="float:right;margin-bottom:1rem;">送出修改</button>
				</FORM>
			</div>
		</div>
		
	</div>
	<%@ include file="/front-end/homepage/footer.file"%>

	<script
		src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>
		<script
		src="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.js"></script>	
<script>
<% for(int i =1 ;i<=3 ;i++){ %>
	$("#up<%= i %>").change(function(){

	  readURL<%= i %>(this);

	});

	 

	function readURL<%= i %>(input){

	  if(input.files && input.files[0]){

	    var reader = new FileReader();

	    reader.onload = function (e) {

	       $("#li<%= i %>").attr('src', e.target.result);

	    }

	    reader.readAsDataURL(input.files[0]);

	  }

	}
<%}%>
</script>
<%	
if(request.getAttribute("errorMsgs")!=null){
	List<String> msgs = (List<String>)request.getAttribute("errorMsgs");
	if (!msgs.isEmpty()) {	
%>
<script>
swal.setDefaults({
    confirmButtonText: "確定",
    cancelButtonText: "取消"
});
swal({
    title: "錯誤!!",
    html: '<c:forEach var="message" items="${errorMsgs}">'+
		'<li style="color: black;">${message}</li>'+
		'</c:forEach>',
    type: "question", // type can be "success", "error", "warning", "info", "question"
    showCancelButton: true,
		showCloseButton: true,
});
</script>
<%
}}
%>

</body>
</html>