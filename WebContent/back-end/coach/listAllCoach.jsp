<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.coach.controller.*"%>
<%
	String feature = "F0002";
%>

<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>

<%
	CoachService coachSvc = new CoachService();
	List<CoachVO> list = coachSvc.getAll();
	pageContext.setAttribute("list", list);
	
%>
<jsp:useBean id="memberSvc" scope="page"
	class="com.members.model.MembersService" />

<html>
<head>
<title>所有教練資料 - listAllCoach.jsp</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.css">
<style>
#coachtable{
	font-size:0.8em;

}

</style>
</head>
<body bgcolor='white'>

<div class="container-fluid" id="coachtable">
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
<div class="table-responsive">
	<table class="table table-hover">
	 <thead>
		<tr>
			<th scope="col">教練編號</th>
			<th scope="col">教練姓名</th>
			<th scope="col">服務地區</th>
			<th scope="col">教練證照1</th>
			<th scope="col">教練證照2</th>
			<th scope="col">教練證照3</th>
			<th scope="col">教練狀態</th>
			<th scope="col">停權</th>
		
		</tr>
		</thead>
		<%@ include file="page1.file"%>
		<tbody>
		<c:forEach var="coachVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">

			<tr class="text-center">
				<td>${coachVO.coa_no}</td>
				<td>${memberSvc.getOneMembers(coachVO.member_no).mem_name}</td>
				<td>${coachVO.service_area}</td>
				<td><img
					src="<%=request.getContextPath()%>/DBGifReader4Coach?member_no=${coachVO.member_no}&licence=coa_licence1"
					width=150px height=150px></td>
				<td><img
					src="<%=request.getContextPath()%>/DBGifReader4Coach?member_no=${coachVO.member_no}&licence=coa_licence2"
					width=150px height=150px></td>
				<td><img
					src="<%=request.getContextPath()%>/DBGifReader4Coach?member_no=${coachVO.member_no}&licence=coa_licence3"
					width=150px height=150px></td>
				<td>${(coachVO.coa_status)==1?'正常':'已停權'}</td>

				<td>
					<FORM METHOD="post" id="formdiscipline"
						ACTION="<%=request.getContextPath()%>/coach/coach.do"
						style="margin-bottom: 0px;">
						<button type="button" class="btn btn-outline-danger" id="btndiscipline" 
						<c:if test="${coachVO.coa_status==2}">
							disabled='disabled'
						</c:if>
						
						>停權</button><input type="hidden"
							name="coa_no" value="${coachVO.coa_no}"> <input
							type="hidden" name="member_no" value="${coachVO.member_no}">
							<input
							type="hidden" name="requestURL"
							value="<%=request.getServletPath()%>">
						<!--送出本網頁的路徑給Controller-->
						<input type="hidden" name="whichPage" value="<%=whichPage%>">
						<!--送出當前是第幾頁給Controller-->
						<input type="hidden" name="action" value="punish">
					</FORM>
				</td>
						
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<%@ include file="page2.file"%>
	</div>
</div>

	<script
		src="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.js"></script>
        <script>
    			swal.setDefaults({
    			    confirmButtonText: "確定",
    			    cancelButtonText: "取消"
    			});
    			$(function () {
    			    $("#btndiscipline").on('click', function () {
    			        swal({
    			            title: "確定停權？",
    			            html: "按下確定後會將教練資格停權",
    			            type: "question", // type can be "success", "error", "warning", "info", "question"
    			            showCancelButton: true,
    			        		showCloseButton: true,
    			        }).then(
    			        	   function (result) {
    		                if (result) {
    		                	$("#formdiscipline").submit();
    		                    swal("完成!", "已停權", "success");
    		                }
    		            }, function(dismiss) { // dismiss can be "cancel" | "overlay" | "esc" | "cancel" | "timer"
    		            		swal("取消", "動作取消", "error");
    			        }).catch(swal.noop);
    			    });
    			});
        </script>

</body>
</html>