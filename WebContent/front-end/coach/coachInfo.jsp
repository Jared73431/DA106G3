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
<title>教練個人頁面 - coachInfo.jsp</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">

<style>
#errorMsgs {
	width: 15rem;
	border: 1px solid #000;
	position: fixed;
	top: 7rem;
	left: 0px;
	background: #F00;
	background-color: rgba(225, 125, 64, 0.2);
}

#errorMsgs li {
	opacity: 1;
}
</style>
</head>
<body bgcolor='white'>
	<%@ include file="/front-end/homepage/nav.file"%>
	<div id="listall">
		<div style="float:right;">
			<a class="btn btn-primary" href="<%=request.getContextPath()%>/front-end/coach/coachInfo_update.jsp" role="button">修改資料</a>
		</div>
		<div class="container">

			<div class="card bg-info" style="max-width: 1000px;">
				<div class="row no-gutters">
					<div class="col-md-4">
						<img
							src="<%=request.getContextPath()%>/MembersShowPicture?member_no=${coachVO.member_no}"
							style="width: 13rem; height: 13rem; border-radius: 50%;"
							class="card-img" alt="...">
					</div>
					<div class="col-md-8">
						<div class="card-body">
							<h5 class="card-title">服務地點:${coachVO.service_area}</h5>
							<p class="card-text">${coachVO.coa_info}</p>
						</div>
					</div>
				</div>
			</div>
	
	<div id="carouselExampleCaptions" class="carousel slide col-6" 
				data-ride="carousel" style="max-width: 540px;">
				<ol class="carousel-indicators">
					<li data-target="#carouselExampleCaptions" data-slide-to="0"
						class="active"></li>
					<li data-target="#carouselExampleCaptions" data-slide-to="1"></li>
					<li data-target="#carouselExampleCaptions" data-slide-to="2"></li>
				</ol>
				<div class="carousel-inner">
					<div class="carousel-item active">
						<img
							src="<%=request.getContextPath()%>/DBGifReader4Coach?member_no=${coachVO.member_no}&licence=coa_licence1"
							style="width: 20rem; height: 15rem" class="d-block w-100"
							alt="...">
						<div class="carousel-caption d-none d-md-block">
							<h5 style="color: red;">${coachVO.coa_skill1}</h5>

						</div>
					</div>
					<div class="carousel-item">
						<img
							src="<%=request.getContextPath()%>/DBGifReader4Coach?member_no=${coachVO.member_no}&licence=coa_licence2"
							style="width: 20rem; height: 15rem" class="d-block w-100"
							alt="...">
						<div class="carousel-caption d-none d-md-block">
							<h5 style="color: red;">${coachVO.coa_skill2}</h5>
						</div>
					</div>
					<div class="carousel-item">
						<img
							src="<%=request.getContextPath()%>/DBGifReader4Coach?member_no=${coachVO.member_no}&licence=coa_licence3"
							style="width: 20rem; height: 15rem" class="d-block w-100"
							alt="...">
						<div class="carousel-caption d-none d-md-block">
							<h5 style="color: red;">${coachVO.coa_skill3}</h5>

						</div>
					</div>
				</div>
				<a class="carousel-control-prev" href="#carouselExampleCaptions"
					role="button" data-slide="prev"> <span
					class="carousel-control-prev-icon" aria-hidden="true"></span> <span
					class="sr-only">Previous</span>
				</a> <a class="carousel-control-next" href="#carouselExampleCaptions"
					role="button" data-slide="next"> <span
					class="carousel-control-next-icon" aria-hidden="true"></span> <span
					class="sr-only">Next</span>
				</a>
			</div>
		</div>

		
	
	<%@ include file="/front-end/homepage/footer.file"%>

	<script
		src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>

	<script>
<%for (int i = 1; i <= 3; i++) {%>
	$("#up<%=i%>").change(function(){

	  readURL<%=i%>(this);

	});

	 

	function readURL<%=i%>(input){

	  if(input.files && input.files[0]){

	    var reader = new FileReader();

	    reader.onload = function (e) {

	       $("#li<%=i%>").attr('src', e.target.result);

				}

				reader.readAsDataURL(input.files[0]);

			}

		}
	<%}%>
		
	</script>
</body>
</html>