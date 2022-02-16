<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${sessionScope.user}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="client_home.title" var="title"/>
<fmt:message key="client_home.profile" var="profile"/>
<fmt:message key="client_home.profile_parameters" var="profile_parameters"/>
<fmt:message key="sign_in.login" var="login"/>
<fmt:message key="sign_up.login_not_correct" var="login_not_correct"/>
<fmt:message key="sign_up.email" var="email"/>
<fmt:message key="sign_up.email_not_correct" var="email_not_correct"/>
<fmt:message key="sign_up.phone_number" var="phone"/>
<fmt:message key="sign_up.phone_not_correct" var="phone_not_correct"/>
<fmt:message key="sign_up.first_name" var="first_name"/>
<fmt:message key="sign_up.name_not_correct" var="name_not_correct"/>
<fmt:message key="sign_up.last_name" var="last_name"/>
<fmt:message key="sign_in.password" var="password"/>
<fmt:message key="sign_up.password_not_correct" var="password_not_correct"/>
<fmt:message key="sign_up.repeat_password" var="repeat_password"/>
<fmt:message key="client_home.new_password" var="new_password"/>
<fmt:message key="client_home.update_data" var="update_data"/>
<fmt:message key="client_home.update_password" var="update_password"/>
<fmt:message key="client_home.change_password" var="change_password"/>
<fmt:message key="client_home.password_updated" var="password_updated"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
          crossorigin="anonymous">
    <link href="../../bootstrap-5.0.2-dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="../../bootstrap-5.0.2-dist/js/bootstrap.bundle.min.js" rel="stylesheet"/>
    <link href="${path}/css/background.css" rel="stylesheet"/>
    <title>${profile}</title>
