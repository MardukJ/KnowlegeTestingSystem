<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<body>
<h1 align="center">TEST</h1>
<a href="/home" align="center">Home</a>
<a href="/exams" align="center">Exams</a>
<a href="/logout" align="center">Logout</a><br>

<form action="/exam" method="post">
    <input type="hidden" name="inviteIdParam" value="${requestScope.invite.id}">
    <input type="hidden" name="action" value="save">
    <input type="hidden" name="questionIdParam" value="${requestScope.question.id}">
    <input type="hidden" name="index" value="${requestScope.questionIndex}">
    <%--<p><b>${requestScope.question.body}</b></p>--%>
    <p>${fn:replace(requestScope.question.body, newLineChar, "<BR>")}</p>
    <i>
    <c:set var="inputType" value='checkbox'/>
    <c:if test="${requestScope.question.correctOptionsCountAdvise}">
        <c:if test="${requestScope.question.countPositiveAnswers > 0}">
            Select ${requestScope.question.countPositiveAnswers} <BR>
        </c:if>
        <c:if test="${requestScope.question.countPositiveAnswers == 1}">
            <c:set var="inputType" value='radio'/>
        </c:if>
    </c:if>
    </i>
    <c:forEach items="${question.options}" var="option">
        <p><input name="option" type="${inputType}" value="${option.id}"
            <c:set var="idQAO" value='${option.id}'/>
            <c:if test="${requestScope.invite.getAnswerForQuestionOption(idQAO)}">checked</c:if>
        > ${option.optionText}</p>
    </c:forEach>
    <p><input type="submit" value="Save"></p>
</form>

<form action="/exam" method="post">
    <input type="hidden" name="inviteIdParam" value="${requestScope.invite.id}">
    <input type="hidden" name="action" value="do">
    <input type="hidden" name="index" value="${(requestScope.questionIndex)+1}">
    <p><input type="submit" value="Next"></p>
</form>


<form action="/exam" method="post">
    <input type="hidden" name="inviteIdParam" value="${requestScope.invite.id}">
    <input type="hidden" name="action" value="finish">
    <p><input type="submit" value="Get results"></p>
</form>

<h1>Time left: <fmt:formatNumber value="${invite.timeLeft/(60*1000)}" maxFractionDigits="0"/> minutes : <fmt:formatNumber value="${invite.timeLeft/1000%60}" maxFractionDigits="0"/> seconds </h1>

<h3 align="center">Creation time (ms): ${creationTime}</h3>
</body>
</html>