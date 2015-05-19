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
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="/resources/css/main.css">
</head>
<body align="center">
<h1 align="center"> EXAMS MANAGEMENT </h1>
<a href="/home" align="center">Home</a>
<a href="/teacher/questions" align="center">Question menu</a>
<a href="/teacher/exams" align="center">Exam management</a>
<a href="/exams" align="center">My exams</a>
<a href="/logout" align="center">Logout</a><br>
<br>

<h1 align="center">${msg}</h1><BR>

<BR>
<form action="/teacher/create_exam" method="post">
<button type="submit">Create new test</button>
</form>
<BR>

<%-- Using JSTL forEach and out to loop a list and display items in table --%>
<table align="center">
    <tbody>
    <tr>
        <th>Exam name</th>
        <th>Exam start time</th>
        <th>Status</th>
    </tr>
<c:forEach items="${requestScope.examList}" var="exam">
    <tr>
        <th><a href="/teacher/exam?idExam=${exam.id}">${exam.name}</a></th>
        <th>${exam.startWindowOpen}</th>
        <th>${exam.status}</th>
    </tr>
</c:forEach>
    </tbody>
</table>

<h3 align="center">Creation time (ms): ${creationTime}</h3>
</body>
</html>
