<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.members.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
	MembersService memberSvc = new MembersService();
	String member_no = (String)session.getAttribute("member_no");
	MembersVO membersVO = memberSvc.getOneMembers(member_no);
	List<MembersVO> list = memberSvc.getAll();
	pageContext.setAttribute("list", list);
	pageContext.setAttribute("membersVO", membersVO);
%>

<%
	Map<Integer, String> sexual = new LinkedHashMap<Integer, String>();
	sexual.put(0, "男性");
	sexual.put(1, "女性");
	pageContext.setAttribute("sexual", sexual);
%>

<%
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
<title>會員資料 - membersPage.jsp</title>

<link rel="stylesheet" href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/lobibox/dist/css/lobibox.css" />
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9.10.12/dist/sweetalert2.all.min.js" integrity="sha256-HyVNOA4KTbmvCLxBoFNrj0FLZtj/RCWyg4zfUjIry0k=" crossorigin="anonymous"></script>
  <script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
  <script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
  <script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>
  <script src="<%=request.getContextPath()%>/js/lobibox/dist/js/lobibox.min.js"></script>
  <script src="<%=request.getContextPath()%>/js/lobibox/dist/js/notifications.min.js"></script>

<style type="text/css">
@import url("https://fonts.googleapis.com/css?family=DM+Sans:400,500,700|Source+Sans+Pro:300,400,600,700&display=swap");
* {
  outline: none;
  box-sizing: border-box;
}

html {
  box-sizing: border-box;
  -webkit-font-smoothing: antialiased;
}

body {
  font-family: "Source Sans Pro", sans-serif;
  background-color: white;
  color: #ccc8db;
}

.container {
  background-color: white;
  display: flex;
  max-width: 1600px;
  height: 100vh;
  overflow: hidden;
  margin: 0 auto;
}

.left-side {
  width: 260px;
  border-right: 1px solid #272a3a;
  display: flex;
  flex-direction: column;
  transition: 0.3s;
  background-color: rgba(126,149,124,0.8);
  overflow: auto;
  flex-shrink: 0;
}
@media screen and (max-width: 930px) {
  .left-side.active {
    z-index: 4;
  }
  .left-side.active > *:not(.logo) {
    opacity: 1;
    transition: 0.3s 0.2s;
  }
  .left-side.active .left-side-button svg:first-child {
    opacity: 0;
  }
  .left-side.active .left-side-button svg:last-child {
    transform: translate(-50%, -50%);
    opacity: 1;
  }
  .left-side:not(.active) {
    width: 56px;
    overflow: hidden;
  }
  .left-side:not(.active) > *:not(.logo):not(.left-side-button) {
    opacity: 0;
  }
  .left-side:not(.active) .logo {
    writing-mode: vertical-lr;
    transform: rotate(180deg);
    transform-origin: bottom;
    display: flex;
    align-items: center;
    margin-top: -10px;
  }
}

.right-side {
  width: 280px;
  flex-shrink: 0;
  margin-left: auto;
  overflow: auto;
  background-color: #151728;
  display: flex;
  flex-direction: column;
}
@media screen and (max-width: 1210px) {
  .right-side {
    position: fixed;
    right: 0;
    top: 0;
    transition: 0.3s;
    height: 100%;
    transform: translateX(280px);
    z-index: 4;
  }
  .right-side.active {
    transform: translatex(0);
  }
}

.main {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  background-color: white;
}

.logo {
  font-family: "DM Sans", sans-serif;
  font-size: 15px;
  color: #fff;
  font-weight: 600;
  text-align: center;
  height: 68px;
  line-height: 68px;
  letter-spacing: 4px;
  position: sticky;
  top: 0;
  background: rgba(126,149,124,0.8);
}

.side-title {
  font-family: "DM Sans", sans-serif;
  color: #5c5e6e;
  font-size: 15px;
  font-weight: 600;
  margin-bottom: 20px;
}

.side-wrapper {
  padding: 30px;
}

