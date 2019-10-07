   		var dataSet = [];
   		var Pdata = [];
   		var map = new Map();
   		var index = 0;
    	  function dataParser(nickName, fileName, index) {
    		  console.log("dataParser() : " + index +  ">>> start!");
         	  $.post("/analy", {"nickName" : nickName, "fileName" : fileName, "index" : index}, function(data){
         		  if(data != null){
         			 console.log("DataSet Make Start!");
         			 console.log(data);
//        			  console.log(data);
//        			  console.log(data.result);
//        			  Pdata = data.result;
//        			  console.log(Pdata);
//        			  console.log(Pdata.length);
//	       			  console.log(data.result.get("key"));
//       			  console.log(Pdata[0]);	
//          			  for(var i = 0; i < Pdata.length; i++){
//        				  console.log(Pdata[i]);
//        				  console.log(Pdata[i].length);
//						  console.log(Object.keys(Pdata[i]).length);
//        				  console.log(Pdata[i].key);
//       				  console.log(Pdata[i].value);
//        				  console.log(map.get("key"));
 						  var d1 = [];
// 						  dataSet[0] = ['Person', 'TellCount'];
//						  for(var j = 0; j < Object.keys(Pdata[i]).length; j++){
//							  if(j == 0){
//								  d1[0,j] = Pdata[i].key; 
//							  }else {
//								  d1[0,j] = parseInt(Pdata[i].value);
//							  }
//						  }
//						  console.log(d1);
//						  dataSet[i + 1] = d1;
//						  dataSet[i] = d1;
//        			  };
        			  
        		  };
//        		console.log(dataSet);
 				console.log("DataSet Make End");
// 				chartStart();
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
            var data = new google.visualization.DataTable();
            data.addColumn('string', '닉네임');
            data.addColumn('number', '대화 횟수');
            
            data.addRows(dataSet); 
            
            var options = {
              title: '구성원별 채팅 비율',
              pieHole: 0.3,
              width: 900,
              height: 700,
              fontSize: 11,
            };

            var chart = new google.visualization.ColumnChart(document.getElementById('piechart'));

            chart.draw(data, options);
            console.log("drawChart() >>> End!");
          };
          
          function click(nickName, fileName){
        	  $("form button:button").on("click", function(){
            	  index = $(this).index();
//            	  console.log("button click");
            	  console.log(index);
            	  if(index == 3){
            		  history.back();
            	  }else {
            		  dataParser(nickName, fileName, index);
            	  };
            	  
            	  
              });
          };