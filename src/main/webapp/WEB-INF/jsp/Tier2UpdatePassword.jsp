<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script src="/js/EmpValidation.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
<script src="/js/security.js"></script>
<div class="content-wrapper">
	<%-- 	<c:choose>
		<c:when test="${role eq 'Individual'}"><%@include file="HeaderPage.jsp" %></c:when>
		<c:when test="${role eq 'Admin'}"><%@include file="HPT3.jsp" %></c:when>
		<c:when test="${role eq 'Tier2'}"><%@include file="HPT2.jsp" %></c:when>
		<c:when test="${role eq 'Tier1'}"><%@include file="HPT1.jsp" %></c:when> 
	 <c:otherwise><%@include file="HPM.jsp" %></c:otherwise> 
		
	
	</c:choose> --%>
	<%@include file="HPT2.jsp"%>
	<div class="content-container col-md-12" id="page-content"
		align="center">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Update Password</h3>
			</div>
			<p>${message }</p>
			<div class="panel-body">
				<form class="form-horizontal" id="UpdatePassword"
					action="/changepassword" method="post">
					<fieldset>
						<div class="form-group">
							<label for="email" class="col-lg-2 control-label">UserName</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" name="username"
									id="username" placeholder="Username" required>
							</div>
						</div>
						<div class="form-group">
							<label for="oldpassword" class="col-lg-2 control-label">Old
								Password</label>
							<div class="col-lg-5">
								<input type="password" class="form-control" name="oldpassword"
									id="oldpassword" placeholder="Old Password" required>
							</div>
						</div>
						<div class="form-group">
							<label for="newpassword" class="col-lg-2 control-label">New
								Password</label>
							<div class="col-lg-5">
								<input type="password" class="form-control" name="newpassword"
									id="newpassword" placeholder="New Password" required>
							</div>
						</div>
						<div class="form-group">
							<label for="" class="col-lg-2 control-label">Confirm New
								Password</label>
							<div class="col-lg-5">
								<input type="password" class="form-control" name="" id=""
									placeholder="Confirm New Password" required>
							</div>
						</div>
						<div class="form-group">
							<div class="col-lg-7 col-lg-offset-2">
								<button type="reset" class="btn btn-default">Reset</button>
								<button id="change_password" name="action"
									value="change_password">Submit</button>
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>


	</div>

</div>
<!-- .content-wrapper -->