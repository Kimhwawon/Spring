<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- /score라는 공총 주소를 쓰되
get, post접속으로 폼 접속인지, 결과 확인인지 나뉩니다. 
따라서 폼 접속은 이미 했다면 목적지는 공통주소인 /score가 되고
post방식으로 전송하도록 하면 결과페이지에 도달할 수 있습니다.  -->
<h1>성적 입력 창</h1>
<form action="/score" method="post">
		<input type="number" name="math" placeholder="수학" max="100" min="0"><br/>
		<input type="number" name="eng" placeholder="영어" max="100" min="0"><br/>
		<input type="number" name="lang" placeholder="언어" max="100" min="0"><br/>
		<input type="number" name="social" placeholder="사탐" max="100" min="0"><br/>
		<input type="number" name="computer" placeholder="컴퓨터" max="100" min="0"><br/>
		<input type ="submit" value="성적확인">
		
	</form>

</form>
</body>
</html>