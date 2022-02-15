<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${sessionScope.user}"/>

<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="header.guest" var="title"/>
<fmt:message key="main.main_button" var="main_button"/>
<fmt:message key="reference.show_menu" var="show_menu"/>
<fmt:message key="header.sign_in" var="sign_in"/>
<fmt:message key="reference.sign_up" var="sign_up"/>
<fmt:message key="about.title" var="read_about"/>
<fmt:message key="header.back_main" var="back_main"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>${title}</title>
</head>
<body>
<div class="dropdown">
    <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1"
            data-bs-toggle="dropdown" aria-expanded="false">
        ${main_button}
    </button>
    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
        <li><a class="dropdown-item" href="${path}/controller?command=show_menu">${show_menu}</a></li>
        <li><a class="dropdown-item" href="${path}/controller?command=go_to_about">${read_about}</a></li>
        <li><a class="dropdown-item" href="${path}/controller?command=go_to_sign_in">${sign_in}</a></li>
        <li><a class="dropdown-item" href="${path}/controller?command=go_to_sign_up">${sign_up}</a></li>
        <li><a class="dropdown-item" href="${path}/controller?command=go_to_main">${back_main}</a></li>
    </ul>
</div>
</body>
</html>