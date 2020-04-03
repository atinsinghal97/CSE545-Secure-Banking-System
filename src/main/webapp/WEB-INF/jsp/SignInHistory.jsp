<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="/js/security.js"></script>
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
			<h3 class="panel-title">SignIn History</h3>
		</div>

<table>
  <tr>
    <th>ID</th>
    <th>UserName</th>
    <th>IP Address</th>
    <th>Log In</th>
  </tr>
<c:forEach var="record" items="${history}">

  <tr>
    <td>${record.getId()}</td>
    <td>${record.getUsername()}</td>
    <td>${record.getIpAddress()}</td>
    <td>${record.getLoggedIn()}</td>
  </tr>

    </c:forEach>
</table>