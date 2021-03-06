<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="order_creation.cart_empty" var="cart_empty"/>
<fmt:message key="client_home.create_order" var="create"/>
<fmt:message key="reference.go_home" var="go_home"/>
<fmt:message key="reference.back_main" var="back_main"/>
<fmt:message key="reference.show_menu" var="show_menu"/>
<fmt:message key="client_home.cart" var="cart"/>
<fmt:message key="order_creation.parameters" var="parameters"/>
<fmt:message key="main.title" var="main_title"/>
<fmt:message key="language.en" var="en"/>
<fmt:message key="language.ru" var="ru"/>
<fmt:message key="order_research.delivery_type" var="delivery_type"/>
<fmt:message key="order_research.delivery" var="delivery"/>
<fmt:message key="order_research.delivery_pick_up" var="pick_up"/>
<fmt:message key="order_research.delivery_time" var="delivery_time"/>
<fmt:message key="order_creation.street" var="street"/>
<fmt:message key="sign_up.name_not_correct" var="street_check"/>
<fmt:message key="order_creation.house" var="house"/>
<fmt:message key="order_creation.house_check" var="house_check"/>
<fmt:message key="order_creation.building" var="building"/>
<fmt:message key="order_creation.flat" var="flat"/>
<fmt:message key="order_creation.number_check" var="number_check"/>
<fmt:message key="order_creation.comment" var="comment"/>
<fmt:message key="order_creation.comment_check" var="comment_check"/>
<fmt:message key="order_creation.wrong" var="wrong"/>
<fmt:message key="order_creation.success" var="success"/>
<fmt:message key="order_creation.go_orders" var="go_orders"/>

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
    <title>${create}</title>
    <style>
        .creation_panel {
            width: 1250px;
            color: #0c4128;
            margin-left: 30px;
            margin-top: 10px;
            white-space: nowrap;
        }
    </style>
</head>
<body>
<c:if test="${sessionScope.cart.size() eq 0 or empty sessionScope.cart}">
    <jsp:include page="../table/cart_table.jsp"/>
    <h4 style="color: #0c4128; margin-left: 200px; font-weight: bold">
            ${cart_empty}
    </h4>
