<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<meta charset="ISO-8859-1">
<body>
	<div>
		<%@include file="HPT1.jsp"%>
	</div>
	<div id="page-content" class="col-md-10" align="center">
		<div>
			<h3>Issue Cashier Cheque</h3>
			<div>
				<form class="form-horizontal" id="SearchCheque" method="post"
					action="/searchcheque">
					<fieldset>
						<div class="col-lg-5">
							<label class="control-label col-lg-3" for="chequeid">Cheque
								ID: </label> <input id="chequeid" class="form-control" type="text"
								name="chequeid" placeholder="Cheque ID" required>
						</div>
						<br>
						<div>
							<div class="col-lg-offset-2 col-lg-6">
								<button name="action" id="searchcheque" value="searchcheque">Issue
									Cheque</button>
								<button class="btn btn-default" type="reset">Reset</button>
								<input value="${_csrf.token}" name="${_csrf.parameterName}"
									type="hidden" />
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