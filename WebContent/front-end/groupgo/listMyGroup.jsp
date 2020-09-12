<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.members.model.*"%>
<%@ page import="com.groupgo.model.*"%>
<%@ page import="com.mygroup.model.*"%>
<% String member_no = (String)session.getAttribute("member_no");
	MembersService membersSvc = new MembersService();
	MembersVO membersVO = membersSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO",membersVO); 
	pageContext.setAttribute("membersSvc",membersSvc); 
	
	List<MyGroupVO> mygroupList = (List<MyGroupVO>) request.getAttribute("mygroupList");
	List<GroupgoVO> groupgoList = (List<GroupgoVO>) request.getAttribute("groupgoList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理揪團</title>
 <link rel="stylesheet" href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/lobibox/dist/css/lobibox.css" />
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">

<style>


div#addGroupgo,div#groupgoIndex{

margin-top:1.1rem;
max-width:7rem;
padding-left: 0.6rem;
padding-top:0.3rem;
height: 2.8rem;
background-color:#E0DBDB;
display:block;

}


p#addGroupgoText,p#groupText{
font-size:1.2rem;
color:black;
margin-left:0.4rem;
margin-top:0.4rem;
font-weight:bold;
}


div#left{
width:8rem;
display:inline-block;
float:left;
position:relative;
top:10%;
left:3%;
}


td.groupgo_id:hover{
background-color:rgba(154,203,176,0.9);
}

div#groupgo_pic{
width:400px;
height:400px;
padding:10px;
}

div#listallgroup{
margin-bottom:5rem;
}
</style>

</head>
<body>
<%@ include file="/front-end/homepage/nav.file"%>
<div id="listall">


<div id="left">
<div id="groupgoIndex">	
       		<a href="<%=request.getContextPath()%>/front-end/groupgo/listAllGroupgo.jsp"><p id="groupText">揪團首頁</p></a>
</div>

<div id="addGroupgo">		
       		<a href="<%=request.getContextPath()%>/front-end/groupgo/addGroupgo.jsp"><p id="addGroupgoText">新增揪團</p></a>
</div>

</div>
<jsp:useBean id="groupgoSvc" scope="page" class="com.groupgo.model.GroupgoService" />
<div class="container" id="listallgroup">

<h1>參與的揪團</h1>
<table class="table table-striped" id="mygroup">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">編號</th>
      <th scope="col">名稱</th>
      <th scope="col">團主</th>
      <th scope="col">揪團狀態</th>
      <th scope="col">你的狀態</th>
      <th scope="col">評分(1-10)</th>
      <th scope="col">修改</th>
      <th scope="col">取消</th>
      <th scope="col">加入聊天室</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach var="mygroupVO" items="${mygroupList}" varStatus ="varStatus" >
			<th scope="row">${varStatus.count}</th>
			<td class="groupgo_id"><a href="<%=request.getContextPath()%>/groupgo/groupgo.do?action=getOne_For_Display&groupgo_id=${mygroupVO.groupgo_id}">${mygroupVO.groupgo_id}</a></td>
			<td>${groupgoSvc.getOneGroup(mygroupVO.groupgo_id).group_name}</td>
			<td>${membersSvc.getOneMembers(groupgoSvc.getOneGroup(mygroupVO.groupgo_id).master_id).known}</td>
			<td>${groupgoStatus[groupgoSvc.getOneGroup(mygroupVO.groupgo_id).group_status]}</td>
			<td>${myGroupStatus[mygroupVO.mygroup_status]}</td>
			<td><input type="number" name="score" class="score" max="10" min="1" step="1" value="${mygroupVO.score}" ${(mygroupVO.mygroup_status == '3')?'':'disabled' }></td>
		
			<td>			  
			     <input type="submit" value="修改評分" class="updateScore" ${(mygroupVO.mygroup_status == '3')?'':'disabled' }>	
			     <input type="hidden" name="member_no" class="member_no" value="${mygroupVO.member_no}">
			     <input type="hidden" name="groupgo_id" class="groupgo_id" value="${mygroupVO.groupgo_id}">
			     <input type="hidden" name="js_status" value="${mygroupVO.mygroup_status}">
			     <input type="hidden" name="action"	value="updateScore"></FORM>
			</td>
			<td>
			     <input type="submit" value="取消" class="cancelmygroup" ${((mygroupVO.mygroup_status == '1')||(mygroupVO.mygroup_status == '2'))?'':'disabled' } >
			     <input type="hidden" name="groupgo_id" class="groupgo_id" value="${mygroupVO.groupgo_id}">
			     <input type="hidden" name="member_no" class="member_no" value="${mygroupVO.member_no}">
			     <input type="hidden" name="mygroup_status" class="mygroup_status" value="4">
			     <input type="hidden" name="action" value="statusCancel">
			</td>
		
		
			<td>
				<a href="<%=request.getContextPath()%>/front-end/groupchat/GroupChatindex.jsp?groupgoid=${mygroupVO.groupgo_id}">
			     <input type="submit" value="進入聊天室"  >
			    </a>			    
			</td>
	</tr>
	</c:forEach>
  
  </tbody>
