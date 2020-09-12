<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.members.model.*"%>
<%@ page import="com.newsknowledge.model.*"%>
<%@ page import="java.util.*"%>
<% String member_no = (String)session.getAttribute("member_no");
	MembersService membersSvc = new MembersService();
	MembersVO membersVO = membersSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO",membersVO); 
%>
<%
NewsKnowledgeService newsknowledgeSvc = new NewsKnowledgeService();
List<NewsKnowledgeVO> sentence = newsknowledgeSvc.getAll();

int random = (int)(Math.random() * sentence.size());
pageContext.setAttribute("newsKnowledgeVO",sentence.get(random));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>每日記錄</title>
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
<link  href="<%=request.getContextPath()%>/js/glDatePicker/glDatePicker.flatwhite.css" rel="stylesheet" type="text/css">  <!-- 原版預設 -->
<link href="<%=request.getContextPath()%>/js/select2/dist/css/select2.min.css" rel="stylesheet" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/lobibox/dist/css/lobibox.css" />
<style>


#posture_container{
min-width:90%;
margin-bottom:3rem;
}
/* #posture_row{ */
/* margin-left:-15px; */
/* margin-right:-15px; */
/* } */

div#date{
	font-size:1rem;
	color:#6A6868;	
	font-weight: bold;
	display:none;
}


tr#recordTittle{
background-color:#72C680;
}
tr.record, .addfood{
background-color:#B9E8D4;
}
tr.detailTittle{
background-color:#C8C8C8;
}
tr#exeTittle{
background-color:#BCC8E7;
}
tr.exe{
background-color:#D3ECF3;
}
tr#postureTittle{
background-color:#F5CACA;
}
tr.posture{
background-color:#F3DACA;
}
table#food,table#exe,table#posture{
min-width:28rem;
text-align:center;

}


p.text{
color:#6A6868;
margin-top:1rem;
font-size:1.5rem;
}
h3{
margin-top:1rem;
}

h1{
margin-top:1rem;

}

tr{
height:2.5rem;
}
input.posture_input{
max-width:3rem;
}
input#mydate{ 
 width:0px !important;
 margin-top:1rem;
 } 

div#datepick{
top:0px !important; 
left:0px !important;
margin:0px;
margin-top:1rem;

}

#dietpic{
max-width:400px;
height:auto;

}

div.chart{
padding:1rem;
margin:0.5rem;
width: 30rem; 
height: 20rem;
border: 1px solid #E7E5E5;

}

/* #dietpic-td{ */
/*  background-color:#C0E8D7;  */
/* } */
td.diet_no{
 display:none;
}

th.detail{
min-width:8rem;
}

p#chart{
text-align:center;
margin-top:9rem;
color:#E7E5E5;
}

p#sentence{
margin-top:350px;

}

p#topic-text{
font-size:2rem;
color:#60AF65;
margin-top:1rem;
font-weight: bold
}
</style>

</head>
<body onload='connect();'>
<%@ include file="/front-end/homepage/nav.file"%>
<%@ include file="/front-end/notificationlist/notesocket.jsp"%>
<div id="listall">


	<div class="container" id="posture_container">
	
		<div class="row">
			<div class="col-3 col-md-3 col-xl-3">
				<p id="topic-text">每日記錄</p>
	 		</div>
	 		<div class="col-5 col-md-5 col-xl-5">		
				<p class="text">剩餘熱量曲線</p>
			</div>
			<div class="col-4 col-md-4 col-xl-4">
			 	<p class="text">體態紀錄</p>					
	 		</div>
	 	</div>
	 
	  	<div class="row" id="posture_row">
	 		<div class="col-3 col-md-3 col-xl-3" id="posture_col">
	 
				<input gldp-id="mydate" style="width: 350px; height: 30px; visibility: visible;" type="text" id="mydate" name="rec_date"/>
				<div   gldp-el="mydate" style="width: 350px; height:300px; " id="datepick"> </div>
		
				<div id="date">請選擇日期</div>
				
	 			<p id="sentence">讀點小知識:<br><a href="<%=request.getContextPath() %>/news_knowledge.do?action=getOne_Display&news_no=${newsKnowledgeVO.news_no}">${newsKnowledgeVO.news_title}</a></p>
	 		
				
			</div>
	
	
			 <div class="col-5 col-md-5 col-xl-5">		
			 	<div id="container" class="chart"><p id="chart">請選擇日期</p></div>
			 
				

			</div>
					
			
		 	<div class="col-4 col-md-4 col-xl-4">
				<table id='posture'>
					<tr id='postureTittle'><th>體重</th><th>體脂肪</th><th>bmi</th><th>TDEE</th><th>剩餘熱量</th><th colspan='2'>編輯</th></tr>
				</table>
				<p class="text">運動紀錄</p>
				<table id='exe'>
					<tr id='exeTittle'><th>運動名稱</th><th>運動時間</th><th>消耗熱量</th><th>修改</th></tr>
				</table>
				
				<p class="text">飲食紀錄</p>
				<table id='food'>
					<tr id='recordTittle'><th>時段</th><th>總熱量</th><th>詳情</th><th>修改</th></tr>
				</table>
					
			</div>
		</div>
	</div>
