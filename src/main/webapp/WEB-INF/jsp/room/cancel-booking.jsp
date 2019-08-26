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
<h1>Cancel Room Booking:</h1>
<form:form action="${ bookingTarget }" method="post">
	<h3>Are you sure to cancel booking number ${ bookingNumber }?</h3>
	<button type="submit">Cancel Booking</button>
</form:form>
<jsp:include page="../footer.jsp"/>
</body>
</html>