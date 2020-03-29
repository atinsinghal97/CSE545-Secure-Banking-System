<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="/css/cssClassess.css" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="/js/security.js"></script>
</head>
<body>
	<div class="content-container">
		<div class="banner-container">
			<header role="banner">
				<nav role="navigation">
					<ul class="top-bar">
						<li><a href="/Admin/SearchEmployee">View Employee</a></li>
						<li><a href="/Admin/CreateEmployee">Create Employee</a></li>
						<li><a href="/Admin/UpdateEmployee">Modify Employee</a></li>
						<li><a href="/Admin/DeleteEmployee">Delete Employee</a></li>
						<li><a href="/Admin/SystemLogs">System Logs</a></li>
						<li><a href="/profile/change_password">Change Password</a></li>
						<form method="post" action="/perform_logout" id="form-logout">
							<input type="submit" value="Logout" />
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
						</form>
					</ul>
				</nav>
			</header>
		</div>
	</div>
</body>
</html>
