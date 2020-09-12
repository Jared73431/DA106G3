<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.stream.Collector"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.course.model.*"%>
<%@ page import="com.course.controller.*"%>
<%@ page import="com.members.model.*"%>

<%
	String member_no = (String) session.getAttribute("member_no");
	MembersService membersSvc = new MembersService();
	MembersVO membersVO = membersSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO", membersVO);
	
%>
<%
	CourseService courseSvc = new CourseService();
	List<CourseVO> listAll = courseSvc.getAll();
	List<CourseVO> list = listAll.stream().filter(e -> e.getCour_status() == 1)
			.filter(e -> e.getCour_deadline().getTime() > System.currentTimeMillis())
			.collect(Collectors.toList());
	pageContext.setAttribute("list", list);
%>
<jsp:useBean id="coachSvc" scope="page"
	class="com.coach.model.CoachService" />

<jsp:useBean id="course_kindSvc" scope="page"
	class="com.course_kind.model.Course_kindService" />
	<jsp:useBean id="membersSvc2" scope="page"
	class="com.members.model.MembersService" />
<html>
<head>
<title>所有課程資料 - listAllCourse.jsp</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
	<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.css">
<style>
.card {
	margin-top: 3em;
}

.card .img {
	border: solid;
	display: block;
	position: static;
	margin: 0;
	max-width: 100%;
}

div {
	display: block;
}

@media screen and (max-width: 767px) {
	.card-header .img {
		width: 100%;
		height: 20%;
		display: block;
	}
}

.card-header {
	padding: 0px 0px 0px 0px;
}

.card-body {
	background-color: brown;
	padding-top: 2px;
}

.card-footer {
	background-color: black;
}

.card-footer p {
	margin: 0 auto;
	color: white;
}

</style>

</head>
<body bgcolor='white'>
	<%@ include file="/front-end/homepage/nav.file"%>
	<div id="listall">


		<div class="container-fluid">

			<%@ include file="page1.file"%>
			<div class="row">

				<c:forEach var="courseVO" items="${list}" begin="<%=pageIndex%>"
					end="<%=pageIndex+rowsPerPage-1%>">
					<div class="col-4">

						<div class="card" style="width: 18rem;">
							<div class="card-header">
								<img
									src="<%=request.getContextPath()%>/MembersShowPicture?member_no=${coachSvc.getOneCoach(courseVO.coa_no).member_no}"
									style="width: 17.93rem; height: 17.93rem;" />
							</div>
							<div class="card-body">
								<div>
									<p class="font-weight-bolder"
										style="font-size: 27px; white-space: nowrap; margin: 2px auto;">${membersSvc2.getOneMembers((coachSvc.getOneCoach(courseVO.coa_no)).member_no).mem_name}</p>
								</div>
								<p style="font-size: 16px; white-space: nowrap; color: orange">${course_kindSvc.getCourse_kind(courseVO.cour_type_no).cour_type}</p>
								<p style="font-size: 20px; white-space: nowrap; color: darkblue">${courseVO.cour_name}</p>
							</div>
							<div class="card-footer">
								<p>${courseVO.cour_addr}</p>

								<p>
									開課:
									<fmt:timeZone value="GMT+8:00">
										<fmt:formatDate value="${courseVO.cour_date}" type="both"
											dateStyle="short" timeStyle="short" />
									</fmt:timeZone></p>

								<p>
									截止:
									<fmt:timeZone value="GMT+8:00">
										<fmt:formatDate value="${courseVO.cour_deadline}" type="both"
											dateStyle="short" timeStyle="short" />
									</fmt:timeZone>

								</p>
								<button type="button" class="btn btn-primary btn-sm"
									data-toggle="modal" data-target="#exampleModal"
									data-mode="${courseVO.cour_mode}" data-no="${courseVO.cour_no}"
									id="cour_mode" style="float: right;">預約</button>

							</div>
						</div>
					</div>


				</c:forEach>
			</div>
			<%@ include file="page2.file"%>
		</div>




		<!-- Modal -->
		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">請選擇授課方式</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>預約課程需花費</p>
						<p style="color: red;">2000元</p>


						<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/cour_booking/cour_booking.do"
							id="form1">
						
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<label class="input-group-text" for="inputGroupSelect01">授課方式</label>
								</div>
								<select class="custom-select" id="inputGroupSelect01"
									name="choose_mode">
									<option selected value='0'>未選擇</option>
								</select>
							</div>
							<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
							<input type="hidden" name="whichPage" value="<%=whichPage%>">
							<input type="hidden" name="member_no" value="${membersVO.member_no}">
							<input type="hidden" name="cour_no" value="123"> <input
								type="hidden" name="action" value="insert">

						</FORM>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">關閉</button>
						<button type="button" class="btn btn-primary" id="btn-primary">確認預約</button>
					</div>
				</div>
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
		$('#exampleModal').on(
				'show.bs.modal',
				function(event) {
					var button = $(event.relatedTarget); // Button that triggered the modal
					var recipient = button.data('mode'); // Extract info from data-* attributes
					var cour_no = button.data('no');
					// If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
					// Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
					var modal = $(this);
					modal.find('#inputGroupSelect01').empty();
					if (recipient == 1) {

						modal.find('#inputGroupSelect01').append(
								"<option selected value='0'>未選擇</option>");
						modal.find('#inputGroupSelect01').append(
								"<option value='1'>網路</option>");
					} else if (recipient == 2) {
						modal.find('#inputGroupSelect01').append(
								"<option selected value='0'>未選擇</option>");
						modal.find('#inputGroupSelect01').append(
								"<option value='2'>面授</option>");
					} else {
						modal.find('#inputGroupSelect01').append(
								"<option selected value='0'>未選擇</option>");
						modal.find('#inputGroupSelect01').append(
								"<option value='1'>網路</option>");
						modal.find('#inputGroupSelect01').append(
								"<option value='2'>面授</option>");
					}
					modal.find($("input:hidden[name='cour_no']")).val(cour_no);
					var ycid = $("input:hidden[name='cour_no']").val();
					console.log(ycid);
				});

		$("#btn-primary").click(function() {
			$("#form1").submit();
		})
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