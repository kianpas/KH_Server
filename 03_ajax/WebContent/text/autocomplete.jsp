<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

</head>
<body>

	<div class="ui-widget">
		<label for="name">Tags: </label> <input id="name">
	</div>

<h1 class="selected"></h1>


</body>
<script>
    $("#name").autocomplete({
    	source:function(request, response){
    	$.ajax({
    		 
    	    	  url:"<%=request.getContextPath()%>/autocomplete",
				data : {
					search : request.term
				},
				success : function(data) {
					
					
					
					
					
					
					console.log(data);
					var arr = data.split("\n");
					
					console.log(arr);
					//arr.slice(0, arr.length -1);
					arr.splice(arr.length -1, 1); //마지막인덱스 하나 제거 원본 변경
					console.log(arr);
					
					arr = $.map(arr, function(name){
						return {
							label:name, //노출텍스트
							value:name // 내무적 처리될값
						}
					})
					console.log(arr);
					//콜백함수 호출
					response(arr);
				},
				error : function(xhr, status, err) {
					console.log(xhr, status, err);
				}

			})
		},
		select:function(event, selected){
			console.log(event);
			console.log(selected);
			
			$("h1.selected").html(selected.item.label);
		}
	});
</script>
</html>