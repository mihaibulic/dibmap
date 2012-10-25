<link rel="stylesheet" type="text/css" href="/css/welcome_header.css" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="welcome" class="visible">
    <img id="welcome_logo" src="/images/text_logo_trans.png" width="300px"></img>
    <form id="welcome_form" action="/open/home/Login" method="POST">
	    <table id="welcome_table">
            <tr>
                <td colspan="2">
                	<input id="login_email" type="text" name="email" value="Email"/>
                </td>
            </tr>
            <tr>
                <td>
                	<input id="login_password" type="text" name="password" value="Password"/>
                </td>
                <td>
                	<input id="login_button" class="disabled button" disabled="disabled" type="submit" value="Log In"/>
                </td>
            </tr>
            <tr>
                <td>
                	<a href="forgot_pw.php">Forgotten password?</a>
               	</td>
                <td>
	                <p>
	                    <input type="checkbox" name="remember" value="yes">
	                    <label>Remember?</label>
	                </p>
                </td>
            </tr>
			<c:if test="${param.error=='invalid'}">	    			
		    	<tr>
		    		<td colspan="2">
		    			<p class="error">
		    				Your email or password isn't right
		   				</p>
		    		</td>
		    	</tr>
			</c:if>
	    </table>
    </form>
</div>

