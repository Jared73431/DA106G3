<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.members.model.*"%>
    
    
<%
	MembersService memberSvc = new MembersService();
	String member_no = (String)session.getAttribute("member_no");
	MembersVO membersVO = memberSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO", membersVO);
%>

<jsp:useBean id="groupgoSvc" scope="page" class="com.groupgo.model.GroupgoService" /> 
<jsp:useBean id="coachSvc" scope="page" class="com.coach.model.CoachService" /> 
<jsp:useBean id="productSvc" scope="page" class="com.product.model.ProductService" /> 
<jsp:useBean id="newsKnowledgeSvc" scope="page" class="com.newsknowledge.model.NewsKnowledgeService" /> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>動吃動吃</title>

 <style type="text/css">
 
    
.menu {
  background: rgba(126,149,124,0.9);
  height: 4.7rem;
  margin-bottom: 1rem;
}
.menu ol {
  list-style-type: none;
  margin: 0 auto;
  padding: 0;
}
.menu > ol {
  max-width: 1000px;
  padding: 0 2rem;
  display: flex;
}
.menu > ol > .menu-item {
  flex: 1;
  padding: 0.75rem 0;
}
.menu > ol > .menu-item:after {
  content: "";
  position: absolute;
  width: 4px;
  height: 4px;
  border-radius: 50%;
  bottom: 5px;
  left: calc(50% - 2px);
  background: #FECEAB;
  will-change: transform;
  transform: scale(0);
  transition: transform 0.2s ease;
}
.menu > ol > .menu-item:hover:after {
  transform: scale(1);
}
.menu-item {
  position: relative;
  line-height: 3rem;
  text-align: center;
}
.menu-item a {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  display: block;
  color: #FFF;
}
.sub-menu .menu-item {
  padding: 0.75rem 0;
  background: #55B699;
  opacity: 0;
  transform-origin: bottom;
  animation: enter 0.2s ease forwards;
}
.sub-menu .menu-item:nth-child(1) {
  animation-duration: 0.2s;
  animation-delay: 0s;
}
.sub-menu .menu-item:nth-child(2) {
  animation-duration: 0.3s;
  animation-delay: 0.1s;
}
.sub-menu .menu-item:nth-child(3) {
  animation-duration: 0.4s;
  animation-delay: 0.2s;
}
.sub-menu .menu-item:hover {
  background: #F8B195;
}
.sub-menu .menu-item a {
  padding: 0 0.75rem;
}
@media screen and (max-width: 600px) {
  .sub-menu .menu-item {
    background: #C06C84;
  }
}
@media screen and (max-width: 600px) {
  .menu {
    position: relative;
  }
  .menu:after {
    content: "";
    position: absolute;
    top: calc(50% - 2px);
    right: 1rem;
    width: 30px;
    height: 4px;
    background: #FFF;
    box-shadow: 0 10px #FFF, 0 -10px #FFF;
  }
  .menu > ol {
    display: none;
    background: #A28588;
    flex-direction: column;
    justify-content: center;
    height: 100vh;
    animation: fade 0.2s ease-out;
  }
  .menu > ol > .menu-item {
    flex: 0;
    opacity: 0;
    animation: enter 0.3s ease-out forwards;
  }
  .menu > ol > .menu-item:nth-child(1) {
    animation-delay: 0s;
  }
  .menu > ol > .menu-item:nth-child(2) {
    animation-delay: 0.1s;
  }
  .menu > ol > .menu-item:nth-child(3) {
    animation-delay: 0.2s;
  }
  .menu > ol > .menu-item:nth-child(4) {
    animation-delay: 0.3s;
  }
  .menu > ol > .menu-item:nth-child(5) {
    animation-delay: 0.4s;
  }
  .menu > ol > .menu-item + .menu-item {
    margin-top: 0.75rem;
  }
  .menu > ol > .menu-item:after {
    left: auto;
    right: 1rem;
    bottom: calc(50% - 2px);
  }
  .menu > ol > .menu-item:hover {
    z-index: 1;
  }
  .menu:hover > ol {
    display: flex;
  }
  .menu:hover:after {
    box-shadow: none;
  }
}

