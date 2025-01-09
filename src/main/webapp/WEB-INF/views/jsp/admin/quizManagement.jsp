<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quiz Management</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/jsp/partials/navbar.jsp" />

<h2>Quiz Management</h2>

<!-- Filter the form: Enter userId, or select categoryId -->
<form action="${pageContext.request.contextPath}/admin/quizManagement" method="get">
    Filter by User ID: <input type="text" name="filterUserId" />
    OR Filter by Category:
    <select name="filterCategoryId">
        <option value="">--None--</option>
        <c:forEach var="cat" items="${categories}">
            <option value="${cat.categoryId}">${cat.name}</option>
        </c:forEach>
    </select>
    <button type="submit">Filter</button>
</form>

<hr/>

<table border="1">
    <tr>
        <th>Quiz ID</th>
        <th>User ID</th>
        <th>Category ID</th>
        <th>Name</th>
        <th>Start Time</th>
        <th>End Time</th>
        <th>Action</th>
    </tr>
    <c:forEach var="q" items="${allQuizzes}">
        <tr>
            <td>${q.quizId}</td>
            <td>${q.userId}</td>
            <td>${q.categoryId}</td>
            <td>${q.name}</td>
            <td>${q.timeStart}</td>
            <td>${q.timeEnd}</td>
            <td>
                <!-- View details: Admin can view answer details -->
                <a href="${pageContext.request.contextPath}/admin/quizDetail?quizId=${q.quizId}">Detail</a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
