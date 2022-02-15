<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="path" value="${pageContext.request.contextPath}"/>
<c:set var="discounts" value="${requestScope.discount_list}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="user_research.id" var="id"/>
<fmt:message key="user_research.discount_rate" var="disc_rate"/>
<fmt:message key="user_research.discount_type" var="disc_type"/>
<fmt:message key="user_research.user_status" var="user_status"/>
<fmt:message key="user_research.user_role" var="user_role"/>
<fmt:message key="user_research.result" var="result"/>
<fmt:message key="sign_in.login" var="login"/>
<fmt:message key="sign_up.first_name" var="first_name"/>
<fmt:message key="sign_up.last_name" var="last_name"/>
<fmt:message key="sign_up.email" var="email"/>
<fmt:message key="sign_up.phone_number" var="phone_number"/>
<fmt:message key="reference.show_details" var="details"/>
<fmt:message key="user_research.action" var="action"/>

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
    <link href="../../bootstrap-5.0.2-dist/js/bootstrap.bundle.min.js" rel="stylesheet"/>
    <link href="//cdn.datatables.net/1.11.4/css/jquery.dataTables.min.css" rel="stylesheet"/>
    <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
    <title>${result}</title>
</head>
<body>
<div class="container" style="background: #86b7fe; width: 1200px">
    <script type="text/javascript">
        $(document).ready(function () {
            $('#usersTable').DataTable({
                pageLength: 5,
                lengthMenu: [5, 10, 25, 50, 100]
            });
        });
    </script>
    <table id="usersTable" class="display table table-striped table-hover" style="margin-left: 50px">
        <caption></caption>
        <thead>
        <tr>
            <th scope="col">${id}</th>
            <th scope="col">${first_name}</th>
            <th scope="col">${last_name}</th>
            <th scope="col">${login}</th>
            <th scope="col">${email}</th>
            <th scope="col">${phone_number}</th>
            <th scope="col">${user_role}</th>
            <th scope="col">${user_status}</th>
            <th scope="col">${disc_type}</th>
            <th scope="col">${disc_rate}</th>
            <th scope="col">${action}</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${requestScope.user_list}">
            <tr>
                <th scope="row">${user.id}</th>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.login}</td>
                <td>${user.email}</td>
                <td>${user.phoneNumber}</td>
                <td>${user.role}</td>
                <td>${user.status}</td>
                <td>${discounts[user.id].type.name()}</td>
                <td>${discounts[user.id].rate}</td>
                <td>
                    <form method="post" action="${path}/controller">
                        <input type="hidden" name="command" value="go_to_user_info">
                        <input type="hidden" name="current_user_id" value="${user.id}"/>
                        <div class="container text-lg-start">
                            <button type="submit" class="btn btn-secondary">
                                    ${details}
                            </button>
                        </div>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>