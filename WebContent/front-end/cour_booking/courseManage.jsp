<%@page import="java.util.stream.Collectors"%>
<%@page import="com.sun.mail.util.logging.CollectorFormatter"%>
<%@page import="java.util.stream.Collector"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.cour_booking.model.*"%>
<%@ page import="com.cour_booking.controller.*"%>
<%@ page import="com.members.model.*"%>

<% String member_no = (String)session.getAttribute("member_no");
	MembersService membersSvc = new MembersService();
	MembersVO membersVO = membersSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO",membersVO); 
%>


<jsp:useBean id="coachSvc" scope="page"
	class="com.coach.model.CoachService" />

<jsp:useBean id="courseSvc" scope="page"
	class="com.course.model.CourseService" />

<html>
<head>
<title>行事曆 - courseManage.jsp</title>
 <link rel="stylesheet" href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">

<link href='<%=request.getContextPath()%>/js/fullcalendar/core/fullcalendar.min.css' rel='stylesheet' />
<link href='<%=request.getContextPath()%>/js/fullcalendar/core/fullcalendar.print.css' rel='stylesheet' media="print"/>
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
</head>
<body bgcolor='white'>
<%@ include file="/front-end/homepage/nav.file"%>
<div id="listall">

<div class="container">
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<span class="badge badge-light">分類</span>
<span class="badge badge-pill badge-success" style="margin-bottom:1em;">自創課程</span>
<span class="badge badge-pill badge-warning">課程取消</span>
<span class="badge badge-pill badge-danger">自創課程被預約中</span>
<span class="badge badge-info">預約中課程</span>

		
		 <div  id="calendar" style="width:60rem;margin-left:2rem;"></div>
		
	</div>

</div>


<%@ include file="/front-end/homepage/footer.file"%>

 <script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>
 <script  type="text/javascript" src='<%=request.getContextPath()%>/js/fullcalendar/core/moment.min.js'></script>
 <script  type="text/javascript" src='<%=request.getContextPath()%>/js/fullcalendar/core/fullcalendar.min.js'></script>
   <script>
  	$( "#calendar" ).fullCalendar({
  		// 參數設定[註1]
  		header: { // 頂部排版
  			left: "prev,next today", // 左邊放置上一頁、下一頁和今天
  			center: "title", // 中間放置標題
  			right: "month,basicWeek,basicDay" // 右邊放置月、周、天
  		},
  		defaultDate: new Date(), // 起始日期
  		weekends: true, // 顯示星期六跟星期日
  		editable: false,  // 啟動拖曳調整日期
  		timeFormat: 'H:mm', // uppercase H for 24-hour clock
  		events: {
  	        url: "<%=request.getContextPath()%>/cour_booking/cour_booking.do?action=getJson&member_no=${membersVO.member_no}",
  	        type: 'GET',
  	        data: {
  	            custom_param1: 'something',
  	            custom_param2: 'somethingelse'
  	        },

  	        error: function() {
  	            alert('there was an error while fetching events!');
  	        },
  	        color: 'yellow',   // a non-ajax option
  	        textColor: 'black' // a non-ajax option
  	    },
	      eventClick: function(event) {
	  	        // opens events in a popup window
	  	        var eventcolor = event.color;
	  	        console.log(eventcolor);
	  	        if(eventcolor=='#FFC107' || eventcolor=='red' || eventcolor=='lightblue'){
	  	        window.open(event.url, 'gcalevent', 'width=700,height=600');
	  	        return false;
	  	        }
	  	    }
  	});
  	
 
  </script>
</body>
</html>