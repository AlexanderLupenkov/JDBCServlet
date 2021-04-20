<%--
  Created by IntelliJ IDEA.
  User: samue
  Date: 4/8/2021
  Time: 2:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Page not found!</title>
</head>
<body>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/errorPageStyle.css" type="text/css"/>

<form class="info">
    <h1>
        4.0.4.
    </h1>
    <h3>
        Sorry, but this page isn't exist.
    </h3>
</form>

<form class="box" action="${pageContext.request.contextPath}/data">
    <input type="submit" name="" value="Tap to get data"/>
</form>

</body>
</html>
