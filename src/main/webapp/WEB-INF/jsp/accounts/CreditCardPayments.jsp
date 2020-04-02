<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Banking System</title>
<script src="/js/security.js"></script>
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
						<div class="card">
							<div id="other" class="" aria-labelledby="headingTwo"
								data-parent="#accordion">
								<form method="post" class="card-body" action="paymentactioncc"
									class="card-body" style="text-align: left;">
																		<div class="input-group mb-3">
										<label> Account Number</label> <input type="number"
											step="1" pattern="[0-9]{,5}" class="form-control"
											placeholder="Account Number" name="Account"
											aria-describedby="basic-addon1">
									</div>
									<div class="input-group mb-3">
										<label>Amount</label> <input type="number"
											class="form-control" min="1" max="${balance}"
											placeholder="Amount" name="Amount" required="required">
									</div>
									<div class="input-group">
										<input type="submit" class="btn btn-success" value="Request">
									</div>
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" />
								</form>
							</div>
						</div>
			</div>
		</div>
	</div>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.2/js/bootstrap-select.min.js"></script>
</body>
</html>
