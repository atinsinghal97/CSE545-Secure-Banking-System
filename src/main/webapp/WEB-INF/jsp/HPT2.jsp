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
			            	<li class="cta"><a class="ButtonDesign" href="/Tier2/Dashboard">Home</a></li>
			            	 <li class="cta"><a class="ButtonDesign" href="/Tier2PendingTransaction">Approve/Decline Transactions</a></li>
			                <li class="cta"><a class="ButtonDesign" href="/Tier2/UpdatePassword">Change Password</a></li>
			                 <li class="cta"><a class="ButtonDesign" href="/Tier2/PendingAccounts">Account Approval</a></li>
			                <li class="cta"><a class="ButtonDesign" href="/Tier2/SearchAccount">Search Customer Account</a></li>
			                <li class="cta"><a class="ButtonDesign" href="/Tier2/DeleteAccount">Delete Customer Account</a></li>
			                <li class="cta"><a class="ButtonDesign" href="/logout">Log Out</a></li>
			                <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
			            </ul>
			        </nav>
				</header>
		    </div>
	    </div>
	</body>
</html>