<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cashier's Check</title>
<script src="/js/security.js"></script>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.2/css/bootstrap-select.min.css">
<link rel="stylesheet" href="css/index.css">
</head>
<body>
	<%@include file="../HeaderPage.jsp"%>
	<div class="container text-center">
		<div class="row">
			<div class="col-sm-12" id="credit">

				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-default text-left">
							<div class="panel-body">

								<p contenteditable="false">Online Cashier's Check Portal</p>
								<li><span>Order Checks</span>&nbsp;&nbsp; <span>Deposit
										Check</span>&nbsp;&nbsp; <span>Online</span></li>
							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-sm-12">
						<div class="well">
							<div class="card">
								<div class="card-header" id="headingOne">
									<h5 class="mb-0">
										<button class="btn btn-link" data-toggle="collapse"
											data-target="#send" aria-expanded="false"
											aria-controls="send">Order Cashier's Check</button>
									</h5>
								</div>
								<div id="send" class="collapse" aria-labelledby="headingOne"
									data-parent="#accordion">
									<form action="CCheckOrderAction" method="post"
										class="card-body" style="text-align: left;">
										<div class="input-group mb-3">
											<label>Recipient's First Name</label> <input type="text"
												pattern="[a-zA-Z]{1,30}" class="form-control"
												placeholder="Recipient's First Name"
												name="Recipient's First Name"
												aria-describedby="basic-addon1" required="required">
										</div>

										<div class="input-group mb-3">
											<label>Recipient's Middle Name</label> <input type="text"
												oninvalid="this.setCustomValidity('Enter a Proper Middle Name')"
												pattern="[a-zA-Z]{1,30}" class="form-control"
												placeholder="Recipient's Middle Name"
												name="Recipient's Middle Name"
												aria-describedby="basic-addon2">
										</div>

										<div class="input-group mb-3">
											<label>Recipient's Last Name</label> <input type="text"
												oninvalid="this.setCustomValidity('Enter a Proper Last Name')"
												pattern="[a-zA-Z]{1,30}" class="form-control"
												placeholder="Recipient's Last Name"
												name="Recipient's Last Name" aria-describedby="basic-addon2">
										</div>

										<div class="input-group mb-3">
											<label>Select Account</label> <select name="Account"
												class="selectpicker mr-3" id="from-account"
												title="Select Account" data-live-search="false">
												<c:forEach var="account" items="${accounts}">
													<option value="${account}">${account}</option>
												</c:forEach>
											</select>
										</div>

										<div class="input-group mb-3">
											<label>Amount</label> &nbsp;&nbsp; <input type="number"
												class="form-control" min="1" max="${balance}"
												oninvalid="this.setCustomValidity('You ain't got that kinda moolah bud!')"
												placeholder="Amount" name="Amount">
										</div>

										<div class="input-group">
											<input type="hidden" name="${_csrf.parameterName}"
												value="${_csrf.token}" /> <input type="submit"
												class="btn btn-success" value="Order">
										</div>
									</form>
								</div>
							</div>
							<div class="card">
								<div class="card-header" id="headingTwo">
									<h5 class="mb-0">
										<button class="btn btn-link collapsed" data-toggle="collapse"
											data-target="#request" aria-expanded="false"
											aria-controls="request">Deposit Cashier's Check</button>
									</h5>
								</div>
								<div id="request" class="collapse" aria-labelledby="headingTwo"
									data-parent="#accordion">
									<form class="card-body" action="ccheckDepositAction"
										method="post" class="card-body" style="text-align: left;">
										<div class="input-group mb-3">
											<label>Cashier's Check Number</label> <input type="text"
												pattern="[0-9]{3,}\CCX[0-9]{3,}" class="form-control"
												placeholder="Cashier's Check Number"
												name="Cashier's Check Number"
												aria-describedby="basic-addon1">
										</div>

										<div class="input-group mb-3">
											<label>Select Account to Deposit to</label> <select
												name="Account" class="selectpicker mr-3" id="from-account"
												title="Select Account" data-live-search="false">
												<c:forEach var="account" items="${accounts}">
													<option value="${account}">${account}</option>
												</c:forEach>
											</select>
										</div>

										<div class="input-group">
											<input type="hidden" name="${_csrf.parameterName}"
												value="${_csrf.token}" /> <input type="submit"
												class="btn btn-success" value="Deposit">
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.2/js/bootstrap-select.min.js"></script>
</body>
</html>