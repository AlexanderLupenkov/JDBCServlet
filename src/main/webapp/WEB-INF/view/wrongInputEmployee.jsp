<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: samue
  Date: 4/7/2021
  Time: 5:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello</title>
</head>
<body>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/dataStyle.css" type="text/css"/>

<jsp:useBean id="Database" class="com.example.dao.Service" scope="request"/>


<table class="table">
    <thead>
    <tr>
        <th>
            ID
        </th>
        <th>
            Name
        </th>
        <th>
            Age
        </th>
        <th>
            Salary
        </th>
    </tr>
    </thead>
    <c:forEach var="employee" items="${Database.users}">
        <tr>
            <td>
                    ${employee.id}
            </td>
            <td>
                    ${employee.name}
            </td>
            <td>
                    ${employee.age}
            </td>
            <td>
                    ${employee.salary}
            </td>
        </tr>
    </c:forEach>
</table>

<form class="box" method="post" action="<c:url value="/data"/>">

    <label>
        <input type="text" name="name" placeholder="Name"><br/>
    </label>

    <label>
        <input type="text" name="age" placeholder="Age"><br/>
    </label>

    <label>
        <input type="text" name="salary" placeholder="Salary"><br/>
    </label>

    <button type="submit" name="sent" value="add">Add employee</button>

    <button type="submit" name="logout" value="logout">Log out</button>

</form>
<p style="color:red">Wrong input!</p>
</body>
</html>