</c:if>
<c:if test="${sessionScope.cart.size() > 0 and empty sessionScope.order_is_created or sessionScope.order_is_created eq false}">
    <form method="post" action="${path}/controller">
        <input type="hidden" name="command" value="create_order">
        <div class="creation_panel">
            <h4 style="margin-left: 100px">
                    ${parameters}
            </h4>
            <div class="container px-2">
                <div class="row gx-2">
                    <div class="col">
                        <div class="input-group mb-3">
                            <select class="form-select" required name="delivery_type"
                                    aria-label="???????????? ???????????? ???? ??????????????????">
                                <option selected>${delivery_type}</option>
                                <option value="delivery">${delivery}</option>
                                <option value="pick_up">${pick_up}</option>
                            </select>
                        </div>
                    </div>
                    <div class="col">
                        <div class="input-group mb-3">
                            <span class="input-group-text" id="basic-addon1">${delivery_time}</span>
                            <input type="date" class="form-control" placeholder="${delivery_time}"
                                   required name="delivery_time" value=""
                                   aria-label="${delivery_time}" aria-describedby="basic-addon1">
                        </div>
                    </div>
                    <div class="col">
                        <div class="input-group mb-3">
                            <span class="input-group-text" id="basic-addon2">${street}</span>
                            <c:if test="${empty requestScope.order_parameters or  not empty requestScope.street_check}">
                                <input type="text" class="form-control" placeholder="${street}" name="street" required
                                       value=""
                                       pattern="(.{3,35})(([a-zA-Z]+[-\s]?[a-zA-Z]+)|([??-????-??]+[-\s]?[??-????-??]+))"
                                       aria-label="${street}" aria-describedby="basic-addon2">
                                <c:if test="${not empty requestScope.street_check}">
                                    <h5>
                                            ${street_check}
                                    </h5>
                                </c:if>
                            </c:if>
                            <c:if test="${not empty requestScope.order_parameters and empty requestScope.street_check}">
                                <input type="text" class="form-control" placeholder="${street}" name="street" required
                                       value="${requestScope.order_parameters.street}"
                                       pattern="(.{3,35})(([a-zA-Z]+[-\s]?[a-zA-Z]+)|([??-????-??]+[-\s]?[??-????-??]+))"
                                       aria-label="${street}"
                                       aria-describedby="basic-addon2">
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="row gx-2">
                    <div class="col">
                        <div class="input-group mb-3">
                            <span class="input-group-text" id="basic-addon3">${house}</span>
                            <c:if test="${empty requestScope.order_parameters or  not empty requestScope.house_number_check}">
                                <input type="text" class="form-control" placeholder="${house}" name="house_number"
                                       required value=""
                                       pattern="[1-9]\d*\w?" aria-label="${house}" aria-describedby="basic-addon3">
                                <c:if test="${not empty requestScope.house_number_check}">
                                    <h5>
                                            ${house_check}
                                    </h5>
                                </c:if>
                            </c:if>
                            <c:if test="${not empty requestScope.order_parameters and empty requestScope.house_number_check}">
                                <input type="text" class="form-control" placeholder="${house}" name="house_number"
                                       required
                                       value="${requestScope.order_parameters.house_number}"
                                       pattern="[1-9]\d*\w?" aria-label="${house}" aria-describedby="basic-addon3">
                            </c:if>
                        </div>
                    </div>
                    <div class="col">
                        <div class="input-group mb-3">
                            <span class="input-group-text" id="basic-addon4">${building}</span>
                            <c:if test="${empty requestScope.order_parameters or  not empty requestScope.building_number_check}">
                                <input type="text" class="form-control" placeholder="${building}" name="building_number"
                                       value=""
                                       pattern="\d{1,5}" aria-label="${building}" aria-describedby="basic-addon4">
                                <c:if test="${not empty requestScope.building_number_check}">
                                    <h5>
                                            ${number_check}
                                    </h5>
                                </c:if>
                            </c:if>
                            <c:if test="${not empty requestScope.order_parameters and empty requestScope.building_number_check}">
                                <input type="text" class="form-control" placeholder="${building}" name="building_number"
                                       value="${requestScope.order_parameters.building_number}"
                                       pattern="\d{1,5}" aria-label="${building}" aria-describedby="basic-addon4">
                            </c:if>
                        </div>
                        <div class="input-group mb-3">
                            <span class="input-group-text" id="basic-addon5">${flat}</span>
                            <c:if test="${empty requestScope.order_parameters or  not empty requestScope.flat_number_check}">
                                <input type="text" class="form-control" placeholder="${flat}" name="flat_number"
                                       value=""
                                       pattern="\d{1,5}" aria-label="${flat}" aria-describedby="basic-addon5">
                                <c:if test="${not empty requestScope.flat_number_check}">
                                    <h5>
                                            ${number_check}
                                    </h5>
                                </c:if>
                            </c:if>
                            <c:if test="${not empty requestScope.order_parameters and empty requestScope.flat_number_check}">
                                <input type="text" class="form-control" placeholder="${flat}" name="flat_number"
                                       value="${requestScope.order_parameters.flat_number}"
                                       pattern="\d{1,5}" aria-label="${flat}" aria-describedby="basic-addon5">
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="row gx-2">
                    <div class="col">
                        <div class="mb-3">
                            <label for="exampleFormControlTextarea1" class="form-label"
                                   style="color: #0c4128; font-size: 20px; font-weight: bold">${comment}</label>
                            <c:if test="${empty requestScope.order_parameters or  not empty requestScope.comment_check}">
                    <textarea class="form-control" id="exampleFormControlTextarea1" rows="3"
                              name="comment"></textarea>
                                <c:if test="${not empty requestScope.comment_check}">
                                    <h5>
                                            ${comment_check}
                                    </h5>
                                </c:if>
                            </c:if>
                            <c:if test="${not empty requestScope.order_parameters and empty requestScope.comment_check}">
                    <textarea class="form-control" id="exampleFormControlTextarea1" rows="3"
                              name="comment">${requestScope.order_parameters.comment}</textarea>
                            </c:if>
                        </div>
                        <div>
                            <c:if test="${sessionScope.order_is_created == false}">
                                <h5 style="color: #b02a37; white-space: nowrap">
                                        ${wrong}
                                </h5>
                            </c:if>
                        </div>
                    </div>
                    <div class="col">
                        <div class="container text-center">
                            <button type="submit" class="btn btn-secondary">
                                    ${create}
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</c:if>
<c:if test="${sessionScope.order_is_created == true and not empty sessionScope.cart}">
    <h5 style="color: #0c4128; white-space: nowrap;margin-left: 40px">
            ${success}
    </h5>
    <jsp:include page="order_payment.jsp"/>
</c:if>
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>