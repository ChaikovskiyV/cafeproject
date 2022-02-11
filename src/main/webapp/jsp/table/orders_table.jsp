<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="path" value="${pageContext.request.contextPath}"/>
<c:set var="orders" value="${requestScope.order_list}"/>
<c:set var="users" value="${requestScope.user_list}"/>
<c:set var="bills" value="${requestScope.bill_list}"/>
<c:set var="deliveries" value="${requestScope.delivery_list}"/>
<c:set var="address_list" value="${requestScope.address_list}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
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
<div class="container">
    <div class="dataTables_paginate">
        <table class="table table-striped table-hover" style="background: #86b7fe; width: 1100px; margin-top: 50px">
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
            <tbody class="nav-list-search">
            <c:forEach var="order" items="${orders}">
            <tr class="alert-danger">
                <th scope="row">${order.id}</th>
                <td>${order.creationDate}</td>
                <td>${order.status}</td>
                <td>${bills[order.userId].totalPrice}</td>
                <td>${bills[order.userId].status}</td>
                <td>${deliveries[order.userId].deliveryType}</td>
                <td>${deliveries[order.userId].deliveryTime}</td>
                <td>
                    <div>
                        <c:forEach var="food" items="${order.cart}">
                            <p>
                                    ${food.key.name}: ${food.value}
                            </p>
                        </c:forEach>
                    </div>
                <td>
                    <form method="post" action="${path}/controller">
                        <input type="hidden" name="command" value="go_to_order_info">
                        <input type="hidden" name="current_order_id" value="${order.id}">
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
    </div>
    <link href="//cdn.datatables.net/1.11.4/css/jquery.dataTables.min.css" rel="stylesheet"/>
    <script src="//cdn.datatables.net/1.11.4/js/jquery.dataTables.min.js"></script>
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#orders_table').DataTable({
                renderer: {
                    "header": "jqueryui",
                    "pageButton": "bootstrap"
                },
                "pageLength": 5,
                "lengthMenu": [ 5, 10, 20, 25]
            });
        });
    </script>
</div>
</body>
</html>