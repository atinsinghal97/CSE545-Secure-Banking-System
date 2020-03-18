<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<meta charset="ISO-8859-1">
<body>
	<div align="center" class="col-md-10" id="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Set New Password</h3>
			</div>
			<div class="panel-body">
				<form method="post" class="form-horizontal" id="NewPassword"
					action="/newpassword">
					<fieldset>
						<div class="col-lg-6 form-group">
							<label class="control-label col-lg-3" for="newpassword">Enter
								new password: </label> <input class="form-control" name="newpassword"
								type="password" id="newpassword" placeholder="New Password"
								required>
						</div>
						<div class="col-lg-6 form-group">
							<label class="control-label col-lg-3" for="confirmpassword">Re-enter
								Password: </label> <input class="form-control" name="confirmpassword"
								type="password" id="confirmpassword"
								placeholder="Re-enter Password" required>
						</div>
						<br>
						<div class="form-group">
							<div class="col-lg-6 col-lg-offset-2">
								<button value="new_password" name="action" id="new_password">Update</button>
								<input value="${_csrf.token}" name="${_csrf.parameterName}"
									type="hidden" />
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
</body>
</html>