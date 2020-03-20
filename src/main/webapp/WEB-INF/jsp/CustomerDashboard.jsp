<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dashboard (Customer)</title>
<link href="css/cssClassess.css" rel="stylesheet" />
<script src="js/customer.js"></script>
</head>
<body onload="loadError()">

<h1>Welcome to Su Devil Bank</h1>

<form method="post" action="/perform_logout" id="form-logout">
	<button type="submit">Logout</button>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>         
</form>

</body>
</html>
