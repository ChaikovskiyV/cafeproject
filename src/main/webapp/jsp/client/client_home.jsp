<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="client_home.title" var="title"/>
<fmt:message key="client_home.create_order" var="create_order"/>
<fmt:message key="client_home.clear_cart" var="cart_clear"/>


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
    <link href="../${path}/bootstrap-5.0.2-dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="../${path}/bootstrap-5.0.2-dist/js/bootstrap.bundle.min.js" rel="stylesheet"/>
    <link href="../${path}/css/background.css" rel="stylesheet"/>
    <title>${title}</title>
</head>
<body>
<div>
    <c:if test="${requestScope.show_profile == true}">
        <jsp:include page="../common/user_profile.jsp"/>
    </c:if>
    <c:if test="${not empty requestScope.order_list}">
        <jsp:include page="../table/orders_table.jsp"/>
    </c:if>
    <c:if test="${requestScope.show_cart == true}">
        <jsp:include page="../table/cart_table.jsp"/>
        <div class="container px-2" style="margin-left: 150px">
            <div class="row gx-2">
                <div class="col">
                    <a href="${path}/controller?command=go_to_create_order"
                       style="color: #0c4128; font-size: 20px; font-weight: bold">${create_order}</a>
                </div>
                <div class="col">
                    <a href="${path}/controller?command=clear_cart"
                       style="color: #0c4128; font-size: 20px; font-weight: bold">${cart_clear}</a>
                </div>
            </div>
        </div>
    </c:if>
</div>
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>