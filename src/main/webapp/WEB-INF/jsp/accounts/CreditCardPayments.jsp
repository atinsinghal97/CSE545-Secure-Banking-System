<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Credit Card Payments</title>
<script src="/js/security.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.2/css/bootstrap-select.min.css">
<link rel="stylesheet" href="css/index.css">
</head>

<body>
	<%@include file="../HeaderPage.jsp"%>
	<div class="container text-center">
		<div class="row">
			<div id="credit" class="col-sm-12">
						<div class="card">
							<div class="" id="other" aria-labelledby="headingTwo" data-parent="#accordion">
								<form class="card-body" style="text-align: left;"method="post" action="paymentactioncc">
									<div class="input-group mb-3">
										<label> Account Number</label> 
										<input class="form-control" name="Account" type="number" pattern="[0-9]{,5}" 
											step="1" placeholder="Account Number" aria-describedby="basic-addon1">
									</div>
									<div class="input-group mb-3">
										<label>Amount</label>
										<input class="form-control" type="number" max="${balance}" min="1"
											placeholder="Amount" name="Amount" required="required">
									</div>
									<div class="input-group">
										<input class="btn btn-success" type="submit" value="Request">
									</div>
									<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}" />
								</form>
							</div>
						</div>
			</div>
		</div>
	</div>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.2/js/bootstrap-select.min.js"></script>
</body>
</html>
