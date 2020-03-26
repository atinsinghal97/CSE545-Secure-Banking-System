<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
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
							 
								<p contenteditable="false">User Primary Account Interface</p>
								<li><span>Current Primary Account:</span>&nbsp;&nbsp; <span>${prime_account}</span>&nbsp;&nbsp;
								</li>
							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-sm-12">
						<div class="well">
							<div class="card">
								<div id="send" class="" aria-labelledby="headingOne"
									data-parent="#accordion">
									<form action="/setprimary" method="post"
										class="card-body" style="text-align: left;">
										<div class="input-group mb-3">
											<label>Select Primary Account</label> <select name="Account"
												class="selectpicker mr-3" id="from-account"
												title="Select Account" data-live-search="false">
												<c:forEach var="account" items="${accounts}">
													<option value="${account}">${account}</option>
												</c:forEach>
											</select>
										</div>
										<div class="input-group">
										<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
											<input type="submit" class="btn btn-success" value="Change">
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