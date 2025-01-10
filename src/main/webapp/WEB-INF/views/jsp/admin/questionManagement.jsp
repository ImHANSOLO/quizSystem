<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Question Management</title></head>
<body>
<h1>Question Management</h1>
<a href="${pageContext.request.contextPath}/admin/questionAdd">Add New Question</a>

<table border="1">
    <tr>
        <th>QID</th>
        <th>Category</th>
        <th>Description</th>
        <th>Active?</th>
        <th>Action</th>
    </tr>
    <c:forEach var="q" items="${questions}">
        <tr>
            <td><c:out value="${q.questionId}"/></td>
            <td><c:out value="${q.categoryId}"/></td>
            <td><c:out value="${q.description}"/></td>
            <td><c:out value="${q.active}"/></td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/questionEdit?questionId=${q.questionId}">
                    Edit
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
