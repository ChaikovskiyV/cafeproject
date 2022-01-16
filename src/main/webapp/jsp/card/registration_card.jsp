<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration bank card</title>
</head>
<body>
<h3>Registration of bank card</h3>
<hr><hr>
<form action="controller" method="post">
    <input type="hidden" name="command" value="registration_new_card"/>
        Number of card:
        <br><br>
    <c:if test="${requestScope.card_id == null or requestScope.card_id > 0}">
        <input type="text" name="card_number" value=""/>
    </c:if>
    <c:if test="${requestScope.card_id == 0}">
        <c:if test="${requestScope.card_number_check == null}">
            <input type="text" name="card_number" value="${requestScope.card_parameters.card_number}"/>
        </c:if>
        <c:if test="${requestScope.card_number_check != null}">
            <input type="text" name="card_number" value=""/>
            <br>
            Card number is wrong
        </c:if>
    </c:if>
        <br><br>
        Expiration date of card:
        <br><br>
    <c:if test="${requestScope.card_id == null or requestScope.card_id > 0}">
        <input type="date" name="card_expiration_date" value=""/>
    </c:if>
    <c:if test="${requestScope.card_id == 0}">
        <c:if test="${requestScope.card_date_check == null}">
            <input type="date" name="card_expiration_date" value="${requestScope.card_parameters.card_expiration_date}"/>
        </c:if>
        <c:if test="${requestScope.card_date_check != null}">
            <input type="date" name="card_expiration_date" value=""/>
            <br>
            Card date is not correct
        </c:if>
    </c:if>
        <br><br>
        Amount:
        <br><br>
    <c:if test="${requestScope.card_id == null or requestScope.card_id > 0}">
        <input type="text" name="card_amount" value=""/>
    </c:if>
    <c:if test="${requestScope.card_id == 0}">
        <c:if test="${requestScope.card_amount_check == null}">
            <input type="text" name="card_amount" value="${requestScope.card_parameters.card_amount}"/>
        </c:if>
        <c:if test="${requestScope.card_amount_check != null}">
            <input type="text" name="card_amount" value=""/>
            <br>
            Card amount is not correct
        </c:if>
    </c:if>
        <br><br>
        <input type="submit" value="register card">
    <c:if test="${requestScope.card_id > 0}">
        <br>
        <h3>Card was registered successfully!</h3>
        <br>
        <a href='<c:url value="/jsp/card/card_info.jsp"/>'>
            Go to card info
        </a>
        <a href='<c:url value="/jsp/main.jsp"/>'>
            Back to main page
        </a>
    </c:if>
</form>
</body>
</html>