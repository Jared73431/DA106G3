<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>
<title>IBM Transactions: Home</title>

<style>
  table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
   <tr><td><h3>IBM Transactions: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for IBM Transactions: Home</p>

<h3>資料查詢:</h3>
	
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li><a href='listAllTransactions.jsp'>List</a> all Transactions.  <br><br></li>
  
  
  <li>
    <FORM METHOD="post" ACTION="transactions.do" >
        <b>輸入交易編號 (如T000001):</b>
        <input type="text" name="trans_no">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>

  <jsp:useBean id="transactionsSvc" scope="page" class="com.transactions.model.TransactionsService" />
   
  <li>
     <FORM METHOD="post" ACTION="transactions.do" >
       <b>選擇交易編號:</b>
       <select size="1" name="trans_no">
         <c:forEach var="transactionsVO" items="${transactionsSvc.all}" > 
          <option value="${transactionsVO.trans_no}">${transactionsVO.trans_no}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/transactions/transactions.do" >
       <b>選擇會員編號:</b>
       <select size="1" name="trans_no">
         <c:forEach var="transactionsVO" items="${transactionsSvc.all}" > 
          <option value="${transactionsVO.trans_no}">${transactionsVO.member_no}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
     </FORM>
  </li>
  
  <li>
  	<FORM METHOD="post" ACTION="members.do">
  		 		
  		<a href='<%=request.getContextPath()%>/members/select_page_members.jsp'>
  		<input type="button" value="會員">
  		
  		</a>
  	</FORM>
  	</li>
</ul>


<h3>交易管理</h3>

<ul>
  <li><a href='addTransactions.jsp'>Add</a> a new Transactions.</li>
</ul>

</body>
</html>