.sub-menu {
  position: absolute;
  width: 100%;
  top: 100%;
  left: 0;
  display: none;
  z-index: 1;
}
.menu-item:hover > .sub-menu {
  display: block;
}
@media screen and (max-width: 600px) {
  .sub-menu {
    width: 100vw;
    left: -2rem;
    top: 50%;
    transform: translateY(-50%);
  }
}

html, body {
  font-size: 17px;
  font-family: "Fira Mono", monospace, microsoft jhengHei;
  margin: 0;
}

* {
  box-sizing: border-box;
}
*:before, *:after {
  box-sizing: inherit;
}

a {
  text-decoration: none;
}

@keyframes enter {
  from {
    opacity: 0;
    transform: scaleY(0.98) translateY(10px);
  }
  to {
    opacity: 1;
    transform: none;
  }
}
@keyframes fade {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

#footer{
  
  height: 5rem;
  padding-top: 2rem;
  background-color:rgba(126,149,124,0.9);
  font-weight: bold;
  position: relative;
　margin-top: -2rem;
  min-width: 100%;
  z-index: -5;
}
</style>

<style type="text/css">

img#logo{
  max-width:4rem;
  position: relative;
  right:8rem;
  margin: 0.2rem;
}
@media screen and (max-width: 1200px) and (min-width: 1140px){
	img#logo{
	  right:5rem;
	}
}
@media screen and (max-width: 1139px) and (min-width: 1020px) {
	img#logo{
	  right:3.5rem;
	}
}
@media screen and (max-width: 1019px) and (min-width: 601px) {
	img#logo{
	  right:1.8rem;
	}
}

div.carousel-inner{
   height: 30rem;
   z-index: -1;
 }
    
img.d-block{
  width: 100%;
  height: 100%;
  position:relative;
  bottom:7rem;
}
    
   


img.card-img-top {
  max-width: 18rem;
  max-height: 11rem;
}

div.col-sm-3{
  padding: 5px;
}



#rownav{
  /*margin-top:0.5rem;*/
}

p.info{
  text-align: center;
  /*border: 1px solid #496652;*/
  margin: 2rem 0 1.5rem 0;
  padding: 0.5rem;
  font-size: 1.2rem;
  background-color:#5A5656;
  color:#fff;
  font-family: Papyrus;
  text-transform:uppercase;
  font-weight: bold;
 
}

img.product, img.coach, img.group,img.news{
  max-width: 100%;
  min-width:100%;
  min-height:100%;
  max-height:100%;
}

img.news{
min-height:9rem;
max-height:9rem;
}

form.log{
    display: inline-block;
    position: fixed;
    top: 3%;
    left: 90%;
    z-index: 5000;
}


.account{
position:fixed;
 top:4%;
 display:inline;
 left:83%;
 z-index:5000;
 font-family:fantasy;
}

div#listall{
margin-bottom:5rem;
}

div.card{
	min-height:20rem;
	max-height:20rem;
}

#somewhere{
position: absolute;
bottom: 2rem;
right: 4rem

}

</style>
 <link rel="stylesheet" href="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.css" />
 <link rel="stylesheet" href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
 <link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
</head>
<body>

