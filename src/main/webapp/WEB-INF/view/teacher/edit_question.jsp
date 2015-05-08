<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 04.05.2015
  Time: 1:24
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
<h1 align="center"> QUESTIONS EDITOR </h1>
<a href="/home" align="center">Home</a>
<a href="/teacher/questions" align="center">Question menu</a>
<a href="/teacher/exams" align="center">Exam menu</a>
<a href="/logout" align="center">Logout</a><br>

<h1 align="center">${msg}</h1>

<%-- Using JSTL forEach and out to loop a list and display items in table --%>
<table align="center">
  <form action="/teacher/edit_question" method="get">
      Question ID = ${question.id}<BR>
      <input type="hidden" name = "id" value="${question.id}">

      Body:<BR>
      <textarea name="body">${question.body}</textarea><BR>

      Teacher comment:<BR>
      <textarea name="teacherComment">${question.teacherComment}</textarea><BR>

      Review comment:<BR>
      <textarea name="reviewComment">${question.reviewComment}</textarea><BR>

      Current version:<BR>
      ${question.version}<BR>
      <input type="hidden" name="version" value="${question.version}">
    <tbody>
    <c:forEach items="${question.options}" var="option" varStatus="oIndex">
        <tr>
            <td>
                <input type="text" name="optionText${oIndex.index}" value="${option.optionText}"/>
            </td>
            <td>
                <c:choose>
                    <c:when test="${option.correctAnswer}">
                        True
                        <label><input type="radio" name="correctAnswer${oIndex.index}" value="true" checked/>True</label>
                        <label><input type="radio" name="correctAnswer${oIndex.index}" value="false"/>False</label>
                    </c:when>
                    <c:otherwise>
                        False
                        <label><input type="radio" name="correctAnswer${oIndex.index}" value="true"/>True</label>
                        <label><input type="radio" name="correctAnswer${oIndex.index}" value="false" checked/>False</label>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
  </tbody>
      <c:if test="${not empty groupName}">
          <input type="hidden" name="groupName" value="${groupName}">
      </c:if>
      <label><input type="checkbox" name="correctOptionsCountAdvise"
          <c:if test="${question.correctOptionsCountAdvise}">
              checked
          </c:if>
          >Correct answers number advise</label>
      <button type="submit" name="action" value="preview">Preview</button>
      <button type="submit" name="action" value="submit">Submit</button>
  </form>
</table>

<h3 align="center">Creation time (ms): ${creationTime}</h3>
</body>
</html>