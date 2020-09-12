<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@page import="java.util.stream.*"%>
<%@ page import="com.members.model.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.coach.controller.*"%>
<%@ page import="com.course.model.*"%>
<%@ page import="com.course.controller.*"%>
<%
	String member_no = (String) session.getAttribute("member_no");

	MembersService membersSvc = new MembersService();
	MembersVO membersVO = membersSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO", membersVO);
%>
<%	
	if(membersVO.getCoa_qualifications()!=1){
		response.sendRedirect(request.getContextPath()+"/front-end/members/membersPage.jsp");
		return;
	}
	
	CoachService coachSvc = new CoachService();
	CoachVO coachVO = coachSvc.getOneCoachByMember(member_no);
	if(coachVO.getCoa_status()==2){
		response.sendRedirect(request.getContextPath()+"/front-end/members/membersPage.jsp");
		return;
	}
	CourseService courseSvc = new CourseService();
	List<CourseVO> listAll = courseSvc.getAll();
	List<CourseVO> list = listAll.stream().filter(e -> e.getCoa_no().equals(coachVO.getCoa_no()))
			.collect(Collectors.toList());
	pageContext.setAttribute("list", list);
%>

<jsp:useBean id="course_kindSvc" scope="page"
	class="com.course_kind.model.Course_kindService" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>教練課程管理清單</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">

<style>
#table1{
	font-size: 0.8em;
}
#MyBlog{

    position: fixed;  /*固定在網頁上不隨卷軸移動，若要隨卷軸移動用absolute*/

    top: 85%;  /*設置垂直位置*/

    left: -5px;  /*設置水平位置，依所放的內容多寡需要自行手動調整*/

    background: #ffffff;  /*背景顏色*/

    padding: 10px 20px;

    border-radius: 10px;  /*圓角*/

    -moz-border-radius: 10px;

    -webkit-border-radius: 10px;

}

#MyBlog:hover{  /*當滑鼠移至此區塊時，伸縮區塊*/

    left: 20px;

}

#MyBlog #title{

    padding-right: 5px;  /*讓標題與連結中間有空隙*/

}
</style>

</head>
<body>
	<%@ include file="/front-end/homepage/nav.file"%>
	<div id="listall">
	
	<div id="MyBlog">


<a  href="<%=request.getContextPath()%>/front-end/coach/coachInfo.jsp">檢視教練資料</a>

</div>
	
		<div class="container">
	<a href="<%=request.getContextPath()%>/front-end/course/addCourse.jsp" class="btn btn-outline-primary btn-sm " 
	role="button" aria-disabled="true" style="float:right">新增課程</a>

			<div class="table-responsive" id="table1">
				<table class="table table-sm table-hover ">
					<thead>
						<tr class="table-dark">
							<th scope="col">課程類型</th>
							<th scope="col">課程名稱</th>
							<th scope="col">授課地點</th>
							<th scope="col">授課方式</th>
							<th scope="col">授課時間</th>
							<th scope="col">課程狀態</th>
							<th scope="col">上次更新時間</th>
							<th scope="col">報名截止日期</th>
							<th scope="col">修改</th>
							<th scope="col">上下架</th>
						</tr>
					</thead>
					<%@ include file="page1.file"%>
					<tbody>
						<c:forEach var="courseVO" items="${list}" begin="<%=pageIndex%>"
							end="<%=pageIndex+rowsPerPage-1%>">
							<tr class="table-info">
								<td scope="row">${course_kindSvc.getCourse_kind(courseVO.cour_type_no).cour_type}</td>
								<td>${courseVO.cour_name}</td>
								<td>${courseVO.cour_addr}</td>

								<td>${(courseVO.cour_mode==1?'網路':(courseVO.cour_mode==2?'面授':'Both'))}</td>

								<td><fmt:timeZone value="GMT+8:00">
										<fmt:formatDate value="${courseVO.cour_date}" type="both"
											dateStyle="short" timeStyle="short" />
									</fmt:timeZone></td>

								<td>${(courseVO.cour_status==1?'上架':(courseVO.cour_status==2?'下架':(courseVO.cour_status==3?'已被預約':'已結案')))}</td>
								<td><fmt:timeZone value="GMT+8:00">
										<fmt:formatDate value="${courseVO.cour_update}" type="both"
											dateStyle="short" timeStyle="short" />
									</fmt:timeZone></td>
								<td><fmt:timeZone value="GMT+8:00">
										<fmt:formatDate value="${courseVO.cour_deadline}" type="both"
											dateStyle="short" timeStyle="short" />
									</fmt:timeZone></td>
								<td>
									<FORM METHOD="post"
										ACTION="<%=request.getContextPath()%>/course/course.do"
										style="margin-bottom: 0px;">
										<button type="submit"class="btn btn-outline-success">修改</button> <input type="hidden"
											name="cour_no" value="${courseVO.cour_no}"> <input
											type="hidden" name="coa_no" value="${courseVO.coa_no}">
										<input type="hidden" name="requestURL"
											value="<%=request.getServletPath()%>">
										<!--送出本網頁的路徑給Controller-->
										<input type="hidden" name="whichPage" value="<%=whichPage%>">
										<!--送出當前是第幾頁給Controller-->
										<input type="hidden" name="action" value="getOne_For_Display">
									</FORM>
								</td>
								<td>
									<FORM METHOD="post"
										ACTION="<%=request.getContextPath()%>/course/course.do"
										style="margin-bottom: 0px;">
										<button type="submit" class="btn btn-outline-danger"
											<c:if test="${courseVO.cour_status==3}">
							disabled
						</c:if>>上下架</button>
										<input type="hidden" name="cour_no"
											value="${courseVO.cour_no}"> <input type="hidden"
											name="requestURL" value="<%=request.getServletPath()%>">
										<!--送出本網頁的路徑給Controller-->
										<input type="hidden" name="whichPage" value="<%=whichPage%>">
										<!--送出當前是第幾頁給Controller-->
										<input type="hidden" name="action" value="updown">
									</FORM>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<%@ include file="page2.file"%>
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

	<script>
		//javascript放這裡
	</script>
</body>
</html>