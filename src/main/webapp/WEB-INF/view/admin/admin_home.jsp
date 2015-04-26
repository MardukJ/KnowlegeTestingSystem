<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 25.04.2015
  Time: 3:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="/resources/css/style_paginator.css">
</head>
<body>
<h1> ADMIN HOME PAGE </h1>
<a href="/all_users">Full users list</a>
<a href="/all_users_page">Users list w pagination</a>
<a href="/logout">logout</a>


<h1>msg =${msg}</h1>

<h1>login =${login}</h1>

<h1>admin =${admin}</h1>


<h3>Creation time (ms): ${creationTime}</h3>
</body>
</html>