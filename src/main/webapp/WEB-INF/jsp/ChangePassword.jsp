<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="hasAuthority('customer')">
    <%@include file="HeaderPage.jsp" %>
</sec:authorize>

<sec:authorize access="hasAuthority('admin')">
    <%@include file="HPT3.jsp" %>
</sec:authorize>

<sec:authorize access="hasAuthority('tier2')">
    <%@include file="HPT2.jsp" %>
</sec:authorize>

<sec:authorize access="hasAuthority('tier1')">
    <%@include file="HPT1.jsp" %>
</sec:authorize>

<sec:authorize access="hasAuthority('merchant')">
    <%@include file="HPM.jsp" %>
</sec:authorize>

<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
<script src="/js/EmpValidation.js"></script>
<script src="/js/security.js"></script>

<div id="page-content" align="center" class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">New Password</h3>
		</div>
		<div class="panel-body">
			<form:form id="NewPassword" class="form-horizontal"
				action="/profile/change_password" method="post"
				modelAttribute="passwordForm">
				<fieldset>
					<div>
						<p>${message}</p>
					</div>
					<form:errors path="isValid" cssClass="error" />

					<div class="form-group">
						<label for="oldpassword" class="col-lg-2 control-label">Old
							Password</label>
						<div class="col-lg-5">
							<form:password cssClass="form-control" path="oldpassword"
								id="oldpassword" name="oldpassword" placeholder="Old Password" />
							<form:errors path="oldpassword" cssClass="error" />
						</div>
					</div>

					<div class="form-group">
						<label for="password" class="col-lg-2 control-label">New
							Password</label>
						<div class="col-lg-5">
							<form:password cssClass="form-control" path="password"
								id="password" name="password" placeholder="New Password"
								required="true" />
							<form:errors path="password" cssClass="error" />
						</div>
					</div>

					<div class="form-group">
						<label for="confirmpassword" class="col-lg-2 control-label">Confirm
							Password</label>
						<div class="col-lg-5">
							<form:password cssClass="form-control" path="confirmpassword"
								id="confirmpassword" name="confirmpassword"
								placeholder="Confirm Password" required="true" />
							<form:errors path="confirmpassword" cssClass="error" />
						</div>
					</div>

					<div class="form-group">
						<div class="col-lg-7 col-lg-offset-2">
							<button id="new_password" name="action" value="reset_password">Change
								Password</button>
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
						</div>
					</div>

				</fieldset>
			</form:form>
		</div>
	</div>
</div>
</body>

</html>