<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Room Booking</title>
	<link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="../header.jsp"/>
<h1>My Bookings:</h1>

<table class="zdp-data-grid">
	<thead>
		<tr>
			<th>Booking Number</th>
			<th>Room Number</th>
			<th>From Date</th>
			<th>To Date</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
	    <c:if test="${mapSize == 0}">
	      <tr><td colspan="5">No Booking!</td></tr>
	      </c:if>
	    <c:if test="${mapSize != 0}">
	        <c:forEach var="bookingDetailEntry" items="${bookingDetailMap}">
		        <c:forEach var="bookingDetail" items="${bookingDetailEntry.value}" varStatus="loop">
		            <tr>
						<c:if test="${loop.index == 0}">
		            	<td rowspan="${fn:length(bookingDetailEntry.value)}">${bookingDetailEntry.key}</td>
					    </c:if>
	
		            	<td>${bookingDetail.roomNumber}</td>
		            	<td>${bookingDetail.startDate}</td>
		            	<td>${bookingDetail.endDate}</td>
	
						<c:if test="${loop.index == 0}">
		            	<td rowspan="${fn:length(bookingDetailEntry.value)}">
		            		<a href="/booking/cancel/${bookingDetailEntry.key}">Cancel Booking</a>
		            	</td>
					    </c:if>
		            </tr>
	        	</c:forEach>
	        </c:forEach>
	     </c:if>
	</tbody>
</table>

<jsp:include page="../footer.jsp"/>
</body>
</html>