</table>



<h1>我創的揪團</h1>
<table class="table table-striped" id="groupgobyme">
  <thead>
    <tr>
    	<th scope="col">#</th>
    	<th scope="col">編號</th>
		<th scope="col">名稱</th>
		<th scope="col">日期</th>
		<th scope="col">狀態</th>
		<th scope="col">報名人數</th>	
		<th scope="col">評價</th>
		<th scope="col">修改</th>
		<th scope="col">取消</th>
		<th scope="col">加入聊天室</th>
      
    </tr>
  </thead>
  <tbody>
  		<c:forEach var="groupgoVO" items="${groupgoList}" varStatus ="varStatus" >

			<tr>
				<th scope="row">${varStatus.count}</th>
				<td class="groupgo_id"><a href="<%=request.getContextPath()%>/groupgo/groupgo.do?action=getOne_For_Display&groupgo_id=${groupgoVO.groupgo_id}">${groupgoVO.groupgo_id}</a></td>
				<td>${groupgoVO.group_name}</td>
				<td><fmt:formatDate value="${groupgoVO.group_date}" pattern="yyyy-MM-dd HH:mm"/></td>
				<td>${groupgoStatus[groupgoVO.group_status]}</td>
				<td>目前${groupgoVO.current_peo}人/ 預計${groupgoVO.people_num}人 </td>
				<td><c:if test="${groupgoVO.group_status==3}"><fmt:formatNumber type="number" value='${groupgoVO.score_sum div groupgoSvc.getOneGroup(groupgoVO.groupgo_id).current_peo}' maxFractionDigits="1" /></c:if>
					<c:if test="${groupgoVO.group_status!=3}">0</c:if></td>
				
	
		
			<td>
			     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/groupgo/groupgo.do" style="margin-bottom: 0px;">
						<input type="submit" value="修改" ${(groupgoVO.group_status == '1')?'':'disabled' }> 
						<input type="hidden" name="groupgo_id" value="${groupgoVO.groupgo_id}"> 
						<input type="hidden" name="action" value="getOne_For_Update">
					</FORM>
			</td>
			<td>
						 
			     <input type="submit" value="取消" class="cancelgroupgo" ${((groupgoVO.group_status == '1')||(groupgoVO.group_status == '2'))?'':'disabled' }>
			     <input type="hidden" class="groupgo_id" name="groupgo_id"  value="${groupgoVO.groupgo_id}">
			</td>
			<td>
				<a href="<%=request.getContextPath()%>/front-end/groupchat/GroupChatindex.jsp?groupgoid=${groupgoVO.groupgo_id}">
			     <input type="submit" value="進入聊天室" >
			    </a>				    
			</td>
		</tr>
	
	</c:forEach>
  
  </tbody>
</table>

</div>


</div>
<%@ include file="/front-end/homepage/footer.file"%>

