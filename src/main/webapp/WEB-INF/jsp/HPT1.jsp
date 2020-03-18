<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
        <link rel="stylesheet" href="css/cssClassess.css"/>     
	</head>
	<body>
		<div class="content-container">
			<div class="banner-container">
				<header role="banner">
			        <nav role="navigation">
			            <ul class="top-bar">
			            	<li class="cta"><a class="ButtonDesign" href="/Tier1Dashboard">Home</a></li>
			            	 <li class="cta"><a class="ButtonDesign" href="/Tier1PendingTransactions">Approve/Decline Transaction</a></li>
		 	                <li class="cta"><a class="ButtonDesign" href="/IssueCheque">Issue Cashiers Cheque</a></li>
			                <li class="cta"><a class="ButtonDesign" href="/Tier1DepositMoney">Deposit Money</a></li>
			                <li class="cta"><a class="ButtonDesign" href="/Tier1WithdrawMoney">Withdraw Money</a></li>
			                <li class="ctd"><a class="ButtonDesign" href="/Tier1UpdatePassword">Change Password</a></li>
			                <li class="cta"><a class="ButtonDesign" href="/logout">Log Out</a></li>

<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/> 
			            </ul>
			        </nav>
				</header>
		    </div>
	    </div>
	</body>
</html>