.side-menu {
  display: flex;
  flex-direction: column;
  font-size: 15px;
  white-space: nowrap;
}
.side-menu svg {
  margin-right: 16px;
  width: 16px;
}
.side-menu a {
  text-decoration: none;
  color: white;
  display: flex;
  align-items: center;
}
.side-menu a:hover {
  color: #42602D;
}
.side-menu a:not(:last-child) {
  margin-bottom: 20px;
}

.follow-me {
  text-decoration: none;
  font-size: 14px;
  display: flex;
  align-items: center;
  margin-top: auto;
  overflow: hidden;
  color: #9c9cab;
  padding: 0 20px;
  height: 52px;
  flex-shrink: 0;
  border-top: 1px solid #272a3a;
  position: relative;
}
.follow-me svg {
  width: 16px;
  height: 16px;
  margin-right: 8px;
}

.follow-text {
  display: flex;
  align-items: center;
  transition: 0.3s;
}

.follow-me:hover .follow-text {
  transform: translateY(100%);
}
.follow-me:hover .developer {
  top: 0;
}

.developer {
  position: absolute;
  color: #fff;
  left: 0;
  top: -100%;
  display: flex;
  transition: 0.3s;
  padding: 0 20px;
  align-items: center;
  background-color: #272a3a;
  width: 100%;
  height: 100%;
}

.developer img {
  border-radius: 50%;
  width: 26px;
  height: 26px;
  object-fit: cover;
  margin-right: 10px;
}

.main-container {
  padding: 20px;
  flex-grow: 1;
  overflow: auto;
  background-color: white;
}

.profile {
  position: relative;
  height: 35vh;
  min-height: 250px;
  max-height: 250px;
  z-index: 1;
}
.profile-cover {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
  border-radius: 4px;
}
.profile:before {
  content: "";
  width: 100%;
  height: 100%;
  position: absolute;
  z-index: -1;
  left: 0;
  top: 0;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  filter: blur(50px);
  opacity: 0.7;
}


.profile-avatar {
  position: absolute;
  align-items: center;
  display: flex;
  z-index: 1;
  bottom: 16px;
  left: 24px;
}

.profile-img {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  object-fit: cover;
  border: 3px solid #151728;
}

.profile-name {
  margin-left: 24px;
  margin-bottom: 24px;
  font-size: 22px;
  color: #fff;
  font-weight: 600;
  font-family: "DM Sans", sans-serif;
}



.box {
  background-color: #151728;
  border-radius: 4px;
}

.intro {
  padding: 20px;
}
.intro-title {
  font-family: "DM Sans", sans-serif;
  color: #5c5e6e;
  font-weight: 600;
  font-size: 18px;
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}
.intro-menu {
  background-color: #8f98a9;
  box-shadow: -8px 0 0 0 #8f98a9, 8px 0 0 0 #8f98a9;
  width: 5px;
  height: 5px;
  border: 0;
  padding: 0;
  border-radius: 50%;
  margin-left: auto;
  margin-right: 8px;
}

.pages {
  margin-top: 20px;
  padding: 20px;
}

.album {
  padding-top: 20px;
  margin-top: 20px;
  background-color:rgba(126,149,124,0.8);
}
.album .status-main {
  border: none;
  display: flex;
}
.album .intro-menu {
  margin-bottom: auto;
  margin-top: 5px;
}

.album-detail {
  width: calc(100% - 110px);
}

.album-title span {
  color: #1771d6;
  cursor: pointer;
}

.album-date {
  font-size: 15px;
  color: #595c6c;
  margin-top: 4px;
}

.album-content {
  padding: 0 20px 20px;
  color:white;
}

.album-photo {
  width: 100%;
  object-fit: cover;
  object-position: center;
  border-radius: 4px;
  margin-top: 10px;
}

.album-photos {
  display: flex;
  margin-top: 20px;
  max-height: 30vh;
}

