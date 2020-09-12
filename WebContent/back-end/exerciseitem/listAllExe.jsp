<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.exerciseitem.model.*"%>

<%
String feature = "F0003";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>
<%
    ExerciseItemService exerciseSvc = new ExerciseItemService();
    List<ExerciseItemVO> list = exerciseSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<html>
<head>
<title>運動項目資料</title>

<style>

</style>

</head>

  	<div class="container">
	  	<div class="row" id="top">
			<div class="col-4 offset-4">
				<%@ include file="page1.file" %>
			</div>
			<div class="col-1 offset-3">
			<a href='<%=request.getContextPath()%>/front-end/exerciseitem/addExe.jsp'>新增</a>
			</div>
		</div>
	
 		 <table class="table">
    		<thead class="thead-light">
		      <tr>
				<th>運動編號</th>
				<th>運動項目</th>
				<th>每公斤每小時消耗熱量</th>	
				<th>修改</th>
				<th>刪除</th>
		      </tr>
		    </thead>
    		<tbody>    
				<c:forEach var="exerciseitemVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
					
					<tr ${(exerciseitemVO.exe_no==param.exe_no)? 'bgcolor=#CCCFF':''}>
						<td>${exerciseitemVO.exe_no}</td>
						<td>${exerciseitemVO.exe_item}</td>
						<td>${exerciseitemVO.exe_cal}</td>
						
						<td>
						  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/exerciseitem/exerciseitem.do" style="margin-bottom: 0px;">
						     <input type="submit" value="修改">
						     <input type="hidden" name="exe_no"  value="${exerciseitemVO.exe_no}">
						     <input type="hidden" name="action"	value="getOne_For_Update">
						     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
			     			<input type="hidden" name="whichPage"	value="<%=whichPage%>">
						   </FORM>
						</td>
						<td>
						  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/exerciseitem/exerciseitem.do" style="margin-bottom: 0px;">
						     <input type="submit" value="刪除">
						     <input type="hidden" name="exe_no"  value="${exerciseitemVO.exe_no}">
						     <input type="hidden" name="action" value="delete">
						     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
			     			<input type="hidden" name="whichPage"	value="<%=whichPage%>">
						  </FORM>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<center><%@ include file="page2.file" %></center>
	</div>
 </div>
 

 

</body>
</html>