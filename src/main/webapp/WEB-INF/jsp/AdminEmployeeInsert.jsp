<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script src="/js/employee_validate.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
<script src="/js/security.js"></script>

<div class="content-wrapper">
	<%@include file="HPT3.jsp"%>
</div>
<div>
	<div class="col-md-12" align="center">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Employee Registration</h3>
			</div>
			<div class="panel-body">
				<form class="form-horizontal" id="EmployeeInsert"
					action="emp_insert" method="post">
					<fieldset>
						<div>
							<p>${message}</p>
						</div>
						<div class="form-group">
							<label for="select" class="col-lg-2 control-label">Employee
								Type</label>
							<div class="col-lg-5">
								<select class="form-control" name="designation" id="designation">
									<option value="">Select Option</option>
									<option value="Tier1">Tier 1</option>
									<option value="Tier2">Tier 2</option>
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
							<label for="username" class="col-lg-2 control-label">Desired
								UserName</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" name="username"
									id="username" placeholder="Username">
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
						<br>
						<div class="form-group">
							<div class="col-lg-7 col-lg-offset-2">
								<button type="reset" class="btn btn-default">Reset</button>
								<button id="register_internal" name="action"
									value="register_internal">Submit</button>
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
							</div>
						</div>
						<div>
							<p>
								<%
									String message = (String) request.getAttribute("alertMsg");
								%>
							</p>
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