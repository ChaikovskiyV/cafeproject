<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="path" value="${pageContext.request.contextPath}"/>
<c:set var="order" value="${sessionScope.current_order}"/>
<c:set var="bill" value="${sessionScope.bill}"/>
<c:set var="order_parameters" value="${sessionScope.order_parameters}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="user_research.id" var="id"/>
<fmt:message key="user_research.result" var="result"/>
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
<fmt:message key="order_creation.pay_order" var="pay"/>
<fmt:message key="card.card_number" var="card_number"/>
<fmt:message key="card.expiration_date" var="expiration_date"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
          crossorigin="anonymous">
    <link href="../../bootstrap-5.0.2-dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="../../bootstrap-5.0.2-dist/js/bootstrap.bundle.min.js" rel="stylesheet"/>
    <link href="${path}/css/background.css" rel="stylesheet"/>
    <title>${result}</title>
</head>
<body>
<table class="table" style="background: #86b7fe; width: 1200px;margin-left: 30px">
    <caption></caption>
    <thead>
    <tr>
        <th scope="col">${id}</th>
        <th scope="col">${creation_date}</th>
        <th scope="col">${status}</th>
        <th scope="col">${product_list}</th>
        <th scope="col">${delivary_type}</th>
        <th scope="col">${delivary_time}</th>
        <th scope="col">${address}</th>
        <th scope="col">${bill_status}</th>
        <th scope="col">${price}</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <th scope="row">${order.id}</th>
        <td>${order.creationDate}</td>
        <td>${order.status}</td>
        <td>
            <c:forEach var="food" items="${order.cart}">
            <div>
                    ${food.key.name}: ${food.value}
            </div>
            </c:forEach>
        <td>${order_parameters.delivery_type}</td>
        <td>${order_parameters.delivery_time}</td>
        <td>
            <div>
                ${order_parameters.street}
            </div>
            <div>
                ${order_parameters.house_number} -
                ${order_parameters.building_number} -
                ${order_parameters.flat_number}
            </div>
        </td>
        <td>${bill.status}</td>
        <td>${bill.totalPrice}</td>
    </tbody>
</table>
<form method="post" action="${path}/controller">
    <input type="hidden" name="command" value="pay">
    <input type="hidden" name="total_price" value="${bill.totalPrice}">
    <div class="container px-2" style="margin: 20px">
        <div class="row gx-2">
            <div class="col">
                <div class="input-group mb-3">
                    <span class="input-group-text" id="basic-addon1">${card_number}</span>
                    <input type="text" class="form-control" placeholder="${card_number}" required name="card_number"
                           value=""
                           pattern="\d{16}" aria-label="${card_number}" aria-describedby="basic-addon1">
                </div>
            </div>
            <div class="col">
                <div class="input-group mb-3">
                    <span class="input-group-text" id="basic-addon2">${expiration_date}</span>
                    <input type="date" class="form-control" placeholder="${expiration_date}" required name="card_expiration_date"
                           value=""
                           aria-label="${expiration_date}" aria-describedby="basic-addon2">
                </div>
            </div>
            <div class="col">
                <div class="container text-lg-start">
                    <button type="submit" class="btn btn-secondary">
                        ${pay}
                    </button>
                </div>
            </div>
        </div>
    </div>
</form>
</body>
</html>