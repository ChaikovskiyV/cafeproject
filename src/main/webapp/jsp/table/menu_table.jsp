<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="pagecontent"/>

<fmt:message key="user_research.id" var="id"/>
<fmt:message key="user_research.result" var="result"/>
<fmt:message key="reference.show_details" var="details"/>
<fmt:message key="user_research.action" var="action"/>
<fmt:message key="menu_research.photo" var="photo"/>
<fmt:message key="menu_creation.name" var="name"/>
<fmt:message key="menu_creation.type" var="type"/>
<fmt:message key="menu_creation.description" var="description"/>
<fmt:message key="menu_creation.price" var="price"/>
<fmt:message key="menu_creation.quantity_in_stock" var="quantity_in_stock"/>
<fmt:message key="menu_creation.add_cart" var="add_cart"/>


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
  <title>${result}</title>
</head>
<body>
<table class="table" style="background: #86b7fe; margin-left: 50px">
  <caption></caption>
  <thead>
  <tr>
    <th scope="col">${id}</th>
    <th scope="col">${name}</th>
    <th scope="col">${type}</th>
    <th scope="col">${description}</th>
    <th scope="col">${price}</th>
    <th scope="col">${photo}</th>
      <th scope="col">${quantity_in_stock}</th>
    <c:if test="${sessionScope.user_role == 'ADMIN' or sessionScope.user_role == 'BARISTA'}">
      <th scope="col">${action}</th>
    </c:if>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="menu" items="${sessionScope.menu_list}">
    <tr>
      <th scope="row">${menu.id}</th>
      <td>${menu.name}</td>
      <td>${menu.type}</td>
      <td>${menu.description}</td>
      <td>${menu.price}</td>
      <td>${menu.image}</td>
      <td>${menu.quantityInStock}</td>
      <c:if test="${sessionScope.user_role == 'ADMIN' or sessionScope.user_role == 'BARISTA'}">
        <td>
          <form method="post" action="${path}/controller">
            <input type="hidden" name="command" value="go_to_user_info">
            <div class="container text-lg-start">
              <button type="submit" class="btn btn-secondary">
                  ${details}
              </button>
            </div>
          </form>
        </td>
      </c:if>
      <c:if test="${sessionScope.user_role == 'CLIENT'}">
        <td>
          <form method="post" action="${path}/controller">
            <input type="hidden" name="command" value="add_to_cart">
            <input type="hidden" name="current_menu_id" value="${menu.id}">
            <div class="container text-lg-start">
              <button type="submit" class="btn btn-secondary">
                  ${add_cart}
              </button>
            </div>
          </form>
        </td>
      </c:if>
    </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>