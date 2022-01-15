<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration bank card</title>
</head>
<body>
<h3>Registration of bank card</h3>
<br/>
<form action="controller" method="post">
    <input type="hidden" name="command" value="registration_new_card"/>
    <c:if test="${requestScope.result == null}">
        Number of card:
        <br><br>
        <input type="text" name="card_number" value=""/>
        <br><br>
        Expiration date of card:
        <br><br>
        <input type="date" name="card_expiration_date" value=""/>
        <br><br>
        Amount:
        <br><br>
        <input type="text" name="card_amount" value=""/>
        <br><br>
        <input type="submit" value="register card">
    </c:if>
    <c:if test="${requestScope.result == true}">
        <br>
        <h3>Card was registered successfully!</h3>
        <br>
        <a href='<c:url value="card_info.jsp"/>'>
            <c:url value="card_info.jsp"/>
        </a>
    </c:if>
    <c:if test="${requestScope.result == false}">

    </c:if>

</form>

<c:if test = "${requestScope.result==true}">
    <p>Card was registered successfully</p>
    <br>
   <a href='<c:url value="card_info.jsp"/>'>
       <c:url value="card_info.jsp"/>
   </a>
</c:if>
<c:if test="${requestScope.result==false}">
    <c:if test="${requestScope.card_number_check != null}">
        <p>Card number is wrong</p>
    </c:if>
    <c:if test="${requestScope.card_number_check == null}">
        card_number="${param.card_number}"
    </c:if>
    <c:if test="${requestScope.card_date_check != null}">
        <p>Card date is not correct</p>
    </c:if>
    <c:if test="${requestScope.card_date_check == null}">
        card_expiration_date="${param.card_expiration_date}"
    </c:if>
    <c:if test="${requestScope.card_amount_check != null}">
        <p>Card amount is not correct</p>
    </c:if>
    <c:if test="${requestScope.card_amount_check == null}">
    card_amount="${param.card_amount}"
</c:if>
</c:if>

</body>
</html>