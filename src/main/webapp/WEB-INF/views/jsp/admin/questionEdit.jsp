<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2025/1/9
  Time: 23:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Edit Question</title></head>
<body>
<h2>Edit Question</h2>

<form action="${pageContext.request.contextPath}/admin/questionEdit" method="post">
  <input type="hidden" name="questionId" value="${question.questionId}" />
  Category:
  <select name="categoryId">
    <c:forEach var="cat" items="${categories}">
      <option value="${cat.categoryId}"
              <c:if test="${cat.categoryId == question.categoryId}">selected</c:if>>
          ${cat.name}
      </option>
    </c:forEach>
  </select>
  <br/>

  Description:
  <input type="text" name="description" value="${question.description}" /><br/>

  Active?
  <input type="checkbox" name="active" value="true" <c:if test="${question.active}">checked</c:if> /><br/>

  <h3>Choices:</h3>
  <c:forEach var="ch" items="${choices}" varStatus="loop">
    <input type="hidden" name="choiceIds" value="${ch.choiceId}" />
    Choice <c:out value="${loop.index}"/>:
    <input type="text" name="choiceDesc" value="${ch.description}"/>
    <!-- correctIndex radio, if ch.isCorrect => checked -->
    <input type="radio" name="correctIndex" value="${loop.index}"
           <c:if test="${ch.correct}">checked</c:if> />
    (Correct?)
    <br/>
  </c:forEach>

  <button type="submit">Update Question</button>
</form>
</body>
</html>
