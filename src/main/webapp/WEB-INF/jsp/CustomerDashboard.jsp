<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Accounts Information</title>
<link rel="stylesheet" href="/css/cstyles.css" />
<script src="/js/customer.js"></script>
<script src="/js/security.js"></script>
</head>
<body onload="loadError()">
	<%@include file="HeaderPage.jsp"%>

	<div class="content-container">
		<div class="accounts-container cards">
			<div>Hello ${users}!</div>
			<label>Accounts</label>
			<c:forEach var="entry" items="${savings}">
				<div class="account-detail cards">
					<div>
						<div class="account-header">
							<h2>Savings Account Number: ${entry.accountNumber}</h2>
							<button class="customButton"
								onclick="DepositWithrawal(${entry.accountNumber})">Deposit/Withdrawal</button>
							<button class="customButton"
								onclick="ViewTransactions(${entry.accountNumber})">View
								Transactions</button>
							<button class="customButton"
								onclick="OpenPayments(${entry.accountNumber})">Transfer
								Funds</button>
						</div>
						<div class="account-body">
							<div>
								<h3>
									<label>Balance: </label> <label>$
										${entry.currentBalance}</label>
								</h3>
							</div>
							<div>
								<label>Interest Rate: </label> <label>${entry.interest}%</label>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
			<c:forEach var="entry" items="${checking}">
				<div class="account-detail cards">
					<div>
						<div class="account-header">
							<h2>Checking Account Number: ${entry.accountNumber}</h2>
							<button class="customButton"
								onclick="DepositWithrawal(${entry.accountNumber})">Deposit/Withdrawal</button>
							<button class="customButton"
								onclick="ViewTransactions(${entry.accountNumber})">View
								Transactions</button>
							<button class="customButton"
								onclick="OpenPayments(${entry.accountNumber})">Transfer
								Funds</button>
						</div>
						<div class="account-body">
							<div>
								<h3>
									<label>Balance: </label> <label>$
										${entry.currentBalance}</label>
								</h3>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
			<c:forEach var="entry" items="${creditcards}">
				<div class="account-detail cards">
					<div>
						<div class="account-header">
							<h2>Credit Card Number: ${entry.accountNumber}</h2>
							<c:choose>
								<c:when test="${role eq 'Individual'}">
									<button class="customButton"
										onclick="ViewTransactions(${entry.accountNumber})">View
										Transactions</button>
									<button class="customButton"
										onclick="OpenMerchPayments(${entry.accountNumber})">Pay
										Merchant</button>
								</c:when>
								<c:otherwise>
									<button class="customButton"
										onclick="ViewTransactions(${entry.accountNumber})">View
										Transactions</button>
									<button class="customButton"
										onclick="OpenMerchPayments(${entry.accountNumber})">Initiate
										Payment</button>
								</c:otherwise>
							</c:choose>


						</div>
						<div class="account-body">
							<div>

								<h3>
									<c:choose>
										<c:when test="${role eq 'Individual'}">
											<label>Current Balance: </label>
											<label>$ ${10000 - entry.currentBalance}</label>
										</c:when>
										<c:otherwise>
											<label>Balance: </label>
											<label>$ ${entry.currentBalance}</label>
										</c:otherwise>
									</c:choose>
								</h3>
							</div>
							<c:choose>
								<c:when test="${role eq 'Individual'}">
									<div>
										<label>Available Balance: </label> <label>$
											${entry.currentBalance}</label>
									</div>
									<div>
										<label>Next Payment Due: </label> <label>04/29/2019</label>
									</div>
									<div>
										<label>APR Charged: </label> <label>${entry.interest}%</label>
									</div>
								</c:when>
							</c:choose>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<form id="dummyform" action="">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<input type="hidden" id="errorMsg" value="${errorMsg}">
</body>
</html>