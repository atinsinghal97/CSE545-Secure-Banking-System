<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<meta charset="ISO-8859-1">
<body>
	<div align="center" class="col-md-10" id="page-content">
		<div>
			<h3>Forgot Password</h3>
			<div>
				<form method="post" action="/forgotpassword" id="ForgotPassword">
					<fieldset>
						<div class="col-lg-6">
							<label class="control-label col-lg-2" for="username">Username:
							</label> <input class="form-control" name="username" type="text"
								id="username" placeholder="Username" required>
						</div>
						<div class="col-lg-6">
							<label class="control-label col-lg-2" for="email">E-mail:
							</label> <input class="form-control" name="email" type="email" id="email"
								placeholder="E-mail" required>
						</div>
						<br>
						<div>
							<div class="col-lg-6 col-lg-offset-2">
								<button name="action" id="forgot_password"
									value="forgot_password">Request OTP</button>
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