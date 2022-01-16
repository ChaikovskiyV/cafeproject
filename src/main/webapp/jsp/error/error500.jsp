<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>


Exception: ${param.exception} <br/>
Message from exception: ${param.message} <br/>
Stack trace:
<c:forEach var="trace" items="${pageContext.exception.stackTrace}">
    <c:out value="${trace}"/>
</c:forEach>
<hr>
<a href="index.jsp">Back to main page</a>
</body>
</html>
