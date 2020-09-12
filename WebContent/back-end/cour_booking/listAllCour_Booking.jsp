<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.stream.Collector"%>
<%@page import="java.sql.Timestamp"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.stream.*"%>
<%@ page import="com.cour_booking.model.*"%>
<%@ page import="com.cour_booking.controller.*"%>
<%@ page import="com.course.model.*"%>
<%@ page import="com.course.controller.*"%>

<%
	String feature = "F0002";
%>

<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>

<%
	CourseService courseSvc = new CourseService();
	List<CourseVO> courseList = courseSvc.getAll();
	 
	long now = System.currentTimeMillis();
	
	List<CourseVO> list = courseList.stream()
									.filter(e -> e.getCour_status() == 3)
									.filter(e -> e.getCour_date().getTime() < now)
									.collect(Collectors.toList());

	pageContext.setAttribute("list", list);
%>
<jsp:useBean id="coachSvc" scope="page"
	class="com.coach.model.CoachService" />
<jsp:useBean id="membersSvc" scope="page"
	class="com.members.model.MembersService" />
<jsp:useBean id="cour_bookingSvc" scope="page"
	class="com.cour_booking.model.Cour_BookingService" />


<html>
<head>
<title>後台審核課程狀況 - listAllCour_Booking.jsp</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.css">
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
	width: 1200px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 5px;
	text-align: center;
}
</style>

</head>
<body bgcolor='white'>



	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<table>
		<tr>
			<th>預約編號</th>
			<th>課程編號</th>
			<th>學員編號</th>
			<th>教練編號</th>
			<th>教練行程確認</th>
			<th>學員行程確認</th>
			<th>教練報到</th>
			<th>學員報到</th>
			<th>評分</th>
			<th>評價</th>
			<th>選擇的上課方式</th>

			<th>後台審核</th>
			<th>停權教練</th>


		</tr>
		<%@ include file="page1.file"%>
		<c:forEach var="courseVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>" varStatus="vo">

			<tr>
				<c:forEach var="cour_bookingVO"
					items="${cour_bookingSvc.getListBooking(null,courseVO.cour_no,null,null)}"
					varStatus="s">
					<c:if test="${s.last}">
						<td>${cour_bookingVO.booking_no}</td>
					</c:if>
				</c:forEach>
				<td>${courseVO.cour_name}</td>

				<c:forEach var="cour_bookingVO"
					items="${cour_bookingSvc.getListBooking(null,courseVO.cour_no,null,null)}"
					varStatus="s">
					<c:if test="${s.last}">
						<td>${membersSvc.getOneMembers(cour_bookingVO.member_no).mem_name}</td>
					</c:if>
				</c:forEach>

				<td>${membersSvc.getOneMembers(coachSvc.getOneCoach(courseVO.coa_no).member_no).mem_name}</td>

				<c:forEach var="cour_bookingVO"
					items="${cour_bookingSvc.getListBooking(null,courseVO.cour_no,null,null)}"
					varStatus="s">
					<c:if test="${s.last}">
						<td>${(cour_bookingVO.coa_comf==1?'確認':(cour_bookingVO.coa_comf==2?'未確認':'取消'))}</td>
						<td>${(cour_bookingVO.trainee_comf==1?'確認':(cour_bookingVO.trainee_comf==2?'未確認':'取消'))}</td>
						<td>${(cour_bookingVO.coa_ci==1?'已報到':'未報到')}</td>
						<td>${(cour_bookingVO.trainee_ci==1?'已報到':'未報到')}</td>
						<td>${(cour_bookingVO.cour_score==0?'未評分':cour_bookingVO.cour_score)}</td>
						<td>${cour_bookingVO.cour_option}</td>
						<td>${(cour_bookingVO.choose_mode==1?'網路':(cour_bookingVO.choose_mode==0?'未選擇':'面授'))}</td>
					</c:if>
				</c:forEach>

				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/cour_booking/cour_booking.do"
						style="margin-bottom: 0px;">
		<button type="submit" class="btn btn-outline-success" >結案</button><input
							type="hidden" name="cour_no"
							value="${courseVO.cour_no}"> <input
							type="hidden" name="requestURL"
							value="<%=request.getServletPath()%>">
						<!--送出本網頁的路徑給Controller-->
						<input type="hidden" name="whichPage" value="<%=whichPage%>">
						<!--送出當前是第幾頁給Controller-->
						<input type="hidden" name="action" value="pass">
					</FORM>
				</td>
				<td>
					<FORM METHOD="post" id="formdiscipline${vo.count}"
						ACTION="<%=request.getContextPath()%>/coach/coach.do"
						style="margin-bottom: 0px;">
						<button type="button" class="btn btn-outline-danger" id="btndiscipline${vo.count}">停權</button>
						 <input type="hidden"
							name="coa_no" value="${courseVO.coa_no}">
						<input type="hidden" name="requestURL"
							value="<%=request.getServletPath()%>">
						<!--送出本網頁的路徑給Controller-->
						<input type="hidden" name="whichPage" value="<%=whichPage%>">
						<!--送出當前是第幾頁給Controller-->
						<input type="hidden" name="action" value="punish">
					</FORM>
				</td>

			</tr>

		</c:forEach>
	</table>
	<%@ include file="page2.file"%>

	<script
		src="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.js"></script>
		<% for(int i = 1;i<=rowsPerPage;i++){ %>
        <script>
    			swal.setDefaults({
    			    confirmButtonText: "確定",
    			    cancelButtonText: "取消"
    			});
    			$(function () {
    			    $("#btndiscipline<%=i%>").on('click', function () {
    			        swal({
    			            title: "確定停權？",
    			            html: "按下確定後會將教練資格停權",
    			            type: "question", // type can be "success", "error", "warning", "info", "question"
    			            showCancelButton: true,
    			        		showCloseButton: true,
    			        }).then(
    			        	   function (result) {
    		                if (result) {
    		                	$("#formdiscipline<%=i%>").submit();
    		                    swal("完成!", "已停權", "success");
    		                }
    		            }, function(dismiss) { // dismiss can be "cancel" | "overlay" | "esc" | "cancel" | "timer"
    		            		swal("取消", "動作取消", "error");
    			        }).catch(swal.noop);
    			    });
    			});
        </script>
		<%} %>
</body>
</html>