<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head><title>Contact Us</title></head>
<body>
<jsp:include page="/WEB-INF/views/jsp/partials/navbar.jsp" />

<h2>Contact Us</h2>
<c:if test="${not empty msg}">
    <p style="color:green;">${msg}</p>
</c:if>
<form action="${pageContext.request.contextPath}/contact/submit" method="post">
    Subject: <input type="text" name="subject"/><br/>
    Message: <br/>
    <textarea name="message"></textarea><br/>
    Email: <input type="text" name="email"/><br/>
    <input type="submit" value="Send"/>
</form>
</body>
</html>
