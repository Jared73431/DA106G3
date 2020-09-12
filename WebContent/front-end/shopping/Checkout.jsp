<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.shopping.model.ShoppingVO" %>
<%@ page import="com.members.model.*"%>

<%
	String member_no = (String)session.getAttribute("member_no");
	MembersService membersSvc = new MembersService();
	MembersVO membersVO = membersSvc.getOneMembers(member_no);
	pageContext.setAttribute("membersVO",membersVO); 
%>

<%  @SuppressWarnings("unchecked")
	Vector<ShoppingVO> buylist = (Vector<ShoppingVO>) session.getAttribute("shoppingcart");
	String total_price =  (String) session.getAttribute("shop_price");
	// 注意一定要setAttribute才能用foreach拿值
	pageContext.setAttribute("buylist",buylist);
%>	
<%  	
		if(membersVO != null){
		// 呼叫會員領班取得該會員剩下點數
		Integer blance = membersVO.getReal_blance();
		// 判斷交易金額是否大於存款
		if (Integer.parseInt(total_price) > blance) {
			boolean alert = true;
			pageContext.setAttribute("alert",alert);
			}
		}
%>
<jsp:useBean id="productcateSvc" scope="page" class="com.productcate.model.ProductCateService" />


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>動吃動吃小舖 - 結帳</title>
</head>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<script src="<%=request.getContextPath()%>/js/sweetalert/sweetalert.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.5.0.min.js"></script>
<script src="<%=request.getContextPath() %>/js/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath() %>/js/vendors/bootstrap/js/bootstrap.min.js"></script>
<style>
	/* 商品名稱 */
	tbody td:first-child {
		width:12em;
	}
	/* 商品類別 */
	tbody td:nth-child(2){
		width:4em;
 	}
	/* 產品數量 */
	tbody td:nth-child(4), tbody td:nth-child(3),
	thead th:nth-child(4), thead th:nth-child(3){
		width:4em;
		text-align:center;
 	}	
	/* 產品總價 */
	.table tbody td:nth-child(5),.table thead th:nth-child(5) {
		text-align:right;
		width:4em;
		border-right:2px solid black;
 	}
 	.table thead th{
		border-bottom:2px solid black; 	
 	}
 	.table tbody{
 	  	font: 14px italic;
 	}
 	.table{
 		border: 2px solid black;
 	}
  	thead {
		background-color: #FFF0F5;
    	border: 2px solid black;
  	}
</style>

<body style="background-color:#ECF0F1">
<!-- 套用首頁標頭 -->
<%@ include file="/front-end/homepage/nav.file"%>
<!-- 套用首頁標頭 -->
<div id="listall">
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
<div class="container-md">
	<div class="row justify-content-center">
		<div class="col-md-1">
			<img src="<%=request.getContextPath() %>/front-end/shopping/images/Add.png" class="img-fluid"> 
		</div>
		<div class="col-md-4 offset-1" style="margin:auto 0px">
			<h3>動吃動吃小舖 - 結帳：</h3>
		</div>
	</div>
</div>
<hr>
<div class="container-md">
	<div class="row justify-content-center">
		   <table class="table table-hover col-md-10">
			  <thead>
			    <tr>
					<th scope="col">商品名稱</th>
					<th scope="col">商品類別</th>
					<th scope="col">商品價格</th>
					<th scope="col">商品數量</th>
					<th scope="col">商品總價</th>
			    </tr>
			  </thead>
			  <tbody>
				<c:forEach var="abuy" items="${buylist}" begin="0" end="<%=buylist.size()-1%>">
					<tr>
						<td scope="col">${abuy.pro_name} </td>
						<td scope="col">${productcateSvc.getOneProductCate(abuy.prc_no).prc_name}</td>
						<td scope="col">${abuy.pro_price}</td>
						<td scope="col">${abuy.quantity} </td>
						<td scope="col"></td>
					</tr>		
				</c:forEach>
					<tr style="border-top:2px solid black;">
						<td colspan="6" style="text-align:right;"> 
						   <font size="+1"><b>$<%=total_price%></b></font>
					    </td>
					</tr>
			  </tbody>
			</table>
	</div>
	<div class="row justify-content-center" style="margin-bottom:30px;">
		<div class="col-md-3 offset-8">
			<FORM name="checkoutForm" action="<%=request.getContextPath()%>/orders.do" method="POST">
	              <input type="hidden" name="action"  value="checkout"> 
	              <input type="hidden" name="member_no"  value="${membersVO.member_no}"> 
	              <input type="hidden" name="total_price"  value="<%=total_price %>"> 
	              <button type="button" class="btn btn-secondary" name="checkLeft">繼續購物</button>
		          <button type="button" class="btn btn-secondary" name="checkoutCart">付款結帳</button>	      	              
			</FORM>
		</div>	
	</div>
</div>

</div>
<!-- 首頁註腳 -->
<%@ include file="/front-end/homepage/footer.file"%>	
<!-- 首頁註腳 -->
<script>

// 送出確認
$('[name=checkoutCart]').click(function(){
	$('[name=checkoutForm]').submit();
});

// 回到商品頁面
$('[name=checkLeft]').click(function(){
	window.location.href = "<%=request.getContextPath() %>/front-end/shopping/listAllShopping.jsp"
});

//使用sweet alert讓餘額不足的問題浮現
<c:if test="${alert != null}">
		swal({
			title : "儲值金不足",
			text : "魔法小卡在呼喚你~",
			icon : "warning",
			buttons : {
				button1 : {
				text : "前往儲值",
				value : true,
				},
				button2 : {
				text : "取消購物",
				value : false,
				}
				},
			}).then(function(value){   //value是按鍵的value可以設定跳轉位置
			if(value){
				window.location.href = "<%=request.getContextPath() %>/front-end/transactions/addTransactions.jsp"
			}
			else{
				window.location.href = "<%=request.getContextPath() %>/front-end/homepage/Home.jsp"
			}
		});
</c:if>	
</script>
</body>
</html>