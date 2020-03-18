<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="js/EmployeeValidate.js"></script>
<script src="js/jquery.validate.js"></script>
<div class="content-wrapper">
	<div align="center" class="col-md-10" id="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Set New Password</h3>
			</div>
			<div class="panel-body">
				<form class="form-horizontal" id="SetPassword" method="post"
					action="/setpassword">
					<fieldset>
						<div class="col-lg-4 form-group">
							<label class="control-label col-lg-2" for="email">Username:
							</label> <input placeholder="User Name" name="username"
								class="form-control" id="username" type="text" required>
						</div>
						<div class="col-lg-4 form-group">
							<label class="control-label col-lg-2" for="password">Password:
							</label> <input placeholder="Password" name="password"
								class="form-control" id="password" type="password" required>
						</div>
						<div class="col-lg-4 form-group">
							<label class="control-label col-lg-2" for="confirmpassword">Confirm
								Password: </label> <input placeholder="Confirm Password"
								name="confirmpassword" class="form-control" id="confirmpassword"
								type="password" required>
						</div>
						<div class="col-lg-offset-2 col-lg-6 form-group">
							<button value="set_password" name="action" id="set_password">
								Submit</button>
							<button class="btn btn-default" type="reset">Reset</button>
							<input value="${_csrf.token}" name="${_csrf.parameterName}"
								type="hidden" />
							<p>${message}</p>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
</div>
<!-- .content-wrapper -->