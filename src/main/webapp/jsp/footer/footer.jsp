<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>
<fmt:message key="footer.about" var="about"/>
<head>
  <meta charset="UTF-8"/>
  <title>Footer</title>
</head>

<footer>
  <div>
    <h6 style="margin-left: 50px; position: absolute; bottom: 0; color: #0c4128; font-weight: bolder; background: #ffc107">
    ${about}
    </h6>
  </div>
</footer>