</head>
<body>
<div class="container px-5"
     style="padding-left: 50px; color: #0c4128; margin-top: 20px; font-weight: bold; white-space: nowrap">
    <div class="row gx-5">
        <div class="col" style="width: 400px">
            <form method="post" action="${path}/controller">
                <input type="hidden" name="command" value="update_user_data">
                <h4 style="margin-left: 50px; color: #0c4128; font-size: 20px; font-weight: bold; white-space: nowrap">
                    ${profile_parameters}
                </h4>
                <div class="input-group mb-3">
                    <span class="input-group-text" id="basic-addon1">${login}</span>
                    <c:if test="${not empty requestScope.login_check}">
                        <input type="text" class="form-control" placeholder="${login}" name="login" required
                               value=""
                               pattern="\w{5,20}" aria-label="${login}" aria-describedby="basic-addon1">
                        <div>
                            <h5>
                                    ${login_not_correct}
                            </h5>
                        </div>
                    </c:if>
                    <c:if test="${not empty requestScope.user_parameters and empty requestScope.login_check}">
                        <input type="text" class="form-control" placeholder="${login}" name="login" required
                               value="${requestScope.user_parameters[login]}" pattern="\w{5,20}"
                               aria-label="${login}"
                               aria-describedby="basic-addon1">
                    </c:if>
                    <c:if test="${empty requestScope.user_parameters}">
                        <input type="text" class="form-control" placeholder="${login}" name="login" required
                               value="${user.login}" pattern="\w{5,20}"
                               aria-label="${login}"
                               aria-describedby="basic-addon1">
                    </c:if>
                </div>
                <div class="input-group mb-3">
                    <span class="input-group-text" id="basic-addon2">${first_name}</span>
                    <c:if test="${not empty requestScope.first_name_check}">
                        <input type="text" class="form-control" placeholder="${first_name}" name="first_name"
                               required
                               value=""
                               pattern="\w{5,25}" aria-label="${first_name}" aria-describedby="basic-addon2">
                        <div>
                            <h5>
                                    ${name_not_correct}
                            </h5>
                        </div>
                    </c:if>
                    <c:if test="${not empty requestScope.user_parameters and empty requestScope.first_name_check}">
                        <input type="text" class="form-control" placeholder="${first_name}" name="first_name"
                               required
                               value="${requestScope.user_parameters[first_name]}" pattern="\w{5,25}"
                               aria-label="${first_name}"
                               aria-describedby="basic-addon2">
                    </c:if>
                    <c:if test="${empty requestScope.user_parameters}">
                        <input type="text" class="form-control" placeholder="${first_name}" name="first_name"
                               required
                               value="${user.firstName}" pattern="\w{5,25}"
                               aria-label="${first_name}"
                               aria-describedby="basic-addon2">
                    </c:if>
                </div>
                <div class="input-group mb-3">
                    <span class="input-group-text" id="basic-addon3">${last_name}</span>
                    <c:if test="${not empty requestScope.last_name_check}">
                        <input type="text" class="form-control" placeholder="${last_name}" name="last_name"
                               required
                               value=""
                               pattern="\w{5,25}" aria-label="${last_name}" aria-describedby="basic-addon3">
                        <div>
                            <h5>
                                    ${name_not_correct}
                            </h5>
                        </div>
                    </c:if>
                    <c:if test="${not empty requestScope.user_parameters and empty requestScope.last_name_check}">
                        <input type="text" class="form-control" placeholder="${last_name}" name="last_name"
                               required
                               value="${requestScope.user_parameters[last_name]}" pattern="\w{5,25}"
                               aria-label="${last_name}"
                               aria-describedby="basic-addon3">
                    </c:if>
                    <c:if test="${empty requestScope.user_parameters}">
                        <input type="text" class="form-control" placeholder="${last_name}" name="last_name"
                               required
                               value="${user.lastName}" pattern="\w{5,25}"
                               aria-label="${last_name}"
                               aria-describedby="basic-addon3">
                    </c:if>
                </div>
                <div class="input-group mb-3">
                    <span class="input-group-text" id="basic-addon4">${email}</span>
                    <c:if test="${not empty requestScope.email_check}">
                        <input type="text" class="form-control" placeholder="${email}" name="email" required
                               value=""
                               pattern="\w+[._-]?\w+@\p{Alpha}+\.\p{Alpha}+" aria-label="${email}"
                               aria-describedby="basic-addon4">
                        <div>
                            <h5>
                                    ${email_not_correct}
                            </h5>
                        </div>
                    </c:if>
                    <c:if test="${not empty requestScope.user_parameters and empty requestScope.email_check}">
                        <input type="text" class="form-control" placeholder="${email}" name="email" required
                               value="${requestScope.user_parameters[email]}"
                               pattern="\w+[._-]?\w+@\p{Alpha}+\.\p{Alpha}+"
                               aria-label="${email}" aria-describedby="basic-addon4">
                    </c:if>
                    <c:if test="${empty requestScope.user_parameters}">
                        <input type="text" class="form-control" placeholder="${email}" name="email" required
                               value="${user.email}"
                               pattern="\w+[._-]?\w+@\p{Alpha}+\.\p{Alpha}+"
                               aria-label="${email}" aria-describedby="basic-addon4">
                    </c:if>
                </div>
                <div class="input-group mb-3">
                    <span class="input-group-text" id="basic-addon5">${phone}</span>
                    <c:if test="${not empty requestScope.phone_number_check}">
                        <input type="text" class="form-control" placeholder="${phone}" name="phone_number"
                               required value=""
                               pattern="+\d{12}" aria-label="${phone}" aria-describedby="basic-addon5">
                        <div>
                            <h5>
                                    ${phone_not_correct}
                            </h5>
                        </div>
                    </c:if>
                    <c:if test="${not empty requestScope.user_parameters and empty requestScope.phone_number_check}">
                        <input type="text" class="form-control" placeholder="${phone}" name="phone_number"
                               required
                               value="${requestScope.user_parameters[phone]}" pattern="+\d{12}"
                               aria-label="${phone}"
                               aria-describedby="basic-addon5">
                    </c:if>
                    <c:if test="${empty requestScope.user_parameters}">
                        <input type="text" class="form-control" placeholder="${phone}" name="phone_number"
                               required
                               value="${user.phoneNumber}" pattern="+\d{12}"
                               aria-label="${phone}"
                               aria-describedby="basic-addon5">
                    </c:if>
                </div>
                <div>
                    <button type="submit" class="btn btn-secondary">
                        ${update_data}
                    </button>
                </div>
            </form>
        </div>
        <div class="col" style="width: 400px">
            <form method="post" action="${path}/controller">
                <input type="hidden" name="command" value="change_password">
                <h4 style="color: #0c4128; font-size: 20px; font-weight: bold; white-space: nowrap">
                    ${change_password}
                </h4>
                <div class="input-group mb-3">
                    <span class="input-group-text" id="basic-addon6">${password}</span>
                    <input type="password" class="form-control" placeholder="${password}" name="password"
                           required value=""
                           pattern="\w{6,20}" aria-label="${password}" aria-describedby="basic-addon6">
                    <c:if test="${not empty requestScope.password_result_check}">
                        <div>
                            <h5>
                                    ${password_not_correct}
                            </h5>
                        </div>
                    </c:if>
                </div>
                <div class="input-group mb-3">
                    <span class="input-group-text" id="basic-addon7">${new_password}</span>
                    <input type="password" class="form-control" placeholder="${password}" name="new_password"
                           required
                           value=""
                           pattern="\w{6,20}" aria-label="${password}" aria-describedby="basic-addon2">
                    <c:if test="${not empty requestScope.new_password_check}">
                        <div>
                            <h5>
                                    ${password_not_correct}
                            </h5>
                        </div>
                    </c:if>
                </div>
                <div class="input-group mb-3">
                    <span class="input-group-text" id="basic-addon8">${repeat_password}</span>
                    <input type="password" class="form-control" placeholder="${repeat_password}"
                           name="password_repeat"
                           required
                           value="" pattern="\w{6,20}"
                           aria-label=${repeat_password} aria-describedby="basic-addon8">
                    <c:if test="${not empty requestScope.password_repeat_check}">
                        <div>
                            <h5>
                                    ${password_not_correct}
                            </h5>
                        </div>
                    </c:if>
                </div>
                <div>
                    <button type="submit" class="btn btn-secondary">
                        ${update_password}
                    </button>
                </div>
            </form>
            <div>
                <c:if test="${requestScope.is_updated_password == false}">
                    <h4 style="color: #0c4128; white-space: nowrap">
                            ${password_updated}
                    </h4>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>