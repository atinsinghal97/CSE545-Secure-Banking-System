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
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.2/css/bootstrap-select.min.css">
<link rel="stylesheet" href="css/index.css">
<script src="/js/security.js"></script>
</head>

<body>
	<%@include file="../HeaderPage.jsp"%>

	<div class="container text-center">
		<div class="row">
			<div class="col-sm-8" id="credit">

				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-default text-left">
							<div class="panel-body">
								<p contenteditable="false">${acctype}</p>
								<p>
								<li><span>Account Number: ${accountid}</span> <span>Balance:
										$ ${balance}</span></li>

								</p>
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
											aria-controls="send">Deposit Funds</button>
									</h5>
								</div>

								<div id="send" class="collapse" aria-labelledby="headingOne"
									data-parent="#accordion">
									<form method="post" action="/paymentactionacc"
										class="card-body" style="text-align: left;">

										<div class="input-group mb-3">
											<label>Deposit Amount</label> <input type="number"
												class="form-control" min="1" max="${100000}"
												placeholder="Deposit Amount" name="Amount"
												required="required">
										</div>
										<input type="hidden" name="Deposit" value="sys" /> <input
											type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
										<div class="input-group">
											<input type="submit" class="btn btn-success" value="Deposit">
										</div>
									</form>
								</div>
							</div>
							<div class="card">
								<div class="card-header" id="headingTwo">
									<h5 class="mb-0">
										<button class="btn btn-link collapsed" data-toggle="collapse"
											data-target="#request" aria-expanded="false"
											aria-controls="request">Withdraw Funds</button>
									</h5>
								</div>
								<div id="request" class="collapse" aria-labelledby="headingTwo"
									data-parent="#accordion">
									<form method="post" class="card-body"
										action="/paymentactionacc" class="card-body"
										style="text-align: left;">

										<div class="input-group mb-3">
											<label>Withdrawal Amount</label> <input type="number"
												class="form-control" min="1" max="${balance}"
												placeholder="Withdrawal Amount" name="Amount"
												required="required">
										</div>
										<input type="hidden" name="Withdraw" value="sys" /> <input
											type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
										<div class="input-group">
											<input type="submit" class="btn btn-success" value="Withdraw">
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>


			<div class="col-sm-4 well" id="transfer">
				<div class="thumbnail">
					<p>Brief Statement</p>
				</div>
				<div class="well">
					<p>account1</p>
					<p>balance</p>
					<p>date</p>
				</div>
				<div class="well">
					<p>account1</p>
					<p>balance</p>
					<p>date</p>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.2/js/bootstrap-select.min.js"></script>
</body>

</html>
