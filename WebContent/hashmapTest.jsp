<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="scripts/hashmap.js"></script>
<script type="text/javascript">
	var hash = new HashMap();
	
	hash.put("test", "result");
	hash.put("apple", "orange");
	hash.put("4x4", "16");
	hash.remove("test");
	hash.put("4x4", "16");
	hash.put("apple", "32");
	hash.put("poop", "fartz");
	hash.remove("test");
	hash.remove("test");
	hash.remove("poop");
	alert(hash.print());
	
	/*
		4
		3
		2
		orange
		16
		32
	*/
	
</script>

</head>
<body>

</body>
</html>