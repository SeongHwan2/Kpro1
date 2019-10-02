<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
 <head>
 <link rel="stylesheet" href="/resources/css/chart.css">
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
   <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
   <script type="text/javascript">
   		
   		var data2 = [];
    	  function dataParser(nickName, fileName) {
         	  $.post("/analy", {"nickName" : nickName, "fileName" : fileName} , function(data){
         		  if(data != null){
        			  console.log(data.result);
        		  }
         		
 				console.log("성공");
 				$("#loading").addClass("dn");
 				$("#piechart").css("display", "block");
        	  }) 
 <%--       		  data2 = `<%= request.getAttribute("result")%>`;
        	  console.log(data2);
        	  var data3 = JSON.parse(data2);
        	  console.log(data3); --%>
        	  console.log(nickName + "-----" + fileName);
          };
    	  
          google.charts.load('current', {'packages':['corechart']});
          google.charts.setOnLoadCallback(drawChart);

          function drawChart() {

            var data = google.visualization.arrayToDataTable([
              ['Person', 'Speakers'],
              ['nickname1', 170],
              ['nickname2', 35],
              ['nickname3', 28],
              ['nickname4', 27],
              ['nickname5', 76]
            ]);

            var options = {
              title: '구성원별 채팅 비율',
              pieHole: 0.3,
              chartArea: {left:0,top:0,width:"100%",height:"100%"},
              width: 900,
              heigth: 500,
            };

            var chart = new google.visualization.PieChart(document.getElementById('piechart'));

            chart.draw(data, options);
          }
          
      $( document ).ready(function() {
    	  	var fileName = '<%=request.getAttribute("fileName") %>';
    	  	var nickName = '<%=request.getAttribute("nickName")%>';
    	  	$("#piechart").css("display", "none");
//    	  	console.log(nickName + "----" + fileName);
    		dataParser(nickName, fileName); 
      });
      
  </script>
  
  <title>Test Chart</title>
  </head>
  <body>
  	<div id="loading">
  		<h1>결과 분석 중입니다.</h1>
  		<img src="/resources/img/tenor.gif" style="width:900px; height: 500px;">
  	</div>
    <div id="piechart" style="width: 900px; height: 500px;"></div>
    <canvas id="myChart"></canvas>
  </body>
</html>