<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<meta charset="ISO-8859-1">
<head>
<script src="/js/security.js"></script>
</head>
<body>
    <div id="page-content" align="center" class="col-md-12">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">New Password</h3>
            </div>
            <div class="panel-body">
                <form:form id="NewPassword" class="form-horizontal" action="/change_password" method="post" modelAttribute="passwordForm">
                    <fieldset>
                    <div><p>${message}</p></div>
          			<form:errors path="isValid" cssClass="error"/>

                        <div class="form-group">
                            <label for="password" class="col-lg-2 control-label">New Password</label>
                            <div class="col-lg-5">
                                <form:password cssClass="form-control" path="password" id="password" name="password" placeholder="New Password" required="true" />
                    			<form:errors path="password" cssClass="error"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="confirmpassword" class="col-lg-2 control-label">Confirm Password</label>
                            <div class="col-lg-5">
                                <form:password cssClass="form-control" path="confirmpassword" id="confirmpassword" name="confirmpassword" placeholder="Confirm Password" required="true" />
                    			<form:errors path="confirmpassword" cssClass="error"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-lg-7 col-lg-offset-2">
                                <button id="new_password" name="action" value="reset_password">Change Password</button>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            </div>
                        </div>

                    </fieldset>
                </form:form>
            </div>
        </div>
    </div>
</body>
</html>
