<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.members.model.*"%>
<%@ page import="com.groupgo.model.*"%>
<%@ page import="com.followmaster.model.*"%>
<%@ page import="com.mygroup.model.*"%>

<% 

	String member_no = (String)session.getAttribute("member_no");
	MembersService membersSvc = new MembersService();
	pageContext.setAttribute("membersSvc",membersSvc); 
	MembersVO membersVO = membersSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO",membersVO); 

	String groupgo_id = request.getParameter("groupgo_id");	
	GroupgoService groupgoSvc = new GroupgoService();
	GroupgoVO groupgoVO = groupgoSvc.getOneGroup(groupgo_id);
	pageContext.setAttribute("groupgoVO",groupgoVO);
	
	FollowMasterService followsvc = new FollowMasterService();
	List<FollowMasterVO> master = followsvc.getMaster(member_no);

	for(FollowMasterVO myMaster : master){
		if(myMaster.getMaster_no().equals(groupgoVO.getMaster_id())){
			pageContext.setAttribute("hasMaster","true");
			break;
		}else{
			pageContext.setAttribute("hasMaster","false");
		}
	}
	
	String path = request.getContextPath();
	pageContext.setAttribute("path",path);
%>

<html>
<head>
<meta charset="UTF-8">
<title>揪團資料</title>
 <link rel="stylesheet" href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/lobibox/dist/css/lobibox.css" />
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<style>


img#groupgo_pic{
min-width:35rem;
max-width:35rem;
}

#map { 
height: 22rem;
width: 40rem; 
 }


div#myGroup,div#addGroupgo,div#groupgoIndex{
margin-top:1.1rem;
max-width:7rem;
padding-left: 0.6rem;
padding-top:0.3rem;
height: 2.8rem;
background-color:#E0DBDB;
display:block;
}


p#myGroupText,p#addGroupgoText,p#groupText{
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

div#oneGroup{
margin-bottom:5rem;
padding:1rem;
border:dashed 8px #E0DBDB;
}

div.row{
margin-top:1rem;
margin-bottom:2rem;
}
table#groupdetail{
min-width:30rem;
}
img#joinbtn{
max-width:10rem;
}
img#reportbtn{
max-width:5rem;
}

th{
min-width:7rem;
}

td#content{
max-width:40rem;
}

img{
max-width:40rem;
}

div#listall{
min-height:85vh;
}

div#footer{
clear:both;
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
	
		<div id="myGroup">	
			<a href="<%=request.getContextPath()%>/mygroup/mygroup.do?action=getMemberAttend"><p id="myGroupText">管理揪團</p></a>
		</div>
	</div>


	<div class="container" id="oneGroup">
		<div class="row justify-content-center">
				<img src="<%=request.getContextPath() %>/showgroupgo.do?groupgo_id=${groupgoVO.groupgo_id}" id="groupgo_pic">			
		</div>
		<div class="row justify-content-center">
			<div class="col-8">
				<table id="groupdetail">
					<tr><th>揪團編號:</th><td>${groupgoVO.groupgo_id}</td></tr>
					<tr><th>揪團名稱:</th><td>${groupgoVO.group_name}</td></tr>
					<tr><th>團主:</th><td>${membersSvc.getOneMembers(groupgoVO.master_id).known}			
					<img id="heart" 
					${(hasMaster=="true")?'src="" title="取消追蹤"':'src="" title="加入追蹤"' }>				
					</td></tr>
					<tr><th>活動日期:</th><td><fmt:formatDate value="${groupgoVO.group_date}" pattern="yyyy-MM-dd HH:mm"/></td></tr>
					<tr><th>活動地點:</th><td>${groupgoVO.place}</td></tr>
					<tr><th>活動狀態:</th><td>${groupgoStatus[groupgoVO.group_status]}</td></tr>
					<tr><th>活動人數:</th><td>目前 ${groupgoVO.current_peo}人 /  預計 ${groupgoVO.people_num}人</td></tr>
					<tr><th>預算:</th><td>${groupgoVO.budget}</td></tr>
					<tr><th>報名截止日:</th><td><fmt:formatDate value="${groupgoVO.deadline}" pattern="yyyy-MM-dd"/></td></tr>
					<tr><th valign="top">內容:</th><td id="content">${groupgoVO.group_content}</td></tr>
				</table>
			</div>
		</div>
		
		<div class="row justify-content-center">
			<div id="map"></div>
			<input type="hidden" value="${groupgoVO.lat}" id="lat">
			<input type="hidden" value="${groupgoVO.lon}" id="lon">
			<input type="hidden" value="${groupgoVO.group_name}" id="group_name">
		</div>
		
		<div class="row justify-content-center">
			<div class="col-3">
				<button type="button" id="join" ${(groupgoVO.group_status != 1)?'disabled':''}><img src="<%=request.getContextPath()%>/picture/join.png" id="joinbtn"></button>
			</div>
			<div class="col-1">
				<button type="button" id="report" ${(groupgoVO.group_status != 3)?'':'disabled'}><img src="<%=request.getContextPath()%>/picture/report.gif" id="reportbtn"></button>
			</div>
		</div>

		

		
	</div>
		
</div>
<%@ include file="/front-end/homepage/footer.file"%>

<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/glDatePicker/jquery.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.css" />
<script src="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/lobibox/dist/js/lobibox.min.js"></script>
<script src="<%=request.getContextPath()%>/js/lobibox/dist/js/notifications.min.js"></script>

