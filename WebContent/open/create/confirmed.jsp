<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/support/head.jsp" %>
</head>
<body>
	<%@ include file="/support/header.jsp" %>
	
	<div class="visible">
		<p>
			Has my account has been confirmed: <b>${confirmed}</b>
		</p>
	</div>
	
	<%@ include file="/support/footer.jsp" %>
	<script type="text/javascript" src="/scripts/LAB.min.js"></script>
	<script type="text/javascript">
		$LAB
		.script("/scripts/util.js");
	</script>
</body>
</html>