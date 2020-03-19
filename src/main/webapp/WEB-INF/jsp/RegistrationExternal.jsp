<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="js/cust_validate.js"></script>
<script src="js/jquery.validate.js"></script>
<div class="content-wrapper">

	<div class="col-md-10" id="page-content" align="center">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Registration Form</h3>
			</div>

			<div class="panel-body">
				<form action="/externalregister" id="RegistrationExternal"
					class="form-horizontal" method="post">
					<fieldset>
						<div>
							<p>${message}</p>
						</div>

						<div class="col-lg-5 form-group">
							<label for="select" class="col-lg-2 control-label">Customer
								Type: </label> <select class="form-control" name="designation"
								id="designation">
								<option value="">Select Account Type</option>
								<option value="merchant">Merchant Account</option>
								<option value="customer">Individual Account</option>
							</select>
						</div>

						<br>

						<div class="col-lg-5 form-group">
							<label class="control-label col-lg-2" for="firstname">First
								Name: </label> <input id="firstname" class="form-control" type="text"
								placeholder="First Name" name="firstname" required>
						</div>

						<div class="col-lg-5 form-group">
							<label class="control-label col-lg-2" for="middlename">Middle
								Name: </label> <input id="middlename" class="form-control" type="text"
								placeholder="Middle Name" name="middlename">
						</div>

						<div class="col-lg-5 form-group">
							<label class="control-label col-lg-2" for="lastname">Last
								Name: </label> <input id="lastname" class="form-control" type="text"
								placeholder="Last Name" name="lastname" required>
						</div>

						<br>

						<div class="col-lg-5 form-group">
							<label class="control-label col-lg-2" for="email">Username:
							</label> <input id="username" class="form-control" type="text"
								placeholder="Username" name="username" required>
						</div>

						<div class="col-lg-5 form-group">
							<label class="control-label col-lg-2" for="password">Password:
							</label> <input id="password" class="form-control" type="password"
								placeholder="Password" name="password" required>
						</div>

						<div class="col-lg-5 form-group">
							<label class="control-label col-lg-2" for="confirmpassword">Re-enter
								Password: </label> <input id="confirmpassword" class="form-control"
								type="password" placeholder="Re-enter Password"
								name="confirmpassword" required>
						</div>

						<br>

						<div class="col-lg-5 form-group">
							<label class="control-label col-lg-2" for="email">E-mail:
							</label> <input id="email" class="form-control" type="email"
								placeholder="E-mail" name="email" required>
						</div>

						<div class="col-lg-5 form-group">
							<label class="control-label col-lg-2" for="phone">Phone
								Number: </label> <input id="phone" class="form-control" type="text"
								placeholder="Phone" name="phone" required>
						</div>

						<br>

						<div class="col-lg-5 form-group">
							<label class="control-label col-lg-2" for="address">Address:
							</label> <input id="address" class="form-control" type="text"
								placeholder="Address" name="address" required>
						</div>

						<br>

						<div class="col-lg-5 form-group">
							<label class="control-label col-lg-2" for="date_of_birth">DOB:
							</label> <input id="date_of_birth" class="form-control" type="date"
								placeholder="Date of Birth" name="date_of_birth" required>
						</div>

						<br>

						<div class="col-lg-5 form-group">
							<label class="control-label col-lg-2" for="ssn">SSN: </label> <input
								id="ssn" class="form-control" type="text"
								placeholder="SSN Number" name="ssn" required>
						</div>

						<br>

						<div class="col-lg-5 form-group">
							<label class="control-label col-lg-2" for="SecurityQuestion1">What
								is your mother's maiden name?: </label> <input id="secquestion1"
								class="form-control" type="text"
								placeholder="Security Question 1" name="secquestion1" required>
						</div>

						<div class="col-lg-5 form-group">
							<label class="control-label col-lg-2" for="SecurityQuestion2">In
								which city did your parents meet?: </label> <input id="secquestion2"
								class="form-control" type="text"
								placeholder="Security Question 2" name="secquestion2" required>
						</div>

						<br>

						<div class="col-lg-8 col-lg-offset-2 form-group">
							<button value="register_external" id="register_external"
								name="action">Submit</button>
							<button class="btn btn-default" type="reset">Reset</button>
							<input value="${_csrf.token}" name="${_csrf.parameterName}"
								type="hidden" />
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
