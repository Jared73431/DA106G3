<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.members.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
	MembersService memberSvc = new MembersService();
	pageContext.setAttribute("memberSvc", memberSvc);
	String member_no = (String)session.getAttribute("member_no");
	MembersVO membersVO = memberSvc.getOneMembers(member_no);
	List<MembersVO> list = memberSvc.getAll();
	pageContext.setAttribute("list", list);
	pageContext.setAttribute("membersVO", membersVO);
%>


<html>
<head>
<title>會員資料 - membersPage.jsp</title>
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9.10.12/dist/sweetalert2.all.min.js" integrity="sha256-HyVNOA4KTbmvCLxBoFNrj0FLZtj/RCWyg4zfUjIry0k=" crossorigin="anonymous"></script>
  <script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
  <script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
  <script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>

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
/*   border-right: 1px solid #272a3a; */
  display: flex;
  flex-direction: column;
  transition: 0.3s;
/*   background-color: rgba(126,149,124,0.8); */
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
  color: #fff;
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
  padding-left:20rem;
  padding-right:20rem;
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
<jsp:useBean id="membersSvc" scope="page" class="com.members.model.MembersService" /> 
<body>
<%@ include file="/front-end/homepage/nav.file"%>
	<div id="listall">
   
    <div class="main-container">
      <div class="profile">
        <div class="profile-avatar">
          <img src="<%=request.getContextPath() %>/MembersShowPicture?member_no=${param.member_no}" alt="" class="profile-img">
          <div class="profile-name">${membersSvc.getOneMembers('param.member_no').member_name}</div>
        </div>
        <img src="<%=request.getContextPath() %>/picture/p1.jpg" alt="" class="profile-cover">
      
      </div>
      
        <div class="timeline-right">
          
          <div class="album box">
            
            <div class="album-content">
           
                <p>會員編號:${param.member_no}</p>
                <p>教練資格:${coa_qualifications.get(membersSvc.getOneMembers(param.member_no).coa_qualifications)}</p>
                <p>性別: ${sexual.get(memberSvc.getOneMembers(param.member_no).sexual)}</p>
                <p>暱稱: ${memberSvc.getOneMembers(param.member_no).known}</p>
                <p>生日: ${memberSvc.getOneMembers(param.member_no).birthday}</p>
                <p>電話: ${memberSvc.getOneMembers(param.member_no).phone}</p>
                <p>郵遞區號: ${memberSvc.getOneMembers(param.member_no).mem_zip}</p>
                <p>身高: ${memberSvc.getOneMembers(param.member_no).height}</p>
                <p>註冊時間: ${memberSvc.getOneMembers(param.member_no).reg_date}</p>
               
               
              
              
             
            </div>
         
          </div>
        </div>
      </div>
    </div>
  </div>

<script>


</script>
</body>
</html>