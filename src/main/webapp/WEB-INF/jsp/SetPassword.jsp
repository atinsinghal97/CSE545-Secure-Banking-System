<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script src="/js/EmployeeValidate.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
<script src="/js/security.js"></script>
<div class="content-wrapper">
	<div id="page-content" class="col-md-12" align="center">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Set New Password</h3>
			</div>
			<div class="panel-body">
				<form id="SetPassword" action="/setpassword" class="form-horizontal"
					method="post">
					<fieldset>
						<div class="form-group">
							<label for="email" class="col-lg-2 control-label">User
								Name</label>
							<div class="col-lg-5">
								<input type="text" id="username" name="username"
									class="form-control" placeholder="User Name" required>
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="col-lg-2 control-label">Password</label>
							<div class="col-lg-5">
								<input type="password" id="password" name="password"
									class="form-control" placeholder="Password" required>
							</div>
						</div>
						<div class="form-group">
							<label for="confirmpassword" class="col-lg-2 control-label">Confirm
								Password</label>
							<div class="col-lg-5">
								<input type="password" id="confirmpassword"
									name="confirmpassword" class="form-control"
									placeholder="Confirm Password" required>
							</div>
						</div>
						<div class="form-group">
							<div class="col-lg-7 col-lg-offset-2">
								<button type="reset" class="btn btn-default">Reset</button>
								<button id="set_password" name="action" value="set_password">Submit</button>
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />

								<p>${message}</p>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>


	</div>

</div>
<!-- .content-wrapper -->