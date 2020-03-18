<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Information of Accounts</title>
<link rel="stylesheet" href="css/cssClassess.css" />
<script src="js/customer.js"></script>
</head>
<body onload="loadError()">

	<h1>Information of Accounts</h1>

	<form id="form-logout" action="/perform_logout" method="post">
		<button type="submit">Logout</button>
	    <input type="hidden"  value="${_csrf.token}" name="${_csrf.parameterName}"/>         
	</form>

</body>
</html>
