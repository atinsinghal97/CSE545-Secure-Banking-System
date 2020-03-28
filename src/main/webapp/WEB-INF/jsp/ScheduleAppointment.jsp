
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script src="/js/cust_validate.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
<script src="/js/security.js"></script>
<script>
	$(function() {
		var todaysDate = new Date();
		var month = todaysDate.getMonth() + 1;
		var day = todaysDate.getDate();
		var year = todaysDate.getFullYear();
		if (month < 10)
			month = '0' + month.toString();
		if (day < 10)
			day = '0' + day.toString();
		var maxDate = year + 1 + '-' + month + '-' + day;
		var minDate = year + '-' + month + '-' + day;
		$('#schedule_date').attr('max', maxDate);
		$('#schedule_date').attr('min', minDate);
	});
</script>
<body>
	<div class="content-wrapper">
		<c:choose>
			<c:when test="${role eq 'Individual'}"><%@include
					file="HeaderPage.jsp"%></c:when>
			<c:otherwise><%@include file="HPM.jsp"%></c:otherwise>
		</c:choose>
		<div class="col-md-12" id="page-content" align="center">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Schedule An Appointment</h3>
				</div>
			</div>
		</div>
		<div class="panel-body" align="center">
			<form class="form-horizontal" id="ScheduleAppointment"
				action="/AppointmentCreate" method="post">
				<fieldset>
					<div class="form-group">
						<label for="select" class="col-lg-2 control-label">Reason
							for the Appointment</label>
						<div class="col-lg-5">
							<select class="form-control" name="appointment" id="appointment">
								<option value="">Select Option</option>
								<option value="Make Personal Info changes">Make
									Personal Info changes</option>
								<option value="Open/Close an Account">Open/Close an
									Account</option>
								<option value="Deposit/Withdraw Money">Deposit/Withdraw
									Money</option>
								<option value="Cashiers Cheque">Cashiers Cheque</option>
								<option value="Others">Others</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="schedule_date" class="col-lg-2 control-label">Schedule
							Date</label>
						<div class="col-lg-5">
							<input type="date" class="form-control" name="schedule_date"
								id="schedule_date" placeholder="Schedule Date">
						</div>
					</div>

					<div class="form-group">
						<div class="col-lg-7 col-lg-offset-2">
							<button type="reset" class="btn btn-default">Reset</button>
							<button type="submit">Book An Appointment</button>
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />

						</div>
					</div>
					<div>
						<p>${message}</p>
					</div>

				</fieldset>
			</form>
		</div>
	</div>
</body>
</html>
