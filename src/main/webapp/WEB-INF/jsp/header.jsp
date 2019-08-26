<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="page-header">
Hotel Reservation Application:
<c:if test="${currentUser != null}">
	Welcome ${currentUser.name}
	<span style="padding-left:30px;font-size: 14px">
		(<a href="/my-bookings">My Bookings</a>|<a href="/room-booking">Book A Room</a>)
	</span>
	<div style="float:right"><a href="<%= request.getContextPath() %>/logout" style="color:#000 !important">Logout</a></div>
</c:if>
</div>


<div class="page-content">