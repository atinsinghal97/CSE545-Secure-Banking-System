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
											aria-controls="send">Send by Account Number</button>
									</h5>
								</div>

								<div id="send" class="collapse" aria-labelledby="headingOne"
									data-parent="#accordion">
									<form method="post" action="/paymentactionacc"
										class="card-body" style="text-align: left;">

										<div class="input-group mb-3">
											<label>Recipient Account Number</label> <input type="number"
												step="1" class="form-control"
												oninvalid="this.setCustomValidity('Enter a Proper Account Number')"
												placeholder="Recipient Account Number"
												name="Recipient Account Number"
												aria-describedby="basic-addon1" required="required">
										</div>

										<div class="input-group mb-3">
											<label>Recipient Last Name</label> <input type="text"
												class="form-control" placeholder="Recipient Last Name"
												oninvalid="this.setCustomValidity('Enter a Proper Last Name')"
												pattern="[a-zA-Z]{1,30}" name="Recipient Last Name"
												aria-describedby="basic-addon2" required="required">
										</div>

										<div class="input-group mb-3">
											<label>Amount</label> <input type="number"
												class="form-control" min="1" max="${balance}"
												placeholder="Amount" name="Amount" required="required">
										</div>

										<div class="input-group">
											<input type="hidden" name="${_csrf.parameterName}"
												value="${_csrf.token}" /> <input type="submit"
												class="btn btn-success" value="Request">
										</div>
									</form>
								</div>
							</div>
							<div class="card">
								<div class="card-header" id="headingTwo">
									<h5 class="mb-0">
										<button class="btn btn-link collapsed" data-toggle="collapse"
											data-target="#request" aria-expanded="false"
											aria-controls="request">Send by Email or Phone
											Number</button>
									</h5>
								</div>
								<div id="request" class="collapse" aria-labelledby="headingTwo"
									data-parent="#accordion">
									<form method="post" class="card-body"
										action="/paymentactionemph" class="card-body" 
										style="text-align: left;">
										<div class="input-group mb-3">
											<label>Recipient Email Address</label> <input type="email"
												minlength="4" maxlength="30" class="form-control"
												placeholder="Recipient Email Address"
												name="Recipient Email Address"
												aria-describedby="basic-addon1">
										</div>

										<div class="input-group mb-3">
											<label>Recipient Phone Number</label> <input type="tel"
												oninvalid="this.setCustomValidity('Must be a 10 digit number')"
												pattern="[0-9]{10}" class="form-control"
												placeholder="Recipient Phone Number"
												name="Recipient Phone Number"
												aria-describedby="basic-addon2">
										</div>

										<div class="input-group mb-3">
											<label>Amount</label> <input type="number"
												class="form-control" min="1" max="${balance}"
												placeholder="Amount" name="Amount" required="required">
										</div>

										<div class="input-group">
											<input type="hidden" name="${_csrf.parameterName}"
												value="${_csrf.token}" /> <input type="submit" onclick="return validate();"
												class="btn btn-success" value="Request">
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
		<script type="text/javascript">
		function validate()
		{
			var phone = document.getElementsByName("Recipient Phone Number")[0].value;
			var email = document.getElementsByName("Recipient Email Address")[0].value;
			if(phone || email)
			{
				return true;
			}
			alert("Both Email and Phone Number fields cannot be blank! Enter either or both!")
			return false;
		}
		</script>
</body>

</html>
