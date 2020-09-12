<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.productcate.model.*" %>
<%@ page import="com.util.controller.*"%>
<%@ page import="com.members.model.*"%>

<%
String member_no = (String)session.getAttribute("member_no");
MembersService membersSvc = new MembersService();
MembersVO membersVO = membersSvc.getOneMembers(member_no);
pageContext.setAttribute("membersVO",membersVO); 
%>

<%
    ProductService productSvc = new ProductService();
    List<ProductVO> list = productSvc.getShop();
    pageContext.setAttribute("list",list);
    
    // 取得ProductCate相關參數 讓版面變乾淨
    ProductCateService productcateSvc = new ProductCateService();
    List<ProductCateVO> pcl = productcateSvc.getAll();
    pageContext.setAttribute("pcl",pcl);
    
    // 取得目前價金
    String total_price = (String) session.getAttribute("shop_price");
    pageContext.setAttribute("total_price",total_price);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>所有商品資料 - listCateShop.jsp</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/fontawesome-5.13.0/css/all.min.css">
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<script src="<%=request.getContextPath()%>/js/sweetalert/sweetalert.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.5.0.min.js"></script>
<script src="<%=request.getContextPath() %>/js/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath() %>/js/vendors/bootstrap/js/bootstrap.min.js"></script>
<style>
  .product img{
  	max-height:240px;
  	max-width:14.13em;
  }
  .pic {
  	padding:0px;
  	display:table-cell;
  	vertical-align:middle;
  }
  .count {
  	line-height: 30px;
  	text-align:center;
  }
  .product a{
  	color:#E35240;
  }
  .shoptop{
  	color: #ffffff;
  	background-color: #E35240;
	border-color: #E35240;
  }
  .badge{
	font-size:16px;
  }  
</style>
</head>
<body style="background-color:#ECF0F1" onload="connect();" onunload="disconnect();">
<!-- 套用首頁標頭 -->
<%@ include file="/front-end/homepage/nav.file"%>
<!-- 套用首頁標頭 -->
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
<div id="listall">
<div class="container-md">
		<div class="row justify-content-center">
			<div>
				<%@ include file="page1.file" %>
			    <input type="button" class="btn btn-dark" onclick="location.href='<%=request.getContextPath() %>/front-end/shopping/listAllShopping.jsp'" value="返回"/>			
			</div>
		</div>
		<div class="row justify-content-end">
			<div class="col-md-2">
				<span class="fas fa-shopping-cart"></span>
				<span class="dollar-sign"></span>
				<span class="total_amount">
					<c:if test="${total_price > 0}">
						<a href="<%=request.getContextPath()%>/shopping.do?whichPage=<%=whichPage %>&action=getCart_Display">$${total_price}</a>
					</c:if>
				</span>
			</div>
		</div>
	<div class="row col-md">
		<div class="col-md-9 top" style="margin:30px auto; text-align:center;">
			<a class="btn shoptop btn-warning" role="button" 
				href="<%=request.getContextPath()%>/shopping.do?action=getCate_Shop&prc_no=PC01">健身-運動器材</a>
			<a class="btn shoptop btn-warning" role="button" 
				href="<%=request.getContextPath()%>/shopping.do?action=getCate_Shop&prc_no=PC02">餐點-塑身系列</a>
			<a class="btn shoptop btn-warning" role="button" 
				href="<%=request.getContextPath()%>/shopping.do?action=getCate_Shop&prc_no=PC03">餐點-肌力系列</a>
			<a class="btn shoptop btn-warning" role="button" 
				href="<%=request.getContextPath()%>/shopping.do?action=getCate_Shop&prc_no=PC04">餐點-水煮系列</a>
			<a class="btn shoptop btn-warning" role="button" 
				href="<%=request.getContextPath()%>/shopping.do?action=getCate_Shop&prc_no=PC05">餐點-單品系列</a>			
		</div>		
	</div>
	<div class="row">
		<c:forEach var="productVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<div class="col-md-4 product">
			<div class="row justify-content-center" style="margin:10px auto 0px; display:table;">
				<!-- 商品圖 -->
				<div class="col-md-10 pic offset-2" style="height: 250px;">
					<a href="<%=request.getContextPath()%>/shopping.do?whichPage=<%=whichPage %>&action=getOne_For_Display&pro_no=${productVO.pro_no}">
						<img src="<%=request.getContextPath() %>/PhotoReader?action=product&pk_no=${productVO.pro_no}">
					</a>
				</div>
			</div>
			<div class="row justify-content-center">
				<!-- 商品名稱+詳情 -->	
				<div class="col-md-10" style="text-align:center; margin:5px auto;">
					<a href="<%=request.getContextPath()%>/shopping.do?whichPage=<%=whichPage %>&action=getOne_For_Display&pro_no=${productVO.pro_no}">${productVO.pro_name}</a>			
				</div>
			</div>
			<div class="row">
				<div class="col-md">
					<span class="col-md-4 offset-2">訂購數量：</span>   
					<span class="col-md-4">售價: $${productVO.pro_price}</span>
				</div>
			</div>
			<div class="row count justify-content-center" style="margin:10px auto;">
				<!-- 購物數量 -->	
				<div class="col-md">
					<FORM name="${productVO.pro_no}" class="col-md">
						<select size="1" name="quantity" class="${productVO.pro_no} col-md-3 offset-1 select">
							<c:forEach var="count" begin="1" end="10">
								<option>${count}</option>
							</c:forEach>
						</select>
						<input class="btn btn-primary col-md-5 offset-1" type="button" name="shopping" value="放入購物車"> 
						<input type="hidden" name="pro_no" value="${productVO.pro_no}">
						<input type="hidden" name="prc_no" value="${productVO.prc_no}">
						<input type="hidden" name="pro_name" value="${productVO.pro_name}">
						<input type="hidden" name="pro_price" value="${productVO.pro_price}"> 
					</FORM>
				</div>				
			</div>			
		</div>
		</c:forEach>		
	</div>	