.album-photos > .album-photo {
  width: 50%;
}

.album-right {
  width: 50%;
  margin-left: 10px;
  line-height: 0;
  display: flex;
  flex-direction: column;
}
.album-right .album-photo {
  height: calc(50% - 10px);
}

.album-actions {
  padding: 0 20px 20px;
}

.album-action {
  margin-right: 20px;
  text-decoration: none;
  color: #a2a4b4;
  display: inline-flex;
  align-items: center;
  font-weight: 600;
}
.album-action:hover {
  color: #fff;
}
.album-action svg {
  width: 16px;
  margin-right: 6px;
}

.stories {
  border-bottom: 1px solid #272a3a;
}

.stories .user-img {
  border: 2px solid #e2b96c;
}

.stories .album-date {
  font-family: "Source Sans Pro", sans-serif;
}

.left-side-button {
  display: none;
}
@media screen and (max-width: 930px) {
  .left-side-button {
    display: flex;
    flex-shrink: 0;
    align-items: center;
    justify-content: center;
    position: relative;
    cursor: pointer;
    height: 60px;
    background-color: rgba(39, 42, 58, 0.5);
    border: 0;
    padding: 0;
    line-height: 0;
    color: #fff;
    border-bottom: 1px solid #272a3a;
  }
  .left-side-button svg {
    transition: 0.2s;
    width: 24px;
  }
  .left-side-button svg:last-child {
    position: absolute;
    left: 50%;
    transform: translate(100%, -50%);
    top: 50%;
    opacity: 0;
  }
}

@media screen and (max-width: 700px) {
  .profile-avatar {
    top: -25px;
    left: 50%;
    transform: translatex(-50%);
    align-items: center;
    flex-direction: column;
    justify-content: center;
  }

  .profile-img {
    height: 100px;
    width: 100px;
  }

  .profile-name {
    margin: 5px 0;
  }

  .profile-menu {
    padding-left: 0;
    width: 100%;
    overflow: auto;
    justify-content: center;
  }

  .profile-menu-link {
    padding: 16px;
    font-size: 15px;
  }
}
@media screen and (max-width: 480px) {
  .profile-menu-link:nth-last-child(1),
  .profile-menu-link:nth-last-child(2) {
    display: none;
  }
}
::-webkit-scrollbar {
  width: 10px;
  border-radius: 10px;
}

/* Track */
::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.01);
}

/* Handle */
::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.11);
  border-radius: 10px;
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
  background: rgba(255, 255, 255, 0.1);
}

</style>

</head>
<body bgcolor='white' onload='connect();'>
<%@ include file="/front-end/homepage/nav.file"%>
<%@ include file="/front-end/notificationlist/notesocket.jsp"%>
<div class="container" x-data="{ rightSide: false, leftSide: false }">
  <div class="left-side" :class="{'active' : leftSide}">
    <div class="left-side-button" @click="leftSide = !leftSide">
      <svg viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" fill="none" stroke-linecap="round" stroke-linejoin="round"><line x1="3" y1="12" x2="21" y2="12"></line><line x1="3" y1="6" x2="21" y2="6"></line><line x1="3" y1="18" x2="21" y2="18"></line></svg>
      <svg stroke="currentColor" stroke-width="2" fill="none" stroke-linecap="round" stroke-linejoin="round" viewBox="0 0 24 24">
  <path d="M19 12H5M12 19l-7-7 7-7"/>
