<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Dashboard</title>
</head>
<body>
<%@include file="HPT3.jsp" %>
	<form id="Tier3home" method="post">
    	<table align="center">
        	<tr>
	           <td>
                 <h2>Admin Dashboard</h2> 
                 <input type="hidden"  name="${_csrf.parameterName}" value="${_csrf.token}"/>
              </td>
            </tr>
            <tr>
            <td>${message}</td>
            </tr>
    </table>
 </form>
</body>
</html>
