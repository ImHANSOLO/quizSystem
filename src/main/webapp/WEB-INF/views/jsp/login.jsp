<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Login</title>
</head>
<body>
<h2>Login</h2>
<c:if test="${not empty error}">
    <p style="color:red;"><c:out value="${error}" /></p>
</c:if>

<form action="${pageContext.request.contextPath}/login" method="post">
    Email: <input type="text" name="email"/><br/>
    Password: <input type="password" name="password"/><br/>
    <input type="submit" value="Login"/>
</form>
<br/>
<a href="${pageContext.request.contextPath}/register">Register</a>
</body>
</html>

<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <title>About</title>--%>
<%--</head>--%>

<%--<body>--%>

<%--<h1>About</h1>--%>
<%--<p>Interests: interest</p>--%>
<%--<p>Food: food</p>--%>
<%--<p>Abc: abc</p>--%>

<%--</body>--%>

<%--</html>--%>