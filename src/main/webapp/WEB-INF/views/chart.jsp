<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
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
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart'));

        chart.draw(data, options);
      }
    </script>
  <title>Test Chart</title>
  </head>
  <body>
    <div id="piechart" style="width: 900px; height: 500px;"></div>
    <canvas id="myChart"></canvas>
  </body>
</html>