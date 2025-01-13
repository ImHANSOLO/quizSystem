<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head><title>Quiz Result</title></head>
<body>
<jsp:include page="/WEB-INF/views/jsp/partials/navbar.jsp" />

<h1>Quiz Result: <c:out value="${quiz.quizId}"/></h1>
<p>Quiz Name: <c:out value="${quiz.name}"/></p>
<p>Start Time: <c:out value="${quiz.timeStart}"/></p>
<p>End Time: <c:out value="${quiz.timeEnd}"/></p>

<c:choose>
    <c:when test="${passFail eq 'PASS'}">
        <h2 style="color:green;">Congratulations! You passed.</h2>
    </c:when>
    <c:otherwise>
        <h2 style="color:red;">Sorry, you failed.</h2>
    </c:otherwise>
</c:choose>

<h2>Questions:</h2>
<c:forEach var="qq" items="${quizQuestions}">
    <p>
        QQ ID: <c:out value="${qq.qqId}"/> |
        Question ID: <c:out value="${qq.question.questionId}"/> |
        Choice ID:
        <c:choose>
            <c:when test="${qq.userChoice != null}">
                <c:out value="${qq.userChoice.choiceId}"/>
            </c:when>
            <c:otherwise>
                (none)
            </c:otherwise>
        </c:choose>
    </p>
</c:forEach>

<a href="${pageContext.request.contextPath}/home">Take Another Quiz</a>
</body>
</html>
