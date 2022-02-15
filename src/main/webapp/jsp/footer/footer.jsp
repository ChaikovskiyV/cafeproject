<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>
<fmt:message key="footer.about" var="about"/>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <title>Footer</title>
</head>

<body>
  <div class="container position-relative e p-3 mb-2 bg-opacity-75" id="foot_position">
    <h6 style="margin-left: 50px; position: absolute; bottom: 0; color: #0c4128; font-weight: bolder; background: #ffc107">
    ${about}
    </h6>
  </div>
  <script type="text/javascript">
    if (document.body.scrollHeight > window.innerHeight) {
      document.getElementById("foot_position").className = "container position-relative e p-3 mb-2 bg-opacity-75";
    } else {
      document.getElementById("foot_position").className = "container position-absolute fixed-bottom e p-3 mb-2 bg-opacity-75";
    }
  </script>
</body>
</html>