</svg>
    </div>
    <div class="logo">會員管理</div>
    <div class="side-wrapper">
      <div class="side-title">MENU</div>
      <div class="side-menu">
        <a href="<%=request.getContextPath()%>/front-end/members/update_members_input.jsp">
          修改資料
        </a>
        <a href="<%=request.getContextPath()%>/front-end/transactions/transactionsPage.jsp">
         交易紀錄
        </a>
        <a href="<%=request.getContextPath()%>/front-end/transactions/addTransactions.jsp">
          儲值購物金
        </a>
       
        <c:if test="${membersVO.coa_qualifications == 0 }">
       <a href="#" id="applyCoach">
          申請教練
		</a>
		 </c:if>
		<a href="<%=request.getContextPath()%>/front-end/cour_booking/courseManage.jsp">
          我的課程
        </a>
        <a href="<%=request.getContextPath()%>/front-end/attendrace/attendRacePage.jsp">
          比賽紀錄
        </a>       
        <a href="<%=request.getContextPath()%>/front-end/followmaster/getMaster.jsp">
          我的追蹤
        </a>
	    <a href="<%=request.getContextPath()%>/front-end/orders/listMemOrders.jsp"> 
	   我的訂單 
	     </a>        
         <a href="<%=request.getContextPath()%>/members/members.do?action=listExper_ByMemberno&member_no=${membersVO.member_no}">
          我的心得
        </a>
         <a href="<%=request.getContextPath()%>/members/members.do?action=listNotes_ByMemberno&member_no=${membersVO.member_no}">
          我的通知
        </a>
      </div>
    </div>
    <div class="side-wrapper">
    
   
    </div>
    
  </div>
  <div class="main">
    
    <div class="main-container">
      <div class="profile">
        <div class="profile-avatar">
          <img src="<%=request.getContextPath() %>/MembersShowPicture?member_no=${membersVO.member_no}" alt="" class="profile-img">
          <div class="profile-name">${membersVO.mem_name}</div>
        </div>
        <img src="<%=request.getContextPath() %>/picture/02.jpg" alt="" class="profile-cover">
      
      </div>
      <div class="timeline">
        <div class="timeline-left">
          
          
          </div>
          
        </div>
        <div class="timeline-right">
          
          <div class="album box">
            
            <div class="album-content">
           
                <p>會員編號:${membersVO.member_no}</p>
                <p>教練資格:${coa_qualifications.get(membersVO.coa_qualifications)}</p>
                <p>性別: ${sexual.get(membersVO.sexual)}</p>
                <p>暱稱: ${membersVO.known}</p>
                <p>信箱: ${membersVO.email}</p>
                <p>生日: ${membersVO.birthday}</p>
                <p>電話: ${membersVO.phone}</p>
                <p>郵遞區號: ${membersVO.mem_zip}</p>
                <p>地址: ${membersVO.address}</p>
                <p>身高: ${membersVO.height}</p>
                <p>註冊時間: ${membersVO.reg_date}</p>
                <p>儲值金餘額: ${membersVO.real_blance}</p> 
               
              
              
             
            </div>
         
          </div>
        </div>
      </div>
    </div>
  </div>

<script>
$("#applyCoach").click(function(){
	Swal.fire({
		  title: 'Select image',
		  html:
			  '<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/members/members.do" name="form1" enctype="multipart/form-data">'
			  +'<input type="file" name="upfile2" onchange="loadFile2(event)">'
			  +'<div id="output2"></div>'
			  +'<input class="btn btn-primary" type="submit" value="申請教練" id="update">'
			  +'<input type="hidden" name="member_no" value="${member_no}">'
			  +'<input type="hidden" name="action" value="applyCoach">'
			  +'<script type="text/javascript">'
			  +'var loadFile2 = function(event){var reader = new FileReader()'
			  +'reader.onload = function(){var output2 = document.getElementById("output2")'
			  +'output2.innerHTML = "<img width="100" id ="preview2">"'
			  +'document.getElementById("preview2").src = reader.result'
			  +'}'
			  +'reader.readAsDataURL(event.target.files[0])'
			  +'}',
			  showConfirmButton: false
		})

		if (file) {
		  const reader = new FileReader()
		  reader.onload = (e) => {
		    Swal.fire({
		      title: 'Your uploaded picture',
		      imageUrl: e.target.result,
		      imageAlt: 'The uploaded picture'
		    })
		  }
		  reader.readAsDataURL(file)
		}
});

</script>
</body>
</html>