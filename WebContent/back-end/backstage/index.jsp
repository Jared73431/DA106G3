<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css"> 
<title>backstage</title>
<style type="text/css">


  h1{
    margin-top:1em;
    text-align: center;
    text-transform: capitalize;
    color: rgba(102,22,22,1);
  }
  body{
     
    font-family: microsoft jhengHei;
   
    margin: auto;
    background-image: url(<%=request.getContextPath()%>/js/back-end/images/bg1.png);
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center center;
  }
  figure{
    width:65%;
    color:black;
    font-weight: bold;
    margin:1em auto;
    /*margin: 1em;*/
    text-align: center;
    transition: all 0.8s;
  }
  figure:hover img{
      /*width: 120%;
      height: 120%;*/
    transform: scale(1.2,1.2);
  }
  figure img{
    width: 90%;

    display: block;
    box-sizing: border-box; 
    margin:0 auto;
    
  }
  
  
  div.container{
    background-color: rgba(102,22,22,0.3);
    border-radius: 120px;
    box-sizing: border-box;
    position:relative;
    top:3em;
    padding-top:2em;
    padding-bottom:2em;
  
  }

  a.log{
    display: inline-block;
    position: fixed;
    top: 3%;
    left: 95%;  
  }
  p{  
    font-size: 1.3rem;
    color:black;
    font-weight: bold;
    text-align: center;   
  }
  p:hover{
    text-decoration:underline;
  }
  
  div#logout{
  position:absolute;
  top:3%;
  right:5%;
  }

form#formlogin{
	display:inline;
}

a.item{
display: inline-block;

}
  </style>
</head>
<body>
  <h1>動吃動吃</h1>
  
  
<div id="logout">
  嗨~ ${adminsVO.admin_name} 
  <form action="<%=request.getContextPath()%>/backloginhandler.do" id="formlogin">
  <input type="hidden" name="action"  value="logout">
  <input type="submit" value="登出">   
  </form>
</div>
	
<div class="container">
<div class="row justify-content-center no-gutters">
 
  <div class="col-3">
    <figure>
     <a href="<%=request.getContextPath()%>/back-end/groupreport/listAllGroupReport.jsp" class="item">
    <img src="<%=request.getContextPath()%>/picture/logo.png" alt="" class="function">
    <figcaption>網頁管理</figcaption>
  </a>
  </figure>
  </div>
  <div class="col-3">
    <figure>
  	<a href="<%=request.getContextPath()%>/back-end/admins/listAllAdmin.jsp" class="item">
    <img src="<%=request.getContextPath()%>/js/back-end/images/p9.png" alt="" class="function">
    <figcaption>後台人員管理</figcaption>
 	 </a>
  </figure>
  </div>
  <div class="col-3">
    <figure>
    <a href="<%=request.getContextPath()%>/back-end/product/listAllProduct.jsp" class="item">
    <img src="<%=request.getContextPath()%>/js/back-end/images/p4.png" alt="" class="function">
    <figcaption>商品管理</figcaption>
    </a>
  </figure>
  </div>
</div>
<div class="row justify-content-center no-gutters">
  <div class="col-3">
    <figure>
    <a href="<%=request.getContextPath()%>/back-end/orders/listAllOrders.jsp" class="item">
    <img src="<%=request.getContextPath()%>/js/back-end/images/p3.png" alt="" class="function">
    <figcaption>訂單管理</figcaption>
    </a>
  </figure>
  </div>
  <div class="col-3">
    <figure>
    <a href="<%=request.getContextPath()%>/back-end/members/listAllMembers.jsp" class="item">
    <img src="<%=request.getContextPath()%>/js/back-end/images/p8.png" alt="" class="function">
    <figcaption>會員管理</figcaption>
    </a>
  </figure>
  </div>
  <div class="col-3">
    <figure>
   <a href="<%=request.getContextPath()%>/back-end/coach/listAllCoach.jsp" class="item">
    <img src="<%=request.getContextPath()%>/js/back-end/images/p6.png" alt="" class="function">
    <figcaption>教練管理</figcaption>
    </a>
  </figure>
  </div>
</div>
<div class="row justify-content-center no-gutters">
  <div class="col-3">
    <figure>
    <a href="<%=request.getContextPath()%>/back-end/notificationlist/listAllNotificationList.jsp" class="item">
    <img src="<%=request.getContextPath()%>/js/back-end/images/p2.png" alt="" class="function">
    <figcaption>通知管理</figcaption>
    </a>
  </figure>
  </div>
  <div class="col-3">
    <figure>
    <a herf="#" class="item">
    <img src="<%=request.getContextPath()%>/js/back-end/images/p1.png" alt="" class="function">
    <figcaption>聊天機器人</figcaption>
    </a>
  </figure>
  </div>
  <div class="col-3">
    <figure>
    <a href="<%=request.getContextPath()%>/back-end/news_knowledge/listAllNewsKnowledge.jsp" class="item">
    <img src="<%=request.getContextPath()%>/js/back-end/images/p5.png" alt="" class="function">
    <figcaption>資訊管理</figcaption>
    </a>
  </figure>
  </div>
</div>

 

  
  
</div>
<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>