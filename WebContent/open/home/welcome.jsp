<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/support/head.jsp" %>
	<link rel="stylesheet" type="text/css" href="/css/form.css" />
</head>
<body>
	<%@ include file="/support/welcome_header.jsp" %>		

	<div id="reg_form" class="visible">
		<h1>Sign up</h1>
		<form action="/open/create/Account" method="POST">
			<ul>
				<li class="message">
					<c:out value="${message}"/>
				</li>
				<c:forEach var="e" items="${errors}">
					<li class="error">
						<c:out value="${e}"/>
					</li>
				</c:forEach>
			</ul>
			<input type="text" name="name" id="name" value="Name" /><br/>
			<input type="text" name="email" id="email" value="Email" /><br/>
			<input type="text" name="password" id="password" value="Password" /><br/>
			<input type="text" name="description" id="description" value="Description" /><br/>
			<input type="submit" id="register" value="Sign up" class="disabled button" disabled="disabled" />
		</form>
		<div id="content"></div>	
	</div>
	
	<%@ include file="/support/footer.jsp" %>
	
	<script type="text/javascript" src="/scripts/LAB.min.js"></script>
	<script type="text/javascript">	
		$LAB
		.script("/scripts/jquery-1.7.2.min.js")
		.script("/scripts/util.js")
		.script("/scripts/form.js")
		.wait(
			function ()
			{
				var login = new Form("login_button");
				login.addInput(id="login_email", tooltipMsg="", type="Email");
				login.addInput(id="login_password", tooltipMsg="", type="Password");
				
				var register = new Form("register");
				register.addInput(id="name", tooltipMsg="what's your name (required)", type="NotBlank");
				register.addInput(id="email", tooltipMsg="Must be an email address you can check (required)", type="Email");
				register.addInput(id="password", tooltipMsg="minimum 6 characters (required)", type="Password");
				register.addInput(id="description", tooltipMsg="Tell us a bit about yourself (required)", type="NotBlank");
			});
	</script>
</body>
</html>