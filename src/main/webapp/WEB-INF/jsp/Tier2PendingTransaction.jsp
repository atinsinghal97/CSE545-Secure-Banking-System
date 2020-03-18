
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Transactions</title>
        <link rel="stylesheet" href="css/cssClassess.css"/>   
	</head>
	<body>
		
		<div class="content-container">
<c:choose>
		<c:when test="${role eq 'Tier1'}"><%@include file="HPT1.jsp" %></c:when>
		<c:otherwise><%@include file="HPT2.jsp" %></c:otherwise>
	</c:choose>		
			<table>
				<thead>
					<tr>
						<th>Date</th>
						<th>Transaction ID</th>
						<th>Sender Account Number</th>
						<th>Recipient Account Number</th>
						<th>Amount</th>
						<th>Status</th>
					</tr>
				</thead>
				
				
				    					
				    		
				    		
				<tbody>
				<tr>
				<td>
				<p>${message}</p>
				</td>
				</tr>
				    <c:forEach items="${transactions}" var="transaction" varStatus="i">
				    
				    	<tr>
				    	
				    	
				    		<td>${transaction.date}</td>
				    		<td>${transaction.transactionID}</td>
				    		<td>
				    			${transaction.payer.accountNumber}
				    		</td>
				    		<td>
				    			${transaction.payee.accountNumber}
				    		</td>
				    		<td>
				    		<c:choose>
				    				<c:when test="${transaction.payee.accountNumber == accountid}">$ ${transaction.amount}</c:when>
				    				<c:otherwise>-$ ${transaction.amount}</c:otherwise>
				    			</c:choose>
				    		</td>
				    		<td>
				    			<c:choose>
				    				<c:when test="${transaction.approvalStatus}">Approved</c:when>
				    				<c:otherwise>Pending</c:otherwise>
				    			</c:choose>
				    		</td>
				    		<td>
				    		<form method="post" action="/authorize" id="authorize">
				    		<input type="hidden" name="transactionID" id="transactionID" value="${transaction.transactionID}">
				    		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
				    		<input type="submit" value="Authorize">
				    		</form>
				    		</td>
				    		
				    		<td>
				    		<form method="post" action="/declinetransaction" id="decline">
				    		<input type="hidden" name="transactionID" id="transactionID" value="${transaction.transactionID}">
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