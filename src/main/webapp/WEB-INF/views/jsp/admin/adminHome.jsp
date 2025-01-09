<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Admin Home</title></head>
<body>
<jsp:include page="/WEB-INF/views/jsp/partials/navbar.jsp" />
<h1>Admin Home</h1>
<ul>
    <li><a href="${pageContext.request.contextPath}/admin/userManagement">User Management</a></li>
    <li><a href="${pageContext.request.contextPath}/admin/contactManagement">Contact Management</a></li>
    <li><a href="${pageContext.request.contextPath}/admin/questionManagement">Question Management</a></li>
    <li><a href="${pageContext.request.contextPath}/admin/quizManagement">Quiz Management</a></li>
</ul>
</body>
</html>
