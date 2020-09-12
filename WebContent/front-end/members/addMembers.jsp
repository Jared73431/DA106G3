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
<title>�|����Ʒs�W - addMembers.jsp</title>

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

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/members/members.do" name="form1" enctype="multipart/form-data">

<div class="form">

<div class="sign-up">
<h1><center>���U�|��</center></h1>
  <div class="form-group">
    <label for="exampleInputEmail1">�ʺ�</label>
	<input type="TEXT" class="form-control" name="known" id="known" size="45" value="${membersVO.known} " />  
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">�ʧO</label>
    <input type="RADIO" name="sexual" size="45"
			 value="<%= (membersVO==null)? "0" : membersVO.getSexual()%>" /><label for="male">�k��</label>
	<input type="RADIO" name="sexual" size="45"
			 value="<%= (membersVO==null)? "1" : membersVO.getSexual()%>" /><label for="female">�k��</label>
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">�m�W</label>
	<input type="TEXT" name="mem_name" class="form-control" id="mem_name" size="45" value="${membersVO.mem_name}" />   
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">�b��</label>
	<input type="TEXT" name="mem_account" class="form-control" size="45" id="mem_account" value="${membersVO.mem_account}"/><span id="result"></span> 
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">�K�X</label>
	<input type="PASSWORD" name="mem_password" class="form-control" id="mem_password" size="45" value="${membersVO.mem_password}" /> 
 </div>
  <div class="form-group">
    <label for="exampleInputPassword1">�H�c</label>
	<input type="TEXT" name="email" size="45" class="form-control" id="email" value="${membersVO.email}" />  
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">�ͤ�</label>
	<input name="birthday" id="b_date1" class="form-control" type="text">	
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">�q��</label>
    <input type="TEXT" name="phone" size="45" class="form-control" id="phone" value="${membersVO.phone}" />
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">�a�}</label>
    <div id="twzipcode"></div>
		<!-- <input id="address" name="address">	 -->
	<input type="TEXT" name="address" size="45" class="form-control" id="address" value="${membersVO.address}" />
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">����</label>
    <input type="TEXT" name="height" size="45" class="form-control" id="height" value="${membersVO.height}" />
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">�W�Ǥj�Y�K</label>
    <input type="file" name="upfile1" onchange="loadFile(event)">
	<div id="output"></div>
  </div>
  
  <input type="hidden" name="action"  value="insert">
  <center><input type="submit" value="Submit" class="btn btn-primary" id="btn"></center>
  <input type="button" id="magic" value="���_�p���s" class="btn-outline-secondary">
</div>

</div>

</FORM>
<%-- <script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script> --%>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/datetimepicker/jquery.datetimepicker.full.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery-twzipcode@1.7.15-rc1/jquery.twzipcode.min.js"></script>



<script type="text/javascript">

	//�w���Ϥ�
	var loadFile = function(event){
		var reader = new FileReader();
		reader.onload = function(){
			var output = document.getElementById('output');
			output.innerHTML = "<img width='100' id ='preview'>";
			document.getElementById("preview").src = reader.result;
		};
		reader.readAsDataURL(event.target.files[0]);
	}
	//�w���Ϥ�
	var loadFile2 = function(event){
		var reader2 = new FileReader();
		reader2.onload = function(){
			var output2 = document.getElementById('output2');
			output2.innerHTML = "<img width='100' id ='preview2'>";
			document.getElementById("preview2").src = reader2.result;
		};
		reader2.readAsDataURL(event.target.files[0]);
	}
	

	//�U�Ԧ��a�}���
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
	//�b�����Ҥ�����
	$(function(){
		$('#mem_account').on('blur', function(){
			$.ajax({
				 type: "POST",
				 url: "<%=request.getContextPath()%>/members/members.do",
				 data: {"mem_account": $('#mem_account').val(), 
					 "action" : "check_Account"},
				 dataType: "json",
				 success: function (data){
					if(data.canPass === "���b���i�H�ϥ�"){
						$("#result").text("���b���i�H�ϥ�");
						$("#btn").removeAttr("disabled");//����disabled�o���ݩ�
					}else{
						$("#result").text("���b���w�Q�ϥ�");
						$("#btn").attr("disabled","disabled");//�]�wdisable�o���ݩʪ��ȬOdisable
					}
						
			     },
	            error: function(){alert("AJAX-class�o�Ϳ��~�o!")}
	        })
	        
		});
	});
	
	$('#magic').click(function(){
		$('#known').val("�p��");
		$('#mem_name').val("�p��");
		$('#mem_account').val("aaa123");
		$('#mem_password').val("123456");
		$('#email').val("jianxing0122@g.ncu.edu.tw");
		$('#b_date1').val("1997-08-08");
		$('#phone').val("0919625114");
		$('[name=mem_zip]').val("406");		
		$('#address').val("�x�����_�ٰϪQ�˸��@�q933��");
		$('#height').val("150");

	});
	

</script>

</body>



<!-- =========================================�H�U�� datetimepicker �������]�w========================================== -->

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
	       step: 1,                //step: 60 (�o�Otimepicker���w�]���j60����)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   value: '<%=birthday%>', // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // �h���S�w���t
//            startDate:	            '2017/07/10',  // �_�l��
//            minDate:               '-1970-01-01', // �h������(���t)���e
           maxDate:               '+1970-01-01'  // �h������(���t)����
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
</html>