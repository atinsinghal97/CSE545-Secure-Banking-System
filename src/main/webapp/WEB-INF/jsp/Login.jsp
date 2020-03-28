<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/loginCSS.css">
<title>Sun Devil Bank</title>
<script src="https://www.google.com/recaptcha/api.js"></script>
<script>history.forward();</script>
</head>

<div class="panel-heading" align="center">
	<h1 class="panel-title">Welcome to Sun Devil Bank!</h1>
</div>

<div class="sparky-gify" style="float:left; width:550px; height:550px">
	<a href="#" style="padding-left: 20px"> 
		<img src="https://i.pinimg.com/originals/69/bc/c3/69bcc3fc973dda049e5bb10c23d212ed.png" height="500px" width="620px" alt="sparky" style="opacity:1">
	</a>
</div>

<div class="content-wrapper" style="width:480px; float:right" align="center">
	<div id="page-content" class="col-md-12" >
		<div class="panel panel-primary">
			
			<div class="panel-body">
				<form id="LoginPage" method="post" action="/process_login">
					<a href="#" style="padding-left: 20px"> 
						<img src="https://i.pinimg.com/originals/69/bc/c3/69bcc3fc973dda049e5bb10c23d212ed.png" height="120px" width="220px" alt="sparky" style="opacity:0.3">
					</a>
					<fieldset>
						<div class="form-group">
							<p>${message}</p>
						</div>
						<div class="form-group">
							<label for="uname"><b>Username: </b></label> 
							<input id="userName" type="text" placeholder="Username"  name="username" maxlength="28" minlength="2" required>
						</div>
						<div class="form-group">
							<label for="psw"><b>Password: </b></label> 
							<input type="password" id="password" placeholder="Password"  name="password" maxlength="50" minlength="2" required>
						</div>
						<br>
						<div class="g-recaptcha" data-sitekey="your_site_key: https://developers.google.com/recaptcha/docs/display"></div>
						<br>
						<div class="form-group">
							<button type="submit" style="background-color: #FFD700; color:#8B0000; font-family: 'AlgerianRegular';  font-weight: bold; font-size: 22px; line-height: 24px; margin: 0 0 24px;">Login</button>
								
							<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}" />
							<div class="form-group"
								style="background-color: #f1f1f1; height: 30px">
								<span><a href="/register" style="color:#685206 ;font-family: 'Helvetica Neue', sans-serif; font-size: 16px; line-height: 24px; margin: 0 0 24px;">Register as new customer</a></span>
								<span style="color:#685206 ;font-family: 'Helvetica Neue', sans-serif; font-size: 16px; line-height: 24px; margin: 0 0 24px;"> | </span>
								<span><a href="/forgot_password" style="color:#685206 ;font-family: 'Helvetica Neue', sans-serif; font-size: 16px; line-height: 24px; margin: 0 0 24px;">Forgot Password</a></span>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
</div>