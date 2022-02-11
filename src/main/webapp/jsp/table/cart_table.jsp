<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="path" value="${pageContext.request.contextPath}"/>
<c:set var="image_map" value="${sessionScope.menu_images}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<fmt:message key="user_research.id" var="id"/>
<fmt:message key="user_research.result" var="result"/>
<fmt:message key="reference.show_details" var="details"/>
<fmt:message key="user_research.action" var="action"/>
<fmt:message key="menu_research.photo" var="photo"/>
<fmt:message key="menu_creation.name" var="name"/>
<fmt:message key="menu_creation.type" var="type"/>
<fmt:message key="menu_creation.description" var="description"/>
<fmt:message key="menu_creation.price" var="price"/>
<fmt:message key="menu_creation.quantity_in_stock" var="quantity_in_stock"/>
<fmt:message key="order_research.quantity" var="product_quantity"/>
<fmt:message key="client_home.delete_from_cart" var="cart_delete"/>
<fmt:message key="client_home.increase" var="increase"/>
<fmt:message key="client_home.reduce" var="reduce"/>

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
    <title>${result}</title>
</head>
<body>
<div>
    <table class="table" style="background: #86b7fe; width: 1200px; margin: 50px">
        <caption></caption>
        <thead>
        <tr>
            <th scope="col">${id}</th>
            <th scope="col">${name}</th>
            <th scope="col">${type}</th>
            <th scope="col">${description}</th>
            <th scope="col">${price}</th>
            <th scope="col">${photo}</th>
            <th scope="col">${product_quantity}</th>
            <th scope="col">${action}</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="menu" items="${sessionScope.cart}">
            <c:set var="current_menu_id" value="${menu.key.id}"/>
            <tr>
                <th scope="row">${menu.key.id}</th>
                <td>${menu.key.name}</td>
                <td>${menu.key.type}</td>
                <td>${menu.key.description}</td>
                <td>${menu.key.price}</td>
                <td>
                    <div>
                        <img src="${image_map[menu.key.id]}" width="50" class="rounded-circle" alt="no image"/>
                    </div>
                </td>
                <td>${menu.value}</td>
                <td>
                    <form method="post" action="${path}/controller">
                        <input type="hidden" name="command" value="delete_from_cart">
                        <input type="hidden" name="current_menu_id" value="${menu.key.id}">
                        <div class="container text-lg-start">
                            <button type="submit" class="btn btn-secondary">
                                    ${cart_delete}
                            </button>
                        </div>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>