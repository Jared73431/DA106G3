<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.experience.model.*"%>
<%@ page import="java.util.*"%>

<%

  ExperienceVO expVO = (ExperienceVO) request.getAttribute("expVO"); //EmpServlet.java (Concroller) �s�Jreq��empVO���� (�]�A�������X��empVO, �]�]�A��J��ƿ��~�ɪ�empVO����)
%>
<%
	Map<String, String> map = new LinkedHashMap<String, String>();
	map.put("1", "�C�]");	map.put("2", "����"); map.put("3", "����");
	map.put("4", "�|��");  map.put("5", "��A��"); 
	pageContext.setAttribute("map", map);
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
<title>�߱o�ק�</title>
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
			<h4>�߱o�ק�</a>
			<input type="button" class="btn btn-dark" onclick="location.href='<%=request.getContextPath() %>/front-end/experience/listAllExperience.jsp" value="��^"/>			
			</h4>
		</div>
	</div>
</div>

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
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
		<th>���~�Ϥ�:</th>
		<td><input type="FILE" name="exper_picture" class="upload" size="45"/></td> <!-- �`�Nclass�Oupload -->
	</tr>
	<tr>
		<td>�߱o�s��:<font color=red><b>*</b></font>
		<%=expVO.getExper_no()%></td>
	</tr>
	
	<tr>
		<td>�q�����O:<font color=red><b>*</b></font>
		<select size="1" name="cate_no">
			<c:forEach var="cate_no" items="${map}">
					<option value="${cate_no.key}"  ${(cate_no.key == expVO.cate_no) ? 'selected':'' } >${cate_no.value}
				</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td>���e:
		<textarea name="exper_context"><%= expVO.getExper_context() %></textarea>
                <script>
                CKEDITOR.replace('exper_context', {
    	            language : 'zh-tw',				//�]�w�y��
    	            height: 400,					
    	            width: 750,
    	            uiColor : '#CCCCFF',
    	            resize_enabled : false,			//�קK�ϥΪ̩�ԥ~��
      	        });
                </script>
                </td>
	</tr>
	<tr>
		<td>���D:
		<input type="TEXT" name="exper_title" size="45"	value="<%=expVO.getExper_title()%>" /></td>
	</tr>
	
	
	<tr>
	<div>
		<img class="preview" style="max-width: 150px; max-height: 150px;" src="<%=request.getContextPath()%>/experReader?exper_no=${expVO.exper_no}">
		<div class="size"></div>                                                
   </div>
	</tr>
</table>
</div>
</div>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="member_no" value="<%=membersVO.getMember_no()%>">
<input type="hidden" name="exper_no" value="<%=expVO.getExper_no()%>">
<input type="submit" value="�e�X�ק�"></FORM>
</body>
<%@ include file="/front-end/homepage/footer.file"%>
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
                $('.size').text("�ɮפj�p�G" + KB + " KB");
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