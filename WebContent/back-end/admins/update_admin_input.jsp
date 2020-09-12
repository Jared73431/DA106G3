<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.admins.model.*"%>
<%@ page import="java.util.*"%>
<%
	String feature = "F0001";
%>

<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>
<%
	AdminsVO adminsVO = (AdminsVO) request.getAttribute("adminsVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>員工資料修改 - update_admin_input.jsp</title>

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
	<div id="listall">

	<h3>資料修改:</h3>


	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
<div class="table-responsive">
	<FORM METHOD="post"
		ACTION="<%=request.getContextPath()%>/admins/admins.do" name="form1"
		enctype="multipart/form-data">
		<table class="table">
			<tr>
				<td>員工編號</td>
				<td><%=adminsVO.getAdmin_no()%></td>
			</tr>
			<tr>
				<td>員工姓名:</td>
				<td><input type="TEXT" name="admin_name" size="45"
					value="<%=adminsVO.getAdmin_name()%>" /></td>
			</tr>
			<tr>
				<td>帳號:</td>
				<td><input type="text" name="admin_account" size="45" class="form-control-plaintext"
					value="<%=adminsVO.getAdmin_account()%>" readonly /></td>
			</tr>
			<tr>
				<td>密碼:</td>
				<td><input type="PASSWORD" name="admin_password" size="45"
					value="<%=adminsVO.getAdmin_password()%>" /></td>
			</tr>
			<tr>
				<td></td>
				<td><img id="pic"
					src="<%=request.getContextPath()%>/DBGifReader4Admin?admin_no=${adminsVO.admin_no}"
					width=100px height=100px></td>
			</tr>
			<tr>
				<td>圖片:</td>
				<td><input type="file" name="admin_photo" id="exampleFormControlFile1"/></td>
			</tr>
			<tr>
				<td>狀態:<font color=red><b>*</b></font></td>
				<td><select size="1" name="admin_status">
						<option value="1" ${(adminsVO.admin_status==1)?'selected':'' }>現職

						
						<option value="2" ${(adminsVO.admin_status==2)?'selected':'' }>離職


						
				</select></td>
			</tr>



		</table>
		<br> <input type="hidden" name="action" value="update"> <input
			type="hidden" name="admin_no" value="<%=adminsVO.getAdmin_no()%>">
		<input type="hidden" name="requestURL"
			value="<%=request.getParameter("requestURL")%>">
		<!--送出本網頁的路徑給Controller-->
		<input type="hidden" name="whichPage" value="<%=request.getParameter("whichPage")%>">
		<!--送出當前是第幾頁給Controller-->
		<input type="submit" value="送出修改">
	</FORM>
	</div>
	</div>
	<script>
	$("#exampleFormControlFile1").change(function(){

		  readURL(this);

		});

		 

		function readURL(input){

		  if(input.files && input.files[0]){

		    var reader = new FileReader();

		    reader.onload = function (e) {

		       $("#pic").attr('src', e.target.result);

		    }

		    reader.readAsDataURL(input.files[0]);

		  }

		}
	
	</script>
</body>






</html>