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
              <c:if test="${cat.categoryId == question.category.categoryId}">
                selected
              </c:if>>
          ${cat.name}
      </option>
    </c:forEach>
  </select>
  <br/>

  Description:
  <input type="text" name="description" value="${question.description}" /><br/>

  Active?
  <input type="checkbox" name="active" value="true"
         <c:if test="${question.active}">checked</c:if> /><br/>

  <h3>Choices:</h3>
  <c:forEach var="ch" items="${choices}" varStatus="loop">
    <!-- hidden: we store the choice ID -->
    <input type="hidden" name="choiceIds" value="${ch.choiceId}" />
    Choice <c:out value="${loop.index}"/>:
    <input type="text" name="choiceDesc" value="${ch.description}" />
    <!-- If ch.isCorrect => radio checked -->
    <input type="radio" name="correctIndex" value="${loop.index}"
           <c:if test="${ch.correct}">checked</c:if> />
    (Correct?)
    <br/>
  </c:forEach>

  <button type="submit">Update Question</button>
</form>
</body>
</html>
