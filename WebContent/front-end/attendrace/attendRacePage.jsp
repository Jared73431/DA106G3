<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.attendrace.model.*" %>
<%@ page import="com.members.model.*" %>

<%
	MembersService memberSvc = new MembersService();
	String member_no = (String)session.getAttribute("member_no");
	MembersVO membersVO = memberSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO", membersVO);
	
	AttendRaceService attendRaceSvc = new AttendRaceService();
	String attend_no = (String)request.getAttribute("attend_no");
	AttendRaceVO attendRaceVO = attendRaceSvc.getOneAttendRace(attend_no);
	pageContext.setAttribute("attendRaceVO", attendRaceVO);
// 	String member_no = (String)request.getAttribute("attend_no");
	List<AttendRaceVO> list = attendRaceSvc.getByMem(member_no);
	pageContext.setAttribute("list", list);
	list.forEach(a -> a.toString());
	
%>

<%
	Map<Integer, String> race_type = new LinkedHashMap<Integer, String>();
	race_type.put(0, "馬拉松");
	race_type.put(1, "自行車賽");
	race_type.put(2, "游泳");
	pageContext.setAttribute("race_type", race_type);
%>


<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<meta charset="BIG5">
<title>Insert title here</title>
<style>
#page{
position:absolute;
left:17rem;
top:6rem;
}

.profile-img{
max-width:20rem;
max-height:30rem;
}
.col-6{
max_width:50%;
top:4.5rem;
left:1rem;
}
</style>

</head>
<body>
<%@ include file="/front-end/homepage/nav.file"%>
<%@ include file="/front-end/members/bar.file"%>
<div class="container">
  <div class="row">
<c:forEach var="attendraceVO" items="${list}">
    <div class="col-6">
      <p>比賽編號:${attendraceVO.attend_no}</p>
      <p>會員編號:${attendraceVO.member_no}</p>
      <p>比賽類型:${race_type.get(attendraceVO.race_type)}</p>
      <p>比賽名次:${attendraceVO.race_name}</p>
      <p>完賽日期:${attendraceVO.fin_date}</p>
      <p>完賽成績:${attendraceVO.fin_time}</p>
      <p>完賽名次:${attendraceVO.fin_rank}</p>
      <p>參賽備註:${attendraceVO.fin_remark}</p>
      <p>完賽證明:<div><img src="<%=request.getContextPath()%>/AttendRaceShowRecord?attend_no=${attendraceVO.attend_no}" alt="" class="profile-img"></div></p>
   <FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/attendrace/attendrace.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="刪除"> <input type="hidden"
							name="attend_no" value="${attendraceVO.attend_no}"> <input
							type="hidden" name="action" value="delete">
					</FORM>
   </div>
   
  </c:forEach>
  </div>
  <a href="<%=request.getContextPath()%>/front-end/attendrace/addAttend.jsp"><button class="btn btn-info" id="page">新增比賽紀錄</button></a>
</div>
<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>