<nav class="menu navbar-expand-md sticky-top">
  
  <ol>
    <li><a href="<%=request.getContextPath()%>/front-end/homepage/Home.jsp"><img src="<%=request.getContextPath()%>/picture/logo.png" id="logo"></a></li>
    
    
     <li class="menu-item"><a href="#0">會員</a>
      <ol class="sub-menu">
        <li class="menu-item"><a href="<%=request.getContextPath()%>/front-end/members/membersPage.jsp">會員管理</a></li>
		<li class="menu-item"><a href="<%=request.getContextPath()%>/front-end/experience/listAllExperience.jsp">心得分享</a></li>  
		<li class="menu-item"><a href="<%=request.getContextPath()%>/front-end/posture/posture.jsp">每日記錄</a></li>
		<li class="menu-item"><a href="<%=request.getContextPath()%>/front-end/followmaster/getMaster.jsp">追蹤列表</a></li>                
      </ol>
    </li>
    <li class="menu-item">
      <a href="<%=request.getContextPath()%>/front-end/shopping/listAllShopping.jsp">商城</a>
      <ol class="sub-menu">
        <li class="menu-item"><a href="<%=request.getContextPath()%>/shopping.do?action=getCate_Shop&prc_no=PC03">餐盒-增肌</a></li>
        <li class="menu-item"><a href="<%=request.getContextPath()%>/shopping.do?action=getCate_Shop&prc_no=PC02">餐盒-減脂</a></li>
        <li class="menu-item"><a href="<%=request.getContextPath()%>/shopping.do?action=getCate_Shop&prc_no=PC01">運動器材</a></li>
      </ol>
    </li>
    <li class="menu-item">
      <a href="#">揪團</a>
       <ol class="sub-menu">
        <li class="menu-item"> <a href="<%=request.getContextPath()%>/front-end/groupgo/listAllGroupgo.jsp">揪團列表</a></li>
      </ol>
    </li>
<li class="menu-item"><a href="<%=request.getContextPath()%>/front-end/course/listAllCourse.jsp">課程</a>
      <ol class="sub-menu">
        <li class="menu-item"><a href="<%=request.getContextPath()%>/front-end/coach/listAllCoach.jsp">查看教練</a></li>
        <li class="menu-item"><a href="<%=request.getContextPath()%>/front-end/cour_booking/courseManage.jsp">課程管理</a></li>
        <c:if test="${membersVO.getCoa_qualifications()==1}">
        <li class="menu-item"><a href="<%=request.getContextPath()%>/front-end/coach/coachManageCourse.jsp"">自創課程管理</a></li>
     	</c:if>
      </ol>
    </li>
    <li class="menu-item"><a href="<%=request.getContextPath()%>/front-end/inform_cate/inform_index.jsp">資訊</a>
      <ol class="sub-menu">
        <li class="menu-item"><a href="<%=request.getContextPath()%>/front-end/gym/listAllGym.jsp">健身地點</a></li>
        <li class="menu-item"><a href="<%=request.getContextPath()%>/front-end/food_inform/listAllFoodInform.jsp">食物資訊</a></li>
        <li class="menu-item"><a href="<%=request.getContextPath()%>/front-end/news_knowledge/listAllNewsKnowledge.jsp">運動新知</a></li>        
        <li class="menu-item"><a href="<%=request.getContextPath()%>/front-end/race/listAllRace.jsp">比賽資訊</a></li>
        <li class="menu-item"><a href="<%=request.getContextPath()%>/front-end/posture/bmiBmr.jsp">TDEE計算</a></li>
      </ol>
    </li>
  </ol>
</nav>


<c:if test="${member_no == null}">
<div>
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/members/members.do" style="margin-bottom: 0px;" class="log">
		<input type="submit" value="登入" class="btn btn-outline-secondary" id="login"> 
		<input type="hidden" name="action" value="beforelogin">
		<input type="hidden" name="location" value="<%=request.getRequestURI()%>">
	</FORM>
	</div>
</c:if>
<c:if test="${member_no != null}">
<div class="logout">
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/members/members.do" style="margin-bottom: 0px;" class="log">
		<input type="submit" value="登出" class="btn btn-outline-secondary" id="logout"> 
		<input type="hidden" name="action" value="logout">
	</FORM>
</div>
<div class="account">
		<p><p>Hello!  ${membersVO.known}</p></p>
</div>
</c:if>

<div id="listall">

 <div class="container" id="change">
  <div class="row justify-content-center no-gutters">
    <div class="col-12 col-md-12 col-xl-12">
      <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
          <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
          <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
          <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner">
          <div class="carousel-item active">
            <img src="<%=request.getContextPath()%>/picture/p1.jpg" class="d-block w-100"  alt="First" >
          </div>
          <div class="carousel-item">
            <img src="<%=request.getContextPath()%>/picture/p2.jpg" class="d-block w-100"  alt="Second" >
          </div>
          <div class="carousel-item">
            <img src="<%=request.getContextPath()%>/picture/p3.jpg" class="d-block w-100" alt="Third" >
          </div>
        </div>
        <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="sr-only">Next</span>
        </a>
      </div>
    </div>
  </div>
