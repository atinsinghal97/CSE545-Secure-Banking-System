
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Approve/Decline Transactions</title>
        <link rel="stylesheet" href="css/cssClassess.css"/>  
        <script src="/js/security.js"></script> 
	</head>
	<body>
		<%@include file="HeaderPage.jsp" %>
		<div class="content-container">
		
			<table>
				<thead>
					<tr>

						<th>Transaction Id</th>
						<th>From Account</th>
						<th>To Account</th>
						<th>Amount</th>
					</tr>
				</thead>
				 					
				    		
				    		
				<tbody>
				<tr>
				<td>
				<p>${message}</p>
				</td>
				</tr>
				
				    <c:forEach items="${transactionSearchForm.transactionSearches}" var="transactionSearch">
				    
				    	<tr>
				    		<td>${transactionSearch.id}&nbsp&nbsp&nbsp&nbsp</td>
				    		<td>${transactionSearch.fromAccountNumber}&nbsp&nbsp&nbsp&nbsp</td>
				    		<td>${transactionSearch.toAccountNumber}&nbsp&nbsp&nbsp&nbsp</td>
				    		<td>${transactionSearch.amount}&nbsp&nbsp&nbsp&nbsp</td>
				    		<td>&nbsp&nbsp&nbsp&nbsp
				    		<form method="post" action="/AuthorizeTransaction" id="authorize">
				    		<input type="hidden" name="id" id="id" value="${transactionSearch.id}">
				    		<input type="hidden" name="fromAccountNumber" id="fromAccountNumber" value="${transactionSearch.fromAccountNumber}">
				    		<input type="hidden" name="toAccountNumber" id="toAccountNumber" value="${transactionSearch.toAccountNumber}">
				    		<input type="hidden" name="amount" id="amount" value="${transactionSearch.amount}">
				    		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
				    		<input type="submit" value="Authorize">
				    		</form>
				    		</td>
				    		
				    		<td>&nbsp&nbsp&nbsp&nbsp
				    		<form method="post" action="/DeclineTransaction" id="decline">
				    		<input type="hidden" name="id" id="id" value="${transactionSearch.id}">
				    		<input type="hidden" name="fromAccountNumber" id="fromAccountNumber" value="${transactionSearch.fromAccountNumber}">
				    		<input type="hidden" name="toAccountNumber" id="toAccountNumber" value="${transactionSearch.toAccountNumber}">
				    		<input type="hidden" name="amount" id="amount" value="${transactionSearch.amount}">
				    		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
				    		<input type="submit" value="Decline">
				    		</form>
				    		</td>
				    	</tr>
				    
				    </c:forEach>
				    
			    </tbody>
		    </table>
		   
		</div>
	</body>
</html>