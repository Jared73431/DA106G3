<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.foodinform.model.*"%>
<%@ page import="java.util.*"%>
<%
String feature = "F0009";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>

<%
  FoodInformVO foodinformVO = (FoodInformVO) request.getAttribute("foodinformVO"); 
  String whichPage = (String) request.getAttribute("whichPage");
  //FoodInformServlet.java(Concroller), 存入req的 foodinformVO 物件
%>
<%
	Map<String, String> fi_status = new LinkedHashMap<String, String>();
	fi_status.put("1", "上架");
	fi_status.put("2", "下架");
	pageContext.setAttribute("fi_status", fi_status);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>食訊詳情 - listOneFoodInform.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<style>
  .table th,.table td {
    border: 0px solid #CCCCFF;
  }
  .table td{
    vertical-align:middle;
  }
  .table {
  	margin:0;
  }
</style>

</head>
<body style="background-color:#ECF5FF">

<div class="container-md">
	<div class="row justify-content-center">
		<div class="col-md-4" style="text-align: center;">	
			<h4>食訊詳情<a href="<%=request.getContextPath() %>/back-end/food_inform/listAllFoodInform.jsp?whichPage=<%=whichPage %>>"><img src="<%=request.getContextPath()%>/js/back-end/images/p5.png" style="height:70px;"></a>
			<input type="button" class="btn btn-dark" onclick="location.href='<%=request.getContextPath() %>/back-end//food_inform/listAllFoodInform.jsp?whichPage=<%=whichPage %>'" value="返回"/>			
			</h4>
		</div>
	</div>
</div>
<div class="container-md">
	<div class="row justify-content-center">	
		<table class="table col-md-10">
			<tr  style="background-color:#FFDAC8;">
				<th>食訊編號</th>		
				<th>食訊標題</th>		
				<th>食訊作者</th>		
				<th>食訊日期</th>		
				<th>食訊狀態</th>			
			</tr>
			<tr style="font-size:14px;">
				<td><%=foodinformVO.getFi_no()%></td>
				<td><%=foodinformVO.getFi_title()%></td>
				<td><%=foodinformVO.getFi_author()%></td>
				<td><fmt:formatDate value="<%=foodinformVO.getFi_date()%>" pattern="yyyy-MM-dd HH:mm"/></td>
				<td>
					<c:forEach var="status" items="${fi_status}">
						<c:if test="${(status.key == foodinformVO.fi_status)}">
							${status.value}
						</c:if>
					</c:forEach>
				</td>
			</tr>
		</table>
		<table class="table col-md-10">
			<tr style="background-color:#FFDAC8;">	
				<th>食訊封面</th>	<td></td>
			</tr>
		</table>
		<div class="col-md-6" style="margin:30px auto;">
			<img src="<%=request.getContextPath() %>/PhotoReader?action=food_inform&pk_no=${foodinformVO.fi_no}" class="img-fluid">
		</div>	
		<table class="table col-md-10">	
			<tr style="background-color:#FFDAC8;">
				<th>食訊內容</th><td></td>
			</tr>
		</table>
		<div class="col-md-8" style="margin:30px auto;"><%=foodinformVO.getFi_content()%></div>
	</div>
</div>
</body>
</html>