<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dashboard (Administrator)</title>
</head>
<body>
<%@include file="HPT3.jsp" %>
	<form method="post" id="Tier3home">
    	<table align="center">
        	<tr>
	           <td>
                 <h2>Welcome admin,</h2> 
                 <input name="${_csrf.parameterName}" type="hidden"  value="${_csrf.token}"/>
              </td>
            </tr>
            <tr>
            <td>${message}</td>
            </tr>
    </table>
 </form>
</body>
</html>
