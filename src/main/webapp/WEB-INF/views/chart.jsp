<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
 <head>
 <link rel="stylesheet" href="/resources/css/chart.css">
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
 <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
 <script type="text/javascript">
   		var dataSet = [];
   		var Pdata = [];
   		var map = new Map();
    	  function dataParser(nickName, fileName) {
    		  console.log("dataParser() >>> start!");
         	  $.post("/analy", {"nickName" : nickName, "fileName" : fileName} , function(data){
         		  if(data != null){
         			 console.log("DataSet Make 시작");
//        			  console.log(data);
//        			  console.log(data.result);
        			  Pdata = data.result
//        			  console.log(Pdata);
//        			  console.log(Pdata.length);
//	       			  console.log(data.result.get("key"));
//       			  console.log(Pdata[0]);	
          			  for(var i = 0; i < Pdata.length; i++){
//        				  console.log(Pdata[i]);
//        				  console.log(Pdata[i].length);
//						  console.log(Object.keys(Pdata[i]).length);
//        				  console.log(Pdata[i].key);
//       				  console.log(Pdata[i].value);
//        				  console.log(map.get("key"));
 						  var d1 = [];
// 						  dataSet[0] = ['Person', 'TellCount'];
						  for(var j = 0; j < Object.keys(Pdata[i]).length; j++){
							  if(j == 0){
								  d1[0,j] = Pdata[i].key; 
							  }else {
								  d1[0,j] = parseInt(Pdata[i].value);
							  }
						  }
//						  console.log(d1);
//						  dataSet[i + 1] = d1;
						  dataSet[i] = d1;
        			  };
        			  
        		  };
        		console.log(dataSet);
 				console.log("DataSet Make 종료");
 				chartStart();
 				$("#loading").addClass("dn");
 				$("#piechart").css("display", "block");
        	  }); 
//        	  console.log(nickName + "-----" + fileName);
         	 console.log("dataParser() >>> End!");
          };
          
          function chartStart(){
        	  console.log("chartStart() >>> start!");
        	  google.charts.load('current', {'packages':['corechart']});
              google.charts.setOnLoadCallback(drawChart);
              console.log("chartStart() >>> End!");
          };
          

          function drawChart() {
        	console.log("drawChart() >>> start!");  
            var data = new google.visualization.DataTable(dataSet);
            data.addColumn('string', 'Person');
            data.addColumn('number', 'TellCount');
            
            data.addRows(dataSet); 
            
 	         /*  
              
            ];   */
            
            var options = {
              title: '구성원별 채팅 비율',
              pieHole: 0.3,
              width: 900,
              height: 700,
            };

            var chart = new google.visualization.PieChart(document.getElementById('piechart'));

            chart.draw(data, options);
            console.log("drawChart() >>> End!"); 
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
    <div id="piechart" style="width: 100%; height: 700px;"></div>
    <canvas id="myChart"></canvas>
  </body>
</html>