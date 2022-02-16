<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="path" value="${pageContext.request.contextPath}"/>
<c:set var="discounts" value="${requestScope.discount_list}"/>
<c:set var="users" value="${requestScope.user_list}"/>

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
    <link href="//cdn.datatables.net/1.11.4/css/jquery.dataTables.min.css" rel="stylesheet"/>
    <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
    <script src="//cdn.datatables.net/1.11.4/js/jquery.dataTables.min.js"></script>
    <title>${result}</title>
</head>
<body>
<div class="container-xxl" style="background: #86b7fe; width: 1300px; margin-left:25px">
    <script type="text/javascript">
        $(document).ready(function () {
            $('#menuTable').DataTable({
                pageLength: 5,
                lengthMenu: [5, 10, 25, 50, 100]
            });
        });
    </script>
    <table id="menuTable" class="display table table-striped table-hover" >
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
        <tbody class="nav-list-search">
        <c:forEach var="current_user" items="${users}">
            <tr class="table-danger">
                <th scope="row">${current_user.id}</th>
                <td>${current_user.firstName}</td>
                <td>${current_user.lastName}</td>
                <td>${current_user.login}</td>
                <td>${current_user.email}</td>
                <td>${current_user.phoneNumber}</td>
                <td>${current_user.role}</td>
                <td>${current_user.status}</td>
                <td>${discounts[current_user.id].type}</td>
                <td>${discounts[current_user.id].rate}</td>
                <td>
                    <form method="post" action="${path}/controller">
                        <input type="hidden" name="command" value="go_to_user_info">
                        <input type="hidden" name="current_user_id" value="${current_user.id}"/>
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