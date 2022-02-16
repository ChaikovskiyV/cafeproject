<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>
<c:set var="bank_card" value="${sessionScope.card}"/>

<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="user_info.card_title" var="title"/>
<fmt:message key="card.card_number" var="card_number"/>
<fmt:message key="register_card.wrong_number" var="card_wrong"/>
<fmt:message key="user_info.expiration_date" var="card_date"/>
<fmt:message key="register_card.wrong_date" var="wrong_date"/>
<fmt:message key="user_info.card_amount" var="card_amount"/>
<fmt:message key="user_info.amount" var="amount"/>
<fmt:message key="register_card.wrong_amount" var="wrong_amount"/>
<fmt:message key="user_info.register_card" var="register_card"/>
<fmt:message key="card_info.delete" var="delete_card"/>
<fmt:message key="user_info.find_card" var="find_card"/>
<fmt:message key="user_info.top_up" var="top_up_card"/>
<fmt:message key="card_info.not_top_up" var="not_top_up"/>
<fmt:message key="card_info.top_up" var="top_up"/>
<fmt:message key="user_research.action" var="action"/>
<fmt:message key="card_info.deleted" var="deleted"/>
<fmt:message key="card_info.not_deleted" var="not_deleted"/>
<fmt:message key="register_card.registered" var="registerd"/>
<fmt:message key="register_card.not_registered" var="not_registerd"/>
<fmt:message key="card_info.not_found" var="not_found"/>

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
    <link href="//cdn.datatables.net/1.11.4/css/jquery.dataTables.min.css" rel="stylesheet"/>
    <script src="//cdn.datatables.net/1.11.4/js/jquery.dataTables.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
    <title>${title}</title>
    <style>
        .card_panel {
            width: 400px;
            color: #0c4128;
            font-size: 20px;
            font-weight: bold;
            margin: 100px;
            white-space: nowrap;
        }
    </style>
