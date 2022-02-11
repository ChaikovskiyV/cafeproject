<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="pagecontent"/>

<fmt:message key="sign_up.title" var="title"/>
<fmt:message key="main.title" var="main_title"/>
<fmt:message key="sign_in.login" var="login"/>
<fmt:message key="sign_up.login_not_correct" var="login_not_correct"/>
<fmt:message key="sign_in.password" var="password"/>
<fmt:message key="sign_up.password_not_correct" var="password_not_correct"/>
<fmt:message key="sign_up.repeat_password" var="repeat_password"/>
<fmt:message key="sign_up.first_name" var="first_name"/>
<fmt:message key="sign_up.name_not_correct" var="name_not_correct"/>
<fmt:message key="sign_up.last_name" var="last_name"/>
<fmt:message key="sign_up.email" var="email"/>
<fmt:message key="sign_up.email_not_correct" var="email_not_correct"/>
<fmt:message key="sign_up.phone_number" var="phone_number"/>
<fmt:message key="sign_up.phone_not_correct" var="phone_not_correct"/>
<fmt:message key="reference.sign_up" var="sign_up"/>
<fmt:message key="reference.sign_in" var="sign_in"/>
<fmt:message key="reference.back_main" var="back_main"/>
<fmt:message key="language.en" var="en"/>
<fmt:message key="language.ru" var="ru"/>


<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
        crossorigin="anonymous">
  <link href="${path}/bootstrap-5.0.2-dist/css/bootstrap.min.css" rel="stylesheet"/>
  <link href="${path}/bootstrap-5.0.2-dist/js/bootstrap.bundle.min.js" rel="stylesheet"/>
  <link href="${path}/css/background.css" rel="stylesheet"/>
  <title>${sign_up}</title>
  <style>
    .sign_in_panel{
      width: 300px;
      color: #1a1e21;
      margin: 80px;
    }
  </style>
</head>
<body>
<div class="main_title" style="color: #0c4128; margin: 50px">
  <h2>
    ${main_title}
  </h2>
