<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.members.model.*"%>
<%@ page import="com.groupgo.model.*"%>
<%@ page import="com.followmaster.model.*"%>
<%@ page import="com.mygroup.model.*"%>

<% String member_no = (String)session.getAttribute("member_no");
	MembersService membersSvc = new MembersService();
	MembersVO membersVO = membersSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO",membersVO); 
	
	List<GroupgoVO> list = (List<GroupgoVO>) request.getAttribute("list");
	int random = (int)(Math.random() * list.size());
	GroupgoVO groupgoVO = (GroupgoVO)list.get(random);
	pageContext.setAttribute("groupgoVO", groupgoVO);
	
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
%>

<html>
<head>
<meta charset="UTF-8">
<title>揪團資料</title>
 <link rel="stylesheet" href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
<style>

 div#right,div#left{ 
 display:inline-block; 
 margin:50px; 
 } 

div#left{
float:left;
}
div#right{
float:right;
}
div#footer{
clear:both;
}


div#groupgo_pic{
width:40rem;

}

img#groupgo_pic{
width:100%;
}
#map { 
height: 25rem;
width: 45rem; 
 }
       
div#match:hover{
cursor: pointer;
z-index:100;
}

div#myGroup,div#match,div#addGroupgo,div#groupgoIndex{

margin-top:1.2rem;
border: solid 3px green;
max-width:7rem;
padding-left: 0.3rem;
height: 3rem;
background-color:#B3DFBE;
display:block;

}

p#myGroupText,p#addGroupgoText,p#matchText,p#groupText{
font-size:1.3rem;
color:green;
margin-left:0.4rem;
margin-top:0.2rem;
}


div#leftbar{
width:8rem;
display:inline-block;
float:left;
position:relative;
top:8%;
left:6rem;
}
</style>



</head>
<body>
<%@ include file="/front-end/homepage/nav.file"%>
<div id="listall">

<div id="leftbar">
<div id="groupgoIndex">	
       		<a href="<%=request.getContextPath()%>/front-end/groupgo/listAllGroupgo_v2.jsp"><p id="groupText">揪團首頁</p></a>
</div>

<div id="addGroupgo">		
       		<a href="<%=request.getContextPath()%>/front-end/groupgo/addGroupgo.jsp"><p id="addGroupgoText">新增揪團</p></a>
</div>

<div id="myGroup">	
       		<a href="<%=request.getContextPath()%>/mygroup/mygroup.do?action=getMemberAttend&member_no=M000001"><p id="myGroupText">管理揪團</p></a>
</div>


</div>


	<div id="left">
		<div id="groupgo_pic">
		<img src="<%=request.getContextPath() %>/showgroupgo.do?groupgo_id=${groupgoVO.groupgo_id}" id="groupgo_pic">
	 	</div> 

			<table>
				<tr><th>揪團編號:</th><td>${groupgoVO.groupgo_id}</td></tr>
				<tr><th>揪團名稱:</th><td>${groupgoVO.group_name}</td></tr>
				<tr><th>團主編號:</th><td>${groupgoVO.master_id}				
				<img id="heart" 
				${(hasMaster=="true")?'src="/DA106G3/picture/heart_red.png" title="取消追蹤"':'src="/DA106G3/picture/heart_gray.png" title="加入追蹤"' }>				
				</td></tr>
				<tr><th>活動日期:</th><td><fmt:formatDate value="${groupgoVO.group_date}" pattern="yyyy-MM-dd HH:mm"/></td></tr>
				<tr><th>活動地點:</th><td>${groupgoVO.place}</td></tr>
				<tr><th>活動狀態:</th><td>${groupgoStatus[groupgoVO.group_status]}</td></tr>
				<tr><th>活動預計人數:</th><td>${groupgoVO.people_num}</td></tr>
				<tr><th>預算:</th><td>${groupgoVO.budget}</td></tr>
				<tr><th>報名截止日:</th><td><fmt:formatDate value="${groupgoVO.deadline}" pattern="yyyy-MM-dd"/></td></tr>
			</table>


	</div>
	<div id="right">
	<input type="submit" id="join" value="加入揪團">
	<input type="submit" id="report" value="檢舉揪團">
	<br>
	<br>
	<br>
		內容介紹:<br>
		<div id="content">${groupgoVO.group_content}</div>
	<div id="map"></div>
	<input type="hidden" value="${groupgoVO.lat}" id="lat">
	<input type="hidden" value="${groupgoVO.lon}" id="lon">
	<input type="hidden" value="${groupgoVO.group_name}" id="group_name">
	
	

	</div>
	
</div>
<%@ include file="/front-end/homepage/footer.file"%>

<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/glDatePicker/jquery.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.css" />
<script src="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.js" type="text/javascript"></script>
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
    <script async defer  src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDUoJlxGp3LVUhApf8FUlctkjveHiTckXY&callback=initMapa"> </script>  

 
    


<script>
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
						  title: '嗚嗚取消追蹤',
						  text: '再按一下就可以再追蹤囉',
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
						  html: data.err+'</br>不能加入囉',

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