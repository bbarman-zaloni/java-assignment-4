<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Room Booking</title>
	<link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="../header.jsp"/>
<h1>Room Booking:</h1>
<form:form action="${ bookingTarget }" method="post" modelAttribute="booking">
	<form:hidden path="status" required="true" />
	<c:if test="${booking.status == 'SELECT_DATE_RANGE'}">
		<h3>Step-1: Select date for booking</h3>
		<table>
			<tr>
				<td>Start Date(YYYY-MM-DD):</td>
				<td><form:input path="startDate" required="true" /></td>
			</tr>
			<tr>
				<td>End Date(YYYY-MM-DD):</td>
				<td><form:input path="endDate" required="true" /></td>
			</tr>
			<tr>
				<td colspan="2">
					<button type="submit">Next</button>
				</td>
			</tr>
		</table>
	</c:if>

	<c:if test="${booking.status == 'SELECT_ROOM'}">
		<h3>Step-2: Select room for booking</h3>
		<table>
			<tr>
				<td>Start Date:</td>
				<td><form:hidden path="startDate" required="true" />${booking.startDate}</td>
			</tr>
			<tr>
				<td>End Date:</td>
				<td><form:hidden path="endDate" required="true" />${booking.endDate}</td>
			</tr>
			<tr>
				<td colspan="2">
					<c:if test="${not empty booking.rooms}">
			        <c:forEach var="room" items="${booking.rooms}">
		            	ROOM # ${room.roomNumber}: <form:checkbox path="rooms" value="${room.roomNumber}" />
			        </c:forEach>
				    </c:if>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<button type="submit">Next</button>
				</td>
			</tr>
		</table>
	</c:if>

	<c:if test="${booking.status == 'CONFIRM_BOOKING'}">
		<h3>Step-3: Confirm booking details</h3>
		<table>
			<tr>
				<td>Start Date:</td>
				<td><form:hidden path="startDate" required="true" />${booking.startDate}</td>
			</tr>
			<tr>
				<td>End Date:</td>
				<td><form:hidden path="endDate" required="true" />${booking.endDate}</td>
			</tr>
			<tr>
				<td colspan="2">
					<form:hidden path="rooms" required="true" />
					<c:if test="${not empty booking.rooms}">
			        <c:forEach var="room" items="${booking.rooms}">
		            	ROOM # ${room.roomNumber}
			        </c:forEach>
				    </c:if>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<button type="submit">Confirm</button>
				</td>
			</tr>
		</table>
	</c:if>

</form:form>

<jsp:include page="../footer.jsp"/>
</body>
</html>