</div>
<div class="sign_in_panel">
  <h4>
    ${title}
  </h4>
  <form method="post" action="${path}/controller">
    <input type="hidden" name="command" value="sign_up_new_user">
    <div class="input-group mb-3">
      <span class="input-group-text" id="basic-addon1">${login}</span>
      <c:if test="${empty requestScope.sing_up_result or requestScope.sing_up_result == true or not empty requestScope.login_check}">
        <input type="text" class="form-control" placeholder="${login}" name="login" required value="" pattern="\w{5,20}" aria-label="${login}" aria-describedby="basic-addon1">
        <c:if test="${not empty requestScope.login_check}">
          <div>
            <h5>
              ${login_not_correct}
            </h5>
          </div>
        </c:if>
      </c:if>
      <c:if test="${requestScope.sing_up_result == false and empty requestScope.login_check}">
        <input type="text" class="form-control" placeholder="${login}" name="login" required value="${requestScope.user_parameters[login]}" pattern="\w{5,20}" aria-label="${login}" aria-describedby="basic-addon1">
      </c:if>
    </div>
    <div class="input-group mb-3">
      <span class="input-group-text" id="basic-addon2">${password}</span>
      <c:if test="${empty requestScope.sing_up_result or requestScope.sing_up_result == true or not empty requestScope.password_result_check}">
      <input type="password" class="form-control" placeholder="${password}" name="password" required value="" pattern="\w{6,20}" aria-label="${password}" aria-describedby="basic-addon2">
        <c:if test="${not empty requestScope.password_result_check}">
          <div>
            <h5>
                ${password_not_correct}
            </h5>
          </div>
        </c:if>
      </c:if>
      <c:if test="${requestScope.sing_up_result == false and empty requestScope.password_result_check}">
      <input type="password" class="form-control" placeholder="${password}" name="password" required value="${requestScope.user_parameters[password]}" pattern="\w{6,20}" aria-label="${password}" aria-describedby="basic-addon2">
      </c:if>
    </div>
    <div class="input-group mb-3">
      <span class="input-group-text" id="basic-addon3">${repeat_password}</span>
      <c:if test="${empty requestScope.sing_up_result or requestScope.sing_up_result == true or not empty requestScope.password_repeat_check}">
      <input type="password" class="form-control" placeholder="${repeat_password}" name="password_repeat" required value="" pattern="\w{6,20}" aria-label=${repeat_password} aria-describedby="basic-addon3">
        <c:if test="${not empty requestScope.password_repeat_check}">
          <div>
            <h5>
                ${password_not_correct}
            </h5>
          </div>
        </c:if>
      </c:if>
    </div>
    <div class="input-group mb-3">
      <span class="input-group-text" id="basic-addon4">${first_name}</span>
      <c:if test="${empty requestScope.sing_up_result or requestScope.sing_up_result == true or not empty requestScope.first_name_check}">
        <input type="text" class="form-control" placeholder="${first_name}" name="first_name" required value="" pattern="\w{5,25}" aria-label="${first_name}" aria-describedby="basic-addon4">
        <c:if test="${not empty requestScope.first_name_check}">
          <div>
            <h5>
                ${name_not_correct}
            </h5>
          </div>
        </c:if>
      </c:if>
      <c:if test="${requestScope.sing_up_result == false and empty requestScope.first_name_check}">
        <input type="text" class="form-control" placeholder="${first_name}" name="first_name" required value="${requestScope.user_parameters[first_name]}" pattern="\w{5,25}" aria-label="${first_name}" aria-describedby="basic-addon4">
      </c:if>
    </div>
    <div class="input-group mb-3">
      <span class="input-group-text" id="basic-addon5">${last_name}</span>
      <c:if test="${empty requestScope.sing_up_result or requestScope.sing_up_result == true or not empty requestScope.last_name_check}">
        <input type="text" class="form-control" placeholder="${last_name}" name="last_name" required value="" pattern="\w{5,25}" aria-label="${last_name}" aria-describedby="basic-addon5">
        <c:if test="${not empty requestScope.last_name_check}">
          <div>
            <h5>
                ${name_not_correct}
            </h5>
          </div>
        </c:if>
      </c:if>
      <c:if test="${requestScope.sing_up_result == false and empty requestScope.last_name_check}">
        <input type="text" class="form-control" placeholder="${last_name}" name="last_name" required value="${requestScope.user_parameters[last_name]}" pattern="\w{5,25}" aria-label="${last_name}" aria-describedby="basic-addon5">
      </c:if>
    </div>

    <div class="input-group mb-3">
      <span class="input-group-text" id="basic-addon6">${email}</span>
      <c:if test="${empty requestScope.sing_up_result or requestScope.sing_up_result == true or not empty requestScope.email_check}">
        <input type="text" class="form-control" placeholder="${email}" name="email" required value="" pattern="\w+[._-]?\w+@\p{Alpha}+\.\p{Alpha}+" aria-label="${email}" aria-describedby="basic-addon6">
        <c:if test="${not empty requestScope.email_check}">
          <div>
            <h5>
                ${email_not_correct}
            </h5>
          </div>
        </c:if>
      </c:if>
      <c:if test="${requestScope.sing_up_result == false and empty requestScope.email_check}">
        <input type="text" class="form-control" placeholder="${email}" name="email" required value="${requestScope.user_parameters[email]}" pattern="\w+[._-]?\w+@\p{Alpha}+\.\p{Alpha}+" aria-label="${email}" aria-describedby="basic-addon6">
      </c:if>
    </div>

    <div class="input-group mb-3">
      <span class="input-group-text" id="basic-addon7">${phone_number}</span>
      <c:if test="${empty requestScope.sing_up_result or requestScope.sing_up_result == true or not empty requestScope.phone_number_check}">
        <input type="text" class="form-control" placeholder="${phone_number}" name="phone_number" required value="" pattern="+\d{12}" aria-label="${phone_number}" aria-describedby="basic-addon7">
        <c:if test="${not empty requestScope.phone_number_check}">
          <div>
            <h5>
                ${phone_not_correct}
            </h5>
          </div>
        </c:if>
      </c:if>
      <c:if test="${requestScope.sing_up_result == false and empty requestScope.phone_number_check}">
        <input type="text" class="form-control" placeholder="${phone_number}" name="phone_number" required value="${requestScope.user_parameters[phone_number]}" pattern="+\d{12}" aria-label="${phone_number}" aria-describedby="basic-addon7">
      </c:if>
    </div>
    <div class="container text-center">
      <button type="submit" class="btn btn-secondary">
        ${sign_up}
      </button>
    </div>
  </form>

  <div >
    <div>
      <a href="${path}/controller?command=go_to_sign_in" style="color: #1a1e21; font-size: 20px; font-weight: bold">${sign_in}</a>
    </div>
    <div>
      <a href="${path}/controller?command=go_to_main" style="color: #1a1e21; font-size: 20px; font-weight: bold">${back_main}</a>
    </div>
  </div>
</div>
</body>
</html>