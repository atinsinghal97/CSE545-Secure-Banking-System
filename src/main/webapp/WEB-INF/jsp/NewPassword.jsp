<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<meta charset="ISO-8859-1">
<body>
<div id="page-content" align="center" class="col-md-12">
			<div class="panel panel-primary">
  				<div class="panel-heading">
    				<h3 class="panel-title">New Password</h3>
 				 </div>
	  			 <div class="panel-body">
					<form id="NewPassword" class="form-horizontal" action="/change_password" method="post">
			  			<fieldset>
			  			<div class="form-group">
						      <label for="newpassword" class="col-lg-2 control-label">New Password</label>
						      <div class="col-lg-5">
						        <input type="password" class="form-control" id="newpassword" name="newpassword" placeholder="New Password" required>
						      </div>
						    </div>
						    <div class="form-group">
						      <label for="confirmpassword" class="col-lg-2 control-label">Confirm Password</label>
						      <div class="col-lg-5">
						        <input type="password" class="form-control" id="confirmpassword" name="confirmpassword" placeholder="Confirm Password" required>
						      </div>
						    </div>
						    <div class="form-group">
						      <div class="col-lg-7 col-lg-offset-2">
						      	<button id="new_password" name="action" value="reset_password">Change Password</button>
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