</div>
<%@ include file="/front-end/homepage/footer.file"%>

<div class="modal fade" id="exeModal" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true" >
	<div class="modal-dialog modal-lg" style="width:30rem">
		<div class="modal-content">				
			<div class="modal-header">               
                <h3 class="modal-title" id="myModalLabel">新增運動紀錄</h3>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
			
			<div class="modal-body">
               <jsp:include page="/front-end/exerciserecord/addExerciseRecord.jsp" />
			</div>

		</div>
	</div>
</div>

<div class="modal fade" id="foodModal" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">				
			<div class="modal-header">               
                <h3 class="modal-title" id="myModalLabel">新增飲食紀錄</h3>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
			
			<div class="modal-body">
               <jsp:include page="/front-end/dietrecord/addDietRecord.jsp" />
			</div>

		</div>
	</div>
</div>



<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/hightcharts/highcharts.js"></script>
<script src="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/glDatePicker/glDatePicker.mis.js"></script>  
<script src="<%=request.getContextPath()%>/js/glDatePicker/glDatePicker.js"></script>      
<script type="text/javascript" src="<%=request.getContextPath()%>/js/select2/dist/js/select2.min.js"></script>
  <script src="<%=request.getContextPath()%>/js/lobibox/dist/js/lobibox.min.js"></script>
  <script src="<%=request.getContextPath()%>/js/lobibox/dist/js/notifications.min.js"></script>
<script>

$(document).ready(function(){
	$('#mydate').glDatePicker(			
		{
		showAlways : true,
		cssName : 'flatwhite', // 可用 'default' 或  'darkneon' 或  'flatwhite'
		format : 'yyyy-mm-dd', // 預設
		dowOffset : 0, // 預設
		allowMonthSelect : false, // 預設
		allowYearSelect : false, // 預設
		prevArrow : '\u25c4', // 預設
		nextArrow : '\u25ba', // 預設
		dowNames : [ '日', '一', '二', '三', '四', '五', '六' ], //自定
		monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月','七月', '八月', '九月', '十月', '十一月', '十二月' ], //自定		
		selectableYears : [ 2020 ], //可選的年份		
		onClick : function(target, cell, date, data) {
// 		target.val(date.getFullYear() + '-'+ (date.getMonth() + 1) + '-'+ date.getDate());	
		var date = date.getFullYear() + '-'+ (date.getMonth() + 1) + '-'+ date.getDate()		
		$('#date').text(date);	
		if (data != null) {
			alert(data.message + '\n' + date);
			}
		line();
		show(); 
		}
	});	
});

</script>

