<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.members.model.*"%>

<%
	MembersService memberSvc = new MembersService();
	String member_no = (String)session.getAttribute("member_no");
	MembersVO membersVO = memberSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO", membersVO);
%>

<html>
<head>
<meta charset="UTF-8">
<title>Login</title>

 <link rel="stylesheet" href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
  <script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
  <script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
  <script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>



<style type="text/css">
	*, *:before, *:after {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

body {
  font-family: 'Open Sans', Helvetica, Arial, sans-serif;
  background: #ededed;
}

input, button {
  border: none;
  outline: none;
  background: none;
  font-family: 'Open Sans', Helvetica, Arial, sans-serif;
}

.tip {
  font-size: 20px;
  margin: 40px auto 50px;
  text-align: center;
}

.cont {
  overflow: hidden;
  position: relative;
  width: 900px;
  height: 550px;
  margin: 0 auto 100px;
  background: #fff;
}

.form {
  position: relative;
  width: 640px;
  height: 100%;
  padding: 80px 30px 0;
}

.sub-cont {
  overflow: hidden;
  position: absolute;
  left: 640px;
  top: 0;
  width: 900px;
  height: 100%;
  padding-left: 260px;
  background: #fff;
  -webkit-transition: -webkit-transform 1.2s ease-in-out;
  transition: -webkit-transform 1.2s ease-in-out;
  transition: transform 1.2s ease-in-out;
  transition: transform 1.2s ease-in-out, -webkit-transform 1.2s ease-in-out;
}
.cont.s--signup .sub-cont {
  -webkit-transform: translate3d(-640px, 0, 0);
          transform: translate3d(-640px, 0, 0);
}

button {
  display: block;
  margin: 0 auto;
  width: 260px;
  height: 36px;
  border-radius: 30px;
  color: #fff;
  font-size: 15px;
  cursor: pointer;
}

.img {
  overflow: hidden;
  z-index: 2;
  position: absolute;
  left: 0;
  top: 0;
  width: 260px;
  height: 100%;
  padding-top: 360px;
}
.img:before {
  content: '';
  position: absolute;
  right: 0;
  top: 0;
  width: 900px;
  height: 100%;
  background-image: url("https://s3-us-west-2.amazonaws.com/s.cdpn.io/142996/sections-3.jpg");
  background-size: cover;
  -webkit-transition: -webkit-transform 1.2s ease-in-out;
  transition: -webkit-transform 1.2s ease-in-out;
  transition: transform 1.2s ease-in-out;
  transition: transform 1.2s ease-in-out, -webkit-transform 1.2s ease-in-out;
}
.img:after {
  content: '';
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.6);
}
.cont.s--signup .img:before {
  -webkit-transform: translate3d(640px, 0, 0);
          transform: translate3d(640px, 0, 0);
}
.img__text {
  z-index: 2;
  position: absolute;
  left: 0;
  top: 50px;
  width: 100%;
  padding: 0 20px;
  text-align: center;
  color: #fff;
  -webkit-transition: -webkit-transform 1.2s ease-in-out;
  transition: -webkit-transform 1.2s ease-in-out;
  transition: transform 1.2s ease-in-out;
  transition: transform 1.2s ease-in-out, -webkit-transform 1.2s ease-in-out;
}
.img__text h2 {
  margin-bottom: 10px;
  font-weight: normal;
}
.img__text p {
  font-size: 14px;
  line-height: 1.5;
}
.cont.s--signup .img__text.m--up {
  -webkit-transform: translateX(520px);
          transform: translateX(520px);
}
.img__text.m--in {
  -webkit-transform: translateX(-520px);
          transform: translateX(-520px);
}
.cont.s--signup .img__text.m--in {
  -webkit-transform: translateX(0);
          transform: translateX(0);
}
.img__btn {
  overflow: hidden;
  z-index: 2;
  position: relative;
  width: 100px;
  height: 36px;
  margin: 0 auto;
  background: transparent;
  color: #fff;
  text-transform: uppercase;
  font-size: 15px;
  cursor: pointer;
}
.img__btn:after {
  content: '';
  z-index: 2;
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  border: 2px solid #fff;
  border-radius: 30px;
}
.img__btn span {
  position: absolute;
  left: 0;
  top: 0;
  display: -webkit-box;
  display: flex;
  -webkit-box-pack: center;
          justify-content: center;
  -webkit-box-align: center;
          align-items: center;
  width: 100%;
  height: 100%;
  -webkit-transition: -webkit-transform 1.2s;
  transition: -webkit-transform 1.2s;
  transition: transform 1.2s;
  transition: transform 1.2s, -webkit-transform 1.2s;
}
.img__btn span.m--in {
  -webkit-transform: translateY(-72px);
          transform: translateY(-72px);
}
.cont.s--signup .img__btn span.m--in {
  -webkit-transform: translateY(0);
          transform: translateY(0);
}
.cont.s--signup .img__btn span.m--up {
  -webkit-transform: translateY(72px);
          transform: translateY(72px);
}

