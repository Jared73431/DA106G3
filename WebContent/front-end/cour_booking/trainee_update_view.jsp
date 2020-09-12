<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.cour_booking.model.*"%>
<%@ page import="com.members.model.*"%>

<%
	String member_no = (String) session.getAttribute("member_no");
	MembersService membersSvc = new MembersService();
	MembersVO membersVO = membersSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO", membersVO);
%>

<%
	Cour_BookingVO cour_bookingVO = (Cour_BookingVO) request.getAttribute("cour_bookingVO");
%>
<jsp:useBean id="coachSvc" scope="page"
	class="com.coach.model.CoachService" />

<jsp:useBean id="course_kindSvc" scope="page"
	class="com.course_kind.model.Course_kindService" />
<jsp:useBean id="courseSvc" scope="page"
	class="com.course.model.CourseService" />
<jsp:useBean id="membersSvc2" scope="page"
	class="com.members.model.MembersService" />
<html>
<head>
<title>學員確認畫面 - trainee_update_view.jsp</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.css">


</head>
<body bgcolor='white'>


	<%@ include file="/front-end/homepage/nav.file"%>
	<div id="listall">
		<div class="container">
		
	
	
			<div class="table-responsive">
				<table class="table table">
					<tr class="table-success">
						<th scope="row">課程名稱</th>
						<td>${courseSvc.getOneCourse(cour_bookingVO.cour_no).cour_name}</td>
					</tr>
					<tr class="table-danger">
						<th scope="row">學員</th>
						<th>${membersSvc2.getOneMembers(cour_bookingVO.member_no).mem_name}</th>
					</tr>
					<tr class="table-success">
						<th scope="row">教練</th>
						<th>${membersSvc2.getOneMembers(coachSvc.getOneCoach(cour_bookingVO.coa_no).member_no).mem_name}</th>
					</tr>
					<tr class="table-danger">
						<th scope="row">您的確認狀態</th>
						<th>${(cour_bookingVO.trainee_comf==1?'確認':(cour_bookingVO.trainee_comf==2?'未確認':'取消'))}
							<FORM METHOD="post"
								ACTION="<%=request.getContextPath()%>/cour_booking/cour_booking.do" id="formcancel"
								style="margin-bottom: 0px;">

								<button type="button" class="btn btn-outline-danger" id="cancelbtn"
									style="float: right;"
									<c:if test="${cour_bookingVO.trainee_comf == 3 || cour_bookingVO.coa_comf == 3}">
							disabled
						</c:if>>取消</button>

								<input type="hidden" name="trainee_comf" value="3"> <input
									type="hidden" name="booking_no"
									value="${cour_bookingVO.booking_no}"> <input
									type="hidden" name="requestURL"
									value="<%=request.getServletPath()%>"> <input
									type="hidden" name="action" value="Trainee_Change">
							</FORM>
							<FORM METHOD="post"
								ACTION="<%=request.getContextPath()%>/cour_booking/cour_booking.do" 
								style="margin-bottom: 0px;">

								<button type="submit" class="btn btn-outline-success" 
									style="float: right;"
									<c:if test="${cour_bookingVO.trainee_comf == 3 }">
							disabled
						</c:if>>確認</button>
								<input type="hidden" name="trainee_comf" value="1"> <input
									type="hidden" name="booking_no"
									value="${cour_bookingVO.booking_no}"> <input
									type="hidden" name="requestURL"
									value="<%=request.getServletPath()%>"> <input
									type="hidden" name="action" value="Trainee_Change">
							</FORM>

						</th>
					</tr>
					<tr class="table-success">
						<th scope="row">教練確認狀態</th>
						<th>${(cour_bookingVO.coa_comf==1?'確認':(cour_bookingVO.coa_comf==2?'未確認':'取消'))}</th>
					</tr>
					<tr class="table-danger">
						<th scope="row">您的報到狀況</th>
						<th>${(cour_bookingVO.trainee_ci==1?'已報到':'未報到')}</th>
					</tr>
					<tr class="table-success">
						<th scope="row">教練報到狀況</th>
						<th>${(cour_bookingVO.coa_ci==1?'已報到':'未報到')}</th>
					</tr>
		
					<tr class="table-danger">
						<th scope="row">評分</th>
							<FORM METHOD="post"
		ACTION="<%=request.getContextPath()%>/cour_booking/cour_booking.do" name="form1">	
						<th><div class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="cour_score"
									id="inlineRadio1" value="1"> <label
									class="form-check-label" for="inlineRadio1">1</label>
							</div>
							<div class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="cour_score"
									id="inlineRadio2" value="2"> <label
									class="form-check-label" for="inlineRadio2">2</label>
							</div>
							<div class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="cour_score"
									id="inlineRadio3" value="3"> <label
									class="form-check-label" for="inlineRadio2">3</label>
							</div>
							<div class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="cour_score"
									id="inlineRadio4" value="4"> <label
									class="form-check-label" for="inlineRadio4">4</label>
							</div>
							<div class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="cour_score"
									id="inlineRadio5" value="5"> <label
									class="form-check-label" for="inlineRadio5">5</label>
							</div></th>
					</tr>
					<tr class="table-danger">
						<th scope="row">評價</th>
						<th>
							<div class="form-group">
								<textarea class="form-control" name="cour_option"
									 rows="3">${cour_bookingVO.cour_option}</textarea>
							</div>
						</th>
					</tr>
					
				</table>
				<input type="hidden" name="action" value="update">
			<input type="hidden" name="booking_no" value="${cour_bookingVO.booking_no}"> 	

		<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">	
				<input class="btn btn-primary" type="submit" value="送出" style="float:right;margin-bottom:1rem;">
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
	swal.setDefaults({
	    confirmButtonText: "確定",
	    cancelButtonText: "取消"
	});
	
	$(function () {
	    $("#cancelbtn").on('click', function () {
	        swal({
	            title: "確定取消？",
	            html: "按下確定後會取消此課程",
	            type: "question", // type can be "success", "error", "warning", "info", "question"
	            showCancelButton: true,
	        		showCloseButton: true,
	        }).then(
	        	   function (result) {
                if (result) {
               	 $(document).find('#formcancel').submit();
                    
                }
            }, function(dismiss) { // dismiss can be "cancel" | "overlay" | "esc" | "cancel" | "timer"
            		swal("未取消", "課程未被取消", "error");
	        }).catch(swal.noop);
	    });
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