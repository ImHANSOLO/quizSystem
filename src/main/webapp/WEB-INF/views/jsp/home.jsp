<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head><title>Home</title></head>
<body>
<jsp:include page="/WEB-INF/views/jsp/partials/navbar.jsp" />

<h1>Welcome to Home Page</h1>

<h2>Categories</h2>
<c:forEach var="cat" items="${categories}">
    <form action="${pageContext.request.contextPath}/quiz/start" method="post" style="display:inline;">
        <input type="hidden" name="categoryId" value="${cat.categoryId}"/>
        <input type="text" name="quizName" placeholder="Quiz Name (optional)"/>
        <button type="submit">Start ${cat.name} Quiz</button>
    </form>
    <br/>
</c:forEach>

<h2>Your Quizzes</h2>
<c:forEach var="q" items="${userQuizzes}">
    <p>
        Quiz ID: <c:out value="${q.quizId}"/> -
        Name: <c:out value="${q.name}"/> -
        Start: <c:out value="${q.timeStart}"/> -
        End: <c:out value="${q.timeEnd}"/>
        [ <a href="${pageContext.request.contextPath}quiz/result?quizId=${q.quizId}">View Result</a> ]
    </p>
</c:forEach>

</body>
</html>
