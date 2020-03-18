<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dashboard of Tier1 Employees</title>
</head>
<body>
<%@include file="HPT1.jsp" %>
	<form id="Tier1Dashboard" method="post">
    	<table align="left">
    		<tr>
	        	<td>
              		<h2>Dashboard of Tier1 Employees</h2>
             	</td>
           </tr>
          	<tr>
            	<td>${message}</td>
            </tr>
    	</table>
 		<input type="hidden" value="${_csrf.token}" name="${_csrf.parameterName}"/>
 	</form>
</body>
</html>
