<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Fund Transfer</title>
</head>

<body>
	<%@include file="HeaderPage.jsp"%>
	<div class="content-container">
		Fund from A/c: ${accountid}

		<form method="post" action="/paymentaction">
			<div>
				<label class="lbel" for="Recipient">Recipient's Name: </label> <input
					class="texter" id="Recipient" type="text" name="Recipient" required>
			</div>
			<div>
				<label class="lbel" for="AccountNumber">Recipient's A/c
					Number: </label> <input class="texter" type="text" name="AccountNumber"
					id="AccountNumber" required>
			</div>
			<div>
				<label class="lbel" for="Amount">Amount: </label> <input
					class="texter" type="text" name="Amount" id="Amount" required>
			</div>
			<input value="Confirm" type="submit"> <input
				value="${_csrf.token}" name="${_csrf.parameterName}" type="hidden" />
		</form>

	</div>
</body>

</html>