<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="user_research.title" var="title"/>
<fmt:message key="order_research.find" var="find"/>
<fmt:message key="order_research.not_found" var="not_found"/>
<fmt:message key="sign_up.email" var="email"/>
<fmt:message key="user_research.find_by_email" var="find_email"/>
<fmt:message key="sign_up.phone_number" var="phone"/>
<fmt:message key="user_research.find_by_phone" var="find_phone"/>
<fmt:message key="user_research.find_parameters" var="find_parameters"/>
<fmt:message key="user_research.find_parameters" var="find_parameters"/>
<fmt:message key="order_research.order_status" var="order_status"/>
<fmt:message key="order_research.waiting" var="order_waiting"/>
<fmt:message key="order_research.cancelled" var="order_cancelled"/>
<fmt:message key="order_research.completed" var="order_completed"/>
<fmt:message key="order_research.ready" var="order_ready"/>
<fmt:message key="order_research.accepted" var="order_accepted"/>
<fmt:message key="order_research.creation_date" var="creation_date"/>
<fmt:message key="order_research.bill_status" var="bill_status"/>
<fmt:message key="order_research.bill_paid" var="bill_paid"/>
<fmt:message key="order_research.bill_not_paid" var="bill_not_paid"/>
<fmt:message key="order_research.payment_date" var="payment_date"/>
<fmt:message key="order_research.delivery_type" var="delivery_type"/>
<fmt:message key="order_research.delivery" var="delivery"/>
<fmt:message key="order_research.delivery_pick_up" var="pick_up"/>
<fmt:message key="order_research.delivery_time" var="delivery_time"/>
<fmt:message key="main.title" var="main_title"/>
<fmt:message key="reference.go_home" var="home"/>
<fmt:message key="reference.back_main" var="back_main"/>
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
    <title>${find}</title>
    <style>
        .order_research {
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
<div class="order_research">
    <div class="container px-5" style="width: 1200px">
        <h4>
            ${title}
        </h4>
        <div class="row gx-5">
            <div class="col">
                <form method="post" action="${path}/controller">
                    <input type="hidden" name="command" value="find_order">
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon1">${email}</span>
                        <input type="text" class="form-control" placeholder="${email}" name="email" value=""
                               pattern="\w+[._-]?\w+@\p{Alpha}+\.\p{Alpha}+" aria-label="${email}"
                               aria-describedby="basic-addon1">
                        <button type="submit" class="btn btn-secondary">
                            ${find_email}
                        </button>
                    </div>
                </form>
            </div>
            <div class="col" style="width: 500px">
                <form method="post" action="${path}/controller">
                    <input type="hidden" name="command" value="find_order">
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon2">${phone}</span>
                        <input type="text" class="form-control" placeholder="${phone}" name="phone_number" value=""
                               pattern="+\d{12}" aria-label=${phone} aria-describedby="basic-addon2">
                        <button type="submit" class="btn btn-secondary">
                            ${find_phone}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <form method="post" action="${path}/controller">
        <input type="hidden" name="command" value="find_order">
        <div class="container px-5" style="width: 1200px">
            <div class="row gx-5">
                <div class="col">
                    <div class="input-group mb-3">
                        <select class="form-select" required name="order_status" aria-label="Waiting">
                            <option selected>${order_status}</option>
                            <option value="Waiting">${order_waiting}</option>
                            <option value="Accepted">${order_accepted}</option>
                            <option value="Cancelled">${order_cancelled}</option>
                            <option value="Ready">${order_ready}</option>
                            <option value="Completed">${order_completed}</option>
                        </select>
                    </div>
                </div>
                <div class="col">
                    <div class="input-group mb-3">
                        <select class="form-select" required name="bill_status" aria-label="Paid">
                            <option selected>${bill_status}</option>
                            <option value="Paid">${bill_paid}</option>
                            <option value="Not_paid">${bill_not_paid}</option>
                        </select>
                    </div>
                </div>
                <div class="col">
                    <div class="input-group mb-3">
                        <select class="form-select" required name="delivery_type" aria-label="Delivery">
                            <option selected>${delivery_type}</option>
                            <option value="Delivery">${delivery}</option>
                            <option value="Pick_up">${pick_up}</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>
        <div class="container px-5" style="width: 1200px">
            <div class="row gx-5" style="width: fit-content">
                <div class="col">
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon3">${creation_date}</span>
                        <input type="date" class="form-control" placeholder="${creation_date}"
                               name="creation_date" value=""
                               aria-label="${creation_date}" aria-describedby="basic-addon3">
                    </div>
                </div>
                <div class="col">
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon4">${payment_date}</span>
                        <input type="date" class="form-control" placeholder="${payment_date}"
                               name="payment_date" value=""
                               aria-label="${payment_date}" aria-describedby="basic-addon4">
                    </div>
                </div>
                <div class="col">
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon5">${delivery_time}</span>
                        <input type="date" class="form-control" placeholder="${delivery_time}"
                               name="delivery_time" value=""
                               aria-label="${delivery_time}" aria-describedby="basic-addon5">
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
<div class="research result">
    <c:if test="${requestScope.result == false}">
        <h4>${not_found}</h4>
    </c:if>
    <c:if test="${requestScope.result == true}">
        <jsp:include page="../table/orders_table.jsp"/>
    </c:if>
</div>
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>