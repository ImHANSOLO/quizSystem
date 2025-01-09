<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Management</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/jsp/partials/navbar.jsp" />
<h2>User Management</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Email</th>
        <th>Name</th>
        <th>Active</th>
        <th>Admin</th>
        <th>Action</th>
    </tr>
    <c:forEach var="u" items="${users}">
        <tr>
            <td>${u.userId}</td>
            <td>${u.email}</td>
            <td>${u.firstname} ${u.lastname}</td>
            <td>${u.active}</td>
            <td>${u.admin}</td>
            <td>
                <c:choose>
                    <c:when test="${u.active}">
                        <!-- Here, if the user is activated, set the button to false -->
                        <form action="${pageContext.request.contextPath}/admin/toggleUserStatus" method="post" style="display:inline;">
                            <input type="hidden" name="userId" value="${u.userId}" />
                            <input type="hidden" name="isActive" value="false" />
                            <button type="submit">Suspend</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form action="${pageContext.request.contextPath}/admin/toggleUserStatus" method="post" style="display:inline;">
                            <input type="hidden" name="userId" value="${u.userId}" />
                            <input type="hidden" name="isActive" value="true" />
                            <button type="submit">Activate</button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
