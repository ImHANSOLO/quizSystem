<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav>
    <a href="${pageContext.request.contextPath}/home">Home</a> |
    <c:if test="${not empty loggedUser}">
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </c:if>
    <c:if test="${empty loggedUser}">
        <a href="${pageContext.request.contextPath}/">Login</a> |
        <a href="${pageContext.request.contextPath}/register">Register</a>
    </c:if>
    <a href="${pageContext.request.contextPath}/contact">Contact Us</a>
</nav>
<hr/>
