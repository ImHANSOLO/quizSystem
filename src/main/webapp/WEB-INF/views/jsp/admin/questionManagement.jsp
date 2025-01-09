<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Question Management</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/jsp/partials/navbar.jsp" />
<h2>Question Management</h2>

<h3>Categories</h3>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
    </tr>
    <c:forEach var="cat" items="${categories}">
        <tr>
            <td>${cat.categoryId}</td>
            <td>${cat.name}</td>
        </tr>
    </c:forEach>
</table>

<hr/>

<h3>All Questions</h3>
<table border="1">
    <tr>
        <th>QID</th>
        <th>Category</th>
        <th>Description</th>
        <th>Active</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="q" items="${questions}">
        <tr>
            <td>${q.questionId}</td>
            <td>${q.categoryId}</td>
            <td>${q.description}</td>
            <td>${q.active}</td>
            <td>
                <!-- Toggle Active -->
                <c:choose>
                    <c:when test="${q.active}">
                        <form action="${pageContext.request.contextPath}/admin/toggleQuestionActive" method="post" style="display:inline;">
                            <input type="hidden" name="questionId" value="${q.questionId}" />
                            <input type="hidden" name="isActive" value="false" />
                            <button type="submit">Disable</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form action="${pageContext.request.contextPath}/admin/toggleQuestionActive" method="post" style="display:inline;">
                            <input type="hidden" name="questionId" value="${q.questionId}" />
                            <input type="hidden" name="isActive" value="true" />
                            <button type="submit">Enable</button>
                        </form>
                    </c:otherwise>
                </c:choose>

                <!-- Edit (update) form -->
                <form action="${pageContext.request.contextPath}/admin/updateQuestion" method="post" style="display:inline;">
                    <input type="hidden" name="questionId" value="${q.questionId}" />
                    Category:
                    <select name="categoryId">
                        <c:forEach var="cat" items="${categories}">
                            <option value="${cat.categoryId}" <c:if test="${cat.categoryId == q.categoryId}">selected</c:if>>${cat.name}</option>
                        </c:forEach>
                    </select>
                    Desc: <input type="text" name="description" value="${q.description}"/>
                    <input type="checkbox" name="isActive" value="true" <c:if test='${q.active}'>checked</c:if> />Active?
                    <button type="submit">Update</button>
                </form>

                <!-- Delete -->
                <form action="${pageContext.request.contextPath}/admin/deleteQuestion" method="post" style="display:inline;">
                    <input type="hidden" name="questionId" value="${q.questionId}" />
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<h4>Add New Question</h4>
<form action="${pageContext.request.contextPath}/admin/addQuestion" method="post">
    <label>Category:</label>
    <select name="categoryId">
        <c:forEach var="cat" items="${categories}">
            <option value="${cat.categoryId}">${cat.name}</option>
        </c:forEach>
    </select>
    <br/>
    <label>Description:</label>
    <input type="text" name="description" />
    <br/>
    <label>Active?</label>
    <input type="checkbox" name="isActive" value="true" checked />
    <br/>
    <button type="submit">Add Question</button>
</form>

</body>
</html>
