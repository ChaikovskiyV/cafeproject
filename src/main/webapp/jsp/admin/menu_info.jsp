<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="path" value="${pageContext.request.contextPath}"/>

<c:set var="current_menu" value="${sessionScope.menu}"/>
<c:set var="menu_param" value="${requestScope.menu_parameters}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<fmt:message key="menu_info.update" var="update"/>
<fmt:message key="menu_info.updated" var="updated"/>
<fmt:message key="menu_info.parameters" var="parameters"/>
<fmt:message key="menu_creation.name" var="name"/>
<fmt:message key="menu_creation.name_check" var="name_check"/>
<fmt:message key="menu_creation.type" var="type"/>
<fmt:message key="menu_creation.type_coffee" var="coffee"/>
<fmt:message key="menu_creation.type_tea" var="tea"/>
<fmt:message key="menu_creation.type_pastry" var="pastry"/>
<fmt:message key="menu_creation.description" var="description"/>
<fmt:message key="menu_creation.description_check" var="description_check"/>
<fmt:message key="menu_creation.price" var="price"/>
<fmt:message key="menu_creation.price_check" var="price_check"/>
<fmt:message key="menu_creation.quantity_in_stock" var="quantity_in_stock"/>
<fmt:message key="menu_creation.quantity_in_stock_check" var="quantity_in_stock_check"/>
<fmt:message key="menu_creation.image" var="image"/>
<fmt:message key="menu_creation.image_check" var="image_check"/>
<fmt:message key="menu_creation.wrong" var="wrong"/>
<fmt:message key="menu_creation.success" var="success"/>
<fmt:message key="order_research.quantity" var="quantity"/>
<fmt:message key="menu_info.top_up" var="top_up"/>


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
    <link href="${path}/bootstrap-5.0.2-dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${path}/bootstrap-5.0.2-dist/js/bootstrap.bundle.min.js" rel="stylesheet"/>
    <link href="${path}/css/background.css" rel="stylesheet"/>
    <title>${update}</title>
</head>
<body>
<div>
    <h4 style="width: 600px; margin-left: 50px; color: #0c4128">
        ${parameters}
    </h4>
