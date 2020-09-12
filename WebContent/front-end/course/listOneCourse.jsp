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
<title>課程資料 - listOneCourse.jsp</title>
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
								class="form-control" value="<%=courseVO.getCour_name()%>" /></th>
						</tr>
						<tr class="table-info">
							<th scope="row">課程類型</th>
							<td><select class="form-control" size="1"
								name="cour_type_no">
									<c:forEach var="course_kindVO" items="${course_kindSvc.all}">
										<option value="${course_kindVO.cour_type_no}"
											<c:if test="${course_kindVO.cour_type_no==courseVO.cour_type_no}">
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
									<option value="1" ${(courseVO.cour_mode==1)?'selected':''}>網路


									
									<option value="2" ${(courseVO.cour_mode==2)?'selected':''}>面授


									
									<option value="3" ${(courseVO.cour_mode==3)?'selected':''}>Both


									
							</select></td>
						</tr>
						<tr class="table-info">
							<td scope="row">授課時間:</td>
							<td><input class="form-control" type="TEXT" name="cour_date"
								size="25" id="f_date1"
								value="<%=courseVO.getCour_date()==null?"":courseVO.getCour_date()%>"
								<c:if test="${courseVO.cour_status==3}">
							readonly="readonly"					
						</c:if> /></td>
						</tr>

						<tr class="table-info">
							<td>報名截止日期:</td>
							<td scope="row"><input class="form-control" type="TEXT"
								name="cour_deadline" size="25" id="f_date2"
								value="<%=courseVO.getCour_deadline()==null?"":courseVO.getCour_deadline()%>"
								<c:if test="${courseVO.cour_status==3}">
							readonly="readonly"					
						</c:if> /></td>
						</tr>
						<tr class="table-info">
							<td scope="row">上次更新時間:</td>
							<td scope="row"><fmt:timeZone value="GMT+8:00">
									<fmt:formatDate value="${courseVO.cour_update}" type="both"
										dateStyle="long" timeStyle="long" />
								</fmt:timeZone></td>
						</tr>
						<tr class="table-info">
							<td scope="row">課程狀態:</td>
							<td><select class="form-control" size="1" name="cour_status">
									<option value="1" ${(courseVO.cour_status==1)?'selected':''}
										<c:if test="${courseVO.cour_status == 3}">
					disabled='disabled'
				</c:if>>上架

									
									<option value="2" ${(courseVO.cour_status==2)?'selected':''}
										<c:if test="${courseVO.cour_status == 3}">
					disabled='disabled'
				</c:if>>下架

										<c:if test="${courseVO.cour_status == 3}">
											<option value="3" ${(courseVO.cour_status==3)?'selected':''}>已被預約


											
										</c:if>
							</select></td>
						</tr>
					</table>
					<input type="hidden" name="action" value="update"> <input
						type="hidden" name="cour_no" value="${courseVO.cour_no}"><input
						type="hidden" name="coa_no" value="${courseVO.coa_no}"> <input
						type="hidden" name="requestURL"
						value="<%=request.getServletPath()%>">
					<button class="btn btn-primary" type="submit" style="float:right;margin-bottom:1rem;">送出修改</button>
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