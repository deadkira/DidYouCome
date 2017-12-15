<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	添加课程：
	<form action="${pageContext.request.contextPath }/addCourse" method="post">
		name:<input type="text" name="name"><br>
		teacherId:<input type="text" name="teacherId"><br>
		venueId:<input type="text" name="venueId"><br>
		year:<input type="text" name="year"><br>
		term:<input type="text" name="term"><br>
		startDate:<input type="text" name="startDate"><br>
		endDate:<input type="text" name="endDate"><br>
		startTime:<input type="text" name="startTime"><br>
		endTime:<input type="text" name="endTime"><br>
		whatDay:<input type="text" name="whatDay"><br>
		<button>添加</button>
	</form>
</body>
</html>