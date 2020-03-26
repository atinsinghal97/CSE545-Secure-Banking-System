<%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
            <title>Employee Registration</title>
            <%@ page isELIgnored="false" %>
<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script src="js/employee_validate.js"></script>
<script src="js/jquery.validate.js"></script>
        </head>

<body>
<div class="content-wrapper">
<%@include file="HPT3.jsp" %>
</div>

<p align="center">To delete an employee please provide the following information.</p>
<form id="EmployeeDelete"  action="/Admin/DelEmployee" method="post">

                <table align="center">
                    <tr>
                    	<td><p>${message}</p></td>
                    </tr>
                    <tr>
                    	<td>
                            <label path="firstname">First Name: </label>
                        </td>
                        <td>
                            <input path="firstname" name="firstName" id="firstName"  />
                        </td>
                    </tr>
                    <tr>
                    	<td>
                            <label path="lastname">Last Name: </label>
                        </td>
                        <td>
                            <input path="lastname" name="lastName" id="lastName"  />
                        </td>
                    </tr>
                    <tr>
                    	<td>
                            <label path="username">User Name: </label>
                        </td>
                        <td>
                            <input path="username" name="userName" id="userName"  />
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td align="left">
                            <button id="emp_del_search" name="action" value="emp_del_search">Submit</button>
                            <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
                        </td>
                    </tr>
                </table>
                
                
</form>               
</body>
</html>
