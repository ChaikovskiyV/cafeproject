<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<fmt:message key="user_research.title" var="title"/>
<fmt:message key="menu_research.find" var="find"/>
<fmt:message key="menu_research.not_found" var="not_found"/>
<fmt:message key="menu_creation.name" var="name"/>
<fmt:message key="menu_creation.type" var="type"/>
<fmt:message key="menu_creation.type_coffee" var="coffee"/>
<fmt:message key="menu_creation.type_tea" var="tea"/>
<fmt:message key="menu_creation.type_pastry" var="pastry"/>
<fmt:message key="menu_creation.price" var="price"/>
<fmt:message key="menu_creation.quantity_in_stock" var="quantity"/>
<fmt:message key="reference.go_home" var="home"/>
<fmt:message key="reference.back_main" var="back_main"/>

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
    <title>${find}</title>
</head>
<body>
<div>
    <form method="post" action="${path}/controller">
        <input type="hidden" name="command" value="find_menu">
        <div class="container px-5" style="width: 1200px">
            <h4 style="color: #0c4128; font-weight: bold">
                ${title}
            </h4>
            <div class="row gx-5">
                <div class="col">
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon1">${name}</span>
                        <input type="text" class="form-control" placeholder="${name}" name="menu_name" value=""
                               pattern="\w{6,35}" aria-label="${name}" aria-describedby="basic-addon1">
                    </div>
                </div>
                <div class="col">
                    <div class="input-group mb-3">
                        <select class="form-select" required name="menu_type" aria-label="Personal">
                            <option selected>${type}</option>
                            <option value="Coffee">${coffee}</option>
                            <option value="Tea">${tea}</option>
                            <option value="Pastry">${pastry}</option>
                        </select>
                    </div>
                </div>
                <div class="col">
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon2">${price}</span>
                        <input type="text" class="form-control" placeholder="${price}" name="menu_price" value=""
                               pattern="\d{1,4}" aria-label="${price}" aria-describedby="basic-addon1">
                    </div>
                </div>
                <div class="col">
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon3">${quantity}</span>
                        <input type="text" class="form-control" placeholder="${quantity}"
                               name="menu_quantity_in_stock" value=""
                               pattern="\w{1,4}" aria-label="${quantity}" aria-describedby="basic-addon1">
                    </div>
                </div>
            </div>
        </div>
        <div class="container text-lg-start">
            <button type="submit" class="btn btn-secondary">
                ${find}
            </button>
        </div>
    </form>
</div>
<div class="research result" style="width: 1200px">
    <c:if test="${requestScope.result == false}">
        <h4>${not_found}</h4>
    </c:if>
    <c:if test="${requestScope.result == true or empty requestScope.result}">
        <jsp:include page="../table/menu_table.jsp"/>
    </c:if>
</div>
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>