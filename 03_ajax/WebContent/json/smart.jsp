<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>스마트 가전센터</title>
<script src="<%= request.getContextPath()%>/js/jquery-3.6.0.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.1.0/chart.js" integrity="sha512-LlFvdZpYhQdASf4aZfSpmyHD6+waYVfJRwfJrBgki7/Uh+TXMLFYcKMRim65+o3lFsfk20vrK9sJDute7BUAUw==" crossorigin="anonymous"></script>
<script src="<%= request.getContextPath()%>/js/moment.js"></script>
  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<style>
div.container {
	float: left;
	width: 29%;
	height: 300px;
	margin: 10px;
	padding: 10px;
	background: lightgrey;
	text-align: center;
}

table {
	border: 1px solid;
	margin: auto;
}

td, th {
	border: 1px solid;
}

span#time {
	text-decoration: underline;
	margin: 15px;
	display: block;
}
</style>
</head>
<body>
	<h1>스마트 가전 센터 주문페이지</h1>
	<p>
		1. ajax를 이용해서 제품주문을 처리하고, 실시간 판매현황(최근5건)에 출력하세요. <br /> 2. 10초마다
		판매랭킹리스트(상위5개제품과 누적판매량)를 갱신하는 ajax함수를 작성하세요. <br /> (bonus) 현재시각을
		표시하세요.
	</p>
	<div id="order-container" class="container">
		<h2>주문</h2>
		<table>
			<tr>
				<th>제품명</th>
				<td><select name="pname" id="pname" required>
						<option value="iPhoneX">iPhoneX</option>
						<option value="iPhone7">iPhone7</option>
						<option value="iPhone7Plus">iPhone7Plus</option>
						<option value="GalaxyNote8">GalaxyNote8</option>
						<option value="Galaxy8">Galaxy8</option>
						<option value="샤오미맥스2">샤오미맥스2</option>
						<option value="LGV30">LGV30</option>
				</select></td>
			</tr>
			<tr>
				<th>주문량</th>
				<td><input type="number" id="amount" name="amount" min="1"
					value="1" required></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button"
					id="btn-order" value="주문" /></td>
			</tr>
		</table>
	</div>
	<div class="container">
		<h2>주문리스트</h2>
		<div id="order-list"></div>
	</div>
	<div class="container">
		<h2>인기순위</h2>
		<span id="time"></span>
		<div id="rank-list"></div>
	</div>
	
	<div>
      <canvas id="myChart" width="400" height="400"></canvas>
    </div>
    
    <div id="chart_div"></div>
    <input type="button"
					id="btn-order111" value="주문" />
    <script>
    
    var chartLabels = [];
    var chartAmount=[];
    var chartDate = [];
    $("#btn-order").click(function(){
    	chartLabels = [];
    	chartAmount=[];
    	chartDate = [];
    	$.ajax({
    	url : "<%=request.getContextPath() %>/smart",

    	success:function(dat){
    		//console.log(data);
    		//console.log(data[0]);
    		
    		$.each(dat, function(key, value){
    			
    			//console.log(key);
    			//console.log(value.name);
    			//console.log(value.amount);
    			console.log(value.date);
    			chartLabels.push(value.name);
    			chartAmount.push(value.amount);
    			chartDate.push(value.date);
    			//console.log(chartLabels);
    			//console.log(chartAmount[0]);	
    			 //console.log(chartDate[0]);
    				
    		
    		});
    		
    		console.log(typeof chartDate[0]);
    		console.log(Number(chartDate[0]));
    		const DATA_COUNT = 7;
    		const NUMBER_CFG = {count: DATA_COUNT, rmin: 1, rmax: 1, min: 0, max: 100};

    		const labels = chartLabels[0];
    		const data = {
    		  labels: labels,
    		  datasets: [
    		    {
    		      label: chartLabels[0],
    		      data: [{x:1, y:chartAmount[0]}],
    		      borderColor: 'rgb(255, 99, 132)',
    		      backgroundColor: 'rgb(255, 99, 132)',
    		    }
    		    
    		  ]
    		};
    			
    		const config = {
    				  type: 'scatter',
    				  data: data,
    				  options: {
    				    responsive: true,
    				    plugins: {
    				      legend: {
    				        position: 'top',
    				      },
    				      title: {
    				        display: true,
    				        text: 'Chart.js Scatter Chart'
    				      }
    				    }
    				  },
    				};
    		
    		 var myChart = new Chart(
    				    document.getElementById('myChart'),
    				    config
    				  );
    		
    		
    	},
    	error:function(xhr, status, err){
    		console.log(xhr, status, err);
    	}
    	});
    });
    
    
</script>
<script>
function drawDots() {  
    var data = new google.visualization.DataTable();
    data.addColumn('number', 'a');
    data.addColumn('number', 'd');

    dots = new Array;
    dots.push([5, 50]);
    dots.push([7,60]);

	$.ajax({
    	url : "<%=request.getContextPath() %>/smart",
    	success:function(dat){
        $.each(dat, function(id, num)
        {
            $.each(num, function(i, e)
            {           
                dots.push([e.a, e.d]);
            });
        }); 
    	
	console.log(data);
        data.addRows(dots);

        var options = {
            title: '',
            hAxis: {title: 'Data 1', minValue: 0, maxValue: 100},
            vAxis: {title: 'Data 2', minValue: 0, maxValue: 100},
            legend: 'none'
        };

        var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));

        chart.draw(data, options);
    } 
});
}
</script>
<script>

var chartLabels = [];
var chartAmount=[];
var chartDate = [];
$("#btn-order111").click(function(){
	chartLabels = [];
	chartAmount=[];
	chartDate = [];
	$.ajax({
	url : "<%=request.getContextPath() %>/smart",

	success:function(data){
		//console.log(data);
		//console.log(data[0]);
		
		$.each(data, function(key, value){
			
			//console.log(key);
			//console.log(value.name);
			//console.log(value.amount);
			console.log(value.date);
			chartLabels.push(value.name);
			chartAmount.push(value.amount);
			chartDate.push(value.date);
			//console.log(chartLabels);
			//console.log(chartAmount[0]);	
			 //console.log(chartDate[0]);
				
		
		});
		var lineChartData = {
                labels : "라벨",
                datasets : [ {
                    label : "스마트폰 판매수",
                    backgroundColor:"#bfdaf9",
                    borderColor: "#80b6f4",
                    pointBorderColor: "#80b6f4",
                    pointBackgroundColor: "#80b6f4",
                    pointHoverBackgroundColor: "#80b6f4",
                    pointHoverBorderColor: "#80b6f4",
                    data : [
                    	{
                    	x:chartDate[0],
                    	 y: chartAmount[0]},
                    	     
                    	     {x:chartDate[1],
                        	     y: chartAmount[2]},
                    	     
                    	     ]
                } ]

            }
	  console.log(lineChartData);
	  
		    var ctx = document.getElementById("canvas").getContext("2d");
		    LineChartDemo = Chart.Scatter(ctx, {
		        data : lineChartData,
		        options : {
		            scales : {
		            	x: {
		                    type: 'linear',
		                    position: 'bottom'
		                  }
		            }
		        }
		    });
	
	},
	error:function(xhr, status, err){
		console.log(xhr, status, err);
	}
	});
});

</script>

</body>
</html>