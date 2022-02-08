<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="pagecontent"/>

<fmt:message key="sign_in.title" var="title"/>
<fmt:message key="reference.sign_up" var="sign_up"/>
<fmt:message key="main.title" var="main_title"/>
<fmt:message key="sign_in.login" var="login"/>
<fmt:message key="sign_in.password" var="password"/>
<fmt:message key="reference.sign_in" var="sign_in"/>
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
    <title>${sign_in}</title>
    <style>
        .sign_in_panel{
            width: 300px;
            color: #1a1e21;
            margin: 100px;
        }
    </style>
</head>
<body>
<div class="main_title" style="color: #0c4128; margin: 50px">
    <h2>
        ${main_title}
    </h2>
</div>
<div class="sign_in_panel">
    <h4>
        ${title}
    </h4>
    <form method="post" action="${path}/controller">
        <input type="hidden" name="command" value="sign_in">
        <div class="input-group mb-3">
            <span class="input-group-text" id="basic-addon1">${login}</span>
            <input type="text" class="form-control" placeholder="${login}" name="login" required value="" pattern="\w{5,20}" aria-label=${login} aria-describedby="basic-addon1">
        </div>

        <div class="input-group mb-3">
            <span class="input-group-text" id="basic-addon2">${password}</span>
            <input type="password" class="form-control" placeholder="${password}" name="password" required value="" pattern="\w{6,20}" aria-label=${password} aria-describedby="basic-addon1">
        </div>
        <div class="container text-lg-start">
            <button type="submit" class="btn btn-secondary">
                ${sign_in}
            </button>
        </div>
    </form>
    <div>
        <div>
            <a href="${path}/controller?command=go_to_main" style="color: #1a1e21; font-size: 20px; font-weight: bold">${back_main}</a>
            <a href="${path}/controller?command=go_to_sign_up" style="color: #1a1e21; font-size: 20px; font-weight: bold">${sign_up}</a>
        </div>
    </div>
</div>
</body>
</html>