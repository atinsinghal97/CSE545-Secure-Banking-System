<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<title>Payments</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.2/css/bootstrap-select.min.css">
	<link rel="stylesheet" href="css/index.css">
	<script src="/js/security.js"></script>
</head>

<body>
	<%@include file="../HeaderPage.jsp"%>
	<div class="container text-center">
		<div class="row">
			<div id="credit" class="col-sm-12">
				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-default text-left">
							<div class="panel-body">
								<p>${ message }</p>
								<p contenteditable="false">${acctype}</p>
								<p>
									<li>
										<span>Account Number: ${accountid}</span>
										<span>Balance: $ ${balance}</span>
									</li>
								</p>
							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-sm-12">
						<div class="well">
							<div class="card">
								<div id="headingOne" class="card-header">
									<h5 class="mb-0">
										<button class="btn btn-link" data-toggle="collapse"
											data-target="#send" aria-expanded="false"
											aria-controls="send">Send by Account Number</button>
									</h5>
								</div>
								<div id="send" class="collapse" aria-labelledby="headingOne" data-parent="#accordion">
									<form method="post" action="/payment/transfer" class="card-body" style="text-align: left;">
										<div class="input-group mb-3">
											<label>Recipient Account Number</label> 
											<input type="number" step="1" class="form-control"
												oninvalid="this.setCustomValidity('Enter a Proper Account Number')"
												placeholder="Recipient Account Number" name="to_acc_number"
												aria-describedby="basic-addon1" required="required">
										</div>
										<div class="input-group mb-3">
											<label>Recipient Last Name</label>
											<input type="text" class="form-control" placeholder="Recipient Last Name"
												oninvalid="this.setCustomValidity('Enter a Proper Last Name')"
												pattern="[a-zA-Z]{1,30}" name="rec_last_name"
												aria-describedby="basic-addon2" required="required">
										</div>
										<div class="input-group mb-3">
											<label>Amount</label>
											<input name="amount" class="form-control" type="number" min="1" max="${balance}"
												placeholder="Amount" required="required">
										</div>
										<div class="input-group">
											<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}" />
											<input class="btn btn-success" type="submit" value="Transfer">
										</div>
									</form>
								</div>
							</div>
							<div class="card">
								<div id="headingTwo" class="card-header" >
									<h5 class="mb-0">
										<button class="btn btn-link collapsed" data-toggle="collapse"
											data-target="#request" aria-expanded="false"
											aria-controls="request">Send by Email or Phone Number</button>
									</h5>
								</div>
								<div id="request" class="collapse" data-parent="#accordion" aria-labelledby="headingTwo">
									<form method="post" class="card-body" action="/payment/transfer_by_default" 
											style="text-align: left;">
										<div class="input-group mb-3">
											<label>Email ID</label>
											<input name="email" class="form-control" type="email" maxlength="30" minlength="4" 
												placeholder="Email ID of recipient" aria-describedby="basic-addon1">
										</div>

										<div class="input-group mb-3">
											<label>Phone No.</label> 
											<input class="form-control" name="phone" type="text"
												pattern="(\+?1-?)?(\([2-9]([02-9]\d|1[02-9])\)|[2-9]([02-9]\d|1[02-9]))-?[2-9]\d{2}-?\d{4}"
												title="Recipients's valid US Phone number +1XXXXXXXXXX" />
										</div>

										<div class="input-group mb-3">
											<label>Amount</label>
											<input class="form-control" type="number" max="${balance}" min="1" 
												name="amount" placeholder="Amount" required="required">
										</div>

										<div class="input-group">
											<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}" />
											<input class="btn btn-success" type="submit" value="Transfer">
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
		function validate() {
			var phone = document.getElementsByName("Recipient Phone Number")[0].value;
			var email = document.getElementsByName("Recipient Email Address")[0].value;
			if (phone || email) {
				return true;
			}
			alert("You cannot leave Email and Phone Number fields blank together! Kindly enter atleast one.")
			return false;
		}
	</script>
</body>

</html>
