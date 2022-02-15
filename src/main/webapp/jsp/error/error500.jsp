<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.properties.pagecontent"/>


<!DOCTYPE html>
<html lang="en">
<header>
    <jsp:include page="../header/header.jsp"/>
</header>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
          crossorigin="anonymous">
    <link href="../../bootstrap-5.0.2-dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="../../bootstrap-5.0.2-dist/js/bootstrap.bundle.min.js" rel="stylesheet"/>
    <link href="${path}/css/background.css" rel="stylesheet"/>
    <title>Error 500</title>
</head>
<body>
<div class="container"  style="background: #86b7fe; margin-left: 200px; margin-top: 50px; width: 600px">
    <table class="table align-content-center">
        <caption></caption>
        <thead>
        <tr>
            <th scope="col">Error 500</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>
                <br>Request from ${pageContext.errorData.requestURI} is failed
                <br>Servlet name: ${pageContext.errorData.servletName}
                <br>Status code: ${pageContext.errorData.statusCode}
                <c:if test="${not empty pageContext.exception}">
                    <br>Exception: ${pageContext.exception}
                    <br> Message from exception: ${pageContext.exception.message}
                </c:if>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>