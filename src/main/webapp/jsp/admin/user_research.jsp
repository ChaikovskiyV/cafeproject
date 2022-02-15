<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="user_research.title" var="title"/>
<fmt:message key="reference.go_home" var="home"/>
<fmt:message key="user_research.find" var="find"/>
<fmt:message key="user_research.find_by_login" var="find_login"/>
<fmt:message key="user_research.find_by_email" var="find_email"/>
<fmt:message key="user_research.find_by_phone" var="find_phone"/>
<fmt:message key="user_research.find_parameters" var="find_parameters"/>
<fmt:message key="user_research.not_found" var="not_found"/>
<fmt:message key="user_research.discount_rate" var="disc_rate"/>
<fmt:message key="user_research.discount_type" var="disc_type"/>
<fmt:message key="user_research.discount_zero" var="discount_zero"/>
<fmt:message key="user_research.discount_staff" var="discount_staff"/>
<fmt:message key="user_research.discount_personal" var="discount_personal"/>
<fmt:message key="user_research.user_status" var="user_status"/>
<fmt:message key="user_research.user_banned" var="user_banned"/>
<fmt:message key="user_research.user_unbanned" var="user_unbanned"/>
<fmt:message key="user_research.user_role" var="user_role"/>
<fmt:message key="user_research.user_admin" var="user_admin"/>
<fmt:message key="user_research.user_barista" var="user_barista"/>
<fmt:message key="user_research.user_client" var="user_client"/>
<fmt:message key="main.title" var="main_title"/>
<fmt:message key="sign_in.login" var="login"/>
<fmt:message key="sign_up.first_name" var="first_name"/>
<fmt:message key="sign_up.last_name" var="last_name"/>
<fmt:message key="sign_up.email" var="email"/>
<fmt:message key="sign_up.phone_number" var="phone_number"/>
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
</head>
<body>
<div>
    <h4 style="color: #0c4128; margin-left: 80px; white-space: nowrap">
        ${title}
    </h4>
    <div class="container px-1" style="width: 1300px; margin-left: 10px">
        <div class="row gx-1">
            <div class="col">
                <form method="post" action="${path}/controller">
                    <input type="hidden" name="command" value="find_user">
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon1">${login}</span>
                        <input type="text" class="form-control" placeholder="${login}" name="login" value=""
                               pattern="\w{5,20}"
                               aria-label="${login}" aria-describedby="basic-addon1">
                        <button type="submit" class="btn btn-secondary">
                            ${find_login}
                        </button>
                    </div>
                </form>
            </div>
            <div class="col">
                <form method="post" action="${path}/controller">
                    <input type="hidden" name="command" value="find_user">
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon2">${email}</span>
                        <input type="text" class="form-control" placeholder="${email}" name="email" value=""
                               pattern="\w+[._-]?\w+@\p{Alpha}+\.\p{Alpha}+" aria-label="${email}"
                               aria-describedby="basic-addon1">
                        <button type="submit" class="btn btn-secondary">
                            ${find_email}
                        </button>
                    </div>
                </form>
            </div>

        </div>
        <div class="row gx-1">
            <div class="col" style="width: 500px">
                <form method="post" action="${path}/controller">
                    <input type="hidden" name="command" value="find_user">
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon3">${phone_number}</span>
                        <input type="text" class="form-control" placeholder="${phone_number}" name="phone_number"
                               value=""
                               pattern="+\d{12}" aria-label=${phone_number} aria-describedby="basic-addon1">
                        <button type="submit" class="btn btn-secondary">
                            ${find_phone}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <form method="post" action="${path}/controller">
        <input type="hidden" name="command" value="find_user">
        <div class="container px-5" style="width: 1200px">
            <div class="row gx-5">
                <div class="col">
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon4">${first_name}</span>
                        <input type="text" class="form-control" placeholder="${first_name}" name="first_name" value=""
                               pattern="\w{6,20}" aria-label="${first_name}" aria-describedby="basic-addon1">
                    </div>
                </div>
                <div class="col">
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon5">${last_name}</span>
                        <input type="text" class="form-control" placeholder="${last_name}" name="last_name" value=""
                               pattern="\w{6,20}" aria-label="${last_name}" aria-describedby="basic-addon1">
                    </div>
                </div>
                <div class="col">
                    <div class="input-group mb-3">
                        <select class="form-select" required name="user_role" aria-label="Client">
                            <option selected>${user_role}</option>
                            <option value="Admin">${user_admin}</option>
                            <option value="Barista">${user_barista}</option>
                            <option value="Client">${user_client}</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>
        <div class="container px-5" style="width: 1200px">
            <div class="row gx-5" style="width: fit-content">
                <div class="col">
                    <div class="input-group mb-3">
                        <select class="form-select" required name="user_status" aria-label="Unbanned">
                            <option selected>${user_status}</option>
                            <option value="Banned">${user_banned}</option>
                            <option value="Unbanned">${user_unbanned}</option>
                        </select>
                    </div>
                </div>
                <div class="col">
                    <div class="input-group mb-3">
                        <select class="form-select" required name="discount_type" aria-label="Personal">
                            <option selected>${disc_type}</option>
                            <option value="Zero">${discount_zero}</option>
                            <option value="Staff">${discount_staff}</option>
                            <option value="Personal">${discount_personal}</option>
                        </select>
                    </div>
                </div>
                <div class="col">
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon7">${disc_rate}</span>
                        <input type="text" class="form-control" placeholder="${disc_rate}" name="discount_rate" value=""
                               pattern="\d\d?" aria-label="${disc_rate}" aria-describedby="basic-addon1">
                    </div>
                </div>
            </div>
        </div>
        <div class="container text-lg-start">
            <button type="submit" class="btn btn-secondary">
                ${find_parameters}
            </button>
        </div>
    </form>
</div>
<div class="research result" style="width: 1200px">
    <c:if test="${requestScope.result == false}">
        <h4 style="margin-left: 500px">${not_found}</h4>
    </c:if>
    <c:if test="${requestScope.result == true}">
        <jsp:include page="../table/user_table.jsp"/>
    </c:if>
</div>
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>