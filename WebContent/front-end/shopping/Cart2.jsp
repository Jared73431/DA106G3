<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*" %>
<%@ page import="com.shopping.model.ShoppingVO" %>
<%@ page import="com.productcate.model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>購物車</title>
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
</head>
<style>
  .table thead {
  	background-color: #FFF0F5;
    border: 2px solid black;
    text-align: center;
    margin: 5px auto;
  }
  .table thead th{
    border-bottom:2px solid black;
  }
  .table tbody {
    border: 2px solid black;
    text-align: center;
    margin: 5px auto;  
  }
  .table td:first-child , .table th:first-child {
    text-align: left;
  }
  
</style>

<body style="background-color:#ECF0F1">
<% 
@SuppressWarnings("unchecked")
Vector<ShoppingVO> list = (Vector<ShoppingVO>) session.getAttribute("shoppingcart");
pageContext.setAttribute("list",list);

%>

<jsp:useBean id="productcateSvc" scope="page" class="com.productcate.model.ProductCateService" />
	
<c:if test="${!list.isEmpty() and list.size()>0}">

<div class="container-md">
	<div class="row justify-content-center">
		<span style="vertical-align: middle; margin:20px auto;">
			<img src="<%=request.getContextPath()%>/front-end/shopping/images/Add2.png" style="height:70px;"> 
			<font size="+3" style="vertical-align: -webkit-baseline-middle;">目前購物車的內容如下：</font>
		</span>
	</div>
</div>
<div class="row justify-content-center">
	<table class="table col-md-9 table-hover">
		<thead>
		    <tr> 
		      <th scope="col">商品名稱</th>
		      <th scope="col">商品類別</th>
		      <th scope="col">商品詳情</th>
		      <th scope="col">價格</th>
		      <th scope="col">數量</th>
		      <th scope="col">移出購物車</th>
		    </tr>
		</thead>
	   	<tbody>
	<c:forEach var="shoppingVO" items= "${list}" varStatus="listSize">
	   	<tr>
			<td scope="col" style="text-align:left;">${shoppingVO.pro_name}</td>
			<td scope="col">${productcateSvc.getOneProductCate(shoppingVO.prc_no).prc_name}</td>
			<td scope="col"><a href="<%=request.getContextPath()%>/shopping.do?action=getOne_Cart_Display&pro_no=${shoppingVO.pro_no}">產品詳情</a></td> <!-- 待研究 商品詳情-->
			<td scope="col">${shoppingVO.pro_price}</td>
			<td scope="col">${shoppingVO.quantity}</td>		
	        <td scope="col">
	          <form name="deleteForm">
	              <input type="hidden" name="action" value="DELETE_AJAX">
	              <input type="hidden" name="del" value="${shoppingVO.pro_no}">
	              <input type="button" value="移 除" class="button" name="removeCart">
	          </form></td>
		</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
	<div class="foot">
          <form name="checkForm" action="<%=request.getContextPath()%>/shopping.do" method="POST">
              <input type="hidden" name="action"  value="CHECKOUT"> 
          </form>
	</div>

</c:if>
<script>
	var action,del;
	$("[name=removeCart]").click(function(){
		// 防止連點
		$(this).attr("disabled",true);
		action=$(this).parent().find("input[name=action]").val();
		del=$(this).parent().find("input[name=del]").val();
		$(this).parents("tr").empty();
		$.ajax({
			url: '<%=request.getContextPath()%>/shopping.do',
			type: "POST",
			data: {
				action : action,
				del : del,
			},
			dataType: "text",
			error: function(xhr){
				console.log(xhr);
			},
			success: function(data){
				if(data>0){
 				$(".total_amount a").text("$"+data);
				}else{
					$(".total_amount").empty();
					$("[name=checkCart]").attr("disabled",true);
				}
			}
		});
	});
</script>
</body>
</html>