<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="pagecontent"/>

<fmt:message key="about.title" var="title"/>
<fmt:message key="main.title" var="main_title"/>
<fmt:message key="about.text" var="about"/>
<fmt:message key="reference.back_main" var="back_main"/>
<fmt:message key="language.en" var="en"/>
<fmt:message key="language.ru" var="ru"/>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
          crossorigin="anonymous">
    <link href="${path}/bootstrap-5.0.2-dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${path}/bootstrap-5.0.2-dist/js/bootstrap.bundle.min.js" rel="stylesheet"/>
    <link href="${path}/css/background.css" rel="stylesheet"/>
    <title>${title}</title>
    <style>
        .about_text{
            width: 600px;
            color: #1a1e21;
            margin: 50px;
        }
    </style>
</head>
<body>
<div class="main_title" style="color: #0c4128; margin: 50px">
    <h2>
        ${main_title}
    </h2>
</div>
<div class="about_text">
    <h4>
        ${about}
    </h4>
    <div >
        <div>
            <a href="${path}/controller?command=go_to_main" style="color: #1a1e21; font-size: 20px; font-weight: bold">${back_main}</a>
        </div>
    </div>
</div>
</body>
</html>