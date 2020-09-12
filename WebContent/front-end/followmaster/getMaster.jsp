<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.members.model.*"%>
<%@ page import="com.followmaster.model.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.coach.controller.*"%>

<%
String member_no = (String)session.getAttribute("member_no");
MembersService membersSvc = new MembersService();
MembersVO membersVO = membersSvc.getOneMembers(member_no);
pageContext.setAttribute("membersSvc",membersSvc); 
pageContext.setAttribute("membersVO",membersVO); 

FollowMasterService followMasterSvc = new FollowMasterService();
List<FollowMasterVO> list = followMasterSvc.getMaster(member_no);
pageContext.setAttribute("list", list);
%>
<jsp:useBean id="coachSvc" scope="page" class="com.coach.model.CoachService" />
<jsp:useBean id="coa_followSvc" scope="page" class="com.coa_follow.model.Coa_FollowService" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>團主追蹤</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">

<style>
img.member_pic{
width:10rem;
height:12rem;
margin-top:3rem;
}

div#border{
border-radius: 80px;
padding: 3em;
margin-bottom:5rem;
background-color:rgba(229,229,229,0.9);
}
</style>

</head>
<body>
<%@ include file="/front-end/homepage/nav.file"%>
<div id="listall">
<div class="container" id="listallgroup">
<div class="row justify-content-center no-gutters" >  
 <h1>追蹤列表</h1>
</div>

<div id="border">
<ul class="nav nav-tabs" id="myTab" role="tablist">
  <li class="nav-item">
    <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">團主</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">教練</a>
  </li>
</ul>
  
  
<div class="tab-content" id="myTabContent">
 	<div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
  		<div class="row justify-content-center no-gutters" >  
	
				<c:forEach var="followMasterVO" items="${list}">
					<div class="col-3">
						<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/followmaster/followmaster.do" style="margin-bottom: 0px;">
								<a href="<%=request.getContextPath()%>/front-end/members/listOneMembers.jsp?member_no=${followMasterVO.master_no}" target="_blank">
							 	<img src="<%=request.getContextPath() %>/MembersShowPicture?member_no=${followMasterVO.master_no}" class="member_pic">
								</a>
								<br>
									${membersSvc.getOneMembers(followMasterVO.master_no).known}
								<input type="submit" value="取消"> 
								<input type="hidden" name="member_no" value="${followMasterVO.member_no}"> 
								<input type="hidden" name="master_no" value="${followMasterVO.master_no}"> 
								<input type="hidden" name="action" value="delete">
						</FORM>
					</div>
				</c:forEach>
		</div>
	</div>
		
	<div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
		<div class="row justify-content-center no-gutters" class="member_row">  
			
			<c:forEach var="coa_followVO" items="${coa_followSvc.getOneFollow(membersVO.member_no,null)}" >
					<div class="col-3">
						<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/coa_follow/coa_follow.do" style="margin-bottom: 0px;">
								<a href="<%=request.getContextPath()%>/front-end/members/listOneMembers.jsp?member_no=${coachSvc.getOneCoach(coa_followVO.coa_no).member_no}" target="_blank">
							 	<img src="<%=request.getContextPath() %>/MembersShowPicture?member_no=${coachSvc.getOneCoach(coa_followVO.coa_no).member_no}" class="member_pic">
								</a>
								<br>
									${membersSvc.getOneMembers(coachSvc.getOneCoach(coa_followVO.coa_no).member_no).known}
								<input type="submit" value="取消"> 
								<input type="hidden" name="coa_no" value="${coa_followVO.coa_no}">
								<input type="hidden" name="member_no" value="${membersVO.member_no}">
								<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
								<input type="hidden" name="action" value="insert" >
						</FORM>
					</div>
		</c:forEach>
		</div>
	</div>
</div>
</div>
</div>
</div>
<%@ include file="/front-end/homepage/footer.file"%>

<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>

<script>
</script>
</body>
</html>