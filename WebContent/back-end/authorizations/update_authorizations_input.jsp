<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.authorizations.model.*"%>
<%
	String feature = "F0001";
%>

<%@ include file="/back-end/backstage/authList.file"%>

<%
	@SuppressWarnings(value = { "unchecked" })
	List<AuthorizationsVO> list = (List<AuthorizationsVO>) request.getAttribute("list"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
	String admin_no = (String) request.getAttribute("admin_no");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>員工權限修改 - update_authorizations_input.jsp</title>

</head>
<body bgcolor='white'>

	<jsp:useBean id="adminsSvc" scope="page"
		class="com.admins.model.AdminsService"></jsp:useBean>
	<jsp:useBean id="featureSvc" scope="page"
		class="com.feature.model.FeatureService"></jsp:useBean>
	<div class="container">
		<div class="table-responsive">
			<table class="table">
				<thead>
					<tr>
						<th scope="col">員工</th>
						<th scope="col">授權功能</th>

					</tr>
				</thead>
 <tbody>
				<FORM METHOD="post"
					ACTION="<%=request.getContextPath()%>/authorizations/authorizations.do"
					name="form1" id="form1">
				<tr>
					<td><c:forEach var="adminsVO" items="${adminsSvc.all}">
							<c:if test="${adminsVO.admin_no==admin_no}">
						${adminsVO.admin_name}  
					</c:if>
						</c:forEach></td>

					<td><c:forEach var="featureVO" items="${featureSvc.all }">
							<input type="checkbox" name="feature"
								value="${featureVO.feature_no}"
								<c:forEach var="authorizationsVO" items="${list}">  

							<c:if test="${featureVO.feature_no==authorizationsVO.feature_no}">
									checked="checked"
							</c:if>
						</c:forEach>>${featureVO.feature_name}

					</c:forEach></td>


				</tr>
				 <tbody>
			</table>
			<br> <input type="hidden" name="action" value="update">
			<input type="hidden" name="admin_no" value="${admin_no}"> <input
				type="hidden" name="requestURL"
				value="<%=request.getParameter("requestURL")%>">
			<!--送出本網頁的路徑給Controller-->
			<input type="hidden" name="whichPage"
				value="<%=request.getParameter("whichPage")%>">
			<!--送出當前是第幾頁給Controller-->
			</FORM>
		</div>
	</div>
</body>


</html>