</div>
<div class="container" id="card">
  <div class="row justify-content-center ">
    <div class="col-sm-12">
      <p class="info">information</p>
    </div>
  </div>
  <div class="row justify-content-center ">
  
<c:forEach var="newsKnowledgeVO" items="${newsKnowledgeSvc.all}" begin="1" end="4">
      <div class="col-sm-3">
      <div class="card">
        <img src="<%=request.getContextPath() %>/PhotoReader?action=news_knowledge&pk_no=${newsKnowledgeVO.news_no}" class="card-img-top news" alt="...">
        <div class="card-body">
          <h5 class="card-title">${newsKnowledgeVO.news_title}</h5>
<%--           <p class="card-text">${newsKnowledgeVO.news_content}</p> --%>
          <a href="<%=request.getContextPath() %>/news_knowledge.do?action=getOne_Display&news_no=${newsKnowledgeVO.news_no}" class="btn btn-info" id="somewhere">Go somewhere</a>
        </div>
      </div>
    </div>
 </c:forEach>
</div>




  <div class="row justify-content-center">
    <div class="col-sm-12">
      <p class="info">product</p>
    </div>
  </div>
<div class="row justify-content-center ">
  
  <c:forEach var="productVO" items="${productSvc.all}" begin="1" end="8">
     <div class="col-sm-3">
      <a href="<%=request.getContextPath()%>/shopping.do?action=getOne_Index_Display&pro_no=${productVO.pro_no}">
      <img src="<%=request.getContextPath() %>/PhotoReader?action=product&pk_no=${productVO.pro_no}" class="product"></a>
    </div>
 </c:forEach>
 
</div>

<div class="row justify-content-center">
    <div class="col-sm-12">
      <p class="info">group</p>
    </div>
</div>
 <div class="row justify-content-center ">
  <c:forEach var="groupgoVO" items="${groupgoSvc.all}" begin="1" end="2">
    <div class="col-sm-6" style="text-align:center;">
  	<a href="<%=request.getContextPath()%>/front-end/groupgo/listOneGroup.jsp?groupgo_id=${groupgoVO.groupgo_id}">
     	<img src="<%=request.getContextPath() %>/showgroupgo.do?groupgo_id=${groupgoVO.groupgo_id}" class="group">     	
  		 ${groupgoVO.group_name}
 </a>
    </div>
   </c:forEach>
   
</div>


<div class="row justify-content-center">
    <div class="col-sm-12">
      <p class="info">coach</p>
    </div>
  </div>
 <div class="row justify-content-center ">
  
<c:forEach var="coachVO" items="${coachSvc.all}" begin="1" end="2">
     <div class="col-sm-3">
      <a href="<%=request.getContextPath()%>/front-end/coach/listAllCoach.jsp">
      <img src="<%=request.getContextPath() %>/MembersShowPicture?member_no=${coachVO.member_no}" class="coach"></a>
    </div>
    <div class="col-sm-3">
      <p class="coach-info">${coachVO.coa_info}</p>
    </div>
 </c:forEach>
 
    
 		</div>
	</div>
</div>

  <div class="container" id="footer">
    <div class="row justify-content-center"></div>
     <div class="col-sm-12">
       <center>Donz Donz @ Institute for Information Industry DA106G3</center>
     </div>
  </div>
  
  <script>
  
  </script>
  
  

  <script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
  <script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
  <script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>

<script src="<%=request.getContextPath()%>/js/sweetalert/sweetalert2.all.min.js" type="text/javascript"></script>
  
  <c:if test="${mail_alert}">
   <script>
//     alert("註冊成功,請去信箱收信驗證!");
$(function(){
	Swal.fire({
		  icon: 'success',
		  title: '註冊成功',
		  text: '請至信箱點擊驗證信件',
		})
});
	
</script>
</c:if>
</body>
</html>