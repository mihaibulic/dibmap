<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<% 
		ServletContext sc = getServletContext();
		String len = sc.getInitParameter("passwordLength"); 
	%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>dibmap</title>

    <link rel="shortcut icon" href="images/favicon.ico" />

    <link rel="stylesheet" type="text/css" href="css/reset.css" />
    <link rel="stylesheet" type="text/css" href="css/font.css" />
    <link rel="stylesheet" type="text/css" href="css/global.css" />
	
	<script type="text/javascript" src="scripts/load.js"></script>
	<script type="text/javascript">
		loadJS(new Array('scripts/jquery-1.7.2.min.js', 
						 'scripts/util.js', 'scripts/hashmap.js', 
						 'scripts/form.js', 'scripts/index.js'));
	</script>
</head>
<body>
	<div id="reg_form" >
		<span id="hint">mouseover fields for hints</span>
		<form action="#" method="POST">
			<input type="text" id="name" value="Name" 
				onfocus="clearDefault('Name')" onblur="validateNotBlank()" onmouseover="showTooltip('required')" onmousemove="changeTooltipPosition()" onmouseout="hideTooltip()"/>
			<input type="text" id="email" value="Email" 
				onfocus="clearDefault('Email')" onblur="validateEmail()" onmouseover="showTooltip('must be a valid address')" onmousemove="changeTooltipPosition()" onmouseout="hideTooltip()"/>
			<input type="text" id="password" value="Password" 
				onfocus="makePassword()" onblur="validateLength(<%=len%>)" onmouseover="showTooltip('must be at least <%=len %> characters long')" onmousemove="changeTooltipPosition()" onmouseout="hideTooltip()"/>
			<input type="text" id="description" value="Description" 
				onfocus="clearDefault('Description')" onblur="validateNotBlank()" onmouseover="showTooltip('tell us a bit about yourself')" onmousemove="changeTooltipPosition()" onmouseout="hideTooltip()"/>
			<input type="submit" id="register" value="Sign up" class="disabled button" disabled="disabled" />
		</form>
		<div id = "content"></div>
	</div>
</body>
</html>