<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 03.05.2015
  Time: 2:50
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
<h1 align="center"> QUESTIONS OF GROUP </h1>
<a href="/home" align="center">Home</a>
<a href="/teacher/questions" align="center">Question menu</a>
<a href="/teacher/exams" align="center">Exam menu</a>
<a href="/logout" align="center">Logout</a><br>

<h1 align="center">${msg}</h1>

<h3><a href="/teacher/new_question?name=${groupName}">New question for group ${groupName}</a></h3>

<%-- Using JSTL forEach and out to loop a list and display items in table --%>
<table align="center">
  <tbody>
  <c:forEach items="${requestScope.validQuestions}" var="question">
      <tr>
          <th>
              Question ID = ${question.id}
              <form action="/teacher/delete_question" method="post">
                  <input type="hidden" name = "idQuestion" value="${question.id}">
                  <button type="submit">delete</button>
              </form>
              <form action="/teacher/edit_question" method="get">
                  <input type="hidden" name = "idQuestion" value="${question.id}">
                  <button type="submit">edit</button>
              </form>
          </th>
      </tr>
      <tr>
          <th>Body:</th>
      </tr>
      <tr>
          <th>${fn:replace(question.body, newLineChar, "<BR>")}</th>
      </tr>
      <tr>
          <th>Teacher comment:</th>
      </tr>
      <tr>
          <th>${fn:replace(question.teacherComment, newLineChar, "<BR>")}</th>
      </tr>
      <tr>
          <th>Review comment:</th>
      </tr>
      <tr>
          <th>${fn:replace(question.reviewComment, newLineChar, "<BR>")}</th>
      </tr>
      <tr>
          <th>Current version:</th>
      </tr>
      <tr>
          <th>${question.version}</th>
      </tr>

                <c:if test="${question.correctOptionsCountAdvise}">
                    <tr>
                        <th>
                            Correct answers number advise
                            <input type="checkbox" checked></input>
                        </th>
                    </tr>
                </c:if>
          <c:forEach items="${question.options}" var="option">
              <tr>
                  <td>
                      ${option.optionText}
                  </td>
                  <td>
                      ${option.correctAnswer}
                  </td>
              </tr>
          </c:forEach>
  </c:forEach>
  </tbody>
</table>

<h3 align="center">Creation time (ms): ${creationTime}</h3>
</body>
</html>