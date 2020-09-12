<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
<title>登入</title>
<style type="text/css">
body{
	margin:0;
	color:#6a6f8c;
	background:url(<%=request.getContextPath()%>/js/back-end/images/b4.png);
	background-size: cover;
	background-repeat: no-repeat;
	background-position:top;
	font:600 16px/18px 'Open Sans',sans-serif;
}
*,:after,:before{box-sizing:border-box}
.clearfix:after,.clearfix:before{content:'';display:table}
.clearfix:after{clear:both;display:block}
a{color:inherit;text-decoration:none}

.login-wrap{
    width:100%;
    margin:auto;
    max-width:525px;
    min-height:670px;
    position:relative;
    /*background:url(https://raw.githubusercontent.com/khadkamhn/day-01-login-form/master/img/bg.jpg) no-repeat center;*/
    box-shadow:0 12px 15px 0 rgba(0,0,0,.24),0 17px 50px 0 rgba(0,0,0,.19);
}
.login-html{
    width:100%;
    height:100%;
    position:absolute;
    padding:90px 70px 50px 70px;
    background:rgba(102,22,22,0.6);
}

.login-form{
    min-height:345px;
    position:relative;
    perspective:1000px;
    transform-style:preserve-3d;
}
.login-form .group{
    margin-bottom:15px;
}
.login-form .group .label,
.login-form .group .input,
.login-form .group .button{
    width:100%;
    color:#fff;
    display:block;
}
.login-form .group .input,
.login-form .group .button{
    border:none;
    padding:15px 20px;
    border-radius:25px;
    background:rgba(255,255,255,.1);
}
.login-form .group input[data-type="password"]{
    text-security:circle;
    -webkit-text-security:circle;
}
.login-form .group .label{
    color:#aaa;
    font-size:12px;
}
.login-form .group .button{
    /*background:#1161ee;*/
    border:solid;
}
.login-form .group label .icon{
    width:15px;
    height:15px;
    border-radius:2px;
    position:relative;
    display:inline-block;
    background:rgba(255,255,255,.1);
}
.login-form .group label .icon:before,
.login-form .group label .icon:after{
    content:'';
    width:10px;
    height:2px;
    background:#fff;
    position:absolute;
    transition:all .2s ease-in-out 0s;
}
.login-form .group label .icon:before{
    left:3px;
    width:5px;
    bottom:6px;
    transform:scale(0) rotate(0);
}
.login-form .group label .icon:after{
    top:6px;
    right:0;
    transform:scale(0) rotate(0);
}
.login-form .group .check:checked + label{
    color:#fff;
}
.login-form .group .check:checked + label .icon{
    background:#1161ee;
}
.login-form .group .check:checked + label .icon:before{
    transform:scale(1) rotate(45deg);
}
.login-form .group .check:checked + label .icon:after{
    transform:scale(1) rotate(-45deg);
}
.login-html .sign-in:checked + .tab + .sign-up + .tab + .login-form .sign-in-htm{
    transform:rotate(0);
}
.login-html .sign-up:checked + .tab + .login-form .sign-up-htm{
    transform:rotate(0);
}

.hr{
    height:2px;
    margin:60px 0 50px 0;
    background:rgba(255,255,255,.2);
}
.foot-lnk{
    text-align:center;
}
</style>
	
</head>
<body>
<div class="login-wrap">
    <div class="login-html">
        <form METHOD="post" ACTION="<%=request.getContextPath()%>/backloginhandler.do">
        <input id="tab-1" type="radio" name="tab" class="sign-in" checked><label for="tab-1" class="tab">Sign In</label>
        <!-- <input id="tab-2" type="radio" name="tab" class="sign-up"><label for="tab-2" class="tab">Sign Up</label> -->
        <div class="login-form">
            <div class="sign-in-htm">
                <div class="group">
                    <label for="user" class="label">Username</label>
                    <input id="user" type="text" class="input"  name="account">
                </div>
                <div class="group">
                    <label for="pass" class="label">Password</label>
                    <input id="pass" type="password" class="input" data-type="password" name="password">
                </div>
                
                <div class="group">
                	<input type="hidden" name="action" value="signin">
                    <input type="submit" class="button" value="Sign In">
                </div>

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>