</div>
<!-- 翻頁區 -->
<div class="row">
	<div class="col-md-12" style="text-align:center; margin:10px auto 30px;">
		<%@ include file="page2.file" %>
	</div>
</div>
<!-- 跳窗1 -->
<c:if test="${openModal != null}">
	<div class="modal" id="basicModal" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
	  <div class="modal-dialog modal-xl" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	        <h5 class="modal-title">商品詳情</h5>
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	      <!-- ==========================以下為原listOne內容================================= -->
			<jsp:include page="listOneShopping.jsp"/>
	      <!-- ==========================以上為原listOne內容================================= -->	      
	      </div>
	      
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>		
	<script>
		$("#basicModal").modal({
			  show: true
			});		
	</script>
</c:if>

<!-- 跳窗2 -->
<c:if test="${openModal2 != null && total_price >0}">
	<div class="modal" id="basicModal2" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
	  <div class="modal-dialog modal-xl" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	        <h5 class="modal-title">購物車詳情</h5>
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	      <!-- ==========================以下為原listOne內容================================= -->
			<jsp:include page="Cart2.jsp"/>
	      <!-- ==========================以上為原listOne內容================================= -->	      
	      </div>
	      
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" name="checkCart">結帳</button>
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>		
	<script>
		$("#basicModal2").modal({
			  show: true
			});		
	</script>
</c:if>

<script>
	// 送出確認
	$('[name=checkCart]').click(function(){
		$('[name=checkForm]').submit();
	});	
</script>

<script>
	// 加入購物車
	var pro_no,prc_no,pro_name,pro_price,quantity;
	$('[name=shopping]').click(function(){
		pro_no=$(this).parent().find("input[name=pro_no]").val();
		prc_no=$(this).parent().find("input[name=prc_no]").val();
		pro_name=$(this).parent().find("input[name=pro_name]").val();
		pro_price=$(this).parent().find("input[name=pro_price]").val();
		quantity=$(this).parent().find("select[name=quantity]").val();
		
		$.ajax({
			url: '<%=request.getContextPath()%>/shopping.do',
			type: "POST",
			data: {
				action : 'ADD_AJAX',
				quantity : quantity,
				pro_no : pro_no,
				prc_no : prc_no,
				pro_name : pro_name,
				pro_price : pro_price,
			},
			dataType: "text",
			error: function(xhr){
				console.log(xhr);
			},
			success: function(data){
				var url = "<%=request.getContextPath()%>/shopping.do?whichPage=<%=whichPage %>&action=getCart_Display";
				$(".total_amount").empty().append("<a></a>");				
				$(".total_amount a").text("$"+data).attr("href",url);
			}
		});
	});
</script>
</div>
<!-- 首頁註腳 -->
<%@ include file="/front-end/homepage/footer.file"%>	
<!-- 首頁註腳 -->

</body>

<script>
//測試websocket
//for websocket接資料
var MyPoint = "/product";
var host = window.location.host;
var path = window.location.pathname;
var webCtx = path.substring(0, path.indexOf('/', 1));
var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
var webSocket;

function connect() {
	// create a websocket
	webSocket = new WebSocket(endPointURL);

	webSocket.onopen = function(event) {
		console.log("WebSocket Connected");
	};

	webSocket.onmessage = function(event) {
		// 接收到的訊息用sweet alert推送
		var productVO = JSON.parse(event.data);
		var pro_no = productVO.pro_no;
		var pro_name = productVO.pro_name;
		var url = "<%=request.getContextPath()%>/shopping.do?whichPage=<%=whichPage %>&action=getOne_For_Display&pro_no=";
		url += pro_no;
		swal({
			title : "燒燙訊息通報",
			text : "新商品: "+pro_name+"上架了!!!",
			icon : "warning",
			buttons : {
				button1 : {
				text : "前往購物",
				value : true,
				},
				button2 : {
				text : "取消",
				value : false,
				}
				},
			}).then(function(value){   //value是按鍵的value可以設定跳轉位置
			if(value){
				window.location.href = url;
			}
			else{
			}
		});
	};

	webSocket.onclose = function(event) {
		console.log("WebSocket Disconnected");
	};
}

function disconnect() {
	webSocket.close();
}

</script>

</html>