<%@page import="java.util.stream.Collectors"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.coach.controller.*"%>
<%@ page import="com.members.model.*"%>

<%
	String member_no = (String) session.getAttribute("member_no");
	MembersService membersSvc = new MembersService();
	MembersVO membersVO = membersSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO", membersVO);
%>

<%
	CoachService coachSvc = new CoachService();
	List<CoachVO> list = coachSvc.getAll().stream().filter(e -> e.getCoa_status() == 1)
			.collect(Collectors.toList());
	pageContext.setAttribute("list", list);
%>
<jsp:useBean id="memberSvc" scope="page"
	class="com.members.model.MembersService" />
<jsp:useBean id="coa_followSvc" scope="page"
	class="com.coa_follow.model.Coa_FollowService" />
<html>
<head>
<title>查看所有教練 - listAllCoach.jsp</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.css">


</head>
<body bgcolor='white'>
	<%@ include file="/front-end/homepage/nav.file"%>
	<div id="listall">


		<%@ include file="page1.file"%>

		<div class="container">

			<div class="row">
				<div class="col-6" id="left">
					<c:forEach var="coachVO" items="${list}" begin="<%=pageIndex%>"
						end="<%=pageIndex+rowsPerPage-1%>">
						<div class="card mb-3" style="max-width: 540px;">


							<div class="row no-gutters">
								<div class="col-md-4">
									<img
										src="<%=request.getContextPath()%>/MembersShowPicture?member_no=${coachVO.member_no}"
										style="width: 11rem; height: 13rem;" class="card-img"
										alt="...">
								</div>
								<div class="col-md-8">
									<div class="card-body">
										<h5 class="card-title"
											style="font-family: Impact, Charcoal, sans-serif; font-size: 1.875em; color: purple;">${memberSvc.getOneMembers(coachVO.member_no).mem_name}</h5>
										<p class="card-text" style="font-size: 0.6rem;">${coachVO.coa_info}</p>
										<FORM METHOD="post"
											ACTION="<%=request.getContextPath()%>/coach/coach.do">
											<input type="hidden" name="action" value="getOne_For_Display">
											<input type="hidden" name="coa_no" value="${coachVO.coa_no}">
											<input type="hidden" name="requestURL"
												value="<%=request.getServletPath()%>"> <input
												type="hidden" name="whichPage"
												value="<%=request.getParameter("whichPage")%>">
											<button type="submit" class="btn btn-outline-info btn-sm"
												id="more"
												style="position: absolute; right: 0px; bottom: 0px;">more</button>
										</FORM>

									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>


				<div class="col-6" id="right">
					<%
						if (request.getAttribute("coachVO") == null) {
					%>
					<img src="<%=request.getContextPath()%>/picture/iwantyou.jpg"
						style="width: 30rem;">
					<%
						}
					%>
					<%
						if (request.getAttribute("coachVO") != null) {
					%>
					<jsp:include page="listOneCoach.jsp" />
					<%
						}
					%>
				</div>
			</div>
		</div>





	</div>
	<%@ include file="page2.file"%>
	<%@ include file="/front-end/homepage/footer.file"%>

	<script
		src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.js"></script>

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