<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="pagecontent"/>

<fmt:message key="user_research.id" var="id"/>
<fmt:message key="user_research.result" var="result"/>
<fmt:message key="reference.show_details" var="details"/>
<fmt:message key="user_research.action" var="action"/>
<fmt:message key="menu_creation.name" var="name"/>
<fmt:message key="menu_creation.type" var="type"/>
<fmt:message key="order_research.bill_status" var="bill_status"/>
<fmt:message key="order_research.product_list" var="product_list"/>
<fmt:message key="order_research.total_price" var="price"/>
<fmt:message key="order_research.quantity" var="quantity"/>
<fmt:message key="order_research.address" var="address"/>
<fmt:message key="order_research.delivery_type" var="delivary_type"/>
<fmt:message key="order_research.delivery_time" var="delivary_time"/>
<fmt:message key="order_research.order_status" var="status"/>
<fmt:message key="order_research.creation_date" var="creation_date"/>
<fmt:message key="sign_up.first_name" var="first_name"/>
<fmt:message key="sign_up.last_name" var="last_name"/>
<fmt:message key="sign_up.email" var="email"/>

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
<table class="table" style="background: #86b7fe; width: 1200px">
    <caption></caption>
    <thead>
    <tr>
        <th scope="col">${id}</th>
        <th scope="col">${creation_date}</th>
        <th scope="col">${status}</th>
        <th scope="col">${price}</th>
        <th scope="col">${bill_status}</th>
        <th scope="col">${delivary_type}</th>
        <th scope="col">${delivary_time}</th>
        <th scope="col">${product_list}</th>
        <th scope="col">${action}</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="order" items="${requestScope.order_list}">
        <tr>
        <th scope="row">${order.id}</th>
        <td>${order.creationDate}</td>
        <td>${order.status}</td>
        <td>${requestScope.bill_list[order.userId].totalPrice}</td>
        <td>${requestScope.bill_list[order.userId].status}</td>
        <td>${requestScope.delivery_list[order.userId].deliveryType}</td>
        <td>${requestScope.delivery_list[order.userId].deliveryTime}</td>
        <td>
        <c:forEach var="food" items="${order.cart}">
            <tr>
                <td>${food.key.name}</td>
                <td>${food.value}</td>
            </tr>
        </c:forEach>
        <td>
            <form method="post" action="${path}/controller">
                <input type="hidden" name="command" value="go_to_user_info">
                <div class="container text-lg-start">
                    <button type="submit" class="btn btn-secondary">
                            ${details}
                    </button>
                </div>
            </form>
        </td>
    </c:forEach>
    </tbody>
</table>
</body>
</html>