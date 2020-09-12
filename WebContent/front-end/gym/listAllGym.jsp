<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.gym.model.*"%>
<%
	GymService gymSvc = new GymService();
    List<GymVO> list = gymSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" http-equiv="Access-Control-Allow-Origin" content="*" />
<title>健身中心資料 - listAllGym.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath() %>/js/vendors/jquery/jquery-3.5.0.min.js"></script>
<script src="<%=request.getContextPath() %>/js/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath() %>/js/vendors/bootstrap/js/bootstrap.min.js"></script>
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<script async defer  src="https://maps.googleapis.com/maps/api/js?key="> </script>  
<style>
  #table-1 {
	background-color: #FFF0AC;
    text-align: center;
  }
  table {
	border: 2px solid black;
  }
  #map { 
	height: 32rem;
	width: 44rem; 
  }
  .table td{
	vertical-align: middle;
	font-size:22px;
  }
  .table thead th{
  	border-bottom: 2px solid black;
  	font-size:28px;
  }
  .table thead th:nth-child(3),.table thead th:nth-child(4),
  .table tbody td:nth-child(3),.table tbody td:nth-child(4){
  	text-align:center;
  }
  
</style>
</head>
<body bgcolor='white' onload="connect();" onunload="disconnect();" style='background-color:#FDFFFF'>
<!-- 套用首頁標頭 -->
<%@ include file="/front-end/homepage/nav.file"%>
<!-- 套用首頁標頭 -->
<div id="listall" style="margin-bottom:20px;">
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
	
	<div class="row justify-content-center">
		<table class="table col-md-8" id="table-1">
			<tr><td>
					 <b><i style="vertical-align: -webkit-baseline-middle;font-size:32px;">
					 	國 民 運 動 中 心
					 </i></b>
			</td></tr>
		</table>
	</div>
	
	<div class="row justify-content-center">				
		<table class="table col-md-8 table-hover">
			<thead>
			<tr class="table-info">
				<th>場館名稱</th>
				<th>場館地址</th>
				<th>場館人數</th>
				<th>位置詳情</th>		
			</tr>
			</thead>
			<tbody style='background-color:#FDFFFF'>
			<c:forEach var="gymVO" items="${list}">					
				<tr>
					<td><a href="${gymVO.gym_site}" target="_blank" style="color:#2AAAAE; font-weight: bold;">${gymVO.gym_name}</a></td>
					<td>${gymVO.gym_address}</td>
					<td class="gym"><i><b id="${gymVO.gym_no}A" style="color:#2AAAAE; font-size:28px;"></b><b>人 </b></i><i> 容留 <b id="${gymVO.gym_no}" style="font-size:28px;"></b>人</i></td>
					<td><button class="btn btn-outline-info btn-lg" name="gym" value="${gymVO.gym_no}" lat="${gymVO.gym_lat}" lon="${gymVO.gym_lon}" gym="${gymVO.gym_name}" style="background-color:#ECECFF; font-size:28px;font-weight:bold;">詳情</button></td>				
				</tr>
			</c:forEach>
			</tbody>
		</table>		
	</div>
	
	<div class="modal" id="basicModal" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
	  <div class="modal-dialog modal-xl" role="document">
	    <div class="modal-content" style='background-color:#FDFFFF'>
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	        <h5 class="modal-title">位置詳情</h5>
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body" style="textalign:center">
	      <!-- ==========================以下為GoogleMap內容================================= -->
			<div class="row justify-content-center">
				<div id="map"></div>
			</div>	
	      <!-- ==========================以上為GoogleMap內容================================= -->	      
	      </div>
	      
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">結束瀏覽</button>
	      </div>
	    </div>
	  </div>
	</div>			
</div>
<!-- 首頁註腳 -->
<%@ include file="/front-end/homepage/footer.file"%>	
<!-- 首頁註腳 -->	
</body>
<script>

//for websocket接資料
var MyPoint = "/echo2";
var host = window.location.host;
var path = window.location.pathname;
var webCtx = path.substring(0, path.indexOf('/', 1));
var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
var webSocket;

function connect() {
	// create a websocket
	webSocket = new WebSocket(endPointURL);

	webSocket.onopen = function(event) {
		console.log("WebSocket Connected");
	};

	webSocket.onmessage = function(event) {
		var Arr = JSON.parse(event.data);
		console.log(Arr);
		// 把抓到的值用JQuery送到指定位置
		for (i=0 ;i<Arr.length;i++){
			var gym_no = Arr[i].gym_no;
			var gym = Arr[i].gym_data;
			// 注意遇到JSONArray還要在解析一次
			var gym_data = JSON.parse(gym);
			// 跟EL不同JavaScript要先把字串拼湊，再放進Jquery語法內
				var tag1 = "#"+gym_no+"A";
			var tag2 = "#"+gym_no;
			// 把值丟到指定的ID位置
			$(tag1).html(gym_data[0]);
			$(tag2).html(gym_data[1]);
		}
	};

	webSocket.onclose = function(event) {
		console.log("WebSocket Disconnected");
	};
}

function disconnect() {
	webSocket.close();
}
</script>

<script type="text/javascript">

	// 展示遮蔽
// var lat, lng , mylocation;
// //先確認使用者裝置能不能抓地點
// if(navigator.geolocation) {

//   // 使用者不提供權限，或是發生其它錯誤
//   function error() {
//     alert('無法取得你的位置');
//   }

//   // 使用者允許抓目前位置，回傳經緯度
//   function success(position) {
//     lat = position.coords.latitude;
//     console.log(lat);
//     lng = position.coords.longitude;
//     console.log(lng);
//     mylocation = {'lat': parseFloat(lat), 'lng': parseFloat(lng)};
//     console.log(mylocation);
//   }

//   // 跟使用者拿所在位置的權限
//   navigator.geolocation.getCurrentPosition(success, error);

// } else {
//   alert('Sorry, 你的裝置不支援地理位置功能。')
// }

	var gym_lat,gym_lon,gym_map,gym_name,gym_location;
 	$('[name=gym]').click(function(){
		gym_map=$(this).val();
		gym_name=$(this).attr("gym_name");
		gym_lat = $(this).attr("lat");
		gym_lon = $(this).attr("lon");
	    gym_location = {'lat': parseFloat(gym_lat), 'lng': parseFloat(gym_lon)};		
        initMap();
		// 觸發跳窗執行
		$("#basicModal").modal({
			  show: true
		});		
	
	});
		
	function initMap() {
	  map = new google.maps.Map(document.getElementById('map'), {
		  
	    center: gym_location,
	    zoom: 17
	  });
	  
	  var marker = new google.maps.Marker({
		    position: gym_location,
		    map: map,
		    title:gym_name,
		    icon:'<%=request.getContextPath()%>/picture/location.png'
		});
	  marker.setMap(map);

	  //展示遮蔽
// 	  var marker2 = new google.maps.Marker({
// 		  position:mylocation,
// 		  map: map,
// 		  title:"我的位置",
// 	  })
	
	}

</script>

</html>