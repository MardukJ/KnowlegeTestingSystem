<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 06.05.2015
  Time: 0:35
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
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
<h1 align="center"> CREATE EXAM: STEP 3 </h1>

<a href="/home" align="center">Home</a>
<a href="/teacher/questions" align="center">Question menu</a>
<a href="/teacher/exams" align="center">Exam menu</a>
<a href="/logout" align="center">Logout</a><br> <BR>

<h1 align="center">${msg}</h1><BR>
<h2>${exception.exceptionMsg}</h2>

<form action="/teacher/create_exam_final" method="get">
    <p>Exam name:</p><BR>
    <input type="text" name="nameParam" value="${myExam.name}"><BR>

    <p>Scoring algorithm:</p><BR>
    <select size="1" name="scoringAlgorithmParam">
        <c:set var="enumValues" value="<%=ua.epam.rd.domain.ScoringAlgorithm.values()%>"/>
        <c:forEach items="${enumValues}" var="enumValue">
            <option <c:if test="${enumValue == myExam.scoringAlgorithm}">selected</c:if>>${enumValue}</option>
        </c:forEach>
    </select>

    <p>Exam start time:</p><BR>
    <p><input type="date" name="startWindowOpenParamD" value="${YYYY}-${MONTH}-${DD}"/></p>
    <p><input type="time" name="startWindowOpenParamT" value="${HH}:${MM}"/>
    </p>
    <%--${myExam.startWindowOpen.hours}--%>
    <%--${myExam.startWindowOpen.minutes}--%>

    <p>Allowed late window, minutes:</p><BR>
    <p><input type="number" name="maxLateTimeInMinutesParam" min="0" max="1440" value="${myExam.maxLateTimeInMinutes}"></p><BR>

    <p>Test time:</p><BR>
    <p><input type="number" name="testTimeInMinutesParam" min="0" max="1440" value="${myExam.testTimeInMinutes}"></p><BR>

    <p>Show results:</p><BR>
    <select size="1" name="showResultsParam">
        <option >False</option>
        <option <c:if test="${myExam.showResults}">selected</c:if>>True</option>
    </select><BR>
    <button type="submit" name="action" value="create">Create</button>
</form>

<form action="/teacher/create_exam_user" method="get">
    <button type="submit">previous step</button>
</form>

<h3 align="center">Creation time (ms): ${creationTime}</h3>
</body>
</html>
