<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>Exception 400</h2>
<ul>
    <li>Exception: <c:out value="${requestScope['javax.servlet.error.exception']}" /></li>
    <li>Exception type: <c:out value="${requestScope['javax.servlet.error.exception_type']}" /></li>
    <li>Exception message: <c:out value="${requestScope['javax.servlet.error.message']}" /></li>
    <li>Request URI: <c:out value="${requestScope['javax.servlet.error.request_uri']}" /></li>
    <li>Servlet name: <c:out value="${requestScope['javax.servlet.error.servlet_name']}" /></li>
    <li>Status code: <c:out value="${requestScope['javax.servlet.error.status_code']}" /></li>
    <li>Stack trace: <pre>${pageContext.out.flush()}${exception.printStackTrace(pageContext.response.writer)}</pre></li>
</ul>

<a href="/index.jsp">Back to main page</a>
</body>
</html>
