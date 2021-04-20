<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello!</title>
</head>
<body>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/loginStyle.css" type="text/css"/>

<form class="box" method="POST" action="login">
    <h1>Sign in</h1>
    <label>
        <input type="text" name="name" placeholder="Username">
    </label>
    <label>
        <input type="password" name="pass" placeholder="Password">
    </label>
    <label> Remember me
        <input type="checkbox" name="rememberMe" value="yes">
        <span class="checkmark"></span>
    </label>
    <input type="submit" name="" value="Log in">
</form>
</body>
</html>
