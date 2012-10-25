<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/support/head.jsp" %>
</head>
<body>
	<%@ include file="/support/header.jsp" %>

	<h1> Create Activity </h1>

	<div class="visible">
		<form method="POST" action="/create/Activity">
			<input type="hidden" name="creator" id="creator" value="${party_id}"/><br/>
			<input type="text" name="activity" id="activity" value="Activity ID"/><br/>
			<input type="text" name="location" id="location" value="Location ID"/><br/>
			<input type="text" name="intensity" id="intensity" value="Intensity (1-5)" /><br/>
			<input type="text" name="start_time" id="start_time" value="2012-08-20 16:00:00" /><br/>
			<input type="text" name="end_time" id="end_time" value="2012-08-20 18:00:00" /><br/>
			<input type="submit" id="register" value="Sign up" class="disabled button" disabled="disabled" />
		</form>
	</div>	

	<%@ include file="/support/footer.jsp" %>
	<script type="text/javascript" src="/scripts/LAB.min.js"></script>
	<script type="text/javascript">
		$LAB
		.script("/scripts/util.js");
	</script>
</body>
</html>