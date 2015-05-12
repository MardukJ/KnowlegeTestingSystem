<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% pageContext.setAttribute("newLineChar", "\n"); %>

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
<h1 align="center">Test result</h1>
<a href="/home" align="center">Home</a>
<a href="/teacher/questions" align="center">Question menu</a>
<a href="/teacher/exams" align="center">Exam menu</a>
<a href="/logout" align="center">Logout</a><br>

<BR>
<c:choose>
    <c:when test="${invite.inviteExam.showResults}">
        <p>Test result: ${invite.result}/${invite.maxResult}</p>

        <c:forEach items="${requestScope.invite.inviteExam.questions}" var="question">
            <tr>
                <th>Body:</th>
            </tr>
            <BR>
            <tr>
                <th>${fn:replace(question.body, newLineChar, "<BR>")}</th>
            </tr>
            <BR>
            <tr>
                <th>Comment:</th>
            </tr>
            <BR>
            <tr>
                <th>${fn:replace(question.reviewComment, newLineChar, "<BR>")}</th>
            </tr>
            <BR>
            <c:forEach items="${question.options}" var="option">
                <tr>
                    <th>Correct answer: ${option.correctAnswer}</th>
                    <BR>
                    <c:set var="idQAO" value='${option.id}'/>
                    <th>Student answer: ${requestScope.invite.getAnswerForQuestionOption(idQAO)} </th>
                </tr>
            </c:forEach>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <p>Thank you for participating in our online test</p>
    </c:otherwise>
</c:choose>
<BR>

<h3 align="center">Creation time (ms): ${creationTime}</h3>
</body>
</html>