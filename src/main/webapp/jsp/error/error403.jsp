<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page  isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Error 403</title>
</head>
<body>
<h2>Error 403. This action is not allowed for ${sessionScope.user_role}.</h2>

Request from ${pageContext.errorData.requestURI} is failed<br/>
Servlet name: ${pageContext.errorData.servletName}<br/>
Status code: ${pageContext.errorData.statusCode}<br/>
Exception: ${pageContext.exception}<br/>
Message from exception: ${pageContext.exception.message}<br/>
stack trace: ${pageContext.exception.stackTrace}<br/>

<a href='<c:url value="/index.jsp"/> '>Back to main page</a>
</body>
</html>
