<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2025/1/9
  Time: 23:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Add Question</title></head>
<body>
<h2>Add New Question</h2>
<form action="${pageContext.request.contextPath}/admin/questionAdd" method="post">
  Category:
  <select name="categoryId">
    <c:forEach var="cat" items="${categories}">
      <option value="${cat.categoryId}">${cat.name}</option>
    </c:forEach>
  </select>
  <br/>

  Description:
  <input type="text" name="description" /><br/>

  Active?
  <input type="checkbox" name="active" value="true" /><br/>

  <h3>Choices:</h3>
  <!-- simplest approach: just fix e.g. 4 choices, or let user add lines -->
  <c:forEach begin="0" end="3" var="i">
    <label>Choice <c:out value="${i}"/>:
      <input type="text" name="choiceDesc" />
      <!-- correctIndex radio -->
      <input type="radio" name="correctIndex" value="${i}" />
      (Correct?)
    </label>
    <br/>
  </c:forEach>
  <button type="submit">Add Question</button>
</form>
</body>
</html>
