<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${sessionScope.current_user}"/>
<c:set var="order" value="${sessionScope.current_order}"/>
<c:set var="bill" value="${sessionScope.current_bill}"/>
<c:set var="delivery" value="${sessionScope.current_delivery}"/>
<c:set var="address" value="${sessionScope.current_address}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="order_info.title" var="title"/>
<fmt:message key="order_info.client_parameters" var="client"/>
<fmt:message key="order_info.parameters" var="parameters"/>
<fmt:message key="order_info.update_evaluation" var="evaluate"/>
<fmt:message key="order_info.not_evaluation" var="order_not_evaluated"/>
<fmt:message key="order_info.bad" var="order_bad"/>
<fmt:message key="order_info.nice" var="order_nice"/>
<fmt:message key="order_info.great" var="order_great"/>
<fmt:message key="order_info.brilliant" var="order_brilliant"/>
<fmt:message key="order_info.update_status" var="change_status"/>
<fmt:message key="order_info.delete" var="delete"/>
<fmt:message key="sign_up.email" var="email"/>
<fmt:message key="sign_up.phone_number" var="phone"/>
<fmt:message key="sign_up.first_name" var="first_name"/>
<fmt:message key="sign_up.last_name" var="last_name"/>
<fmt:message key="user_research.user_status" var="user_status"/>
<fmt:message key="order_research.creation_date" var="creation_date"/>
<fmt:message key="order_research.product_list" var="product_list"/>
<fmt:message key="order_research.total_price" var="price"/>
<fmt:message key="order_research.bill_status" var="bill_status"/>
<fmt:message key="order_research.payment_date" var="payment_date"/>
<fmt:message key="order_research.delivery_time" var="delivery_time"/>
<fmt:message key="order_research.delivery_type" var="delivery_type"/>
<fmt:message key="order_research.payment_date" var="payment_date"/>
<fmt:message key="order_research.address" var="address"/>
<fmt:message key="order_research.payment_date" var="payment_date"/>
<fmt:message key="order_creation.comment" var="comment"/>
<fmt:message key="order_research.order_status" var="order_status"/>
<fmt:message key="order_research.ready" var="order_ready"/>
<fmt:message key="order_research.waiting" var="order_waiting"/>
<fmt:message key="order_research.cancelled" var="order_cancelled"/>
<fmt:message key="order_research.completed" var="order_completed"/>
<fmt:message key="order_research.accepted" var="order_accepted"/>
<fmt:message key="order_info.payment" var="payment"/>
<fmt:message key="card.card_number" var="card_number"/>
<fmt:message key="user_info.expiration_date" var="expiration_date"/>
<fmt:message key="order_creation.pay_order" var="pay"/>

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
    <link href="../../bootstrap-5.0.2-dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="../../bootstrap-5.0.2-dist/js/bootstrap.bundle.min.js" rel="stylesheet"/>
    <link href="${path}/css/background.css" rel="stylesheet"/>
    <title>${title}</title>
</head>
<body>
<div>
    <h5 style="color: #0c4128; font-weight: bold; padding-left: 150px; margin-top: 30px">
        ${title}
    </h5>
    <div class="container px-5"
         style="width: 1200px; color: #0c4128; font-weight: bold; padding-left: 150px; margin-top: 20px">
        <h5 style="color: #0c4128; font-weight: bold">
            ${parameters}
        </h5>
        <div class="row gx-5">
            <div class="col">
                <div class="alert alert-info" role="alert">
                    ${creation_date}: ${order.creationDate}
                </div>
            </div>
            <div class="col">
                <div class="alert alert-info" role="alert">
                    <c:forEach var="menu" items="${order.cart}">
                        <div>
                                ${menu.key.name}: ${menu.value}
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="col">
                <div class="alert alert-info" role="alert">
                    <div>
                        ${comment}:
                    </div>
                    <div>
                        ${order.comment}
                    </div>
                </div>
            </div>
            <div class="col">
                <c:if test="${sessionScope.user_role eq 'CLIENT'}">
                    <div class="alert alert-info" role="alert">
                            ${order_status}: ${order.status}
                    </div>
                </c:if>
                <c:if test="${sessionScope.user_role eq 'ADMIN' or sessionScope.user_role eq 'BARISTA'}">
                    <form method="post" action="${path}/controller">
                        <input type="hidden" name="command" value="change_order_status">
                        <input type="hidden" name="order_id" value="${order.id}">
                        <div class="input-group mb-3">
                            <select class="form-select" required name="order_status" aria-label="${order.status}">
                                <option selected>${order.status}</option>
                                <option value="Waiting">${order_waiting}</option>
                                <option value="Accepted">${order_accepted}</option>
                                <option value="Ready">${order_ready}</option>
                                <option value="Cancelled">${order_cancelled}</option>
                                <option value="Completed">${order_completed}</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-secondary">
                                ${change_status}
                        </button>
                    </form>
                </c:if>
            </div>
        </div>
    </div>
    <div class="container px-5" style="width: 1200px; color: #0c4128; font-weight: bold; padding-left: 150px; margin-top: 20px">
        <h5 style="color: #0c4128; font-weight: bold">
            ${payment}
        </h5>
        <div class="row gx-5">
            <div class="col">
                <div class="alert alert-info" role="alert">
                    ${price}: ${bill.totalPrice}
                </div>
            </div>
            <div class="col">
                <div class="alert alert-info" role="alert">
                    ${bill_status}: ${bill.status}
                </div>
            </div>
            <div class="col">
                <div class="alert alert-info" role="alert">
                    ${payment_date}: ${bill.paymentDate}
                </div>
            </div>
        </div>
    </div>
    <div class="container px-5" style="width: 1200px; color: #0c4128; font-weight: bold; padding-left: 150px; margin-top: 20px">
        <h5 style="color: #0c4128; font-weight: bold">
            ${client}
        </h5>
        <div class="row gx-5">
            <div class="col">
                <div class="alert alert-info" role="alert">
                    ${user.firstName} ${user.lastName}
                </div>
            </div>
            <div class="col">
                <div class="alert alert-info" role="alert">
                    ${email}: ${user.email}
                </div>
            </div>
            <div class="col">
                <div class="alert alert-info" role="alert">
                    ${phone}: ${user.phoneNumber}
                </div>
            </div>
        </div>
    </div>
    <div class="container px-5" style="width: 1200px">
        <div class="row gx-5">
            <div class="col">
                <c:if test="${sessionScope.user_role eq 'CLIENT'}">
                    <c:if test="${order.status.name() eq 'COMPLETED'}">
                        <form method="post" action="${path}/controller">
                            <input type="hidden" name="command" value="update_order_evaluation">
                            <input type="hidden" name="order_id" value="${order.id}">
                            <div class="row gx-5">
                                <div class="col">
                                    <div class="input-group mb-3">
                                        <select class="form-select" required name="user_role"
                                                aria-label="${order.evaluation}">
                                            <option selected>${order.evaluation}</option>
                                            <option value="no_evaluation">${order_not_evaluated}</option>
                                            <option value="bad">${order_bad}</option>
                                            <option value="nice">${order_nice}</option>
                                            <option value="great">${order_great}</option>
                                            <option value="brilliant">${order_brilliant}</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col">
                                    <button type="submit" class="btn btn-secondary">
                                            ${evaluate}
                                    </button>
                                </div>
                            </div>
                        </form>
                    </c:if>
                    <c:if test="${order.status.name() eq 'WAITING' and bill.status.name() eq 'NOT_PAID'}">
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
                    </c:if>
                </c:if>
            </div>
        </div>
    </div>
</div>
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>