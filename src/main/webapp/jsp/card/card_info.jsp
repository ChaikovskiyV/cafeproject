<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>Bank card info</title>
</head>
<body>
<h3>Bank card info</h3>
<hr>
<hr>
<h5>To find card insert card number and expiration date:</h5>
<form action="${path}/controller" method="post">
    <input type="hidden" name="command" value="find_bank_card"/>
    <input type="text" required name="card_number" value="" pattern="\d{16}"/>
    <input type="date" required name="card_expiration_date" value=""/>
    <input type="submit" value="Find card"/>
</form>
<c:if test="${requestScope.is_found == false}">
    <h3>Card wasn't found.</h3>
</c:if>
<c:if test="${sessionScope.delete_result == true}">
    <h3>Card was deleted successfully!</h3>
</c:if>
<c:if test="${sessionScope.card_id > 0}">
    <table>
        <tr>
            <th>Card number</th>
            <th> Expiration date</th>
            <th>Available amount</th>
        </tr>
        <tr>
            <td><c:out value="${sessionScope.card_parameters.card_number}"/></td>
            <td><c:out value="${sessionScope.card_parameters.card_expiration_date}"/></td>
            <td><c:out value="${sessionScope.card_parameters.card_amount}"/></td>
        </tr>
    </table>
    <h5>Top up card balance</h5>
    <form action="${path}/controller" method="post">
        <input type="hidden" name="command" value="top_up_card_balance">
        <input type="submit" value="Top up balance">
        <input type="text" required name="amount" value="" pattern="\d{1,5}">
    </form>
    <form action="${path}/controller" method="post">
        <input type="hidden" name="command" value="delete_bank_card">
        <input type="submit" value="Delete card"/>
    </form>
    <c:if test="${requestScope.result == true}">
        <h3>Card ${sessionScope.card_parameters.card_number} was top up successfully!</h3>
    </c:if>
    <c:if test="${requestScope.result == false}">
        <h3>Sorry, but card ${sessionScope.card_parameters.card_number} wasn't top up!</h3>
    </c:if>
</c:if>
<c:if test="${sessionScope.delete_result == false}">
    <h3>It hasn't managed to delete this card. May be it has already been deleted.</h3>
</c:if>
<a href='<c:url value="/jsp/main.jsp"/>'>
    Back to main page
</a>
<hr/>
<a href='<c:url value="registration_card.jsp"/>'>
    Register new bank card
</a>
</body>
</html>