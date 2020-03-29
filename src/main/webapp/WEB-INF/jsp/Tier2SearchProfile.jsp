<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
   <meta charset="ISO-8859-1">
   <body>
      <div class="content-wrapper">
         <%@include file="HPT2.jsp" %>
      </div>
      <div class="col-md-12" id="page-content" align="center">
         <div class="panel panel-primary">
            <div class="panel-heading">
               <h3 class="panel-title">${type} Profiles</h3>
            </div>

			<table>
				<thead>
					<tr>
						<th>User Name</th>
						<th>Created Date</th>
						<th>Modified Date</th>
						<th>Incorrect Login Attempts</th>
					</tr>
				</thead>
				    		
				<tbody>
				<tr>
				<td>
				<p>${message}</p>
				</td>
				</tr>
				
				    <c:forEach items="${userSearch.getUserProfiles()}" var="profile">

				    	<tr>
				    		<td>${profile.getUsername()}&nbsp;&nbsp;&nbsp;&nbsp;</td>
				    		<td>${profile.getCreatedDate()}&nbsp;&nbsp;&nbsp;&nbsp;</td>
				    		<td>${profile.getModifiedDate()}&nbsp;&nbsp;&nbsp;&nbsp;</td>
				    		<td>${profile.getIncorrectAttempts()}&nbsp;&nbsp;&nbsp;&nbsp;</td>
				    		<td>&nbsp;&nbsp;&nbsp;&nbsp;
					    		<form method="post" action="/Tier2/UnlockProfile" id="authorize">
						    		<input type="hidden" name="username" id="username" value="${profile.getUsername()}">
						    		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
						    		<input type="submit" value="Unlock">
					    		</form>
				    		</td>
				    	</tr>
				    
				    </c:forEach>
				    
			    </tbody>
		    </table>

         </div>
      </div>
   </body>
</html>