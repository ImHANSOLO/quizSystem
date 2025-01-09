<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quiz Page</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/jsp/partials/navbar.jsp" />

<h1>Quiz ID: <c:out value="${quiz.quizId}"/></h1>

<c:forEach var="qq" items="${quizQuestions}">
    <p>
        QQ ID: ${qq.qqId}, Question ID: ${qq.questionId}, user_choice_id: ${qq.userChoiceId}
    </p>
</c:forEach>

<form action="${pageContext.request.contextPath}/quiz/submit" method="post">
    <input type="hidden" name="quizId" value="${quiz.quizId}"/>
    <button type="submit">Submit Quiz</button>
</form>

</body>
</html>
