<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bank card info</title>
</head>
<body>
<h3>Bank card info</h3>
<hr><hr>
<c:if test="${requestScope.card_parameters == null}">
    <form action="controller" method="post">
        <input type="hidden" name="command" value="find_bank_card"/>
        <input type="text" name="card_number" value=""/>
        <input type="date" name="card_expiration_date" value=""/>
        <input type="submit" value="Find card"/>
    </form>
    <c:if test="${requestScope.delete_result == true}">
        <h3>Card was deleted successfully!</h3>
    </c:if>
</c:if>
<c:if test="${requestScope.card_parameters != null}">
<table>
    <tr>
        <th>Card number</th>
        <th> Expiration date</th>
        <th>Available amount</th>
    </tr>
    <tr>
        <td><c:out value="${requestScope.card_parameters.card_number}"/> </td>
        <td><c:out value="${requestScope.card_parameters.card_expiration_date}"/> </td>
        <td><c:out value="${requestScope.card_parameters.card_amount}"/> </td>
    </tr>
</table>
<hr><hr>
<form action="controller" method="post">
    <input type="hidden" name="command" value="delete_bank_card">
    <input type="button" value="Delete card"/>
</form>
    <c:if test="${requestScope.delete_result == false}">
        <h3>It hasn't managed to delete this card. May be it has already been deleted.</h3>
    </c:if>
</c:if>
<a href='<c:url value="/jsp/main.jsp"/>'>
    Back to main page
</a>
</body>
</html>