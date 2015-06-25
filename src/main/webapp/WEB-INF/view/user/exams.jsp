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
<h1 align="center"> STUDENT EXAMS </h1>
<a href="/home" align="center">Home</a>
<a href="/logout" align="center">Logout</a><br>

<%-- Using JSTL forEach and out to loop a list and display items in table --%>
<table align="center">
  <tbody>
  <tr>
      <th>Exam name</th>
      <th>Exam start time</th>
      <th>Status</th>
  </tr>
  <c:forEach items="${requestScope.invitesList}" var="invite">
            <tr>
                <td>
                     <c:out value="${invite.inviteExam.name}"></c:out>
                </td>
                <td>
                     <c:out value="${invite.inviteExam.startWindowOpen}"></c:out>
                </td>
                <td>
                     <c:out value="${invite.inviteStatus}"></c:out>
                </td>
                <td>
                    <form action="/exam" method="post">
                        <input type="hidden" name="inviteIdParam" value="${invite.id}">
                        <button type="submit">go</button>
                    </form>
                </td>
            </tr>
  </c:forEach>
  </tbody>
</table>

<h3 align="center">Creation time (ms): ${creationTime}</h3>
</body>
</html>