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
						<div class="form-group">
							<p>${message}</p>
						</div>

	  			 <div>
					  <form id="ForgotPassword" action="/forgot_password" method="post">
			  			<fieldset>
			  			  <div>
						      <label for="username" class="col-lg-2 control-label">User's name</label>
						      <div class="col-lg-5">
						        <input type="text" class="form-control" id="username" name="username" placeholder="User's Name" required>
						      </div>
						    </div>
						    
						    <div>
                  <label for="email">Email</label><br>
                  <div>
						        <input type="radio" id="email" name="mode" value="1">
						      </div>
						    </div>
						    <div>
                  <label for="sms">SMS</label><br>
                  <div>
						        <input type="radio" id="sms" name="mode" value="0">
						      </div>
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
	  			
	  			<div class="panel-body">
					  <form id="ResetPassword" action="/reset_password" method="post">
			  			<fieldset>
			  			
						    <div>
						      <label for="otp" class="col-lg-2 control-label">OTP</label>
						      <div class="col-lg-5">
						        <input type="text" class="form-control" id="token" name="token" placeholder="OTP">
						      </div>
						    </div>
						    
						    <div>
						      <div class="col-lg-7 col-lg-offset-2">
						      	<button id="reset_password" name="action" value="reset_password">Change Password</button>
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
