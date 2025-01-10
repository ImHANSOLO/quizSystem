<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quiz Management</title>
</head>
<body>

<h1>Quiz Management</h1>
<form action="${pageContext.request.contextPath}/admin/quizManagement" method="get">
    UserId: <input type="text" name="filterUserId" />
    Category: <select name="filterCategoryId">
    <option value="">--All--</option>
    <c:forEach var="cat" items="${categories}">
        <option value="${cat.categoryId}">${cat.name}</option>
    </c:forEach>
</select>
    <button type="submit">Filter</button>
</form>

<table border="1" cellpadding="5" cellspacing="0">
    <tr>
        <th>Quiz ID</th>
        <th>User ID</th>
        <th>Category ID</th>
        <th>Quiz Name</th>
        <th>Start Time</th>
        <th>End Time</th>
        <th>Detail</th>
    </tr>
    <c:forEach var="q" items="${allQuizzes}">
        <tr>
            <td><c:out value="${q.quizId}" /></td>
            <td><c:out value="${q.userId}" /></td>
            <td><c:out value="${q.categoryId}" /></td>
            <td><c:out value="${q.name}" /></td>
            <td><c:out value="${q.timeStart}" /></td>
            <td><c:out value="${q.timeEnd}" /></td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/quizDetail?quizId=${q.quizId}">
                    Detail
                </a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
