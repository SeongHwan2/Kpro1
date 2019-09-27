<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>web2 home</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/resources/css/web2Home.css">
<script>
$(document).ready(function() {
	var pro = {};
	var imgUrl = "";
	var nick = "";
	var token ="";
	var loCheck = false;
	var storage = [];
	if(`<%= session.getAttribute("nick") %>` != 'null'){
		imgUrl = '<%= session.getAttribute("imgUrl") %>';
		nick = '<%= session.getAttribute("nick") %>' ;
		token = '<%= session.getAttribute("access_token") %>';
		console.log(nick + " : " +  imgUrl + " token : " + token);
		$(".profileC img").attr("src", imgUrl);
		$(".profileC p").text(nick);
		$("#lout input").val(token);
		$("#create2").removeClass("dn");
		$("#profile").removeClass("dn");
		$("article h2").text("Who am I");
		loCheck = true;
	}
	
	if(!loCheck) {
		$("#lout").addClass("dn");
	}else {
		$("#loin").addClass("dn");
	}
	
	function select() {
		$.post("/select", {"nick" : nick } , function(data){
			if(data != "") {
				console.log(data);
				storage = data.list;
				console.log(storage);
				console.log(storage.length);
				if(storage.length > 0){
					$(".list").empty();
					for(var i = 0; i < storage.length; i++) {
						var listR = '<li class="list-group-item">' + storage[i].title + '</li>' + 
								  '<div id="toggle">' + '</div>';
						$(".list").append(listR);
					}
					
					$(".list-group-item").on("click", function(){
						var index = $(".list-group-item").index(this);
						console.log(index);
						console.log(storage[index]);
						location.href = "/create2/?index=" + index;
					})
				}else if(storage.length == 0){
					var tag = '<li class="list-group-item">' + "업로드한 파일이 없습니다." + '</li>';
					$(".list").append(tag);										
				}
			} else {
				$(".list").empty();
				var tag = '<li class="list-group-item">' + '로그인이 필요합니다.' + '</li>';
				$(".list").append(tag);
			}
			
			
		})
	}
	
	$("#create2").on("click", function(){
		location.href = "/create2";
	})
	
	select();
});	
</script>
</head>
<body>
	<header>
		<h1>카카오대화분석</h1>
	</header>
	<nav>
		<form action="/loin" id="loin">
			<button type="submit">로그인</button>
		</form>
		<form action="lout" id="lout">
			<button type="submit">로그아웃</button>
			<input type="text" name="key" class="dn">
		</form>
	</nav>
	<section>
		<article class="Container">
		  <h2>로그인이 필요합니다</h2>
		     <div id="profile" class="profileC dn">
		       <img src="" width="75%" height="200px" alt="profilePhoto">
		       <div class="w3-container">
		       <p>nickName</p>
		       </div>
		     </div>   
		  <ul class="list">
		  </ul>
		  <button type="button" id ="create2" class="dn">파일등록</button> 
		</article>
	</section>
	<footer>푸터</footer>

</body>
</html>