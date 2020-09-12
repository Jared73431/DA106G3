<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.posture.model.*"%>

<%
	PostureVO postureVO = (PostureVO) request.getAttribute("postureVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>紀錄新增 - addPosture.jsp</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/select2/dist/js/select2.min.js"></script>

<link rel="stylesheet" href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>
  

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
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 0px solid #CCCCFF;
}

th, td {
	padding: 1px;
}
</style>

</head>
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td>
				<h3>紀錄新增 - adPosture.jsp</h3>
			</td>
			<td>
				<h4>
					<a href="<%=request.getContextPath()%>/front-end/posture/select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>資料新增:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/posture/posture.do" name="form1">
		<table>
			<tr>
				<td>會員編號:</td>
				<td><input type="text" name="member_no"
					value="<%=(postureVO == null) ? "" : postureVO.getMember_no()%>"/>
				</td>
			</tr>

			<tr>
				<td>日期:</td>
				<td><input type="date" name="record_date" 
				value="<%=(postureVO == null) ? "" : postureVO.getRecord_date()%>"/></td>
					
			</tr>

			<tr>
				<td>體重:</td>
				<td><input type="text" name="weight"
					value="<%=(postureVO == null) ? "" : postureVO.getWeight()%>" /></td>
			</tr>
			<jsp:useBean id="membersSvc" class="com.members.model.MembersService" />
			
			<tr>
				<td>體脂肪:</td>
				<td><input type="text" name="bodyfat"
					value="<%=(postureVO == null) ? "" : postureVO.getBodyfat()%>" />
				</td>

			</tr>
			<tr>
				<td>基礎代謝率:</td>
				<td><input type="text" name="bmr"
					value="<%=(postureVO == null) ? "" : postureVO.getBmr()%>" /></td>

			</tr>
			<tr>
				<td>BMI:</td>
				<td><input type="text" name="bmi"
					value="<%=(postureVO == null) ? "" : postureVO.getBmi()%>" />
			<input type="hidden" name="action" value="what_bmi"> 
			<input type="submit" value="查詢">
			</tr>
			
			<tr>
				<td>剩餘熱量</td>
				<td><input type="text" name="remain_cal"
					value="<%=(postureVO == null) ? "" : postureVO.getRemain_cal()%>" />
				</td>

			</tr>
		</table>
		<br> <input type="hidden" name="action" value="insert"> <input
			type="submit" value="送出新增">
	</FORM>
	
	<c:if test="${mybmi != null}">

<div class="modal fade" id="basicModal" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">				
			<div class="modal-header">               
                <h3 class="modal-title" id="myModalLabel">新增食物</h3>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
			
			<div class="modal-body">
               <jsp:include page="/front-end/posture/bmiBmr.jsp" />
			</div>

		</div>
	</div>
</div>

	 <script>
    		 $("#basicModal").modal({show: true});
        </script>
	
	</c:if>
</body>