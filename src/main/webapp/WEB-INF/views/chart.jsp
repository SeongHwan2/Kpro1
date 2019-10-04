<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
 <head>
 <link rel="stylesheet" href="/resources/css/chart.css">
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
 <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
 <script type="text/javascript" src="/resources/js/chart.js"></script>
 <script type="text/javascript">    
$(document).ready(function() {
    var fileName = '<%=request.getAttribute("fileName") %>';
    var nickName = '<%=request.getAttribute("nickName")%>';
    $("#piechart").css("display", "none");
//  console.log(nickName + "----" + fileName);
    dataParser(nickName, fileName, index);
    click(nickName, fileName);
});   
  </script>
  
  <title>Test Chart</title>
  </head>
  <body>
  	<div id="loading">
  		<h1>결과 분석 중입니다.</h1>
  		<img src="/resources/img/tenor.gif" style="width:100%; height: 500px;">
  	</div>
    <div id="piechart" style="width: 100%; height: 700px;"></div>
    <form action="">
    	<button type="button" id="person">인원별</button>
    	<button type="button" id="time">시간대별</button>
    	<button type="button" id="keyword">키워드별</button>
    	<button type="button">홈</button>
    </form>
  </body>
</html>