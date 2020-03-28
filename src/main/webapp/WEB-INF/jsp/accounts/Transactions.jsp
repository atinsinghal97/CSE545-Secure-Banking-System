<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Banking System</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<script src="https://unpkg.com/jspdf@latest/dist/jspdf.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.2/css/bootstrap-select.min.css">
<link rel="stylesheet" href="css/index.css">
<script src="/js/security.js"></script>

</head>
<body>
	<%@include file="../HeaderPage.jsp"%>
	<div class="container text-center">
		<div class="row" id="getter">
			<div class="col-sm-8" id="credit">
				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-default text-left">
							<div class="panel-body">
								<p contenteditable="false">${accountType}Detail</p>
								<p>
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" />
								<li><span>Account Number: ${accountid}</span>&nbsp;&nbsp; <span>$
										${balance}</span>&nbsp;&nbsp; <span style="float: right;">Placeholder</span></li>
								</p>
							</div>
						</div>
					</div>
				</div>
				<div id="xvg">
					<c:forEach items="${transactions}" var="transaction">
						<div class="row">
							<div class="col-sm-3">
								<div class="well">
									<p>Transaction ID: ${transaction.transactionID}</p>
								</div>
							</div>
							<div class="col-sm-9">
								<div class="well">
									<p align="left">
										<span>Transaction Date: </span> <span>${transaction.date}</span>
										<b style="float: right;"> <span>Status: </span> <span>
												<c:choose>
													<c:when test="${transaction.approvalStatus}">Approved</c:when>
													<c:otherwise>Pending</c:otherwise>
												</c:choose>
										</span>
										</b>
									</p>
									<p align="left">
										<span><c:choose>
												<c:when
													test="${transaction.payee.accountNumber == accountid}">Credit from</c:when>
												<c:otherwise>Debit to</c:otherwise>
											</c:choose> </span>&nbsp;&nbsp;
										<c:choose>
											<c:when test="${transaction.payee.accountNumber == 1}">Withdrawal</c:when>
											<c:when test="${transaction.payer.accountNumber == 1}">Deposit</c:when>
											<c:when
												test="${transaction.payee.accountNumber == accountid}">${transaction.payer.accountNumber}</c:when>
											<c:otherwise>${transaction.payee.accountNumber}</c:otherwise>
										</c:choose>
										<span style="float: right;"> <c:choose>
												<c:when
													test="${transaction.payee.accountNumber == accountid}">$ ${transaction.amount}</c:when>
												<c:otherwise>-$ ${transaction.amount}</c:otherwise>
											</c:choose> <c:choose>
												<c:when
													test="${transaction.payee.accountNumber == accountid && transaction.approvalStatus}">
													<b>Closing Balance:</b> $ ${transaction.payeeBalance}</c:when>
												<c:when
													test="${transaction.payee.accountNumber != accountid && transaction.approvalStatus}">
													<b>Closing Balance:</b> $ ${transaction.payerBalance}</c:when>
											</c:choose>
										</span>
									</p>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="col-sm-4 well" id="transfer">
				<div class="thumbnail">
					<button id="cmd" class="btn btn-success"
						onclick="return downloader();">Statement Download</button>
				</div>
				<div class="well">
					<p>Account Holder</p>
					<p>${user}</p>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function downloader() {
			var doc = new jsPDF();
			var source = window.document.getElementById("credit");
			doc.fromHTML(source, 15, 15);
			doc.save('account_statement.pdf');
		}
	</script>
</body>
</html>
