<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Service Requests</title>
<script src="/js/security.js"></script>
</head>
<body>
	<%@include file="../HeaderPage.jsp"%>

	<div style="display: flex; flex-direction: column; margin: auto">
		<button class="customButton"
			onclick="window.location.href = '/generateAppointmentOtp'">Schedule
			Appointment</button>
		<button class="customButton"
			onclick="window.location.href = '/CashiersCheck'">Order
			Cashier's Check</button>
		<button class="customButton"
			onclick="window.location.href = '/generateAccountOtp'">Open
			New Account</button>
		<button class="customButton"
			onclick="window.location.href = '/PrimeAccount'">Set Primary
			Account</button>
	</div>
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />
</body>
</html>
