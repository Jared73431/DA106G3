<%@page import="java.util.Comparator"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.course.model.*"%>
<%@ page import="java.io.*"%>

<%@ page import="com.members.model.*"%>

<% String member_no = (String)session.getAttribute("member_no");
	MembersService membersSvc = new MembersService();
	MembersVO membersVO = membersSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO",membersVO); 
%>

<%
	CoachVO coachVO = (CoachVO) request.getAttribute("coachVO");
	
	String coa_no = coachVO.getCoa_no();
	CourseService courseSvc = new CourseService();
	List<CourseVO> list = courseSvc.getAll().stream().filter(e -> e.getCoa_no().equals(coa_no))
			.filter(e -> e.getCour_status() == 1)
			.filter(e -> e.getCour_deadline().getTime() > System.currentTimeMillis())
			.sorted(Comparator.comparing(CourseVO::getCour_deadline)).collect(Collectors.toList());

	pageContext.setAttribute("coachVO", coachVO);
	pageContext.setAttribute("Coachlist", list);
%>
<jsp:useBean id="memberSvc" scope="page"
	class="com.members.model.MembersService" />
<jsp:useBean id="coa_followSvc" scope="page"
	class="com.coa_follow.model.Coa_FollowService" />
<html>
<head>
<title>教練資料 - listOneCoach.jsp</title>
<link href='<%=request.getContextPath()%>/js/fullcalendar/core/main.css' rel='stylesheet' />
    <link href='<%=request.getContextPath()%>/js/fullcalendar/daygrid/main.css' rel='stylesheet' />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.css" />
 <link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<style>
</style>

</head>
<body bgcolor='white'>

	<div class="container">

		<div id="picture" style="margin-bottom: 1em;">
			<div id="carouselExampleCaptions" class="carousel slide"
				data-ride="carousel">
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
		<c:if test="${membersVO.member_no!= coachVO.member_no}">
			<img id="heart" 
			${coa_followSvc.check(membersVO.member_no,coachVO.coa_no)!=0?'src="" title="取消追蹤"':'src="" title="加入追蹤"' }>		
		</c:if>
		<div id="course">
			<c:forEach var="courseVO" items="${Coachlist}" begin="0" end="2">
				<div class="card border-warning mb-3" style="max-width: 30rem;">
					<div class="card-header" style="max-height:6rem;">
						截止:
						<fmt:timeZone value="GMT+8:00">
							<fmt:formatDate value="${courseVO.cour_deadline}" type="both"
								dateStyle="short" timeStyle="short" />
						</fmt:timeZone>
					</div>
					<div class="card-body text-danger" style="max-height:13rem;">
						<h5 class="card-title">${courseVO.cour_name}</h5>
						<p class="card-text">${courseVO.cour_addr}</p>
						<p style="font-size: 16px;">
							開課日期:
							<fmt:timeZone value="GMT+8:00">
								<fmt:formatDate value="${courseVO.cour_date}" type="both"
									dateStyle="short" timeStyle="short" />
							</fmt:timeZone>
						</p>
						<button type="button" class="btn btn-primary btn-sm"
							data-toggle="modal" data-target="#exampleModal"
							data-mode="${courseVO.cour_mode}" data-no="${courseVO.cour_no}"
							id="cour_mode" style="float: right;">預約</button>
					</div>
				</div>

			</c:forEach>
<div id="calendar"></div>
		</div>
		
			
		
		
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





<script
		src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>


    <script src='<%=request.getContextPath()%>/js/fullcalendar/core/main.js'></script>
    <script src='<%=request.getContextPath()%>/js/fullcalendar/daygrid/main.js'></script>
    <script src="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.js" type="text/javascript"></script>
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
	
	<script>
	window.onload=function(){
		if($('#heart').attr('title') == "加入追蹤"){
			 $('#heart').attr("src", "<%=request.getContextPath()%>/picture/heart_gray.png");
		}else {
			  $('#heart').attr("src","<%=request.getContextPath()%>/picture/heart_red.png");
		
	}
	}
	
	$("#heart").click(function(){
		  if($(this).attr('title') == "加入追蹤"){

			  $(this).attr("title", "取消追蹤");
			  $(this).attr("src", "<%=request.getContextPath()%>/picture/heart_red.png");
			 		  
			  $.ajax({
					type: "POST",
					url: "<%=request.getContextPath()%>/coa_follow/coa_follow.do",
					data: "action=insert&coa_no=${coachVO.coa_no}&member_no=${member_no}",
					success: function(data){

						
					}
			   });
		  }else {
			  $(this).attr("src","<%=request.getContextPath()%>/picture/heart_gray.png");
			  $(this).attr("title","加入追蹤");
			   $.ajax({
					type: "POST",
					url: "<%=request.getContextPath()%>/coa_follow/coa_follow.do",
					data: "action=insert&coa_no=${coachVO.coa_no}&member_no=${member_no}",
					success: function(data){	

					}
			  });
		  }
	})
	
	</script>
	
	
	
	
</body>
</html>