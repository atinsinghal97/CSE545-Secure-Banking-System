<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script src="js/employeeValidate.js"></script>
<script src="js/jquery.validate.js"></script>

	<div class="content-wrapper">
		<%@include file="HPT3.jsp" %>
		<div class="col-md-12" id="page-content" align="center">
			<div class="panel panel-primary">
  				<div class="panel-heading">
    				<h3 class="panel-title">Update Employee Information</h3>
 				 </div>
	  			 <div class="panel-body">
	  			 <form class="form-horizontal"  action="emp_update_search" id="EmployeeUpdateSearch" method="post">
	  			 <div class="form-group">
						      <label for="username_search" class="col-lg-2 control-label">User Name</label>
						      <div class="col-lg-12">
						        <input type="text" class="form-control" id="username_search" name="username_search" placeholder="User Name" required>
						      </div>
						    </div>
						    <div class="form-group">
						      <div class="col-lg-5 col-lg-offset-2">
						      	<button type="reset" class="btn btn-default">Reset</button>
						        <button id="emp_update_search" name="action" value="emp_update_search">Search</button>
						        <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
						      </div>
						      <div>
						      <p>${message}</p>
						      </div>
						    </div>
	  			 </form>
					<form class="form-horizontal" id="EmployeeUpdate" action="emp_update" method="post">
			  			<fieldset>
			  				<div class="form-group">
						      <label for="empusername" class="col-lg-2 control-label">Employee Username</label>
						      <div class="col-lg-5">
						        <input type="text" class="form-control" id="empusername" name="empusername"  placeholder="Employee Username" value=<%=request.getAttribute("empusername") %>>
						      </div>
						    </div>
			  				<div class="form-group">
						      <label for="email" class="col-lg-2 control-label">Email</label>
						      <div class="col-lg-7">
						        <input type="email" class="form-control" id="email"  name="email" placeholder="Employee Email" value=<%=request.getAttribute("Email") %>>
						      </div>
						    </div>
						    <div class="form-group">
						      <label for="firstname" class="col-lg-2 control-label">Employee First Name</label>
						      <div class="col-lg-7">
						        <input type="text" class="form-control" id="firstName" name="firstname" placeholder="Employee First Name" value=<%=request.getAttribute("FirstName") %>>
						      </div>
						    </div>
						    <div class="form-group">
						      <label for="middlename" class="col-lg-2 control-label">Employee Middle Name</label>
						      <div class="col-lg-7">
						        <input type="text" class="form-control" id="middleName" name="middlename" placeholder="Employee Middle Name" value=<%=request.getAttribute("MiddleName") %>>
						      </div>
						    </div>
						    <div class="form-group">
						      <label for="lastname" class="col-lg-2 control-label">Employee Last Name</label>
						      <div class="col-lg-7">
						        <input type="text" class="form-control"  name="lastname" id="lastName" placeholder="Employee Last Name" value=<%=request.getAttribute("LastName") %>>
						      </div>
						    </div>
						    <div class="form-group">
						      <label for="phone" class="col-lg-2 control-label">Employee Phone Number</label>
						      <div class="col-lg-7">
						        <input type="text" class="form-control" id="phone" name="phone"  placeholder="Phone" value=<%=request.getAttribute("Phone") %>>
						      </div>
						    </div>
						    <div class="form-group">
						      <label for="date_of_birth" class="col-lg-2 control-label">Employee DOB</label>
						      <div class="col-lg-7">
						        <input type="date" class="form-control" name="date_of_birth" id="date_of_birth" placeholder="Date of Birth" value=<%=request.getAttribute("DOB") %>>
						      </div>
						    </div>
						        <br>
						     <div class="form-group">
						      <div class="col-lg-7 col-lg-offset-2">
						      	<button type="reset" class="btn btn-default">Reset</button>
						        <button id="update_internal" name="action" value="update_internal">Submit</button>
						        <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
						      </div>
						    </div>           
			  			</fieldset>
		  			</form>
	 			 </div>
			</div>
			
				
			</div>
		
		</div> <!-- .content-wrapper -->
	
	<script type="text/javascript">
	$(document).ready(function() {
		sideNavigationSettings();
	});
	</script>
</body>
</html>
