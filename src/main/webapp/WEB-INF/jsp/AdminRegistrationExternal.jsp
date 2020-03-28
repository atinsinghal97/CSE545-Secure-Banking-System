
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script src="/js/cust_validate.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
<script src="/js/security.js"></script>
<div class="content-wrapper">
	<%@include file="HPT3.jsp"%>
	<div class="col-md-12" id="page-content" align="center">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Registration</h3>
			</div>
			<div class="panel-body">
				<form class="form-horizontal" id="RegistrationExternal"
					action="/Admin/ExternalRegister" method="post">
					<fieldset>
						<div>
							<p>${message}</p>
						</div>
						<div class="form-group">
							<label for="select" class="col-lg-2 control-label">Customer
								Type</label>
							<div class="col-lg-5">
								<select class="form-control" name="designation" id="designation">
									<option value="">Select Option</option>
									<option value="tier1">tier 1</option>
									<option value="tier2">tier 2</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="firstname" class="col-lg-2 control-label">First
								Name</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" id="firstname"
									name="firstname" placeholder="First Name">
							</div>
						</div>
						<div class="form-group">
							<label for="middlename" class="col-lg-2 control-label">Middle
								Name</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" id="middlename"
									name="middlename" placeholder="Middle Name">
							</div>
						</div>
						<div class="form-group">
							<label for="lastname" class="col-lg-2 control-label">Last
								Name</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" id="lastname"
									name="lastname" placeholder="Last Name">
							</div>
						</div>
						<div class="form-group">
							<label for="email" class="col-lg-2 control-label">Desired
								UserName</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" name="username"
									id="username" placeholder="Username">
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="col-lg-2 control-label">Desired
								Password</label>
							<div class="col-lg-5">
								<input type="password" class="form-control" name="password"
									id="password" placeholder="Password">
							</div>
						</div>
						<div class="form-group">
							<label for="confirmpassword" class="col-lg-2 control-label">Confirm
								Password</label>
							<div class="col-lg-5">
								<input type="password" class="form-control"
									name="confirmpassword" id="confirmpassword"
									placeholder="Confirm Password">
							</div>
						</div>
						<div class="form-group">
							<label for="email" class="col-lg-2 control-label">Email</label>
							<div class="col-lg-5">
								<input type="email" class="form-control" name="email" id="email"
									placeholder="Email">
							</div>
						</div>

						<div class="form-group">
							<label for="address" class="col-lg-2 control-label">Address</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" name="address"
									id="address" placeholder="Address">
							</div>
						</div>
						<div class="form-group">
							<label for="phone" class="col-lg-2 control-label">Phone
								Number</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" name="phone" id="phone"
									placeholder="Phone">
							</div>
						</div>

						<div class="form-group">
							<label for="date_of_birth" class="col-lg-2 control-label">DOB</label>
							<div class="col-lg-5">
								<input type="date" class="form-control" name="date_of_birth"
									id="date_of_birth" placeholder="Date of Birth">
							</div>
						</div>

						<div class="form-group">
							<label for="ssn" class="col-lg-2 control-label">SSN</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" name="ssn" id="ssn"
									placeholder="SSN Number">
							</div>
						</div>

						<div class="form-group">
							<label for="SecurityQuestion1" class="col-lg-2 control-label">What
								is your childhood nickname?</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" name="secquestion1"
									id="secquestion1" placeholder="Security Question 1">
							</div>
						</div>

						<div class="form-group">
							<label for="SecurityQuestion2" class="col-lg-2 control-label">Who
								was your childhood hero?</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" name="secquestion2"
									id="secquestion2" placeholder="Security Question 2">
							</div>
						</div>

						<br>
						<div class="form-group">
							<div class="col-lg-7 col-lg-offset-2">
								<button type="reset" class="btn btn-default">Reset</button>
								<button id="register_external" name="action"
									value="register_external">Submit</button>
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />

							</div>
						</div>


					</fieldset>
				</form>
			</div>
		</div>


	</div>

</div>
<!-- .content-wrapper -->

<script>
	$(function() {
		var dtToday = new Date();

		var month = dtToday.getMonth() + 1;
		var day = dtToday.getDate();
		var year = dtToday.getFullYear();
		if (month < 10)
			month = '0' + month.toString();
		if (day < 10)
			day = '0' + day.toString();

		var maxDate = year + '-' + month + '-' + day;
		var minDate = year - 100 + '-' + month + '-' + day;
		//alert(minDate);
		$('#date_of_birth').attr('max', maxDate);
		$('#date_of_birth').attr('min', minDate);
	});
</script>
</body>
</html>
