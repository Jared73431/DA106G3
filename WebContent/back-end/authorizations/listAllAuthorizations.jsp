
<%@page import="com.feature.model.*"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.authorizations.model.*"%>
<%@ page import="com.admins.model.*"%>
<%
	String feature = "F0001";
%>

<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>

<%
	AdminsService adminsSvc = new AdminsService();
	List<AdminsVO> list = adminsSvc.getAll();
	pageContext.setAttribute("list", list);

	AuthorizationsService authorizationsSvc = new AuthorizationsService();
	List<AuthorizationsVO> list2 = authorizationsSvc.getAll();
	pageContext.setAttribute("list2", list2);

	FeatureService featureSvc = new FeatureService();
	List<FeatureVO> list3 = featureSvc.getAll();
	pageContext.setAttribute("list3", list3);
%>


<html>
<head>
<title>所有員工授權資料 - listAllAuthorizations.jsp</title>
<meta name="viewport" content="width=device-width, initial-scale=1">



</head>
<body bgcolor='white'>

<div class="container">

<div class="table-responsive">
  <table class="table table-hover">
   <thead>
   		<tr>
			<th scope="col">員工姓名</th>
			<th scope="col">授權功能</th>

			<th scope="col">修改</th>
			<th scope="col">清除權限</th>
		</tr>
   			<%@ include file="page1.file"%>
   			
   </thead>
   <tbody>
   <c:forEach var="adminsVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">

			<tr>
				<td>${adminsVO.admin_name}</td>

				<td><c:forEach var="authorizationsVO" items="${list2}">
						<c:if test="${(authorizationsVO.admin_no==adminsVO.admin_no)}">
							<c:forEach var="featureVO" items="${list3}">
								<c:if
									test="${(authorizationsVO.feature_no==featureVO.feature_no) }">
									[${featureVO.feature_name}]
								</c:if>
							</c:forEach>
						</c:if>
					</c:forEach></td>


				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/authorizations/authorizations.do"
						style="margin-bottom: 0px;">
						<button class="btn btn-outline-success" type="submit" >修改</button> <input type="hidden"
							name="admin_no" value="${adminsVO.admin_no}"><input
							type="hidden" name="requestURL"
							value="<%=request.getServletPath()%>">
						<!--送出本網頁的路徑給Controller-->
						<input type="hidden" name="whichPage" value="<%=whichPage%>">
						<!--送出當前是第幾頁給Controller--> <input
							type="hidden" name="action" value="getOne_For_Update">
					</FORM>
				</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/authorizations/authorizations.do"
						style="margin-bottom: 0px;">
						<button class="btn btn-outline-danger" type="submit" >刪除</button> <input type="hidden"
							name="admin_no" value="${adminsVO.admin_no}"><input
							type="hidden" name="requestURL"
							value="<%=request.getServletPath()%>">
						<!--送出本網頁的路徑給Controller-->
						<input type="hidden" name="whichPage" value="<%=whichPage%>">
						<!--送出當前是第幾頁給Controller--> <input
							type="hidden" name="action" value="delete">
					</FORM>
				</td>

			</tr>
		</c:forEach>
   </tbody>
  </table>
  <%@ include file="page2.file"%>
</div>

		
</div>

	
<c:if test="${openModal!=null}">

<div class="modal fade" id="basicModal" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
	<div class="modal-dialog modal-xl">
		<div class="modal-content">
				
			<div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title" id="myModalLabel"></h3>
            </div>
			
			<div class="modal-body">
<!-- =========================================以下為原listOneEmp.jsp的內容========================================== -->
               <jsp:include page="update_authorizations_input.jsp" />
<!-- =========================================以上為原listOneEmp.jsp的內容========================================== -->
			</div>
			
			<div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="btn-primary">確認送出</button>
            </div>
		
		</div>
	</div>
</div>

        <script>
    		 $("#basicModal").modal({show: true});
    		 $("#btn-primary").click(function(){
    			 $("#form1").submit();
    		 })

        </script>
 </c:if>
</body>
</html>