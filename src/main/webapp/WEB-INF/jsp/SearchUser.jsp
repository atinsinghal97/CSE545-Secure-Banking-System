<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<meta charset="ISO-8859-1">

<body>
	<div class="content-wrapper">
		<%@include file="HPT3.jsp"%>
	</div>
	<div align="center" class="col-md-10" id="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Search User</h3>
			</div>
			<div class="panel-body">
				<form class="form-horizontal" action="/search" id="SearchUser"
					method="post">
					<fieldset>
						<div class="col-lg-6 form-group">
							<label class="control-label col-lg-2" for="username">Username:
							</label> <input placeholder="Username" id="username" type="text"
								class="form-control" name="username" required>
						</div>
						<div class="form-group">
							<div class="col-lg-offset-2 col-lg-6">
								<button value="search_user" name="action" id="search_user">Search</button>
								<button class="btn btn-default" type="reset">Reset</button>
								<input name="${_csrf.parameterName}" type="hidden"
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
								<h2>
									Personal Details
									</h1>
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
									<label>E-mail: </label> <label>${entry.email}</label>
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