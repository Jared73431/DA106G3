<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.attendrace.model.*"%>
<%@ page import="com.members.model.*"%>

<%
	AttendRaceVO attendRaceVO = (AttendRaceVO) request.getAttribute("attendRaceVO");
	MembersVO membersVO = (MembersVO)session.getAttribute("membersVO");
%>

<%
	MembersService memberSvc = new MembersService();
	String member_no = (String)session.getAttribute("member_no");
	membersVO = memberSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO", membersVO);

%>

<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" >
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/datetimepicker/jquery.datetimepicker.css" >
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>�|����Ʒs�W - addAttend.jsp</title>

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
  .addtend{
			width: 450px;
			margin: auto auto auto 35px;
		}
		.form{
		margin:auto 30%;
	}
	 #page{
  position:absolute;
  left:16rem;
  top:5rem;
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
 
  .col-6{
  max_width:50%;
  top:3rem;
  }
  
</style>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />

</head>
<body bgcolor='white'>
<%@ include file="/front-end/homepage/nav.file"%>
<%@ include file="/front-end/members/bar.file"%>
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/attendrace/attendrace.do" name="form1" enctype="multipart/form-data">
<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
<div class="form">

<div class="addtend">
<h1><center>�s�W���ɬ���</center></h1>
  <div class="form-group">
    <label for="exampleInputEmail1">�|���s��</label>
	<tr>${member_no}</tr>
  </div>
  <div class="form-group">
    <label for="exampleInputRace_type">��������</label>
    <select name="race_type">
          <option value="<%= (attendRaceVO==null)? "0" : attendRaceVO.getRace_type()%>">���ԪQ</option>
          <option value="<%= (attendRaceVO==null)? "1" : attendRaceVO.getRace_type()%>">�ۦ樮</option>
          <option value="<%= (attendRaceVO==null)? "2" : attendRaceVO.getRace_type()%>">��a</option>
    </select>
  </div>
  <div class="form-group">
    <label for="exampleInputRace_name">���ɦW��</label>
	<input type="TEXT" name="race_name" class="form-control" id="race_name" size="45" value="" />   
  </div>
  <div class="form-group">
    <label for="exampleInputFin_date">���ɤ��</label>
	<input type="TEXT" name="fin_date" class="form-control" id="f_date1"/>
  </div>
  <div class="form-group">
    <label for="exampleInputFin_time">���ɦ��Z</label>
	<input type="TEXT" name="fin_time" class="form-control" id="fin_time" size="45" value=""/> 
 </div>
  <div class="form-group">
    <label for="exampleInputFin_rank">���ɦW��</label>
	<input type="TEXT" name="fin_rank" size="45" class="form-control" id="fin_rank" value="" />  
  </div>
  <div class="form-group">
    <label for="exampleInputFin_remark">���ɳƵ�</label>
	<input type="TEXT" name="fin_remark" class="form-control" id="fin_remark" type="text">	
  </div>
  <div class="form-group">
    <label for="exampleInput">�W���Ү�</label>
    <input type="file" name="upfile1" onchange="loadFile(event)">
	<div id="output"></div>
  </div>
  
  <input type="hidden" name="member_no" value="<%=membersVO.getMember_no() %>">
<input type="hidden" name="action" value="insert">
<center><input type="submit" value="Submit" class="btn btn-primary" id="btn1"></center>
<input type="button" id="magic" value="���_�p���s" class="btn-outline-secondary">
  
</div>

</div>
</FORM>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/datetimepicker/jquery.datetimepicker.full.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery-twzipcode@1.7.15-rc1/jquery.twzipcode.min.js"></script>

<script>
var loadFile = function(event){
		var reader = new FileReader();
		reader.onload = function(){
			var output = document.getElementById('output');
			output.innerHTML = "<img width='100' id ='preview'>";
			document.getElementById("preview").src = reader.result;
		};
		reader.readAsDataURL(event.target.files[0]);
	}
	
$('#magic').click(function(){
	$('#race_name').val("�W�����ԪQ");
	$('#f_date1').val("2019-10-25");
	$('#fin_time').val("02:20:10");
	$('#fin_rank').val("1");
	$('#fin_remark').val("�ڬO�Ĥ@�W!!!");

});
</script>

<!-- =========================================�H�U�� datetimepicker �������]�w========================================== -->

<% 
  java.sql.Date fin_date = null;
  try {
	  fin_date = attendRaceVO.getFin_date();
   } catch (Exception e) {
	   fin_date = new java.sql.Date(System.currentTimeMillis());
   }
%>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

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
        $('#f_date1').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (�o�Otimepicker���w�]���j60����)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   value: '<%=fin_date%>', // value:   new Timestamp(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // �h���S�w���t
           //startDate:	            '2017/07/10',  // �_�l��
           //minDate:               '-1970-01-01', // �h������(���t)���e
           //maxDate:               '+1970-01-01'  // �h������(���t)����
        });
        
        
   
        // ----------------------------------------------------------�H�U�ΨӱƩw�L�k��ܪ����-----------------------------------------------------------

        //      1.�H�U���Y�@�Ѥ��e������L�k���
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

        
        //      2.�H�U���Y�@�Ѥ��᪺����L�k���
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


        //      3.�H�U����Ӥ�����~������L�k��� (�]�i���ݭn������L���)
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
</body>
</html>