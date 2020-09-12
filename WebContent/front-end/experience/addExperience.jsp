<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.experience.model.*"%>

<%
  ExperienceVO expVO = (ExperienceVO) request.getAttribute("expVO");
%>
<%@ page import="com.members.model.*"%>
    
    
<%
 MembersService memberSvc = new MembersService();
 String member_no = (String)session.getAttribute("member_no");
 MembersVO membersVO = memberSvc.getOneMembers(member_no);
 pageContext.setAttribute("membersVO", membersVO);
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>

<title>心得新增 </title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath() %>/js/ckeditor/ckeditor.js"></script>
<script src="<%=request.getContextPath() %>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
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
<%@ include file="/front-end/homepage/nav.file"%>
<div id="listall">
<div class="container-md">
	<div class="row justify-content-center">
		<div class="col-md-4" style="text-align: center;">	
			<h4>增加心得</a>
			<input type="button" class="btn btn-dark" onclick="location.href='<%=request.getContextPath() %>/front-end/experience/listAllExperience.jsp" value="返回"/>			
			</h4>
		</div>
	</div>
</div>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
<div class="container-md">
	<div class="row justify-content-center">
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/experience.do" name="form1" accept="image/gif, image/jpeg, image/png" enctype="multipart/form-data">
<table class="table col-md-12">
<tr>
		<td>封面:</td>
		<td><input type="FILE" name="exper_picture" class="upload" size="45"/>
	</tr>
	
<tr>
		<td>標題:</td>
		<td><input type="TEXT" name="exper_title" size="45"
			 value="<%= (expVO==null)? "100" : expVO.getExper_title()%>" /></td>
	</tr>
	<tr><td>心得</td>
		 <textarea name="exper_context"></textarea>
                <script>
                CKEDITOR.replace('exper_context', {
    	            language : 'zh-tw',				//設定語言
    	            height: 400,					
    	            width: 750,
    	            uiColor : '#CCCCFF',
    	            resize_enabled : false,			//避免使用者拖拉外框
      	        });
                </script>
	</tr>
	<tr>
		<td>類別編號:<font color=red><b>*</b></font></td>
		<td><select size="1" name="cate_no">
			<c:forEach var="cate_no" items="${cate_no}">
					<option value="${cate_no.key}"  ${(cate_no.key == expVO.cate_no) ? 'selected':'' } >${cate_no.value}
				</c:forEach>
		</select></td>
	</tr>

	

		
</table>

<br>
<input type="hidden" name="action" value="insert">
<input type="hidden" name="member_no" value="<%=membersVO.getMember_no()%>">
<input type="submit" value="送出新增"></FORM>
<div class="content">
		<img class="preview" style="max-width: 150px; max-height: 150px;">
		<div class="size"></div>
</div>
<%@ include file="/front-end/homepage/footer.file"%>
</div>
</div>
</body>

<script>
$(function (){
 
    function format_float(num, pos)
    {
        var size = Math.pow(10, pos);
        return Math.round(num * size) / size;
    }
 
    function preview(input) {
 
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            
            reader.onload = function (e) {
                $('.preview').attr('src', e.target.result);
                var KB = format_float(e.total / 1024, 2);
                $('.size').text("檔案大小：" + KB + " KB");
            }
 
            reader.readAsDataURL(input.files[0]);
        }
    }
 
    $("body").on("change", ".upload", function (){
        preview(this);
    })
    
})
</script>

</html>