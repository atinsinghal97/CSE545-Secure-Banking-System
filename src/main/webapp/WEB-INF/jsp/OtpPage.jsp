<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org"
	xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity4">

<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">
	
</script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/virtual-keyboard/1.30.1/js/jquery.keyboard.min.js">
	
</script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-mousewheel/3.1.13/jquery.mousewheel.min.js">
	
</script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/virtual-keyboard/1.30.1/js/jquery.keyboard.extension-typing.min.js">
	
</script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/virtual-keyboard/1.30.1/css/keyboard.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.css"
	rel="stylesheet">
<script src="/js/security.js"></script>
</head>

<body>
	<div th:replace="header :: header" />
	<div class="container">
		<div class="starter-template">
			<h2>OTP Validation</h2>
			<p>${ message }</p>
			<form method="post" action="/OpenAccount" id="validateOtp">
				<fieldset>
					<%-- <div th:if="${param.error}">
						<div class="alert alert-danger">OTP is Invalid. Try Again.</div>
					</div> --%>
					<div class="form-group">
						<input name="otpnum" type="number" step="1" max="1000000"
							id="otpnum" class="form-control input-lg" autofocus="true"
							required readonly />
					</div>
					<div class="row">
						<div class="col-md-5 col-sm-5 col-xs-5">
							<button class="btn btn-lg btn-primary btn-block" id="otpsender">Submit</button>

							<input name="${_csrf.parameterName}" type="hidden"
								value="${_csrf.token}" />
						</div>
					</div>
				</fieldset>
			</form>
		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#otpsender").click(function(event) {
				event.preventDefault();
				var data = 'otpnum=' + $("#otpnum").val();
				$.ajax({
					url : "/validateOtp",
					type : "GET",
					dataType : 'text',
					data : data,
					timeout : 600000,
					cache : false,
					success : function(response) {
						alert(response);
						$("#validateOtp").submit();
					},
					error : function(xhr, status, error) {
						alert(xhr.responseText);
						return false;
					}
				});
			});
		});
		$('#otpnum').keyboard({
			layout : 'num',
			restrictInput : true,
			preventPaste : true,
			autoAccept : true
		}).addTyping();
	</script>
</body>
</html>