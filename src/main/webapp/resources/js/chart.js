   		var dataSet = [];
   		var Pdata = [];
   		var statusL = [];
   		var map = new Map();
   		var index = 0;
    	  function dataParser(nickName, fileName) {
         	  $.post("/analy", {"nickName" : nickName, "fileName" : fileName}, function(data){
         		  if(data != null){
//         			 console.log(data);
//         			 console.log(data.result.get("status"));
         			 console.log("databaseCheck >> Start!");
         			 statusL = data.result;
         			 var status = statusL[0].status;
         			 console.log(status);
         			 if("true" == status) {
         				 $("#buttonC").removeClass("dn");
         			 };
//        			  Pdata = data.result;
//        			  console.log(Pdata);
//        			  console.log(Pdata.length);
//	       			  console.log(data.result.get("key"));
//       			  console.log(Pdata[0]);	
        		  };
//        		console.log(dataSet);
 				console.log("databaseCheck >> End");
// 				chartStart();
 				$("#loading").addClass("dn");
        	  }); 
//        	  console.log(nickName + "-----" + fileName);
          };
          
          function chartStart(index){
        	  console.log("chartStart() >>> start!");
        	  google.charts.load('current', {'packages':['corechart']});
        	  switch(index){
        	  case 0:
        		  google.charts.setOnLoadCallback(drawChart);
        		  break;
        	  case 1:
        		  google.charts.setOnLoadCallback(drawChart1);
        		  break;
        	  default:
        		  break;
        	  }
              
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
          
          function drawChart1() {
          	console.log("drawChart1() >>> start!");  
              var data = new google.visualization.DataTable();
              data.addColumn('string', '시간');
              data.addColumn('number', '대화횟수');
              
              data.addRows(dataSet); 
              
              var options = {
            	hAxis: {
            	     title: 'Time'
            	},
            	vAxis: {
            	     title: 'frequency'
            	},
                width: 900,
                height: 700,
                fontSize: 11,
              };

              var chart = new google.visualization.LineChart(document.getElementById('piechart'));

              chart.draw(data, options);
              console.log("drawChart1() >>> End!");
            };
          
          function click(nickName, fileName){
        	  $("form button:button").on("click", function(){
            	  index = $(this).index();
//            	  console.log("button click");
            	  console.log(index);
            	  if(index == 3){
            		  truncate();
            		  history.back();
            	  }else {
            		  dataSet = [];
            		  mDataSet(index);
            	  };
              });
          };
          
          function mDataSet(index) {
        	  console.log("DataSet Make Start!");
        	  $.post("/dataSelect", {"index" : index}, function(data){
//        		  console.log(data);
        		  Pdata = data.result;
    			  console.log(Pdata);
    			  var col1;
    			  var col2;
    			  
	  			  for(var i = 0; i < Pdata.length; i++){
	  				switch(index) {
	    			  case 0:
	    				  col1 = Pdata[i].nickname;
	    				  col2 = parseInt(Pdata[i].cnt);
	    				  break;
	    			  case 1:
	    				  col1 = Pdata[i].date;
	    				  col2 = parseInt(Pdata[i].cnt);
	    				  break;
	    			  }
					  console.log(Pdata[i]);
					  console.log(Object.keys(Pdata[i]).length);
					  console.log(Pdata[i].nickname);
					  console.log(Pdata[i].cnt);
						  var d1 = [];
					  for(var j = 0; j < Object.keys(Pdata[i]).length; j++){
						  if(j == 0){
							  d1[0,j] = col1; 
						  }else {
							  d1[0,j] = col2;
						  }
					  }
					  console.log(d1);
					  dataSet[i] = d1;
				  };
				  console.log(dataSet);
				  chartStart(index);
				  $("#piechart").css("display", "block");
        	  });
          }
          
          function truncate() {
        	  $.post("/tr", function(){
        		  console.log("truncate!");
        	  })
          }