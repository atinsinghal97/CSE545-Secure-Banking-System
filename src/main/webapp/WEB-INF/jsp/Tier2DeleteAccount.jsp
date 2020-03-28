<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<script src="/js/security.js"></script>
</head>
<meta charset="ISO-8859-1">
<body>
	<div>
		<%@include file="HPT2.jsp"%>
	</div>
	<div id="page-content" class="col-md-12" align="center">
		<div>
			<div>
				<h3>
					<b>DELETE</b> Account
				</h3>
			</div>
			<div>
				<form id="DeleteAccount" action="/Tier2/DelAcc" method="post"
					class="form-horizontal">
					<fieldset>
						<div>
							<label for="accountnumber" class="col-lg-2 control-label">
								<b>Account #</b>
							</label>
							<div>
								<input type="text" id="accountnumber" name="accountnumber"
									placeholder="Account #" class="form-control" required>
							</div>
						</div>
						<div>
							<div class="col-lg-7 col-lg-offset-2">
								<button type="reset" class="btn btn-default">Reset</button>
								<button action="submit">Delete</button>
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