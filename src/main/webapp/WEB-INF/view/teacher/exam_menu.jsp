<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 06.05.2015
  Time: 0:35
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% pageContext.setAttribute("newLineChar", "\n"); %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" href="/resources/css/main.css">
</head>
<body align="center">
<h1 align="center"> EXAMS MENU </h1>
<a href="/home" align="center">Home</a>
<a href="/teacher/questions" align="center">Question menu</a>
<strike><a href="/teacher/tests" align="center">Exam menu</a></strike>e>
<a href="/logout" align="center">Logout</a><br>

<h1 align="center">${msg}</h1>

<h3 align="center">Creation time (ms): ${creationTime}</h3>
</body>
</html>
