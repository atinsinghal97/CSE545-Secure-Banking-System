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
			            	<input type="hidden"  name="${_csrf.parameterName}"  value="${_csrf.token}"/>
			            	<li class="cta"><a class="ButtonDesign" href="/accinfo">Home</a></li>
			            	 <li class="cta"><a class="ButtonDesign" href="/ServiceRequest">Service Requests</a></li>
			                <li class="cta"><a class="ButtonDesign" href="/UpdatePassword">Change Password</a></li>
			                <li class="cta"><a class="ButtonDesign" href="/login">Log Out</a></li>
			            </ul>
			        </nav>
				</header>
		    </div>
	    </div>
	</body>
</html>