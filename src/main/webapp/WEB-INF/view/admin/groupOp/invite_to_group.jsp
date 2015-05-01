<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 01.05.2015
  Time: 0:17
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

<h1>Add user to group <a href="/admin/group_details?name=${name}">${name}</a></h1>

<h2 align="center">${msg}</h2>
<h2 align="center">${exception.exceptionMsg}</h2>

<form action="/admin/add_to_group" method="post">
    <input type="hidden" name="name" value="${name}">
    <input type="search" name="login" value="${login}">
    <input type="submit" value="Go">
</form>
<section class="about">
    <h3>Creation time (ms): ${creationTime}</h3>
</section>
</body>
</html>
