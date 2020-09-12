<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.course.model.*"%>
<%@ page import="com.members.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String member_no = (String) session.getAttribute("member_no");
	MembersService membersSvc = new MembersService();
	MembersVO membersVO = membersSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO", membersVO);
%>
<%
	CourseVO courseVO = (CourseVO) request.getAttribute("courseVO");
%>
<jsp:useBean id="coachSvc" scope="page"
	class="com.coach.model.CoachService" />
<jsp:useBean id="membersSvc2" scope="page"
	class="com.members.model.MembersService" />
<jsp:useBean id="course_kindSvc" scope="page"
	class="com.course_kind.model.Course_kindService" />

<jsp:useBean id="gymSvc" scope="page" class="com.gym.model.GymService" />
<jsp:useBean id="cour_bookingSvc" scope="page"
	class="com.cour_booking.model.Cour_BookingService" />
<html>
<head>
<title>新增課程資料 -addCourse.jsp</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/js/datetimepicker/jquery.datetimepicker.css" />
	<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
	<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.css">
<style>


.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}
</style>

</head>
<body bgcolor='white'>
	<%@ include file="/front-end/homepage/nav.file"%>
	<div id="listall">
		<div class="container">

			<div class="table-responsive">
				<FORM METHOD="post"
					ACTION="<%=request.getContextPath()%>/course/course.do"
					name="form1">
					<table class="table">
						<tr class="table-info">
							<th scope="row">課程名稱</th>
							<th><input type="TEXT" name="cour_name" size="20"
								class="form-control" value="${param.cour_name}" /></th>
						</tr>
						<tr class="table-info">
							<th scope="row">課程類型</th>
							<td><select class="form-control" size="1"
								name="cour_type_no">
									<c:forEach var="course_kindVO" items="${course_kindSvc.all}">
										<option value="${course_kindVO.cour_type_no}"
											<c:if test="${course_kindVO.cour_type_no==param.cour_type_no}">
									selected='selected'
							</c:if>>${course_kindVO.cour_type}
									</c:forEach>
							</select></td>
						</tr>
						<tr class="table-info">
							<th scope="row">授課地點</th>
							<td><select class="form-control" size="1" name="cour_addr">
									<c:forEach var="gymVO" items="${gymSvc.all}">
										<option value="${gymVO.gym_name}"
											<c:if test="${gymVO.gym_name==param.cour_addr}">
								selected="selected"
							</c:if>>${gymVO.gym_name}
									</c:forEach>
							</select></td>
						</tr>
						<tr class="table-info">
							<th scope="row">授課方式</th>
							<td><select class="form-control" size="1" name="cour_mode">
									<option value="1" ${(param.cour_mode==1)?'selected':''}>網路
									<option value="2" ${(param.cour_mode==2)?'selected':''}>面授
									<option value="3" ${(param.cour_mode==3)?'selected':''}>Both
							</select></td>
						</tr>
						<tr class="table-info">
							<td scope="row">授課時間:</td>
							<td><input class="form-control" type="TEXT" name="cour_date"
								size="25" id="f_date1"
								value="${(param.cour_date==null?'':param.cour_date)}"/>
								</td>
						</tr>

						<tr class="table-info">
							<td>報名截止日期:</td>
							<td scope="row"><input class="form-control" type="TEXT"
								name="cour_deadline" size="25" id="f_date2"
								value="${(param.cour_deadline==null?'':param.cour_deadline)}"
								 /></td>
						</tr>
					</table>
					<input type="hidden" name="action" value="insert"> 
					<input type="hidden" name="member_no" value="${membersVO.member_no}"> 
					<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
					<button class="btn btn-primary" type="submit" style="float:right;margin-bottom:1rem;">新增</button>
				</Form>
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
	<script src="<%=request.getContextPath()%>/js/datetimepicker/jquery.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/datetimepicker/jquery.datetimepicker.full.js"></script>
		<script
		src="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.js"></script>	

	<script>
		$.datetimepicker.setLocale('zh');
		$('#f_date1').datetimepicker(
				{
					theme : '', //theme: 'dark',
					timepicker : true, //timepicker:true,
					step : 60, //step: 60 (這是timepicker的預設間隔60分鐘)
					format : 'Y-m-d H:i:00', //format:'Y-m-d H:i:s',

					onShow : function() {
						this.setOptions({
							minDate : $('#f_date2').val() ? $('#f_date2').val()
									: new Date()
						})
					},
					timepicker : true
				});

		$('#f_date2').datetimepicker({
			theme : '', //theme: 'dark',
			timepicker : true, //timepicker:true,
			step : 60, //step: 60 (這是timepicker的預設間隔60分鐘)
			format : 'Y-m-d H:i:00', //format:'Y-m-d H:i:s',
			minDate : new Date(),
			onShow : function() {
				this.setOptions({
					maxDate : $('#f_date1').val() ? $('#f_date1').val() : false
				})
			},
			timepicker : true
		});
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