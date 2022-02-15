<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="menu_creation.create" var="create"/>
<fmt:message key="menu_creation.title" var="title"/>
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
<fmt:message key="main.title" var="main_title"/>
<fmt:message key="reference.back_main" var="back_main"/>
<fmt:message key="reference.show_menu" var="show_menu"/>
<fmt:message key="language.en" var="en"/>
<fmt:message key="language.ru" var="ru"/>


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
            width: 400px;
            color: #0c4128;
            margin-left: 80px;
            margin-top: 20px;
            font-weight: bold;
            white-space: nowrap;
        }
    </style>
</head>
<body>
<form method="post" action="${path}/controller">
    <input type="hidden" name="command" value="create_menu">
    <div class="creation_panel">
        <h4>
            ${title}
        </h4>
        <div class="input-group mb-3">
            <span class="input-group-text" id="basic-addon1">${name}</span>
            <c:if test="${empty requestScope.menu_id or (requestScope.menu_id == 0 and not empty requestScope.menu_name_check) or requestScope.menu_id > 0}">
                <input type="text" class="form-control" placeholder="${name}" name="menu_name" required value=""
                       pattern="[\wА-Яа-я]{5,25}" aria-label="${name}" aria-describedby="basic-addon1">
                <c:if test="${not empty requestScope.menu_name_check}">
                    <h5>
                            ${name_check}
                    </h5>
                </c:if>
            </c:if>
            <c:if test="${requestScope.menu_id == 0 and empty requestScope.menu_name_check}">
                <input type="text" class="form-control" placeholder="${name}" name="menu_name" required
                       value="${requestScope.menu_parameters.menu_name}" pattern="[\wА-Яа-я]{5,20}" aria-label="${name}"
                       aria-describedby="basic-addon1">
            </c:if>
        </div>
        <div class="input-group mb-3">
            <select class="form-select" required name="menu_type" aria-label="Пример выбора по умолчанию">
                <option selected>${type}</option>
                <option value="coffee">${coffee}</option>
                <option value="tea">${tea}</option>
                <option value="pastry">${pastry}</option>
            </select>
        </div>
        <div class="mb-3">
            <label for="exampleFormControlTextarea1" class="form-label"
                   style="color: #0c4128; font-size: 20px; font-weight: bold">${description}</label>
            <c:if test="${empty requestScope.menu_id or (requestScope.menu_id == 0 and not empty requestScope.menu_description_check) or requestScope.menu_id > 0}">
                <textarea class="form-control" id="exampleFormControlTextarea1" rows="3"
                          name="menu_description"></textarea>
                <c:if test="${not empty requestScope.menu_description_check}">
                    <h5>
                            ${description_check}
                    </h5>
                </c:if>
            </c:if>
            <c:if test="${requestScope.menu_id == 0 and empty requestScope.menu_description_check}">
                <textarea class="form-control" id="exampleFormControlTextarea1" rows="3"
                          name="menu_description">${requestScope.menu_parameters.menu_description}</textarea>
            </c:if>
        </div>
        <div class="input-group mb-3">
            <span class="input-group-text" id="basic-addon4">${price}</span>
            <c:if test="${empty requestScope.menu_id or (requestScope.menu_id == 0 and not empty requestScope.menu_price_check) or requestScope.menu_id > 0}">
                <input type="text" class="form-control" placeholder="${price}" name="menu_price" required value=""
                       pattern="\d{1,5}" aria-label="${price}" aria-describedby="basic-addon1">
                <c:if test="${not empty requestScope.menu_price_check}">
                    <h5>
                            ${price_check}
                    </h5>
                </c:if>
            </c:if>
            <c:if test="${requestScope.menu_id == 0 and empty requestScope.menu_price_check}">
                <input type="text" class="form-control" placeholder="${price}" name="menu_price" required
                       value="${requestScope.menu_parameters.menu_price}" pattern="\d{1,5}" aria-label="${price}"
                       aria-describedby="basic-addon1">
            </c:if>
        </div>
        <div class="input-group mb-3">
            <span class="input-group-text" id="basic-addon5">${quantity_in_stock}</span>
            <c:if test="${empty requestScope.menu_id or (requestScope.menu_id == 0 and not empty requestScope.menu_quantity_in_stock_check) or requestScope.menu_id > 0}">
                <input type="text" class="form-control" placeholder="${quantity_in_stock}" name="menu_quantity_in_stock"
                       required value="" pattern="\d{1,4}" aria-label="${quantity_in_stock}"
                       aria-describedby="basic-addon1">
                <c:if test="${not empty requestScope.menu_quantity_in_stock_check}">
                    <h5>
                            ${quantity_in_stock_check}
                    </h5>
                </c:if>
            </c:if>
            <c:if test="${requestScope.menu_id == 0 and empty requestScope.menu_price_check}">
                <input type="text" class="form-control" placeholder="${quantity_in_stock}" name="menu_quantity_in_stock"
                       required value="${requestScope.menu_parameters.menu_quantity_in_stock}" pattern="\d{1,4}"
                       aria-label="${quantity_in_stock}" aria-describedby="basic-addon1">
            </c:if>
        </div>
        <div class="mb-3">
            <label for="formFile" class="form-label"
                   style="color: #0c4128; font-size: 20px; font-weight: bold">${image}</label>
            <c:if test="${empty requestScope.menu_id or (requestScope.menu_id == 0 and not empty requestScope.menu_image_check) or requestScope.menu_id > 0}">
                <input type="file" name="menu_image" formenctype="multipart/form-data">
                <c:if test="${not empty requestScope.menu_image_check}">
                    <h5>
                            ${image_check}
                    </h5>
                </c:if>
            </c:if>
            <c:if test="${requestScope.menu_id == 0 and empty requestScope.menu_image_check}">
                <input class="form-control" type="file" id="formFile" name="menu_image"
                       value="${requestScope.menu_parameters.menu_image}" formenctype="multipart/form-data">
            </c:if>
        </div>
        <div>
            <c:if test="${requestScope.menu_id == 0}">
                <h5 style="color: #b02a37">
                        ${wrong}
                </h5>
            </c:if>
            <c:if test="${requestScope.menu_id > 0}">
                <h5 style="color: #0c4128">
                        ${success}
                </h5>
            </c:if>
        </div>
        <div class="container text-center">
            <button type="submit" class="btn btn-secondary">
                ${create}
            </button>
        </div>
    </div>
</form>
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>