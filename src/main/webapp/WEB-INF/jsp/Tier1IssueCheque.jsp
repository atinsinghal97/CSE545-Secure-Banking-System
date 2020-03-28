<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<meta charset="ISO-8859-1">
<head>
<script src="/js/security.js"></script>
</head>
<body>
	<div>
		<%@include file="HPT1.jsp"%>
	</div>
	<div id="page-content" align="center" class="col-md-12">
		<div>
			<div>
				<h3>
					<b>Issue Cheque</b>
				</h3>
			</div>
			<div>
				<form id="Tier1IssueCheque" class="form-horizontal"
					action="/Tier1/IssueCheque" method="post">
					<fieldset>
						<div>
							<label for="Tier1IssueCheque" class="col-lg-2 control-label">Issue
								Cheque</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" id="accountNumber"
									name="accountNumber" placeholder="Account Number" required>
								<input type="text" class="form-control" id="amount"
									name="amount" placeholder="Amount" required>
							</div>
						</div>
						<div>
							<div class="col-lg-7 col-lg-offset-2">
								<button type="reset" class="btn btn-default">Reset</button>
								<button id="Tier1IssueChequeDone" name="action"
									value="/Tier1/IssueCheque">Issue Cheque</button>
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
							</div>
							<div>
								<p>${message}</p>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>

</body>
</html>