</div>
<div class="container px-5"
     style="padding-left: 50px; color: #0c4128; margin-top: 20px; font-weight: bold; white-space: nowrap">
    <div class="row gx-5">
        <div class="col" style="width: 600px">
            <form method="post" action="${path}/controller">
                <input type="hidden" name="command" value="update_menu_name">
                <input type="hidden" name="menu_id" value="${current_menu.id}">
                <div class="row gx-1">
                    <div class="col">
                        <div class="input-group mb-3">
                            <span class="input-group-text" id="basic-addon1">${name}</span>
                            <c:if test="${empty requestScope.is_updated_name or requestScope.is_updated_name == true}">
                                <input type="text" class="form-control" placeholder="${name}" name="menu_name" required
                                       value="${current_menu.name}" pattern="[\wА-Яа-я]{5,25}" aria-label="${name}"
                                       aria-describedby="basic-addon1">
                            </c:if>
                            <c:if test="${requestScope.is_updated_name == false}">
                                <input type="text" class="form-control" placeholder="${name}" name="menu_name" required
                                       value=""
                                       pattern="[\wА-Яа-я]{5,20}" aria-label="${name}" aria-describedby="basic-addon1">
                                <h5>
                                        ${name_check}
                                </h5>
                            </c:if>
                        </div>
                    </div>
                    <c:if test="${sessionScope.user_role eq 'ADMIN'}">
                        <div class="col">
                            <div class="container text-center">
                                <button type="submit" class="btn btn-secondary">
                                        ${update}
                                </button>
                            </div>
                        </div>
                    </c:if>
                </div>
            </form>
            <form method="post" action="${path}/controller">
                <input type="hidden" name="command" value="update_menu_type">
                <input type="hidden" name="menu_id" value="${current_menu.id}">
                <div class="row gx-1">
                    <div class="col">
                        <div class="input-group mb-3">
                            <select class="form-select" required name="menu_type"
                                    aria-label="Пример выбора по умолчанию">
                                <option selected>${current_menu.type}</option>
                                <option value="coffee">${coffee}</option>
                                <option value="tea">${tea}</option>
                                <option value="pastry">${pastry}</option>
                            </select>
                        </div>
                    </div>
                    <c:if test="${sessionScope.user_role eq 'ADMIN'}">
                        <div class="col">
                            <div class="container text-center">
                                <button type="submit" class="btn btn-secondary">
                                        ${update}
                                </button>
                            </div>
                        </div>
                    </c:if>
                </div>
            </form>
            <form method="post" action="${path}/controller">
                <input type="hidden" name="command" value="update_menu_description">
                <input type="hidden" name="menu_id" value="${current_menu.id}">
                <div class="row gx-1">
                    <div class="col">
                        <div class="mb-3">
                            <label for="exampleFormControlTextarea1" class="form-label"
                                   style="color: #0c4128; font-size: 20px; font-weight: bold">${description}</label>
                            <c:if test="${empty requestScope.is_updated_description or requestScope.is_updated_description == true}">
                <textarea class="form-control" id="exampleFormControlTextarea1" rows="3"
                          name="menu_description">${current_menu.description}</textarea>
                            </c:if>
                            <c:if test="${requestScope.is_updated_description == false}">
                <textarea class="form-control" id="exampleFormControlTextarea1" rows="3"
                          name="menu_description"></textarea>
                                <h5>
                                        ${description_check}
                                </h5>
                            </c:if>
                        </div>
                    </div>
                    <c:if test="${sessionScope.user_role eq 'ADMIN'}">
                        <div class="col">
                            <div class="container text-center">
                                <button type="submit" class="btn btn-secondary">
                                        ${update}
                                </button>
                            </div>
                        </div>
                    </c:if>
                </div>
            </form>
            <form method="post" action="${path}/controller">
                <input type="hidden" name="command" value="update_menu_price">
                <input type="hidden" name="menu_id" value="${current_menu.id}">
                <div class="row gx-1">
                    <div class="col">
                        <div class="input-group mb-3">
                            <span class="input-group-text" id="basic-addon4">${price}</span>
                            <c:if test="${empty requestScope.is_updated_price or requestScope.is_updated_price == true}">
                                <input type="text" class="form-control" placeholder="${price}" name="menu_price"
                                       required
                                       value="${current_menu.price}" pattern="\d{1,5}" aria-label="${price}"
                                       aria-describedby="basic-addon1">
                            </c:if>
                            <c:if test="${requestScope.is_updated_price == false}">
                                <input type="text" class="form-control" placeholder="${price}" name="menu_price"
                                       required
                                       value="" pattern="\d{1,5}" aria-label="${price}"
                                       aria-describedby="basic-addon1">
                                <h5>
                                        ${price_check}
                                </h5>
                            </c:if>
                        </div>
                    </div>
                    <<c:if test="${sessionScope.user_role eq 'ADMIN'}">
                    <div class="col">
                        <div class="container text-center">
                            <button type="submit" class="btn btn-secondary">
                                    ${update}
                            </button>
                        </div>
                    </div>
                </c:if>
                </div>
            </form>
            <form method="post" action="${path}/controller">
                <input type="hidden" name="command" value="replenish_menu_stock">
                <input type="hidden" name="menu_id" value="${current_menu.id}">
                <div class="row gx-1">
                    <div class="col">
                        <div class="alert alert-info" role="alert" style="width: 350px">
                            ${quantity_in_stock}: ${current_menu.quantityInStock}
                        </div>
                    </div>
                </div>
                <div class="row gx-1">
                    <div class="col">
                        <div class="input-group mb-3">
                            <span class="input-group-text" id="basic-addon5">${quantity}</span>
                            <input type="text" class="form-control" placeholder="${quantity_in_stock}"
                                   name="menu_quantity_in_stock"
                                   required value="${current_menu.quantityInStock}" pattern="\d{1,4}"
                                   aria-label="${quantity_in_stock}" aria-describedby="basic-addon1">
                            <c:if test="${requestScope.is_updated_quantity == false}">
                                <h5>
                                        ${quantity_in_stock_check}
                                </h5>
                            </c:if>
                        </div>
                    </div>
                    <c:if test="${sessionScope.user_role eq 'ADMIN'}">
                        <div class="col">
                            <div class="container text-center">
                                <button type="submit" class="btn btn-secondary">
                                        ${top_up}
                                </button>
                            </div>
                        </div>
                    </c:if>
                </div>
            </form>
        </div>
        <div class="col" style="width: 300px">
            <form method="post" action="${path}/controller">
                <input type="hidden" name="command" value="update_menu_image">
                <input type="hidden" name="menu_id" value="${current_menu.id}">
                <div class="row gx-1">
                    <div class="col">
                        <div class="mb-3">
                            <img src="${sessionScope.menu_images[current_menu.id]}" width="50" class="rounded-circle"
                                 alt="no image"/>
                            <c:if test="${requestScope.is_updated_image == false}">
                            <input type="file" name="menu_image" formenctype="multipart/form-data">
                                <h5>
                                        ${image_check}
                                </h5>
                            </c:if>
                        </div>
                        <c:if test="${sessionScope.user_role eq 'ADMIN'}">
                            <div class="col">
                                <div class="container text-center">
                                    <button type="submit" class="btn btn-secondary">
                                            ${update}
                                    </button>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>