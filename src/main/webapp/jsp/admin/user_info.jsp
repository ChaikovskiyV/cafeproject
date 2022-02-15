<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${sessionScope.current_user}"/>
<c:set var="discount" value="${sessionScope.discount}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="user_info.title" var="title"/>
<fmt:message key="user_info.delete" var="delete"/>
<fmt:message key="sign_in.login" var="login"/>
<fmt:message key="sign_up.email" var="email"/>
<fmt:message key="sign_up.phone_number" var="phone"/>
<fmt:message key="sign_up.first_name" var="first_name"/>
<fmt:message key="sign_up.last_name" var="last_name"/>
<fmt:message key="user_research.user_role" var="user_role"/>
<fmt:message key="user_research.user_admin" var="user_admin"/>
<fmt:message key="user_research.user_barista" var="user_barista"/>
<fmt:message key="user_research.user_client" var="user_client"/>
<fmt:message key="user_info.update_role" var="update_role"/>
<fmt:message key="user_info.update_status" var="update_status"/>
<fmt:message key="user_info.update_discount" var="update_discount"/>
<fmt:message key="user_research.discount_rate" var="disc_rate"/>
<fmt:message key="user_research.discount_type" var="disc_type"/>
<fmt:message key="user_research.discount_zero" var="discount_zero"/>
<fmt:message key="user_research.discount_staff" var="discount_staff"/>
<fmt:message key="user_research.discount_personal" var="discount_personal"/>
<fmt:message key="user_research.user_status" var="user_status"/>
<fmt:message key="user_research.user_banned" var="user_banned"/>
<fmt:message key="user_research.user_unbanned" var="user_unbanned"/>
<fmt:message key="reference.go_home" var="go_home"/>
<fmt:message key="user_info.go_find_user" var="go_find_user"/>

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
    <h4 style="color: #0c4128; font-weight: bold; padding-left: 150px; margin-top: 50px">
        ${title}
    </h4>
    <div class="container px-5" style="width: 1200px">
        <div class="row gx-5">
            <div class="col">
                <div class="alert alert-info" role="alert">
                    ${first_name}: ${user.firstName}
                </div>
            </div>
            <div class="col">
                <div class="alert alert-info" role="alert">
                    ${last_name}: ${user.lastName}
                </div>
            </div>
        </div>
    </div>
    <div class="container px-5" style="width: 1200px">
        <div class="row gx-5">
            <div class="col">
                <div class="alert alert-info" role="alert">
                    ${login}: ${user.login}
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
                <form method="post" action="${path}/controller">
                    <input type="hidden" name="command" value="update_user_role">
                    <input type="hidden" name="user_id" value="${user.id}">
                    <div class="row gx-5">
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
                        <div class="col">
                            <button type="submit" class="btn btn-secondary">
                                ${update_role}
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col">
                <form method="post" action="${path}/controller">
                    <input type="hidden" name="command" value="update_user_status">
                    <input type="hidden" name="user_id" value="${user.id}">
                    <div class="row gx-5">
                        <div class="col">
                            <div class="input-group mb-3">
                                <select class="form-select" required name="user_status" aria-label="${user.status}">
                                    <option selected>${user_status}</option>
                                    <option value="Banned">${user_banned}</option>
                                    <option value="Unbanned">${user_unbanned}</option>
                                </select>
                            </div>
                        </div>
                        <div class="col">
                            <button type="submit" class="btn btn-secondary">
                                ${update_status}
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="row gx-5">
                <div class="col">
                    <form method="post" action="${path}/controller">
                        <input type="hidden" name="command" value="update_user_discount">
                        <input type="hidden" name="user_id" value="${user.id}">
                        <div class="row gx-5">
                            <div class="col">
                                <div class="input-group mb-3">
                                    <select class="form-select" required name="discount_type"
                                            aria-label="${discount.type}">
                                        <option selected>${disc_type}</option>
                                        <option value="Zero">${discount_zero}</option>
                                        <option value="Staff">${discount_staff}</option>
                                        <option value="Personal">${discount_personal}</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col">
                                <div class="input-group mb-3">
                                    <span class="input-group-text" id="basic-addon1">${disc_rate}</span>
                                    <input type="text" class="form-control" placeholder="${disc_rate}"
                                           name="discount_rate"
                                           value="${discount.rate}"
                                           pattern="\d\d?" aria-label="${disc_rate}" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="col">
                                <button type="submit" class="btn btn-secondary">
                                    ${update_discount}
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>