<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.css" />
<script src="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.js" type="text/javascript"></script>
 <script src="<%=request.getContextPath()%>/js/lobibox/dist/js/lobibox.min.js"></script>
  <script src="<%=request.getContextPath()%>/js/lobibox/dist/js/notifications.min.js"></script>

<script>

swal.setDefaults({
    confirmButtonText: "確定",
    cancelButtonText: "取消"
});

    $("#mygroup").on('click', ".updateScore" ,function () {
    	var member_no = $(this).parent().find('.member_no').val();
    	var groupgo_id = $(this).parent().find('.groupgo_id').val();
    	var score = $(this).parent().parent().find('.score').val();
    
    	$.ajax({
			 type: "POST",
			 url: "<%=request.getContextPath()%>/mygroup/mygroup.do",
			 data: "action=updateScore&member_no="+member_no+"&groupgo_id="+groupgo_id+"&score="+score,
 			 dataType: "json",
			 success: function(result){
				 if(result.success === 'Y')
				 swal("完成!", "更新成功", "success");
				 if(result.success === 'N')
					 swal("更新失敗!", "請填 1-10 ", "error");
			 },
            error: function(dismiss) { // dismiss can be "cancel" | "overlay" | "esc" | "cancel" | "timer"
        		swal("失敗!", "請填數字", "error");
        }
    	
    });
    });
    
    $("#mygroup").on('click', ".cancelmygroup" ,function () {
    	var click = $(this);
    	swal({
            title: "確定取消？",
            html: "按下確定後就退出揪團了,不能重新加入喔!!!!!!!",
            type: "question", // type can be "success", "error", "warning", "info", "question"
            showCancelButton: true,
        	showCloseButton: true,
        }).then(
        	function(result){
        		if(result){
        			let member_no = click.parent().find('.member_no').val();
        	    	let groupgo_id = click.parent().find('.groupgo_id').val();
        	    	let mygroup_status =click.parent().find('.mygroup_status').val();
        	    	$.ajax({
        				 type: "POST",
        				 url: "<%=request.getContextPath()%>/mygroup/mygroup.do",
        				 dataType:"json",
        				 data: {
        					 action:"statusCancel",
        					 member_no:member_no,
        					 groupgo_id:groupgo_id,
        					 mygroup_status:mygroup_status
        				 },
        	 			 dataType: "json",
        				 success: function(data){
        				if(data.success === 'Y'){
        				 swal("完成!", "已取消揪團", "success");
        				 setTimeout("window.location.reload()",1500);
        				}else{
        					swal("失敗!", "sorry", "error");
        					
        					} 
        				 }
        			});
        		}
        		}, function(dismiss){
        			 swal("取消", "繼續享受揪團吧~", "error");       			
        		 }).catch(swal.noop);
    	
        });
        		
        
    
    $("#groupgobyme").on('click', ".cancelgroupgo" ,function () {
    	var click = $(this);
    	swal({
            title: "確定取消？",
            html: "按下確定後就取消揪團了喔!!!!!!!",
            type: "question", // type can be "success", "error", "warning", "info", "question"
            showCancelButton: true,
        	showCloseButton: true,
        }).then(
        	function(result){
        		if(result){
        	    	let groupgo_id = click.parent().find('.groupgo_id').val();
        	    	$.ajax({
        				 type: "POST",
        				 url: "<%=request.getContextPath()%>/groupgo/groupgo.do",
        				 dataType:"json",
        				 data: {
        					 action:"update_status",       					
        					 groupgo_id:groupgo_id,
        					 group_status:"5"
        				 },
        	 			 dataType: "json",
        				 success: function(data){
        				if(data.success === 'Y'){
        				 swal("完成!", "已取消揪團", "success");
        				 setTimeout("window.location.reload()",1500);
        				}else{
        					swal("失敗!", "sorry", "error");
        					} 
        				 }
        			});
        		}
        		}, function(dismiss){
        			 swal("取消", "繼續享受揪團吧~", "error");       		
        		 }).catch(swal.noop);
        });

</script>
</body>
</html>