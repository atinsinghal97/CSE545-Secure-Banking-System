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
	<div class="content-wrapper">
		<%@include file="HPT3.jsp"%>
	</div>
	<div id="page-content" align="center" class="col-md-12">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">User Search</h3>
			</div>
			<div class="panel-body">
				<form id="SearchUser" action="/search" method="post"
					class="form-horizontal">
					<fieldset>
						<div class="form-group">
							<label for="username" class="col-lg-2 control-label">User
								Name</label>
							<div class="col-lg-5">
								<input type="text" id="username" name="username"
									class="form-control" placeholder="User Name" required>
							</div>
						</div>
						<div class="form-group">
							<div class="col-lg-7 col-lg-offset-2">
								<button type="reset" class="btn btn-default">Reset</button>
								<button id="search_user" name="action" value="search_user">Search</button>
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />

							</div>
							<div>
								<p>${message}</p>
							</div>
						</div>
					</fieldset>
				</form>
				<c:forEach var="entry" items="${personal}">
					<div class="account-detail cards">
						<div>
							<div class="account-header">
								<h1>Personal Details</h1>
							</div>
							<div class="account-body">
								<div>
									<label>Username:</label> <label>${entry.username}</label>
								</div>
								<div>
									<label>First Name: </label> <label> ${entry.firstName}</label>
								</div>
								<div>
									<label>Middle Name: </label> <label>${entry.middleName}</label>
								</div>

								<div>
									<label>Last Name: </label> <label>${entry.lastName}</label>
								</div>

								<div>
									<label>Email: </label> <label>${entry.email}</label>
								</div>

								<div>
									<label>Phone: </label> <label>${entry.phoneNumber}</label>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>

</body>
</html>