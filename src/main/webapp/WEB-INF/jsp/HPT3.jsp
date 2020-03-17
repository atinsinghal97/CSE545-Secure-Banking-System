<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
        <link rel="stylesheet" href="css/cssClassess.css"/>     
	</head>
	<body>
		<div class="content-container">
			<div class="banner-container">
				<header role="banner">
			        <nav role="navigation">
			            <ul class="top-bar">
			            	<li class="cta"><a class="ButtonDesign" href="/AdminHome">Home</a></li>
			                <li class="ctd"><a class="ButtonDesign" href="/signinhistory">Sign-In History</a></li>
			                <li class="cta"><a class="ButtonDesign" href="/UpdatePassword">Change Password</a></li>
			                <li class="cta"><a class="ButtonDesign" href="/EmployeeRegister">Add Employee</a></li>
			                    <li class="cta"><a class="ButtonDesign" href="/EmployeeUpdate">Update Employee Info</a></li>
			                <li class="ctd"><a class="ButtonDesign" href="/Search">Search Employee</a></li>
			                <li class="cta"><a class="ButtonDesign" href="/EmployeeDelete">Delete Employee</a></li>
			                <li class="cta"><a class="ButtonDesign" href="/logout">Log Out</a></li>
<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/> 
			            </ul>
			        </nav>
				</header>
		    </div>
	    </div>
	</body>
</html>