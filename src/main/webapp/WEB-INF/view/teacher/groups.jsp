<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<h1 align="center"> TEACHER GROUPS LIST </h1>
<a href="/home" align="center">Home</a>
<a href="/teacher/questions" align="center">Question menu</a>
<a href="/teacher/exams" align="center">Exam management</a>
<a href="/exams" align="center">My exams</a>
<a href="/logout" align="center">Logout</a><br>
<br>

<%-- Using JSTL forEach and out to loop a list and display items in table --%>
<table align="center">
  <tbody>
  <tr>
    <th>Groups:</th>
  </tr>
  <c:forEach items="${requestScope.groupList}" var="group">
        <c:choose>
          <c:when test="${group.blocked}">
          </c:when>
          <c:otherwise>
            <tr><td>
                <a href="/teacher/group?name=${group.groupName}">
                    <c:out value="${group.groupName}"></c:out>
                </a>
            </td></tr>
          </c:otherwise>
        </c:choose>
  </c:forEach>
  </tbody>
</table>

<h3 align="center">Creation time (ms): ${creationTime}</h3>
</body>
</html>