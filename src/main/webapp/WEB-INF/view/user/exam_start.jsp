<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 03.05.2015
  Time: 2:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="/resources/css/main.css">
</head>
<body align="center">
<h1 align="center">Start test</h1>
<a href="/home" align="center">Home</a>
<a href="/exams" align="center">Exams</a>
<a href="/logout" align="center">Logout</a><br>

<BR>
<p>Test name: ${invite.inviteExam.name}</p>
<p>Test time: ${invite.inviteExam.testTimeInMinutes} minutes</p>
<BR>
<form action="/exam" method="post">
    <input type="hidden" name="inviteIdParam" value="${requestScope.invite.id}">
    <input type="hidden" name="action" value="do">
    <button type="submit">start</button>
</form>

<h3 align="center">Creation time (ms): ${creationTime}</h3>
</body>
</html>