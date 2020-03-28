<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script src="/js/employee_validate.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
<script src="/js/security.js"></script>

<div class="content-wrapper">
	<%@include file="HPT3.jsp"%>
	<div class="col-md-12" id="page-content" align="center">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Employee Update</h3>
			</div>
			<div class="panel-body">
				<form class="form-horizontal" id="EmployeeUpdateSearch"
					action="/Admin/UpdateSearch" method="post">
					<div class="form-group">
						<label for="username_search" class="col-lg-2 control-label">User
							Name</label>
						<div class="col-lg-5">
							<input type="text" class="form-control" id="username"
								name="username" placeholder="User Name" required>
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-7 col-lg-offset-2">
							<button type="reset" class="btn btn-default">Reset</button>
							<button id="emp_update_search" name="action"
								value="emp_update_search">Search</button>
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
						</div>
						<div>
							<p>${message}</p>
						</div>
					</div>
				</form>
				<form class="form-horizontal" id="EmployeeUpdate"
					action="/Admin/UpdateValues" method="post">
					<fieldset>
						<div class="form-group">
							<label for="empusername" class="col-lg-2 control-label">Employee
								Username</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" name="userName"
									id="userName" placeholder="Username"
									value=<%=request.getAttribute("userName")%> readOnly required>
							</div>
						</div>
						<div class="form-group">
							<label for="email" class="col-lg-2 control-label">Email</label>
							<div class="col-lg-5">
								<input type="email" class="form-control" name="email" id="email"
									placeholder="Email" value=<%=request.getAttribute("email")%>
									required>
							</div>
						</div>
						<div class="form-group">
							<label for="firstname" class="col-lg-2 control-label">First
								Name</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" id="firstName"
									name="firstName" placeholder="First Name"
									value=<%=request.getAttribute("firstName")%> required>
							</div>
						</div>
						<div class="form-group">
							<label for="middlename" class="col-lg-2 control-label">Middle
								Name</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" id="middleName"
									name="middleName" placeholder="Middle Name"
									value=<%=request.getAttribute("middleName")%> required>
							</div>
						</div>
						<div class="form-group">
							<label for="lastname" class="col-lg-2 control-label">Last
								Name</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" id="lastName"
									name="lastName" placeholder="Last Name"
									value=<%=request.getAttribute("lastName")%> required>
							</div>
						</div>
						<div class="form-group">
							<label for="phone" class="col-lg-2 control-label">Phone
								Number</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" name="phoneNumber"
									id="phoneNumber" placeholder="Phone"
									value=<%=request.getAttribute("phoneNumber")%> required>
							</div>
						</div>
						<br>
						<div class="form-group">
							<div class="col-lg-7 col-lg-offset-2">
								<button type="reset" class="btn btn-default">Reset</button>
								<button id="update_internal" name="action"
									value="update_internal">Submit</button>
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

<script type="text/javascript">
	$(document).ready(function() {
		sideNavigationSettings();
	});
</script>
</body>
</html>
