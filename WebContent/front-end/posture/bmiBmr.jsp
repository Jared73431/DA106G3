<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.members.model.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TDEE計算</title>
 <link rel="stylesheet" href="<%=request.getContextPath()%>/js/vendors/bootstrap/css/bootstrap.min.css">
<link rel="icon" href="<%=request.getContextPath()%>/picture/logo.png">
<style>

span.body{
	margin: 3rem auto;
}
span#bmi, span#tdee, span#bmr {
	color: green;
	font-weight:bold;
}

</style>

</head>
<body>
<%@ include file="/front-end/homepage/nav.file"%>
<div id="listall">
	<div class="container">
		<div class="row">
		<div class="col-6">
		
			<h3>基礎代謝率是甚麼呢？</h3>
			<br>
			<p>你的身體肥胖指數要看BMI。要減肥就要算出「基礎代謝率(BMR)」，這可是決定你是否能成功減肥的關鍵。基礎代謝率是指人類在靜止不動的情況下(通常指的是躺著的時候)，身體一天可以消耗多少的熱量。</p>
			
			<p>基礎代謝率會受「年齡、體重、肌肉量、疾病、進食、環境溫度變化等」影響數字變化。</p>
			
			<p>通常不可逆的年紀越大，基礎代謝率會跟著下降，所以常聽較年長的人說「不吃東西呼吸都會胖！」或是「吃得比以前少，但是還是發胖」。</p>
			
			<p>另外還有一個常見的狀況，就是當我們體重減輕的時候，也會導致基礎代謝率下降。靠吃的很少在短時間瘦身減重，如果一回到正常吃東西的量，很快就會復胖回來。</p>
		
			<p><b>如果是想減肥減脂的人，建議一天減少10%-20%的每日消耗熱量</b><br>

			<b>如果是想增肌，建議一天增加5%-10%的每日消耗熱量</b></p>
			<p>只要人體累積短少約7700大卡，體重就能減輕1公斤。</p>
		
 
			
		</div>
		<div class="col-5 offset-1">
			<span class="body">
				<h5>計算小工具</h5>
				<br>
				<lebel for='sex'>性別*</lebel>
				<br>
					<select id='sex' placeholder='sex'>
					  <option value='male'>男生</option>
					  <option value='female'>女生</option>
					</select>
				<br>
				<lebel for='age'>年齡*</lebel>
				<br>
					<input type='number' id='age' placeholder='age'>
				<br>
				<lebel for='height'>身高(cm)*</lebel>
				<br>
					<input type='number' id='height' placeholder='height' min='50'>
				<br>
				<lebel for='weight'>體重(kg)*</lebel>
				<br>
					<input type='number' id='weight' placeholder='weight'>
			
				<button id='test'>計算</button>
				<br>
				<br>
				<p>你的基礎代謝率BMR: <span id='bmr'></span></p>
				<p>你的BMI: <span id='bmi'></span></p>
				<hr>
				<select id='exercise'>
				  <option value='1.2'>辦公室工作者，幾乎不運動</option>
				  <option value='1.375'>每週輕鬆的運動3-5天</option>
				  <option value='1.55'>每週中等強度的運動3-5天</option>
				  <option value='1.725'>每週高強度運動6-7天</option>
				  <option value='1.9'>勞力密集工作或每天訓練者</option>
				</select>
				
				<button id='test2'>計算</button>
				<br>
				<br>
				<p>你的每日總消耗熱量: <span id='tdee'></span></p>
			</span>
			</div>
		</div>
	</div>
</div>
<%@ include file="/front-end/homepage/footer.file"%>
</body>

<script src="<%=request.getContextPath()%>/js/vendors/jquery/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/popper/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vendors/bootstrap/js/bootstrap.min.js"></script>

<script>

$('#test').click(function(){

if($('#sex').val() === 'male'){
 var bmr = 66 + $('#age').val()*(-6.8) + $('#height').val()*5 + $('#weight').val()*13.7;
 bmr = Math.round(bmr * 10.0) / 10.0 ;
}else if($('#sex').val() === 'female'){
 var bmr = 655 + $('#age').val()*(-4.7) + $('#height').val()*1.8 + $('#weight').val()*9.6;
 bmr = Math.round(bmr * 10.0) / 10.0 ;
}

var bmi = $('#weight').val()/(($('#height').val()/100)*($('#height').val()/100));
bmi = Math.round(bmi * 10.0) / 10.0 ;

$('#bmr').text(bmr);
$('#bmi').text(bmi);

});

$('#test2').click(function(){
  console.log("bmr="+$('#bmr').text());
  var tdee = $('#exercise').val()*$('#bmr').text();
  tdee = Math.round(tdee * 10.0) / 10.0 ;
  $('#tdee').text(tdee);
})

  
</script>
</html>