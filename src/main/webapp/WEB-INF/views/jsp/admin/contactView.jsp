<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Let IntelliJ see that 'contact' is on the page -->
<c:set var="contact" scope="page" value="${contact}" />

<html>
<head><title>Contact View</title></head>
<body>
<h2>Contact #<c:out value="${contact.contactId}"/></h2>
<p><strong>Subject:</strong> <c:out value="${contact.subject}"/></p>
<p><strong>Email:</strong> <c:out value="${contact.email}"/></p>
<p><strong>Message:</strong><br/>
    <c:out value="${contact.message}"/>
</p>
<p><strong>Time:</strong> <c:out value="${contact.time}"/></p>

<a href="${pageContext.request.contextPath}/admin/contactManagement">Back</a>
</body>
</html>