<script type="text/javascript">
    
    var map;
    var lat = document.getElementById('lat').value;
    var lon = document.getElementById('lon').value;
    var group_name = document.getElementById('group_name').value;
    var mylocation = {'lat': parseFloat(lat), 'lng': parseFloat(lon)};

    function initMap() {
    	

      map = new google.maps.Map(document.getElementById('map'), {
    	  
        center: mylocation,
        zoom: 18
      });
      
      var marker = new google.maps.Marker({
    	    position: mylocation,
    	    map: map,
    	    title:group_name,
    	    icon:'<%=request.getContextPath()%>/picture/location.png'
    	});
      marker.setMap(map);
    }

</script>
    <script async defer  src="https://maps.googleapis.com/maps/api/js?key=&callback=initMap"> </script>  

 
    


<script>

window.onload=function(){
	if($('#heart').attr('title') == "加入追蹤"){
		 $('#heart').attr("src", "<%=request.getContextPath()%>/picture/heart_gray.png");
	}else {
		  $('#heart').attr("src","<%=request.getContextPath()%>/picture/heart_red.png");
	
}
}
$("#heart").click(function(){
	  if($(this).attr('title') == "加入追蹤"){

		  $(this).attr("title", "取消追蹤");
		  $(this).attr("src", "<%=request.getContextPath()%>/picture/heart_red.png");
		 		  
		  $.ajax({
				type: "POST",
				url: "<%=request.getContextPath()%>/followmaster/followmaster.do",
				data: "action=insert&master_no=${groupgoVO.master_id}&member_no=${member_no}",
				success: function(data){
					swal({
							type: 'success',
						  title: '追蹤',
						  text: '成功追蹤團主${groupgoVO.master_id}',

						})
					
				}
		   });
	  }else {
		  $(this).attr("src","<%=request.getContextPath()%>/picture/heart_gray.png");
		  $(this).attr("title","加入追蹤");
		   $.ajax({
				type: "POST",
				url: "<%=request.getContextPath()%>/followmaster/followmaster.do",
				data: "action=delete&master_no=${groupgoVO.master_id}&member_no=${member_no}",
				success: function(data){	
					swal({
						  title: '取消追蹤',
						  text: '嗚嗚~再按一下就可以再追蹤囉!',
// 						  type: 'error',
<%-- 						  imageUrl: '<%=request.getContextPath()%>/picture/heart_red.png', --%>
// 						  imageWidth: 300,
// 						  imageHeight: 150,
						})
				}
		  });
	  }
})

$("#join").click(function(){
		  $.ajax({
				type: "POST",
				url: "<%=request.getContextPath()%>/mygroup/mygroup.do",
				data: "action=insert&groupgo_id=${groupgoVO.groupgo_id}&member_no=${member_no}",
				datatype : "json",
				success: function(data){
					data = JSON.parse(data);
					
					if (data.err == undefined || data.err == ''){
						swal({
							type: 'success',
						  title: '加入',
						  text: '成功加入 : ${groupgoVO.group_name}'+
						  		'等待 <fmt:formatDate value="${groupgoVO.deadline}" pattern="yyyy-MM-dd"/> 時揪團確認喔',
						})
					}else{
						swal({
							type: 'error',
						  title: '失敗',
						  html: data.err+'~不能加入囉',

						})
					}
			
				}
		   });
})

$('#report').click(function(){
		swal({
			title: '檢舉揪團',
			html:
				'<form>' +
  			  '<div class="form-group">' +
  			    '<label for="揪團名稱" class="pull-left">揪團名稱：</label>' +
  			    '<input type="text" class="form-control" id="groupgo_id" placeholder="groupgo_id" value="${groupgoVO.group_name}" disabled>' +
  			  '</div>' +
  			  '<div class="form-group">' +
  			    '<label for="member_no" class="pull-left">檢舉人：</label>' +
  			    '<input type="text" class="form-control" id="member_no" placeholder="member_no" value="${member_no}" disabled>' +
  			  '</div>' +
  			  '<div class="form-group">' +
  			    '<label for="report_content" class="pull-left">檢舉內容：</label>' +
  			    '<textarea class="form-control" id="report_content" placeholder="檢舉內容"></textarea>' +
  			  '</div></form>',	
			
			type: "warning",
			showCancelButton: true,
			preConfirm: function () {
            return new Promise(function (resolve, reject) {          	
                var data = {};
                data.action = 'insert';
                data.groupgo_id = '${groupgoVO.groupgo_id}';
                data.member_no = '${member_no}';
                data.report_content = $('#report_content').val();
               
                if (data.report_content == "") {
        			$('#report_content').focus();
        			reject('請輸入內容！');
        		} else resolve(data);
            })
        },
        onOpen: function () {
            $('#report_content').focus();
        },
		}).then(function (data) {
			if (data) {				
		    		insertData(data, function(result){
		    			
		    			if(result.success === 'Y')
		    				swal({
		    		    		type: 'success',
		    		    		title: '檢舉成功',
		    		   		html: 
		    		   			'<b>等小編會盡快處理的!!!</b>',
		    		    }).catch(swal.noop);
		    			
		    		}, function(result){
		    			swal({
	    		    		type: 'error',
	    		    		title: '檢舉過了啦',
	    		   		html: '<b>耐心等待小編處理嘛</b> !!!'
		    			}).catch(swal.noop);
		    		});
			}
		}).catch(swal.noop);
		
});

function insertData(data, succ_callback, fail_callback){
	$.ajax({
		 type: "GET",
		 url: "<%=request.getContextPath()%>/groupreport/groupreport.do",
		 data: data,
		 dataType: "json",
		 success: succ_callback,
         error: fail_callback
     });
}

</script>
</body>
</html>