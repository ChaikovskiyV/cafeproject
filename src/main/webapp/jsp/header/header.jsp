<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<c:set var="path" value="pageContext.request.contextPath"/>
<c:set var="current_user" value="${sessionScope.user}"/>

<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="main.title" var="main_title"/>
<fmt:message key="client_home.welcome" var="welcome"/>
<fmt:message key="header.guest" var="guest"/>
<fmt:message key="user_research.user_admin" var="admin"/>
<fmt:message key="user_research.user_barista" var="barista"/>
<fmt:message key="header.language_en" var="en"/>
<fmt:message key="header.language_ru" var="ru"/>
<fmt:message key="header.title" var="title"/>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
          crossorigin="anonymous">
    <link href="../../bootstrap-5.0.2-dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="../../bootstrap-5.0.2-dist/js/bootstrap.bundle.min.js" rel="stylesheet"/>
    <link href="${path}/css/background.css" rel="stylesheet"/>
    <title>${title}</title>
</head>
<header>
    <div style="margin-left: 50px; color: #ffc107; font-weight: bold; font-size: 30px">
        <div class="container px-5" >
            <div class="row gx-5">
                <div class="col">
                    <c:choose>
                        <c:when test="${sessionScope.user_role eq 'ADMIN'}">
                            <jsp:include page="admin.jsp"/>
                        </c:when>
                        <c:when test="${sessionScope.user_role eq 'BARISTA'}">
                            <jsp:include page="barista.jsp"/>
                        </c:when>
                        <c:when test="${sessionScope.user_role eq 'CLIENT'}">
                            <jsp:include page="client.jsp"/>
                        </c:when>
                        <c:otherwise>
                            <jsp:include page="guest.jsp"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col" style="white-space: nowrap">
                    ${main_title}
                </div>
                <div class="col" style="white-space: nowrap">
                    <c:choose>
                        <c:when test="${sessionScope.user_role eq 'ADMIN'}">
                            ${welcome}, ${admin}!
                        </c:when>
                        <c:when test="${sessionScope.user_role eq 'BARISTA'}">
                            ${welcome}, ${barista}!
                        </c:when>
                        <c:when test="${sessionScope.user_role eq 'CLIENT'}">
                            ${welcome}, ${current_user.firstName}!
                        </c:when>
                        <c:otherwise>
                            ${welcome}, ${guest}!
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col" style="white-space: nowrap">
                    <a href="${pageContext.request.contextPath}/controller?command=change_locale&language=EN" style="color: #ffc107">EN</a>
                    <a href="${pageContext.request.contextPath}/controller?command=change_locale&language=RU" style="color: #ffc107">RU</a>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
</header>