<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dashboard of Tier2 Employees</title>
</head>
<body>
<%@include file="HPT2.jsp" %>
	<form method="post" id="Tier3home">
    	<table align="left">
        	<tr>
	           	<td>
                 <h2>Dashboard of Tier2 Employees</h2>
                 <input type="hidden" value="${_csrf.token}" name="${_csrf.parameterName}"/>
             	</td>
            </tr>
            
            <tr>
            	<td>${message}</td>
            </tr>
    	</table>
 	</form>
</body>
</html>
