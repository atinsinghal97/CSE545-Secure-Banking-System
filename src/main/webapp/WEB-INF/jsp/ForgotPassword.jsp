<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<meta charset="ISO-8859-1">
<body>
<div id="page-content" class="col-md-12" align="center">
			<div>
  				<div>
    				<h3> <b>Forgot Password</b></h3>
 				 </div>
	  			 <div>
					<form id="ForgotPassword" action="/forgotpassword" method="post">
			  			<fieldset>
			  			<div>
						      <label for="username" class="col-lg-2 control-label">User's name</label>
						      <div class="col-lg-5">
						        <input type="text" class="form-control" id="username" name="username" placeholder="User's Name" required>
						      </div>
						    </div>
						    <div>
						      <label for="email" class="col-lg-2 control-label">E-mail</label>
						      <div class="col-lg-5">
						        <input type="email" class="form-control" id="email" name="email" placeholder="E-mail" required>
						      </div>
						    </div>
						    <div>
						      <div class="col-lg-7 col-lg-offset-2">
						      	<button id="forgot_password" name="action" value="forgot_password">Request OTP</button>
						      	<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
						      </div>
						    </div>
			  			</fieldset>
			  			</form>
			  			</div>
			  			</div>
			  			</div>
			  			
</body>
</html>