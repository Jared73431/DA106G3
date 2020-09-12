<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.admins.model.*"%>
<%@ page import="java.util.*"%>
<%
	String feature = "F0001";
%>

<%@ include file="/back-end/backstage/authList.file"%>
<%@ include file="/back-end/backstage/navbar.file"%>
<%
	AdminsService adminSvc = new AdminsService();
	List<AdminsVO> list = adminSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>員工資料新增 - addAdmin.jsp</title>



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
				<c:if test="${not empty errorMsgs}">
				<div id="errorMsgs">
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: black;">${message}</li>
						</c:forEach>
					</ul>
				</div>
			</c:if>
		<div class="container" align="center" style="margin: 7rem 14rem;">

				<FORM METHOD="post"
					ACTION="<%=request.getContextPath()%>/admins/admins.do"
					name="form1" enctype="multipart/form-data">

					<div class="form-group row">
						<div class="col-1"><label for="exampleInputEmail1">帳號</label> </div>
						<div class="col-6">
						<input type="email" class="form-control" id="exampleInputEmail1"
							aria-describedby="emailHelp" placeholder="請使用 G-mail" name="admin_account" value="${param.admin_account}"></div> 
					</div>
					  <div class="form-group row">
    					<div class="col-1"><label for="formGroupExampleInput">員工姓名</label></div>
    					<div class="col-6">
   						 <input type="text" class="form-control" name="admin_name" id="formGroupExampleInput" placeholder="請輸入姓名" value="${param.admin_name}"></div>
  					</div>
  					  <div class="form-group row">
					    <div class="col-1"><label for="exampleFormControlFile1">員工照片</label></div>
					   <div class="col-6"> <input type="file" class="form-control-file" name="admin_photo" id="exampleFormControlFile1">
					  </div></div>
					<img id="pic" src="" width="120rem" height="120rem" style="float:left">
						<br> <input type="hidden" name="admin_status" value='1'>
						<input type="hidden" name="action" value="insert"> <button
							type="submit" class="btn btn-outline-success" style="float:inherit;">送出新增</button>
						
				</FORM>
			</div>
		</div>
</body>

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


</html>