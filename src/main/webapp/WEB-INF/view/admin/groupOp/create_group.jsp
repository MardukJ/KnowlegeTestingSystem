<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 30.04.2015
  Time: 18:44
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User details</title>
    <link rel="stylesheet" href="/resources/css/main.css">
</head>
<body>

<nav>
    <ul id="n" class="clearfix">
        <li><a href="/admin/home">Back to admin homepage</a></li>
    </ul>
</nav>

<div id="content" class="clearfix">
    <section id="left">
        <h2>Create group </h2>

        <h2 align="center">${msg}</h2>

        <h2 align="center">${exception.exceptionMsg}</h2>

        <form action="/admin/create_group" method="post">
            <label> <input type="text" name="groupName" value="${name}"/></label>
            <input type="submit" value="Go"/>
        </form>

</div>
</div>
</body>
</html>
