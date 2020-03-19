<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/loginCSS.css" rel="stylesheet">
<title>Sparky's Den Bank</title>
</head>

<div class="panel-heading" align="center">
	<h1 class="panel-title">Welcome to Su Devil Bank!</h1>
</div>

<div class="sparky-gify" style="float:left; width:550px; height:550px">
	<a href="#" style="padding-left: 20px"> 
		<img src="img/bg_sparky.jfif" height="500px" width="620px" alt="sparky" style="opacity:1">
	</a>
</div>

<div class="content-wrapper" style="width:500px; float:right" align="center">
	<div id="page-content" class="col-md-12" >
		<div class="panel panel-primary">
			
			<div class="panel-body">
				<form id="LoginPage" method="post" action="/process_login">
					<a href="#" style="padding-left: 20px"> 
						<img src="img/sparky.png" height="120px" width="220px" alt="sparky" style="opacity:0.3">
					</a>
					<fieldset>
						<div class="form-group">
							<p>${message}</p>
						</div>
						<div class="form-group">
							<label for="uname"><b>Username</b></label> <input id="userName" type="text"
								placeholder="User ID"  name="username"  
								maxlength="28" minlength="2"
								required>
						</div>
						<div class="form-group">
							<label for="psw"><b>Password</b></label> <input type="password"
								placeholder="Enter Password" id="password" name="password" 
								maxlength="50" minlength="2"
								required>
						</div>
						<div class="form-group">
							<button type="submit">Login</button>
								
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							<div class="form-group"
								style="background-color: #f1f1f1; height: 30px">
								<span><a href="/register">New Customer?</a></span>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
</div>
