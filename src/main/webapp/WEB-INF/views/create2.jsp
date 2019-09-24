<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/resources/css/create.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
	$( document ).ready(function() {
		var nickname = '<%=session.getAttribute("nick") %>';
		var index = -1;
		var bIndex = -1;
		
		/* $('input[name=txt]').keydown(function() {
			if (event.keyCode === 13) {
			    event.preventDefault();
			};
		}); */
			  
		
		if(location.search != ""){
			index = location.search.replace("?", "").split("=")[1];
			console.log(index);
			$("#insert").addClass("dn");
			$("#fList").removeClass("dn");
			$("input[name=file]").addClass("dn");
			$("textarea[name=txt]").addClass("di");
			$(".di").attr("disabled", true);
			$("h2").text("learnMore");
		}
		
		$("input[name=nickName]").val(nickname);
		
	    $("#list").on("click", function(){
	    	console.log("list!!");
	    	location.href="/home2"
	    })
	    
	    $(".buttonC button:button").on("click", function(){
	    	bIndex = $(".buttonC button:button").index(this);
	    	console.log(bIndex);
	    })
	    
	    function select(){
			$("tbody").empty();
			$.ajax({
				url: "/select",
				type: "POST"
			}).done(function(data){
				console.log("성공");
				console.log(data);
				console.log(data.list);
				storage = data.list;
				console.log(storage);
				if(index != -1){
					console.log(index);
					$('input[name=title]').val(storage[index].title);
					$('textarea[name=txt]').val(storage[index].txt);
					if(storage[index].fileName != ""){
						$("#download").removeClass("dn");
						$("input[name=url]").val(storage[index].fileUrl);
						$("input[name=fileName]").val(storage[index].fileName);
					}
					if (storage[index].nickName == nickname || nickname == "주인장" || nickname == "관리자"){
						$("#update").removeClass("dn");
						$("#delete").removeClass("dn");
						$("textarea[name=txt]").attr("disabled", false);
					}
					var filename = (storage[index].fileName!=null)?storage[index].fileName:"업로드된 파일 없음";
					$('#fList').val(filename);
				}
			})
		}
	    
	    $('#update').click(function(){
	    	  $.ajax({
	    		url: '/ud',
	    		type: 'POST',
	    		data: {
	    			"txt" : $("textarea[name=txt]").val(),
	    			"no" : 	storage[index].no,
	    			"type" : bIndex
	    		}
	    	}).done(function(data){
	    		if(data.status == "1"){
	    			alert("수정성공");
	    			$("textarea[name=txt]").attr("disabled", true);
	    		}else {
	    			alert("수정실패 : 수정할 내용을 입력하세요!");
	    		}
	    	})
	    })
	    
	    $("#delete").click(function(){
	    	 $.ajax({
		    		url: '/ud',
		    		type: 'POST',
		    		data: {
		    			"no" : 	storage[index].no,
		    			"type" : bIndex,
		    			"nickName" : nickname
		    		}
		    	}).done(function(data){
		    		if(data.status == "1"){
		    			alert("삭제성공");
		    			location.href="/home2";
		    		}else {
		    	
		    		}
		    	})
	    })
	    
	    select();  
	    
	});
</script>
</head>
<body>
<%-- <%=session.getAttribute("nick") %> --%>
	<header>
		<h2>Input & Output</h2>
	</header>
	<section>
		<form id="" action="/insert2" method="POST" enctype="multipart/form-data">
			<p>
			<label>제목</label>
			<input type="text" name="title" placeholder="제목을 입력하세요" class="tb di" required="required"></p>
			<p>
			<label>내용</label>
			<textarea name="txt" class="textbox" required="required"></textarea>
			</p>
			<div>
			<label>첨부파일</label>
			<input type="file" multiple="multiple" name="file" class="di"  accept="image/*, .txt, .pdf, .opd">
			<input type="text" id="fList"class="dn di">
			</div>
			</p>
			<input type="text" name="nickName" class="dn">
			<div class="buttonC">
				<button type="button" id="list">목록</button>
				<button type="submit" id="insert">등록</button>
				<button type="button" id="delete" class="dn">삭제</button>
				<button type="button" id="update" class="dn">수정</button>
			</div>
		</form>
		<form action="/download" method="post">
			<button type="submit" id="download" class="dn">파일다운</button>
			<input type="text" name="url" class="dn">
			<input type="text" name="fileName" class="dn">
		</form>
	</section>
</body>
</html>