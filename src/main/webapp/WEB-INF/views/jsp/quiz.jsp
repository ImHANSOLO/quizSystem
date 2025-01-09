<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Quiz Page</title>
</head>
<body>

<jsp:include page="/WEB-INF/views/jsp/partials/navbar.jsp"/>

<h1>Quiz ID: <c:out value="${quiz.quizId}"/></h1>

<!-- Form that submits to /quiz/submit -->
<form action="${pageContext.request.contextPath}/quiz/submit" method="post">
    <input type="hidden" name="quizId" value="${quiz.quizId}" />

    <c:forEach var="qq" items="${quizQuestions}">
        <h3>Question: <c:out value="${qq.question.description}"/></h3>

        <!-- display choices as radio -->
        <c:forEach var="ch" items="${qq.question.choices}">
            <label>
                <input type="radio"
                       name="userChoice_${qq.qqId}"
                       value="${ch.choiceId}"
                <c:if test="${qq.userChoiceId eq ch.choiceId}">checked</c:if>
                />
                <c:out value="${ch.description}"/>
            </label>
            <br/>
        </c:forEach>
        <hr/>
    </c:forEach>

    <button type="submit">Submit Quiz</button>
</form>

</body>
</html>
