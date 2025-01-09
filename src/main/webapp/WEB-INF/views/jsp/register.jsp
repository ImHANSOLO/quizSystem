<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head><title>Register</title></head>
<body>
<h2>Register</h2>
<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>
<form action="${pageContext.request.contextPath}/register" method="post">
    Email: <input type="text" name="email"/><br/>
    Password: <input type="password" name="password"/><br/>
    First Name: <input type="text" name="firstname"/><br/>
    Last Name: <input type="text" name="lastname"/><br/>
    <input type="submit" value="Register"/>
</form>
</body>
</html>