</head>
<body>
<div class="card_panel">
    <h4 style="color: #0c4128; font-size: 20px; font-weight: bold; margin-left: 50px">
        ${title}
    </h4>
    <c:if test="${requestScope.register_card == true}">
        <form method="post" action="${path}/controller">
            <input type="hidden" name="command" value="registration_new_card">
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon1">${card_number}</span>
                <c:if test="${empty sessionScope.card_id or sessionScope.card_id > 0 or (sessionScope.card_id == 0 and not empty requestScope.card_number_check)}">
                    <input type="text" class="form-control" placeholder="${card_number}" name="card_number" required
                           value="" pattern="\d{16}" aria-label=${card_number} aria-describedby="basic-addon1">
                    <c:if test="${not empty requestScope.card_number_check}">
                        <div>
                                ${card_wrong}
                        </div>
                    </c:if>
                </c:if>
                <c:if test="${sessionScope.card_id == 0 and empty requestScope.card_number_check}">
                    <input type="text" class="form-control" placeholder="${card_number}" name="card_number" required
                           value="${requestScope.card_parameters.card_number}" pattern="\d{16}"
                           aria-label=${card_number} aria-describedby="basic-addon1">
                </c:if>
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon2">${card_date}</span>
                <input type="date" class="form-control" placeholder="${card_date}" name="card_expiration_date" required
                       value="" aria-label=${card_date} aria-describedby="basic-addon2">
                <c:if test="${not empty requestScope.card_date_check}">
                    <div>
                            ${wrong_date}
                    </div>
                </c:if>
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon3">${card_amount}</span>
                <c:if test="${empty sessionScope.card_id or sessionScope.card_id > 0 or (sessionScope.card_id == 0 and not empty requestScope.card_amount_check)}">
                    <input type="text" class="form-control" placeholder="${card_amount}" name="card_amount" required
                           value="" pattern="\d{1,4}" aria-label=${card_amount} aria-describedby="basic-addon3">
                    <c:if test="${not empty requestScope.card_amount_check}">
                        <div>
                                ${wrong_amount}
                        </div>
                    </c:if>
                </c:if>
                <c:if test="${sessionScope.card_id == 0 and empty requestScope.card_amount_check}">
                    <input type="text" class="form-control" placeholder="${card_amount}" name="card_amount" required
                           value="${requestScope.card_parameters.card_amount}" pattern="\d{1,4}"
                           aria-label=${card_amount} aria-describedby="basic-addon3">
                </c:if>
            </div>
            <div class="container text-lg-start">
                <button type="submit" class="btn btn-secondary">
                        ${register_card}
                </button>
            </div>
        </form>
        <div>
            <c:if test="${sessionScope.card_id > 0}">
                ${registerd}
            </c:if>
            <c:if test="${sessionScope.card_id == 0}">
                ${not_registerd}
            </c:if>
        </div>
        <div>
            <a href="${path}/controller?command=go_to_find_bank_card"
               style="color: #0c4128; font-size: 20px; font-weight: bolder">${find_card}</a>
        </div>
    </c:if>
    <c:if test="${requestScope.find_card eq true or
    (sessionScope.is_found eq false or empty sessionScope.is_found) and empty requestScope.register_card}">
        <form method="post" action="${path}/controller">
            <input type="hidden" name="command" value="find_bank_card">
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon4">${card_number}</span>
                <input type="text" class="form-control" placeholder="${card_number}" name="card_number" required
                       value="" pattern="\d{16}" aria-label=${card_number} aria-describedby="basic-addon4">
            </div>

            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon5">${card_date}</span>
                <input type="date" class="form-control" placeholder="${card_date}" name="card_expiration_date" required
                       value="" aria-label=${card_date} aria-describedby="basic-addon5">
            </div>
            <c:if test="${sessionScope.is_found eq false}">
                ${not_found}
            </c:if>
            <div class="container text-lg-start">
                <button type="submit" class="btn btn-secondary">
                        ${find_card}
                </button>
            </div>
        </form>
        <div>
            <a href="${path}/controller?command=go_to_registration_card"
               style="color: #0c4128; font-size: 20px; font-weight: bolder; white-space: nowrap">${register_card}</a>
        </div>
    </c:if>
    <c:if test="${sessionScope.is_found eq true and empty requestScope.register_card and
    empty requestScope.find_card}">
    <table class="table" style="background: #86b7fe; margin-left: 50px">
        <caption></caption>
        <thead>
        <tr>
            <th scope="col">${card_number}</th>
            <th scope="col">${card_date}</th>
            <th scope="col">${card_amount}</th>
            <c:if test="${empty requestScope.is_deleted}">
                <th scope="col">${delete_card}</th>
                <th scope="col">${top_up_card}</th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th scope="row">${bank_card.cardNumber}</th>
            <td>${bank_card.expirationDate}</td>
            <td>${bank_card.amount}</td>
            <c:if test="${empty requestScope.is_deleted}">
                <td>
                    <form method="post" action="${path}/controller">
                        <input type="hidden" name="command" value="delete_bank_card">
                        <input type="hidden" name="card_id" value="${bank_card.id}">
                        <div class="container text-lg-start">
                            <button type="submit" class="btn btn-secondary">
                                    ${delete_card}
                            </button>
                        </div>
                    </form>
                </td>
                <td>
                    <form method="post" action="${path}/controller">
                        <input type="hidden" name="command" value="top_up_card_balance">
                        <input type="hidden" name="card_id" value="${bank_card.id}">
                        <div class="input-group mb-3">
                            <span class="input-group-text" id="basic-addon3">${amount}</span>
                            <input type="text" class="form-control" placeholder="${card_amount}" name="card_amount"
                                   required
                                   value="" pattern="\d{1,4}" aria-label=${card_amount} aria-describedby="basic-addon3">
                            <c:if test="${requestScope.result == false}">
                                <div>
                                        ${wrong_amount}
                                </div>
                            </c:if>
                        </div>
                        <div class="container text-lg-start">
                            <button type="submit" class="btn btn-secondary">
                                    ${top_up_card}
                            </button>
                        </div>
                    </form>
                </td>
            </c:if>
        </tr>
        </tbody>
    </table>
    <div>
        <c:if test="${requestScope.is_deleted == true}">
            ${deleted}
        </c:if>
        <c:if test="${requestScope.is_deleted == false}">
            ${not_deleted}
        </c:if>
        <c:if test="${requestScope.result == false}">
            ${not_top_up}
        </c:if>
    </div>
    <div style="color: #0c4128; font-size: 20px; font-weight: bolder; margin-left: 50px">
        <a href="${path}/controller?command=go_to_registration_card">${register_card}</a>
        <a href="${path}/controller?command=go_to_find_bank_card" style="padding-left: 30px">${find_card}</a>
    </div>
</div>
</c:if>

<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>