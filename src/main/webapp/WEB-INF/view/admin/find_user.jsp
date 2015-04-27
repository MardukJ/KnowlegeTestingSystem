<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 27.04.2015
  Time: 1:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="/resources/css/style_paginator.css">
    <title>Find user</title>
</head>
<body align="center">
<h1 align="center"> Find user </h1>

<h2 align="center">${msg}</h2>

<h2 align="center">${exception.exceptionMsg}</h2>

<form action="/admin/user_details" method="get">
    <label> <input type="text" name="login" value="%username%"/></label>
    <input type="submit" value="Go"/>
</form>


<h3 align="center">Creation time (ms): ${creationTime}</h3>
</body>
</html>
