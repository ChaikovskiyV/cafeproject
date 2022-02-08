<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="path" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${sessionScope.user}"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="pagecontent"/>

<fmt:message key="admin_home.title" var="title"/>
<fmt:message key="main.main_button" var="main_button"/>
<fmt:message key="reference.change_language" var="change_language"/>
<fmt:message key="language.en" var="en"/>
<fmt:message key="language.ru" var="ru"/>
<fmt:message key="reference.show_menu" var="show_menu"/>
<fmt:message key="client_home.show_orders" var="show_order"/>
<fmt:message key="reference.sign_out" var="sign_out"/>
<fmt:message key="user_research.find" var="show_users"/>
<fmt:message key="client_home.profile" var="profile"/>
<fmt:message key="card_info.info" var="card_info"/>
<fmt:message key="client_home.welcome" var="welcome"/>
<fmt:message key="client_home.create_order" var="create_order"/>
<fmt:message key="main.title" var="main_title"/>
<fmt:message key="client_home.clear_cart" var="cart_clear"/>
<fmt:message key="menu_creation.create" var="create_product"/>


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
</head>
<body>
<div class="dropdown">
    <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1"
            data-bs-toggle="dropdown" aria-expanded="false">
        ${main_button}
    </button>
    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
        <li><a class="dropdown-item" href="${path}/controller?command=show_menu">${show_menu}</a></li>
        <li><a class="dropdown-item" href="${path}/controller?command=go_to_creation_menu">${create_product}</a></li>
        <li><a class="dropdown-item" href="${path}/controller?command=go_to_find_user">${show_users}</a></li>
        <li><a class="dropdown-item" href="${path}/controller?command=go_to_find_order">${show_order}</a></li>
        <li><a class="dropdown-item" href="${path}/controller?command=go_to_user_profile">${profile}</a></li>
        <li><a class="dropdown-item" href="${path}/controller?command=go_to_card_info">${card_info}</a></li>
        <li><a class="dropdown-item" href="${path}/controller?command=sign_out">${sign_out}</a></li>
    </ul>
</div>
<div class="container px-2">
    <div class="row gx-2" style="width: 1200px; padding-left: 50px; margin-inside: 300">
        <div class="col">
            <h2>
                ${main_title}
            </h2>
        </div>
        <div class="col">
            <h2>
                ${welcome}, ${user.login}!
            </h2>
        </div>
    </div>
</div>
<div>
    <c:if test="${requestScope.show_profile == true}">
        <jsp:include page="user_profile.jsp"/>
    </c:if>
    <c:if test="${requestScope.show_card == true}">
        <jsp:include page="card/card_info.jsp"/>
    </c:if>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>