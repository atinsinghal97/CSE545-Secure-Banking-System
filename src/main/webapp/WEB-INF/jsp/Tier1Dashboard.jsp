<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Tier1 Dashboard</title>
<script src="/js/security.js"></script>
</head>
<body>
	<%@include file="HPT1.jsp"%>
	<form id="Tier1Dashboard" method="post">
		<table align="center">
			<tr>
				<td>
					<h2>TIER-1 Dashboard</h2> <input type="hidden"
					name="${_csrf.parameterName}" value="${_csrf.token}" />

				</td>
			</tr>
			<tr>
				<td>${message}</td>
			</tr>
		</table>
	</form>
</body>
</html>
