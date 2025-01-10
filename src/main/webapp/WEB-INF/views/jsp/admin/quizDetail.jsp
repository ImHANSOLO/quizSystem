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
  <h3>Question: <c:out value="${qq.question.description}"/></h3>

  <!-- userChoiceId -->
  <c:choose>
    <c:when test="${qq.userChoiceId != null}">
      <!-- Find the choice selected by the user and display the description + true or false -->
      <c:set var="userChosen" value=""/>
      <!-- Iterate over the choices of the question, finding the choice corresponding to userChoiceId -->
      <c:forEach var="ch" items="${qq.question.choices}">
        <c:if test="${ch.choiceId eq qq.userChoiceId}">
          <!-- userChosen = ch.description -->
          <c:set var="userChosen" value="${ch.description}" />
          <c:set var="isCorrect" value="${ch.correct}" />
        </c:if>
      </c:forEach>
      <p>
        You chose: <c:out value="${userChosen}"/> -
        <c:choose>
          <c:when test="${isCorrect}">
            <span style="color:green">Correct</span>
          </c:when>
          <c:otherwise>
            <span style="color:red">Wrong</span>
          </c:otherwise>
        </c:choose>
      </p>
    </c:when>
    <c:otherwise>
      <p style="color:gray">No choice selected</p>
    </c:otherwise>
  </c:choose>

  <hr/>
</c:forEach>

<a href="${pageContext.request.contextPath}/admin/quizManagement">Back to Management</a>
</body>
</html>
