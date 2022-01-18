<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error 500</title>
</head>
<body>
<h3>Error 500</h3>

Request from ${pageContext.errorData.requestURI} is failed<br/>
Servlet name: ${pageContext.errorData.servletName}<br/>
Status code: ${pageContext.errorData.statusCode}<br/>
Exception: ${pageContext.exception}<br/>
Message from exception: ${pageContext.exception.message}<br/>
stack trace: ${pageContext.exception.stackTrace}<br/>

<a href='<c:url value="/index.jsp"/> '>Back to main page</a>
</body>
</html>
