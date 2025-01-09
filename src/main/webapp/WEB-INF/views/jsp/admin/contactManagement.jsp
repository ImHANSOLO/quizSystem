<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Contact Management</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/jsp/partials/navbar.jsp" />
<h2>Contact Management</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Subject</th>
        <th>Email</th>
        <th>Message</th>
        <th>Time</th>
    </tr>
    <c:forEach var="ct" items="${contacts}">
        <tr>
            <td>${ct.contactId}</td>
            <td>${ct.subject}</td>
            <td>${ct.email}</td>
            <td><c:out value="${ct.message}"/></td>
            <td>${ct.time}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
