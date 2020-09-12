<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.members.model.*"%>
<%
String feature = "F0006";
%>
<%@ include file="/back-end/backstage/authList.file" %>
<%@ include file="/back-end/backstage/navbar.file" %>

<%
  MembersVO membersVO = (MembersVO) request.getAttribute("membersVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
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

<table id="table-1">
	<tr><td>
		 <h3>員工資料修改 - update_emp_input.jsp</h3>
		 <h4><a href="select_page_members.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料修改:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="members.do" name="form1" enctype="multipart/form-data">
<table>
	<tr>
		<td>會員編號:<font color=red><b>*</b></font></td>
		<td><%=membersVO.getMember_no()%></td>
	</tr>
	<tr>
		<td>教練資格:<font color=red><b>*</b></font></td>
		<td>${coa_qualifications.get(membersVO.coa_qualifications)}</td>
	</tr>
	<tr>
		<td>暱稱:</td>
		<td><input type="TEXT" name="known" size="45"	value="<%=membersVO.getKnown()%>" /></td>
	</tr>
	<tr>
		<td>性別:</td>
		<td><input type="RADIO" name="sexual" size="45"
			 value="0" <%= (membersVO.getSexual()==0)?"checked":"" %>/><label for="male">男性</label>
			<input type="RADIO" name="sexual" size="45"
			 value="1" <%= (membersVO.getSexual()==1)?"checked":"" %>/><label for="female">女性</label>
		</td>
	</tr>
	<tr>
		<td>姓名:</td>
		<td><input type="TEXT" name="mem_name" size="45" value="<%=membersVO.getMem_name()%>" /></td>
	</tr>
	<tr>
		<td>帳號:<font color=red><b>*</b></font></td>
		<td><%=membersVO.getMem_account()%></td>
	</tr>
	<tr>
		<td>會員狀態:<font color=red><b>*</b></font></td>
		<td>${mem_status.get(membersVO.mem_status)}</td>
	</tr>
	<tr>
		<td>信箱:</td>
		<td><input type="TEXT" name="email" size="45" value="<%=membersVO.getEmail()%>" /></td>
	</tr>
	<tr>
		<td>密碼:</td>
		<td><input type="TEXT" name="mem_password" size="45" value="<%=membersVO.getMem_password()%>" /></td>
	</tr>
	<tr>
		<td>生日:</td>
		<td><input name="birthday" id="b_date1" type="text" ></td>
	</tr>
	<tr>
		<td>電話:</td>
		<td><input type="TEXT" name="phone" size="45" value="<%=membersVO.getPhone()%>" /></td>
	</tr>
	<tr>
		
		<td>郵遞區號:</td>
		<td id="twzipcode"></td>
	</tr>
	
	<tr>
		<td>地址:</td>
		<td>

<div id="twzipcode"></div>
<!-- <input id="address" name="address">	 -->

		<input type="TEXT" name="address" size="45" id="address"
			 value="<%=membersVO.getAddress()%>" />
			 </td>
	</tr>
	<tr>
		<td>儲值金餘額:<font color=red><b>*</b></font></td>
		<td>
		<%=membersVO.getReal_blance()%>
<!-- 		<FORM METHOD="post" ACTION="members.do" > -->
  		 		
  		<a href='<%=request.getContextPath()%>/transactions/addTransactions.jsp'>
  		<input type="button" value="儲值">
  		
  		</a>
<!--   	</FORM> -->
		</td>
	</tr>
	<tr>
		<td>身高:</td>
		<td><input type="TEXT" name="height" size="45" value="<%=membersVO.getHeight()%>" /></td>
	</tr>
	<tr>
		<td>註冊日期:<font color=red><b>*</b></font></td>
		<td><%=membersVO.getReg_date()%></td>
	</tr>
	<tr>
	<td>上傳大頭貼</td>
	<td>
		<input type="file" name="upfile1" onchange="loadFile(event)">
		<div id="output"><img id='oimg' width="100" onerror="javascipt:this.src='/members/images/images.jpeg'" src="<%=request.getContextPath() %>/MembersShowPicture?member_no=${membersVO.member_no}"></div>
	</td>
	<tr>
	<td>上傳證照</td>
	<td>
		<input type="file" name="upfile2" onchange="loadFile2(event)">
		<div id="output2"><img id='oimg2' width="100" onerror="javascipt:this.src='/members/images/images.jpeg'" src="<%=request.getContextPath() %>/MembersShowLicense?member_no=${membersVO.member_no}"></div>
	</td>
	</tr>
	

	

</table>
<br>
<input type="hidden" name="action" value="update_back">
<input type="hidden" name="member_no" value="<%=membersVO.getMember_no()%>">
<input type="hidden" name="coa_qualifications" value="<%=membersVO.getCoa_qualifications()%>">
<input type="hidden" name="mem_status" value="<%=membersVO.getMem_status()%>">
<input type="hidden" name="real_blance" value="<%=membersVO.getReal_blance()%>">
<input type="hidden" name="reg_date" value="<%=membersVO.getReg_date()%>">
<input type="hidden" name="mem_account" value="<%=membersVO.getMem_account()%>">
<input type="submit" value="送出修改"></FORM>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
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
 		   value: '<%=membersVO.getBirthday()%>', // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
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