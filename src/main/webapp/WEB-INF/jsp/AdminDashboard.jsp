<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dashboard of Administrator</title>
</head>
<body>
<%@include file="HPT3.jsp" %>
	<form id="Tier3home" method="post">
    	<table align="left">
        	<tr>
	           <td>
                 <h2>Dashboard of Administrator</h2> 
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
