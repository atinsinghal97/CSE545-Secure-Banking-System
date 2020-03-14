<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Tier1 Dashboard</title>
</head>
<body>
<%@include file="HPT1.jsp" %>
	<form id="Tier1Dashboard" method="post">
    	<table align="center">
    	<tr>
	           <td>
                 <h2>TIER-1 Dashboard</h2>
              </td>
            </tr>
            <tr>
            <td>${message}</td>
            </tr>
    	</table>
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
 </form>
</body>
</html>
