<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>글쓰기</title>
	<!--  <link rel="stylesheet" href="/resources/css/main.css"> -->
	<script type="text/javascript" src="/resources/libs/ckeditor/ckeditor.js"></script>
	<style>
		body {margin: 45px 0 0 0;}
		
		input, textarea {
		width: calc(100% - 22px); padding: 10px; 
		border-radius: 4px; border: 1px solid #CCCCCC;
		}
		textarea {resize: none;}
		
		.container {width: 90%; margin: 0 auto;}
	</style>
	<script>
		function start() {
			CKEDITOR.replace('editor', {
				uiColor: '#FFFFFF',
				resize_enabled: false,
				toolbar: [
		/* 				 { name: 'clipboard', items: [ 'Undo', 'Redo' ] }
		 				,{ name: 'styles', items: [ 'Styles', 'Format' ] }
						,{ name: 'basicstyles', items: [ 'Bold', 'Italic', 'Strike', '-', 'RemoveFormat' ] }
						,{ name: 'paragraph', items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 
							'Blockquote', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ] }
		//				,{ name: 'links', items: [ 'Link', 'Unlink' ] }
		//				,{ name: 'insert', items: [ 'Image', 'EmbedSemantic', 'Table' ] }
		//				,{ name: 'editing', items: [ 'Scayt' ] }
						,{ name: 'colors', items: [ 'TextColor', 'BGColor' ] }
						,{ name: 'tools', items: [ 'Maximize' ] } */
						{ name: 'document', items: [ 'Source', '-', 'Save', 'NewPage', 'Preview', 'Print', '-', 'Templates' ] },
						{ name: 'clipboard', items: [ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo' ] },
						{ name: 'editing', items: [ 'Find', 'Replace', '-', 'SelectAll', '-', 'Scayt' ] },
						{ name: 'forms', items: [ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField' ] },
						'/',
						{ name: 'basicstyles', items: [ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'CopyFormatting', 'RemoveFormat' ] },
						{ name: 'paragraph', items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote', 'CreateDiv', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-', 'BidiLtr', 'BidiRtl', 'Language' ] },
						{ name: 'links', items: [ 'Link', 'Unlink', 'Anchor' ] },
						{ name: 'insert', items: [ 'Image', 'Flash', 'Table', 'HorizontalRule', 'Smiley', 'SpecialChar', 'PageBreak', 'Iframe' ] },
						'/',
						{ name: 'styles', items: [ 'Styles', 'Format', 'Font', 'FontSize' ] },
						{ name: 'colors', items: [ 'TextColor', 'BGColor' ] },
						{ name: 'tools', items: [ 'Maximize', 'ShowBlocks' ] },
						{ name: 'about', items: [ 'About' ] }
				]
			});
	    }
		
	</script>
</head>
<body onload="start()">
	<div class="container">
		<h1>글쓰기</h1>
		<form>	
			<p>
		   	<label>제목</label>
		   	</p>
		   	<input type="text" name="title">	  
		   	<p>
		   	<label>내용</label>
		   	</p>
		   <textarea id="editor" name="text"></textarea>
		   <button type="submit" formaction="/write/insert">확인</button>
		   <a href="/home2"><button type="button">목록</button></a>   	
		</form>
	</div>
</body>
</html>