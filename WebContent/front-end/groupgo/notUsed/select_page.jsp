<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.groupgo.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Groupgo: Home</title>

<style>
  table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script>
$(document).ready(function(){
	 
   
 $("#form1").click(function(){
		
			if(window.navigator.geolocation == undefined){
				alert("can't get location!");
				return;
			}
 			navigator.geolocation.getCurrentPosition(successCallback,errorCallback);
 			 function successCallback(e){
				 
 				var lon = e.coords.longitude;
 				var lat = e.coords.latitude;
 				var mapUrl="../../groupgo/groupgo.do?action=getNear_For_Display&lon="+lon+"&lat="+lat;
				window.location.assign(mapUrl);
 			 }
 			function errorCallback(e){
 				console.log("error");
 			}
 		});
					           
}); 
		
</script>

</head>
<body bgcolor='white'>

<table id="table-1">
   <tr><td><h3>Groupgo: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for Groupgo: Home</p>

<h3>資料查詢:</h3>
	
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li><a href='<%=request.getContextPath()%>/front-end/groupgo/listAllGroupgo.jsp'>List</a> all Groupgo.  <br><br></li>
 
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/groupgo/groupgo.do" >
        <b>輸入揪團編號 (如G000001):</b>
        <input type="text" name="groupgo_id">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>

 <jsp:useBean id="groupgoSvc" scope="page" class="com.groupgo.model.GroupgoService" />
  <jsp:useBean id="membersSvc" scope="page" class="com.members.model.MembersService" />
   
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/groupgo/groupgo.do" >
       <b>選擇揪團名稱:</b>
       <select size="1" name="groupgo_id">
         <c:forEach var="groupgoVO" items="${groupgoSvc.all}" > 
          <option value="${groupgoVO.groupgo_id}">${groupgoVO.group_name}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/groupgo/groupgo.do" >
       <b>選擇團主編號:</b>
       <select size="1" name="master_id">
         <c:forEach var="membersVO" items="${membersSvc.all}" >  
          <option value="${membersVO.member_no}">${membersVO.member_no}
          </option>
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getMaster_For_Display">
       <input type="submit" value="送出">
     </FORM>
  </li>
</ul>
 
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/groupgo/groupgo.do" >
       <b>輸入團主編號:</b>
       <input type="text" name="member_no">
        <b>輸入揪團編號 (如G000001):</b>
        <input type="text" name="groupgo_id">
       <input type="hidden" name="action" value="getMany">
       <input type="submit" value="送出">
     </FORM>
  </li>
</ul>
 
 
 <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/groupgo/groupgo.do" id="form1"> 
        <b>位置:</b>
        <input type="hidden" name="lon" value="" id="lon">
        <input type="hidden" name="lat" value="" id="lat">
        <input type="hidden" name="action" value="getNear_For_Display">
        <input type="submit" value="送出" id="form1" >
     </FORM>
  </li>
  
  

<h3>揪團管理</h3>

<ul>
  <li><a href='<%=request.getContextPath()%>/front-end/groupgo/addGroupgo.jsp'>Add</a> a new Groupgo.</li>
</ul>



</body>
</html>