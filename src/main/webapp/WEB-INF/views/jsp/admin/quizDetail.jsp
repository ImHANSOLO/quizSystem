<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2025/1/9
  Time: 21:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Quiz Detail</title>
</head>
<body>
<h1>Quiz Detail</h1>
<p>Quiz ID: <c:out value="${quiz.quizId}" /></p>
<p>User ID: <c:out value="${quiz.userId}" /></p>
<p>Category: <c:out value="${quiz.categoryId}" /></p>
<p>Name: <c:out value="${quiz.name}" /></p>
<p>Start Time: <c:out value="${quiz.timeStart}" /></p>
<p>End Time: <c:out value="${quiz.timeEnd}" /></p>
<p>Result: <c:out value="${passFail}" /></p>

<hr/>
<h2>Questions:</h2>
<c:forEach var="qq" items="${quizQuestions}">
  <p>
    QQ ID: <c:out value="${qq.qqId}" /><br/>
    Question ID: <c:out value="${qq.questionId}" /><br/>
    User Choice: <c:out value="${qq.userChoiceId}" /><br/>
  </p>
  <hr/>
</c:forEach>

<a href="${pageContext.request.contextPath}/admin/quizManagement">Back to Management</a>
</body>
</html>

