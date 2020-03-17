<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
        <link rel="stylesheet" href="css/cssClasses.css"/>     
	</head>
	<body>
		<div class="content-container">
			<div class="banner-container">
				<header role="banner">
			        <nav role="navigation">
			            <ul class="top-bar">
			            	<li class="cta"><a class="ButtonDesign" href="/accinfo">Home</a></li>
			            	  <li class="cta"><a class="ButtonDesign" href="/ServiceRequest">Service Request</a></li>
			                <li class="cta"><a class="ButtonDesign" href="/ChangePassword">Change Password</a></li>
			                <li class="cta"><a class="ButtonDesign" href="/logout">Log Out</a></li>
<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/> 
			            </ul>
			        </nav>
				</header>
		    </div>
	    </div>
	</body>
	</html>