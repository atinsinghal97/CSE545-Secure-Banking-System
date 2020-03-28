<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title><b> Transactions </b></title>
<link rel="stylesheet" href="/css/cstyles.css" />
<script src="/js/security.js"></script>
</head>
<body>
	<%@include file="HeaderPage.jsp"%>
	<div>
		<table>
			<thead>
				<tr>
					<th>Date</th>
					<th>Transaction ID</th>
					<th>Sender Account #</th>
					<th>Recipient Account #</th>
					<th>Amount(in USD)</th>
					<th>Status</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${transactions}" var="transaction">
					<tr>
						<td>${transaction.date}</td>
						<td>${transaction.transactionID}</td>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<td>${transaction.payer.accountNumber}</td>
						<td>${transaction.payee.accountNumber}</td>
						<td><c:choose>
								<c:when test="${transaction.payee.accountNumber == accountid}">$ ${transaction.amount}</c:when>
								<c:otherwise>-$ ${transaction.amount}</c:otherwise>
							</c:choose></td>
						<td><c:choose>
								<c:when test="${transaction.approvalStatus}">Approved</c:when>
								<c:otherwise>Pending</c:otherwise>
							</c:choose></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>