<jsp:useBean id="dietRecordSvc" class="com.dietrecord.model.DietRecordService" />
<script>
		
	function show(){
 		var  member_no = '${membersVO.member_no}'; 
		var rec_date = $('#date').text();
			
		$.ajax({
			type: "POST",
			url: "<%=request.getContextPath()%>/dietrecord/dietrecord.do",
			data: "action=getAll_jsp&rec_date="+rec_date+"&member_no="+member_no,
			datatype : "json",
			success: function(data){
				var data = JSON.parse(data);
				
				console.log(data);
				var posture = data[0];
				var food = data[1];
				var exe = data[2];
				var period = new Map([[0,'早餐'],[1,'午餐'],[2,'晚餐'],[3,'其他']]);	
				
					$('.posture').remove();
					if (typeof(posture.weight) != 'undefined'){
					$("#postureTittle").after("<tr class='posture'><td>"+posture.weight+"</td><td>"+posture.bodyfat+"</td><td>"+posture.bmi+"</td><td>"+posture.bmr+"</td><td>"+posture.remain_cal+"</td><td><input type='submit'value='修改' id='revisePosture'></td></tr>")
					}else{$("#postureTittle").after("<tr class='posture'><td colspan='6'>沒資料</td><td><button type='button' id='btnAddPosture'>新增</button></td></tr>");
					}
					
					$('#posture').on('click','#revisePosture',function(){
						$('.posture').remove();						
						$("#postureTittle").after("<tr class='posture'><td><input type='text' name='weight' id='weight' class='posture_input' value='"+posture.weight+"'></td><td><input type='text' id='bodyfat' class='posture_input' name='bodyfat' value='"+posture.bodyfat+"'></td><td><input type='text' name='bmi' class='posture_input' id='bmi' value='"+posture.bmi+"'></td>"
								+"<td><input type='text' name='bmr' class='posture_input' id='bmr'value='"+posture.bmr+"'></td><td><input type='text' id='remain_cal'class='posture_input' name='remain_cal' value='"+posture.remain_cal+"'></td>"
								+"<td>"								
								+"<input type='hidden' class='posture_no' value='"+posture.posture_no+"'>"
								+"<input type='hidden' class='member_no' value='"+member_no+"'>"
								+"<input type='hidden' class='record_date' value='" +rec_date+ "'>"
								+"<input type='submit' value='修改'  class='revised'></td>"
								+"<td><input type='submit'value='刪除' class='deletePosture'><input type='hidden' name='posture_no' class='posture_no' value='"+posture.posture_no+"'>"
								+"</td></tr>");
					});
				$('.diet').remove();	
				$('.record').remove();
				$('.detailTittle').remove();
// 				$('.detail').remove();
				$('.addfood').remove();
				if(food.length !=0){
				$.each(food, function(index, element){					
					$("#recordTittle").after("<tr class='record' id='record'><td class='diet_no'>"+element.diet_no+"</td><td>"+period.get(element.eat_period)+"</td><td>"+element.total+"</td><td>"
							+"<input type='submit' value='詳細' class='more'></td><td><a href='<%=request.getContextPath()%>/dietrecord/dietrecord.do?action=getOne_For_Update&diet_no="+element.diet_no+"' target=_blank>修改</td></tr>");
					if(element.photo != 'false'){		
					$("#record").after("<tr class='record'><td colspan='4' id='dietpic-td'><img src='<%=request.getContextPath()%>/showdiet.do?diet_no="+element.diet_no+"' id='dietpic'></td></tr>");
					}
				});	
				  $('.record').last().after("<tr class='addfood'><td colspan='4'><button type='button' id='btnAddFood'>繼續新增</button></td></tr>");
				}else{
					$("#recordTittle").after("<tr class='record'><td colspan='3'>沒資料</td><td><button type='button' id='btnAddFood'>新增資料</button></td></tr>");
				}
				
				$('.exe').remove();
				if(exe.length !=0){
				$.each(exe, function(index, element){					
					$("#exeTittle").after("<tr class='exe'><td>"+element.exe_item+"</td><td>"+element.exe_time+"</td><td>"+element.exe_tcal+"</td><td>"
							+"<input type='submit'value='刪除' class='deleteExe'><input type='hidden' name='exerec_no' class='exerec_no' value='"+element.exerec_no+"'></td></tr>");
				});
					$('.exe').last().after("<tr class='exe'><td colspan='5'><button type='button' class='btnAddExe'>繼續新增</button></td></td></tr>");

					
				}else{
					$("#exeTittle").after("<tr class='exe'><td colspan='2'>沒資料</td><td><td><button type='button' class='btnAddExe'>新增</button></td></tr>");
<%-- 					<a href='<%=request.getContextPath()%>/front-end/exerciserecord/addExerciseRecord.jsp?member_no="+member_no+"&exe_date="+rec_date+"' Target='_blank'>新增資料</a></td></tr>"); --%>
				}
				
				
			}
		
		});
}
	$('#food').on('click','.more',function(){		
		var diet_no =$(this).parent().parent().find('.diet_no').text();	
		
		var click = $(this);
			$.ajax({
				type: "POST",
				url: "<%=request.getContextPath()%>/dietrecord/dietrecord.do",
				data: "action=getfooddetail_jsp&diet_no="+diet_no,
				datatype : "json",
				success: function(data){
					var data = JSON.parse(data);
					let record = click.parent().parent();	
					let diet_records = [];
					$.each(data, function(index, element){
						let diet_record = "<tr class='detail'><input type='hidden' name='food_no' id='food_no' value='"+element.food_no+"'><input type='hidden' name='diet_no' id='diet_no' value='"+element.diet_no+"'>"
 						+"<td id='food_name'>"+element.food_name+"</td><td>"+element.amount+"</td><td>"+element.food_cal+"</td></tr>";
 						
						diet_records.push(diet_record);
 					});
													
					swal({
						title: '詳情',
						html:"<table><tr class='detailTittle'><th class='detail'>食物名稱</th><th class='detail'>份量</th><th class='detail'>熱量</th></tr>"					  			
	 						+diet_records
					  		+"</table>",	
					});
// 					$('#deletediet').click(function(){
								
// 						var diet_no =$(this).parent().parent().find('#diet_no').val();
// 						var food_no =$(this).parent().parent().find('#food_no').val();
								
// 						var click = $(this);
// 							$.ajax({
// 								type: "POST",
<%-- 								url: "<%=request.getContextPath()%>/dietdetail/dietdetail.do", --%>
// 								data: "action=delete&diet_no="+diet_no+"&food_no="+food_no,
// 								datatype : "json",
// 								success: function(data){						
// 									show();
// 								}
// 							});	
// 					});	
				}
						
		});	
	});
	
	

	
	$('#exe').on('click','.deleteExe',function(){
		var exerec_no =$(this).parent().find('.exerec_no').val();
		console.log(exerec_no);
		
		var click = $(this);
					$.ajax({
						type: "POST",
						url: "<%=request.getContextPath()%>/exerciserecord/exerciserecord.do",
						data: "action=delete&exerec_no="+exerec_no,
						datatype : "json",
						success: function(data){
 							show();
						}
					});	
});	

	$('#posture').on('click','.deletePosture',function(){
		event.stopPropagation();
		var posture_no =$(this).parent().find('.posture_no').val();
		
		var click = $(this);
					$.ajax({
						type: "POST",
						url: "<%=request.getContextPath()%>/posture/posture.do",
						data: "action=delete&posture_no="+posture_no,
						datatype : "json",
						success: function(data){
 							show();
						}
					});	
});	
	
	$('#posture').on('click','.revised',function(){
		event.stopPropagation();
		var posture_no =$(this).parent().find('.posture_no').val();
		var member_no =$(this).parent().find('.member_no').val();
		var record_date =$(this).parent().find('.record_date').val();
		var weight =$(this).parent().parent().find('#weight').val();
		var bodyfat =$(this).parent().parent().find('#bodyfat').val();
		var bmi =$(this).parent().parent().find('#bmi').val();
		var bmr =$(this).parent().parent().find('#bmr').val();
		var remain_cal =$(this).parent().parent().find('#remain_cal').val();

		var click = $(this);
					$.ajax({
						type: "POST",
						url: "<%=request.getContextPath()%>/posture/posture.do",
						data: {
							"action" : "update",
							"posture_no" : posture_no,
							"member_no" : member_no,
							"record_date" : record_date,
							"weight" : weight,
							"bodyfat" : bodyfat,
							"bmi" : bmi,
							"bmr" : bmr,
							"remain_cal" : remain_cal
						},
						datatype : "json",
						success: function(data){
							var data = JSON.parse(data);
							if(data.success === 'Y')
								swal("修改完成!","", "success");	   
 							show();
						}
					});	
});	
	
