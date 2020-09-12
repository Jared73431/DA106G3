<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.race.model.*"%>
<%-- 比賽防錯誤頁 --%>
<%
	RaceService raceSvc = new RaceService();
	List<RaceVO> list = raceSvc.getRace("R001");
	pageContext.setAttribute("list",list);
	
	List<RaceinformVO> inform = raceSvc.getAll();
	pageContext.setAttribute("inform",inform);	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>所有比賽資料 - listAllRace.jsp</title>
	<!-- 注意CSS要放前面才能被參數覆蓋 -->
   	<link rel="stylesheet" href="<%=request.getContextPath() %>/js/jquery-ui/jquery-ui.css" type="text/css" />
	<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
	<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
	<script src="<%=request.getContextPath() %>/js/jquery-ui/jquery.min.js"></script>
   	<script src="<%=request.getContextPath() %>/js/jquery-ui/jquery-ui.min.js"></script>
 	
<style>
  /*Tooltip and Pointer CSS*/
  .ui-tooltip { position:absolute; z-index:9999; font-size:11pt; font-family:Calibri;  text-align:left }
  body .ui-tooltip { border-width:2px; }

  .ui-button { display: inline-block; position: relative; padding: 0; margin: 0; margin-right: .3em; text-decoration: none !important; cursor: pointer; text-align: center; zoom: 1; overflow: visible; }
  /*button text element */
  .ui-button .ui-button-text { display: block; line-height: 0.4;  }
  .ui-button-text-only .ui-button-text { padding: .3em .45em; }
  .limit { background: #F4CAD6; } 
  
    a:link,a:visited
    {
	color:#03c;
	text-decoration: none
    }

    a:hover 
    {
	color:#339;
	text-decoration:underline;
    }
  
  	.table td{
	vertical-align: middle;
	height:6em;
  	}
  	tbody,thead{
  	font-size:14px;
  	}
  	b{
  	font-size:16px;
  	}
  	  
</style>

</head>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<body style='background-color:#FBFFFD'>
	<!-- 套用首頁標頭 -->
	<%@ include file="/front-end/homepage/nav.file"%>
	<!-- 套用首頁標頭 -->
	<div id="listall" style="margin:60px auto;">
	<div class="container-fluid" style="text-align:center;">
    		<div class="col-md">
    			<h1>全國賽事</h1>
    			<b>如對各項賽事有任何疑問，請逕洽賽事主辦單位，網頁管理者恕不回答相關賽事問題。</b>	
    		</div>
    		<div>
    			<span style="display:block; margin:10px auto">
    				<b>歷 史 比 賽 訊 息 請 參 考 :</b>
    			</span>
    			    <FORM METHOD="post" ACTION="<%=request.getContextPath() %>/race.do" >
      				 <b>選擇比賽年度:</b>
				       <select size="1" name="race_no">
				       	<c:forEach var="raceinformVO" items="${inform}" >
				          <option value="${raceinformVO.race_no}"  ${(raceinformVO.race_no=="race_no") ?'selected':''}>${raceinformVO.race_year}                		        
			        	 </c:forEach>   
				       </select>
				       <input type="hidden" name="action" value="getOne_Race_Display">
				       <input class="btn btn-dark" type="submit" value="送出">
				       <input type="button" class="btn btn-dark" onclick="location.href='<%=request.getContextPath() %>/front-end/inform_cate/inform_index.jsp'" value="返回"/>
				     </FORM>
    		</div>
	</div>

<div class="container-fluid" style="margin-top:20px; font-size:small;">
	<div class="row justify-content-center">
    		<b style="font-size:16px;  margin-bottom:10px;">● 資 訊 如 下 所 示: 共<font color=red><%=list.size()%></font>筆</b>
	</div>
	<div class="row justify-content-center">
	  <table class="table col-xl-10 table-hover">
	    <thead class="thead-dark">
	      <tr style="text-align:center;">
			<th scope="col">賽事名稱</th>
			<th scope="col">賽事日期</th>
			<th scope="col">賽事里程</th>
			<th scope="col">承辦單位</th>
			<th scope="col">賽事地點</th>
			<th scope="col" style="margin-left:10px;">報名日期</th>
	      </tr>
	    </thead>
	    <tbody>
	    		<c:forEach var="raceVO" items="${list}">
			<tr>
				${raceVO.race_name}
				<td scope="col">${raceVO.race_time}</td>
				<td scope="col">${raceVO.race_content}</td>
				<td scope="col">${raceVO.race_organizer}</td>
				<td scope="col">${raceVO.race_location}</td>
				<td scope="col" style="text-align:right">${raceVO.race_deadline}</td>
			</tr>
		</c:forEach>
	    	
	    </tbody>
	  </table>

	</div> 
</div>
</div>
<!-- 首頁註腳 -->
<%@ include file="/front-end/homepage/footer.file"%>	
<!-- 首頁註腳 -->
</body>	
    <script>
    $(function() {
        $("button").button().click(function( event ) {
                event.preventDefault();
            });
	$("button").tooltip({      track: true    });
    });
    </script>
    <script type="text/javascript">
    // <![CDATA[
    $(document).ready(function(event){
        $("td > a").click(function(event){
            $("td > a").attr("target","_blank");
         });
        
    });
    // ]]>
    </script>
</html>