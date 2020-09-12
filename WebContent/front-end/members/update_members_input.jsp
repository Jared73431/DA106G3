<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.members.model.*"%>

<%
String member_no = (String)session.getAttribute("member_no");
MembersService membersSvc = new MembersService();
MembersVO membersVO = membersSvc.getOneMembers(member_no);
pageContext.setAttribute("membersVO",membersVO); 
%>

<%
	Map<Integer, String> sexual = new LinkedHashMap<Integer, String>();
	sexual.put(0, "男性");
	sexual.put(1, "女性");
	pageContext.setAttribute("sexual", sexual);

	Map<Integer, String> coa_qualifications = new LinkedHashMap<Integer, String>();
	coa_qualifications.put(0, "沒資格");
	coa_qualifications.put(1, "有資格");
	coa_qualifications.put(2, "審核中");
	pageContext.setAttribute("coa_qualifications", coa_qualifications);

	Map<Integer, String> mem_status = new LinkedHashMap<Integer, String>();
	mem_status.put(0, "正常");
	mem_status.put(1, "停權");
	mem_status.put(2, "審核中");
	pageContext.setAttribute("mem_status", mem_status);
%>

<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" >
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/datetimepicker/jquery.datetimepicker.css" >
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>員工資料修改 - update_member_input.jsp</title>

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
	.update{
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
<%@ include file="/front-end/members/bar.file"%>
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

<div class="update">
<h1><center>會員資料修改</center></h1>
<div class="form-group">
    <label for="exampleInputMember_no">會員編號</label>
	<%=membersVO.getMember_no()%>  
  </div>
  <div class="form-group">
    <label for="exampleInputCoa_qualifications">教練資格</label>
	${coa_qualifications.get(membersVO.coa_qualifications)}  
  </div>
  
  <div class="form-group">
    <label for="exampleInputKnown">暱稱</label>
	<input type="TEXT" name="known" size="45" class="form-control" value="<%=membersVO.getKnown()%>" />  
  </div>
  <div class="form-group">
    <label for="exampleInputSexual">性別</label>
    <input type="RADIO" name="sexual" size="45"
			 value="0"<%= (membersVO.getSexual()==0)?"checked":"" %>/><label for="male">男性</label>
	<input type="RADIO" name="sexual" size="45"
			 value="1"<%= (membersVO.getSexual()==0)?"checked":"" %> /><label for="female">女性</label>
  </div>
  <div class="form-group">
    <label for="exampleInputMem_name">姓名</label>
	<input type="TEXT" name="mem_name" class="form-control" size="45" value="${membersVO.mem_name}" />   
  </div>
  <div class="form-group">
    <label for="exampleInputMem_account">帳號</label>
	<%=membersVO.getMem_account()%>
  </div>
  <div class="form-group">
    <label for="exampleInputMem_password">密碼</label>
	<input type="PASSWORD" name="mem_password" class="form-control" size="45" value="${membersVO.mem_password}" /> 
 </div>
  <div class="form-group">
    <label for="exampleInputEmail">信箱</label>
	<input type="TEXT" name="email" size="45" class="form-control" value="${membersVO.email}" />  
  </div>
  <div class="form-group">
    <label for="exampleInputBirthday">生日</label>
	<input name="birthday" id="b_date1" class="form-control" type="text">	
  </div>
  <div class="form-group">
    <label for="exampleInputPhone">電話</label>
    <input type="TEXT" name="phone" size="45" class="form-control" value="${membersVO.phone}" />
  </div>
  <div class="form-group">
    <label for="exampleInputAddress">地址</label>
    <div id="twzipcode"></div>
		<!-- <input id="address" name="address">	 -->
	<input type="TEXT" name="address" size="45" class="form-control" id="address" value="${membersVO.address}" />
  </div>
  <div class="form-group">
    <label for="exampleInputReal_blance">儲值金餘額</label>
	<%=membersVO.getReal_blance()%>  
  </div>
  <div class="form-group">
    <label for="exampleInputHeight">身高</label>
    <input type="TEXT" name="height" size="45" class="form-control" value="${membersVO.height}" />
  </div>
  <div class="form-group">
    <label for="exampleInputPicture">上傳大頭貼</label>
	<div id="output"><img id='oimg' width="100" onerror="javascipt:this.src='/members/images/images.jpeg'" src="<%=request.getContextPath() %>/MembersShowPicture?member_no=${membersVO.member_no}"></div>
    <input type="file" name="upfile1" onchange="loadFile(event)">
	<div id="output"></div>
  </div>
  
  <input type="hidden" name="action" value="update">
  <input type="hidden" name="member_no" value="<%=membersVO.getMember_no()%>">
  <input type="hidden" name="coa_qualifications" value="<%=membersVO.getCoa_qualifications()%>">
  <input type="hidden" name="mem_status" value="<%=membersVO.getMem_status()%>">
  <input type="hidden" name="real_blance" value="<%=membersVO.getReal_blance()%>">
  <input type="hidden" name="reg_date" value="<%=membersVO.getReg_date()%>">
  <input type="hidden" name="mem_account" value="<%=membersVO.getMem_account()%>">
  <center><input type="submit" value="Submit" class="btn btn-primary" id="btn"></center>
</div>

</div>
</FORM>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/datetimepicker/jquery.datetimepicker.full.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery-twzipcode@1.7.15-rc1/jquery.twzipcode.min.js"></script>

<script type="text/javascript">
	var loadFile = function(event){
		var reader = new FileReader();
		reader.onload = function(){
			var output = document.getElementById('output');
			var oimg = document.getElementById('oimg');
			output.removeChild(oimg);
			output.innerHTML = "<img width='100' id ='preview'>";
			document.getElementById("preview").src = reader.result;
		};
		reader.readAsDataURL(event.target.files[0]);
	}
	
	var loadFile2 = function(event){
		var reader = new FileReader();
		reader.onload = function(){
			var output2 = document.getElementById('output2');
			var oimg2 = document.getElementById('oimg2');
			output2.removeChild(oimg2);
			output2.innerHTML = "<img width='100' id ='preview2'>";
			document.getElementById("preview2").src = reader.result;
		};
		reader.readAsDataURL(event.target.files[0]);
	}

	
	$("#twzipcode").twzipcode({
		zipcodeName:"mem_zip",
		zipcodeSel: "<%= (membersVO==null)? "300" : membersVO.getMem_zip()%>"
	});
	
		$("#twzipcode").on('change', function(){
			var county = $("#twzipcode").twzipcode('get', 'county');
			var district = $("#twzipcode").twzipcode('get', 'district');
			var result = county+district;
			$("#address").val(result);
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
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
        
        
   
        // ----------------------------------------------------------以下用來排定無法選擇的日期-----------------------------------------------------------

        //      1.以下為某一天之前的日期無法選擇
//              var somedate1 = new Date('2017-06-15');
//              $('#f_date1').datetimepicker({
//                  beforeShowDay: function(date) {
//                	  if (  date.getYear() <  somedate1.getYear() || 
//         		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
//         		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
//                      ) {
//                           return [false, ""]
//                      }
//                      return [true, ""];
//              }});

        
        //      2.以下為某一天之後的日期無法選擇
        //      var somedate2 = new Date('2017-06-15');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() >  somedate2.getYear() || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});


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