function line(){
	var rec_date = $('#date').text();
	var member_no = '${membersVO.member_no}';
	   $.ajax({
			type: "POST",
			url: "<%=request.getContextPath()%>/posture/posture.do",
			cache: false,
			data: "action=getline&member_no="+member_no+"&record_date="+rec_date,
			datatype : "json",
			success: function(result){ 		
				
 				var data = JSON.parse(result);	
 				console.log(data[0]);
			if(data[0] != false){				
				var remain_cal = data[0];
				var record_date = data[1];				
				getmyline(remain_cal,record_date);
 				}else{
 					$('#container').text("新增多筆資料才能分析喔");
 				}
			},
	   error: function(){
		   alert('error');
		   }		
		});	
}
	
function getmyline(remain_cal,record_date){
		console.log(remain_cal);
		console.log(record_date);
	   var title = {
	      text: ''   
	   };	   
// 	   var subtitle = {
// 	      text: 'Source: runoob.com'
// 	   };	   	   
	   var xAxis = {
	      categories: record_date
	   };	   
	   var yAxis = {
	      title: {
	         text: '剩餘熱量'
	      },
	      plotLines: [{
	         value: 0,
	         width: 1,
	         color: '#808080'
	      }]
	   };   
	   var tooltip = {
	      valueSuffix: 'cal'
	   }
	   var legend = {
	      layout: 'vertical',
	      align: 'right',
	      verticalAlign: 'middle',
	      borderWidth: 0
	   };	 	   
	   var series =  [
	      {
	         name: '${membersVO.member_no}',
	         data: remain_cal
	      }
	   ];
	   var json = {};

	   json.title = title;
	//   json.subtitle = subtitle;
	   json.xAxis = xAxis;
	   json.yAxis = yAxis;
	   json.tooltip = tooltip;
	   json.legend = legend;
	   json.series = series;

	   $('#container').highcharts(json);
	}

	$('#posture').on('click','#btnAddPosture',function(){
		event.stopPropagation();
		var rec_date = $('#date').text();
		swal({
			title: '新增紀錄',
			html:
				'<form>' +
  			  '<div class="form-group">' +
  			    '<label for="record_date" class="pull-left">紀錄時間：</label>' +
  			    '<input type="text" class="form-control" id="record_date" value ='+rec_date+' disabled>' +
  			  '</div>' +
  			  '<div class="form-group">' +
  			    '<label for="weight" class="pull-left">體重：</label>' +
  			    '<input type="number" class="form-control" id="weight" placeholder="weight">' +
  			  '</div>' +
  			  '<div class="form-group">' +
  			    '<label for="bodyfat" class="pull-left">體脂肪：</label>' +
  			    '<input type="number" class="form-control" id="bodyfat" placeholder="bodyfat">' +
  			  '</div>' +
  			'<div class="form-group">' +
			    '<label for="bmr" class="pull-left"> TDEE：</label ><a href="<%=request.getContextPath()%>/front-end/posture/bmiBmr.jsp" target="_blank">甚麼是TDEE</a>' +
			    '<input type="number" class="form-control" id="bmr" placeholder="bmr">' +
			  '</div>' +
			  '<div class="form-group">' +
			    '<label for="bmi" class="pull-left">BMI：</label>' +
			    '<input type="number" class="form-control" id="bmi" placeholder="bmi">' +
			  '</div>' +
  			'</form>',	
			
			type: "warning",
			showCancelButton: true,
			preConfirm: function () {
            return new Promise(function (resolve, reject) {          	
                var data = {};
                data.action = 'insert';
                data.member_no = '${member_no}';
                data.record_date = $('#record_date').val().trim();
                data.weight = $('#weight').val().trim();
                data.bodyfat = $('#bodyfat').val().trim();
                data.bmr = $('#bmr').val().trim();
                data.bmi = $('#bmi').val().trim();               
                if (data.record_date == "") {
        			$('#record_date').focus();
        			reject('請選擇日期！');
        		} else if (!data.weight) {
        			$('#weight').focus();
        			reject('請輸入體重！');

        		} else if (!data.bmr) {
        			$('#bmr').focus();
        			reject('請輸入基礎代謝率！');

        		} else resolve(data);
            })
        },
        onOpen: function () {
            $('#weight').focus();
        },
		}).then(function (data) {
			if (data) {
		    		insertData(data, function(result){
		    			if(result.success === 'Y')
		    				swal({
		    		    		type: 'success',
		    		    		title: '儲存成功',
		    		   		html: 
		    		   			'<b>紀錄時間為：</b>' +  data.record_date + '<br>' +
		    		   			'<b>體重是：</b>' + data.weight + '<br>' +
		    		   			'<b>體脂肪是：</b>' + data.bodyfat +'<br>' +
		    		   			'<b>基礎代謝率是：</b>' +data.bmr + '<br>' +
		    		   			'<b>bmi是：</b>' + data.bmi,
		    		    }).catch(swal.noop);
		    		}, function(result){
		    			swal({
	    		    		type: 'error',
	    		    		title: '儲存失敗',
	    		   		html: '<b>失敗訊息是：</b> AJAX error!!!'
		    			}).catch(swal.noop);
		    		});
		    		show();
			}
		}).catch(swal.noop);
		
});
	
	function insertData(data, succ_callback, fail_callback){
		$.ajax({
			 type: "POST",
			 url: "<%=request.getContextPath()%>/posture/posture.do",
			 data: data,
			 dataType: "json",
			 success: succ_callback,
             error: fail_callback
         });
	}
	
	
	
$('#exe').on('click','.btnAddExe',function(){
	 $("#exeModal").modal({show: true});
	 var rec_date = $('#date').text();
	  $('#exe_date').val(rec_date);

});

$('#food').on('click','#btnAddFood',function(){
	 $("#foodModal").modal({show: true});
	 var rec_date = $('#date').text();
	  $('#rec_date').val(rec_date);
});

$('#food').on('click','#editFood',function(){
	 $("#foodEditModal").modal({show: true});

});
	

</script>
</body>
</html>