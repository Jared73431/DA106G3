<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" >
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/datetimepicker/jquery.datetimepicker.css" >
<title>會員資料新增 - addMembers.jsp</title>

<style>
	.sign-up{
			width: 450px;
			margin: auto auto auto 35px;
		}
	.form{
		margin:auto 30%;
	}
</style>

</head>
<body bgcolor='white'>
<%@ include file="/front-end/homepage/nav.file"%>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/members/members.do" name="form1" enctype="multipart/form-data">

<div class="form">

<div class="sign-up">
<h1><center>註冊會員</center></h1>
  <div class="form-group">
    <label for="exampleInputEmail1">暱稱</label>
	<input type="TEXT" class="form-control" name="known" id="known" size="45" value="${membersVO.known} " />  
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">性別</label>
    <input type="RADIO" name="sexual" size="45"
			 value="<%= (membersVO==null)? "0" : membersVO.getSexual()%>" /><label for="male">男性</label>
	<input type="RADIO" name="sexual" size="45"
			 value="<%= (membersVO==null)? "1" : membersVO.getSexual()%>" /><label for="female">女性</label>
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">姓名</label>
	<input type="TEXT" name="mem_name" class="form-control" id="mem_name" size="45" value="${membersVO.mem_name}" />   
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">帳號</label>
	<input type="TEXT" name="mem_account" class="form-control" size="45" id="mem_account" value="${membersVO.mem_account}"/><span id="result"></span> 
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">密碼</label>
	<input type="PASSWORD" name="mem_password" class="form-control" id="mem_password" size="45" value="${membersVO.mem_password}" /> 
 </div>
  <div class="form-group">
    <label for="exampleInputPassword1">信箱</label>
	<input type="TEXT" name="email" size="45" class="form-control" id="email" value="${membersVO.email}" />  
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">生日</label>
	<input name="birthday" id="b_date1" class="form-control" type="text">	
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">電話</label>
    <input type="TEXT" name="phone" size="45" class="form-control" id="phone" value="${membersVO.phone}" />
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">地址</label>
    <div id="twzipcode"></div>
		<!-- <input id="address" name="address">	 -->
	<input type="TEXT" name="address" size="45" class="form-control" id="address" value="${membersVO.address}" />
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">身高</label>
    <input type="TEXT" name="height" size="45" class="form-control" id="height" value="${membersVO.height}" />
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">上傳大頭貼</label>
    <input type="file" name="upfile1" onchange="loadFile(event)">
	<div id="output"></div>
  </div>
  
  <input type="hidden" name="action"  value="insert">
  <center><input type="submit" value="Submit" class="btn btn-primary" id="btn"></center>
  <input type="button" id="magic" value="神奇小按鈕" class="btn-outline-secondary">
</div>

</div>

</FORM>
<%-- <script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script> --%>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/datetimepicker/jquery.datetimepicker.full.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery-twzipcode@1.7.15-rc1/jquery.twzipcode.min.js"></script>



<script type="text/javascript">

	//預覽圖片
	var loadFile = function(event){
		var reader = new FileReader();
		reader.onload = function(){
			var output = document.getElementById('output');
			output.innerHTML = "<img width='100' id ='preview'>";
			document.getElementById("preview").src = reader.result;
		};
		reader.readAsDataURL(event.target.files[0]);
	}
	//預覽圖片
	var loadFile2 = function(event){
		var reader2 = new FileReader();
		reader2.onload = function(){
			var output2 = document.getElementById('output2');
			output2.innerHTML = "<img width='100' id ='preview2'>";
			document.getElementById("preview2").src = reader2.result;
		};
		reader2.readAsDataURL(event.target.files[0]);
	}
	

	//下拉式地址選單
	$("#twzipcode").twzipcode({
		zipcodeName:"mem_zip",
		zipcodeSel: "${membersVO.mem_zip}"
	});
	
		$("#twzipcode").on('change', function(){
			var county = $("#twzipcode").twzipcode('get', 'county');
			var district = $("#twzipcode").twzipcode('get', 'district');
			var result = county+district;
			$("#address").val(result);
	});

</script>

<script>
	//帳號驗證不重複
	$(function(){
		$('#mem_account').on('blur', function(){
			$.ajax({
				 type: "POST",
				 url: "<%=request.getContextPath()%>/members/members.do",
				 data: {"mem_account": $('#mem_account').val(), 
					 "action" : "check_Account"},
				 dataType: "json",
				 success: function (data){
					if(data.canPass === "此帳號可以使用"){
						$("#result").text("此帳號可以使用");
						$("#btn").removeAttr("disabled");//移除disabled這個屬性
					}else{
						$("#result").text("此帳號已被使用");
						$("#btn").attr("disabled","disabled");//設定disable這個屬性的值是disable
					}
						
			     },
	            error: function(){alert("AJAX-class發生錯誤囉!")}
	        })
	        
		});
	});
	
	$('#magic').click(function(){
		$('#known').val("小智");
		$('#mem_name').val("小智");
		$('#mem_account').val("aaa123");
		$('#mem_password').val("123456");
		$('#email').val("jianxing0122@g.ncu.edu.tw");
		$('#b_date1').val("1997-08-08");
		$('#phone').val("0919625114");
		$('[name=mem_zip]').val("406");		
		$('#address').val("台中市北屯區松竹路一段933號");
		$('#height').val("150");

	});
	

</script>

</body>



<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<% 
  java.sql.Date birthday = null;
  try {
	   birthday = membersVO.getBirthday();
   } catch (Exception e) {
	   birthday = new java.sql.Date(System.currentTimeMillis());
   }
%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>
        $.datetimepicker.setLocale('zh');
        $('#b_date1').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   value: '<%=birthday%>', // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
//            startDate:	            '2017/07/10',  // 起始日
//            minDate:               '-1970-01-01', // 去除今日(不含)之前
           maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
        
        
   
        // ----------------------------------------------------------以下用來排定無法選擇的日期-----------------------------------------------------------

        //      1.以下為某一天之前的日期無法選擇
        //      var somedate1 = new Date('2017-06-15');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() <  somedate1.getYear() || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});

        
        //      2.以下為某一天之後的日期無法選擇
//              var somedate2 = new Date('2017-06-15');
//              $('#b_date1').datetimepicker({
//                  beforeShowDay: function(date) {
//                	  if (  date.getYear() >  somedate2.getYear() || 
//         		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
//         		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
//                      ) {
//                           return [false, ""]
//                      }
//                      return [true, ""];
//              }});


        //      3.以下為兩個日期之外的日期無法選擇 (也可按需要換成其他日期)
        //      var somedate1 = new Date('2017-06-15');
        //      var somedate2 = new Date('2017-06-25');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() <  somedate1.getYear() || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
        //		             ||
        //		            date.getYear() >  somedate2.getYear() || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});
        
</script>
</html>