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
    <link rel="stylesheet" href="/resources/css/main.css">
</head>
<body align="center">
<h1 align="center"> STUDENT HOME PAGE </h1>

<a href="/exams" align="center">Exams</a>
<a href="/logout" align="center">Logout</a>

<h1 align="center">${msg}</h1>

<h3 align="center">Creation time (ms): ${creationTime}</h3>
</body>
</html>