h2 {
  width: 100%;
  font-size: 26px;
  text-align: center;
}

label {
  display: block;
  width: 260px;
  margin: 25px auto 0;
  text-align: center;
}
label span {
  font-size: 12px;
  color: #cfcfcf;
  text-transform: uppercase;
}

input {
  display: block;
  width: 100%;
  margin-top: 5px;
  padding-bottom: 5px;
  font-size: 16px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.4);
  text-align: center;
}

.forgot-pass {
  margin-top: 15px;
  text-align: center;
  font-size: 12px;
  color: #cfcfcf;
}

.submit {
  margin-top: 40px;
  margin-bottom: 20px;
  background: #d4af7a;
  text-transform: uppercase;
}

.fb-btn {
  border: 2px solid #d3dae9;
  color: #8fa1c7;
}
.fb-btn span {
  font-weight: bold;
  color: #455a81;
}

.sign-in {
  -webkit-transition-timing-function: ease-out;
          transition-timing-function: ease-out;
}
.cont.s--signup .sign-in {
  -webkit-transition-timing-function: ease-in-out;
          transition-timing-function: ease-in-out;
  -webkit-transition-duration: 1.2s;
          transition-duration: 1.2s;
  -webkit-transform: translate3d(640px, 0, 0);
          transform: translate3d(640px, 0, 0);
}

.sign-up {
  -webkit-transform: translate3d(-900px, 0, 0);
          transform: translate3d(-900px, 0, 0);
}
.cont.s--signup .sign-up {
  -webkit-transform: translate3d(0, 0, 0);
          transform: translate3d(0, 0, 0);
}

.icon-link {
  position: absolute;
  left: 5px;
  bottom: 5px;
  width: 32px;
}
.icon-link img {
  width: 100%;
  vertical-align: top;
}
.icon-link--twitter {
  left: auto;
  right: 5px;
}
#login{
  display: block;
  margin: 0 auto;
  width: 260px;
  height: 36px;
  border-radius: 30px;
  color: #fff;
  font-size: 15px;
  cursor: pointer;
}

#logbtn{
z-index:1000;
}

input#login{
	margin-top:2rem;
	max-width:5rem;
}
form.log{
    display: none !important;
}
</style>




</head>




<body>
<%@ include file="/front-end/homepage/nav.file"%>
<div id="listall">
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/members/members.do">

	
	
	<p class="tip">登入會員</p>
<div class="cont">
  <div class="form sign-in">
   
    <label>
      <span>帳號</span>
      <input type="text" placeholder="請輸入帳號..." id="username" name="mem_account"/>
    </label>
    <label>
      <span>密碼</span>
      <input type="password" placeholder="請輸入密碼..." id="password" name="mem_password"/>
    </label>
       
   
    <input type="submit" class="submit" name="submit" id="login" value="登入"/>
    <input type="hidden" name="action" value="login">
    
    <c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
   
  </div>
  <div class="sub-cont">
    <div class="img">
      <div class="img__text m--up">
        <h2>還沒有會員嗎?</h2>
        <p>點擊以下註冊來註冊會員喔!</p>
      </div>
      
      <div class="img__btn">
      
       <span class="m--up"><a href="<%=request.getContextPath() %>/front-end/members/addMembers.jsp" id="logbtn">Sign Up</a></span>
      </div>
    </div>
  </div>
</div>
<input type="hidden" name="action" value="login">


	
</FORM>
</div>
<%@ include file="/front-end/homepage/footer.file"%>
<!-- <FORM METHOD="post" ACTION="members.do"> -->
<!-- 	<label>帳號:</label><input type="text" name="mem_account"><br> -->
<!-- 	<label>密碼:</label><input type="password" name="mem_password"><br> -->
<!-- 	<input type="submit" name="submit" value="登入"> -->
<!-- 	<input type="hidden" name="action" value="login"> -->
<!-- </FORM> -->






</body>
</html>