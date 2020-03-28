<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Transfer Funds</title>
<script src="/js/security.js"></script>
</head>
<body>
	<%@include file="HeaderPage.jsp"%>
	<div class="content-container">
		Fund from Account# : ${accountid}
		<form action="/paymentaction" method="post">
			<div>
				<label for="Recipient" class="lbel">Name of Recipient </label> <input
					id="Recipient" name="Recipient" type="text" class="texter" required>
			</div>
			<div>
				<label for="AccountNumber" class="lbel">Recipient Account# </label>
				<input id="AccountNumber" name="AccountNumber" type="text"
					class="texter" required>
			</div>
			<div>
				<label for="Amount" class="lbel">Amount</label> <input id="Amount"
					name="Amount" type="text" class="texter" required>
			</div>
			<input type="submit" value="Confirm"> <input type="hidden"
				name="${_csrf.parameterName}" value="${_csrf.token}" />

		</form>
	</div>
</body>
</html>