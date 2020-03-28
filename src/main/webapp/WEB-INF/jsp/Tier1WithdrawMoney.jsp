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
					<b>Withdraw Money</b>
				</h3>
			</div>
			<div>
				<form id="Tier1WithdrawMoney" class="form-horizontal"
					action="/Tier1/WithdrawMoney" method="post">
					<fieldset>
						<div>
							<label for="Tier1WithdrawMoney" class="col-lg-2 control-label">Withdraw
								Money</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" id="amount"
									name="amount" placeholder="Amount" required> <input
									type="text" class="form-control" id="accountNumber"
									name="accountNumber" placeholder="Account Number" required>
							</div>
						</div>
						<div>
							<div class="col-lg-7 col-lg-offset-2">
								<button type="reset" class="btn btn-default">Reset</button>
								<button id="Tier1WithdrawMoneyDone" name="action"
									value="/Tier1/WithdrawMoney">Withdraw Money</button>
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