<%@page import="java.io.BufferedInputStream"%>
<%@page import="com.admins.model.AdminsVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.admins.model.*"%>


<%
	String feature = "F0001";
%>

<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>
<%	

	AdminsService adminSvc = new AdminsService();
	List<AdminsVO> list = adminSvc.getAll();
	pageContext.setAttribute("list", list);
	
	
%>


<html>
<head>
<title>所有員工資料 - listAllEmp.jsp</title>


<style>
.table tbody tr td{
            vertical-align: middle;
        }
</style>

</head>
<body bgcolor='white'>
 

          <div class="container">


<a href="<%=request.getContextPath()%>/back-end/admins/addAdmin.jsp" class="btn btn-primary btn-sm " role="button" aria-disabled="true">新增員工</a>
<div class="table-responsive">
	  <table class="table table-hover">
	  <thead>
		<tr>
			<th scope="col">員工編號</th>
			<th scope="col">員工姓名</th>
			<th scope="col">帳號</th>
			<th scope="col">圖片</th>
			<th scope="col">狀態</th>
			<th scope="col">修改</th>
		</tr>
		</thead>
		<%@ include file="page1.file"%>
		<c:forEach var="adminsVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">
		<tbody>
			<tr>
				<td scope="col">${adminsVO.admin_no}</td>
				<td>${adminsVO.admin_name}</td>
				<td>${adminsVO.admin_account}</td>
				<td><img
					src="<%=request.getContextPath()%>/DBGifReader4Admin?admin_no=${adminsVO.admin_no}"
					width=100px height=100px></td>
				<td <c:if test="${adminsVO.admin_status == 2}">
					style="color:red;"
				</c:if>
				
				>${((adminsVO.admin_status) ==1)?'現職':'離職'}</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/admins/admins.do"
						style="margin-bottom: 0px;">
						<button class="btn btn-outline-success" type="submit" >修改</button><input type="hidden"
							name="admin_no" value="${adminsVO.admin_no}"> <input
							type="hidden" name="requestURL"
							value="<%=request.getServletPath()%>">
						<!--送出本網頁的路徑給Controller-->
						<input type="hidden" name="whichPage" value="<%=whichPage%>">
						<input type="hidden" name="action" value="getOne_For_Update">
					</FORM>
				</td>
				

			</tr>
			</tbody>
		</c:forEach>
	</table>
</div>
	<%@ include file="page2.file"%>
            </div>
  
         
</body>
</html>