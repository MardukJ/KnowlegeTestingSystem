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
<body>
<h1 align="center"> CREATE EXAM: STEP 1 </h1>
<h3 align="center"> add some questions </h3>
<a href="/home" align="center">Home</a>
<a href="/teacher/questions" align="center">Question menu</a>
<a href="/teacher/exams" align="center">Exam management</a>
<a href="/exams" align="center">My exams</a>
<a href="/logout" align="center">Logout</a><br>
<h2>${exception.exceptionMsg}</h2>
<h1 align="center">${msg}</h1><BR>

<p>Exam questions (total ${requestScope.examQuestionsSize}):</p>
<%-- Using JSTL forEach and out to loop a list and display items in table --%>
<table align="center" style="border:1px solid black;" width="90%">
    <tbody>
    <c:forEach items="${requestScope.examQuestions}" var="question">
        <tr>
            <th align="left">${fn:replace(question.body, newLineChar, "<BR>")}</th>
        </tr>
        <tr>
            <th>Teacher comment:</th>
        </tr>
        <tr>
            <th align="left">${fn:replace(question.teacherComment, newLineChar, "<BR>")}</th>
        </tr>
        <tr>
            <th>
                <form action="/teacher/create_exam" method="post">
                    <input type="hidden" name = "idQuestion" value="${question.id}">
                    <input type="hidden" name="action" value="removeQ">

                    <input type="hidden" name="pageE" value="${currentPageE}">
                    <input type="hidden" name="pageA" value="${currentPageA}">
                    <button type="submit">remove</button>
                </form>
            </th>
        </tr>

        <tr>
            <th>
                <BR>
            </th>
        </tr>

    </c:forEach>
    </tbody>
</table><BR>
First ${firstPageE}
Current ${currentPageE}
Total ${totalPagesE}
<form action="/teacher/create_exam" method="post">
    <label>Go to<input type="number" name="pageE" value="${currentPageE}"/></label>
    <input type="hidden" name="pageA" value="${currentPageA}">
    <input type="submit" value="Go"/>
</form>

<p>Available questions:</p><BR>
<%-- Using JSTL forEach and out to loop a list and display items in table --%>
<table align="center" style="border:1px solid black;"  width="90%">
    <tbody>
    <c:forEach items="${requestScope.availableQuestions}" var="questionA">
        <tr>
            <th align="left">${fn:replace(questionA.body, newLineChar, "<BR>")}</th>
        </tr>
        <tr>
            <th>Teacher comment:</th>
        </tr>
        <tr>
            <th align="left">${fn:replace(questionA.teacherComment, newLineChar, "<BR>")}</th>
        </tr>
        <tr>
            <th>
                <form action="/teacher/create_exam" method="post">
                    <input type="hidden" name = "idQuestion" value="${questionA.id}">
                    <input type="hidden" name="action" value="addQ">

                    <input type="hidden" name="pageA" value="${currentPageA}">
                    <input type="hidden" name="pageE" value="${currentPageE}">

                    <button type="submit">Add</button>
                </form>
            </th>
        </tr>
    </c:forEach>
    </tbody>
</table><BR>
First ${firstPageA}
Current ${currentPageA}
Total ${totalPagesA}
<form action="/teacher/create_exam" method="post">
    <label>Go to<input type="number" name="pageA" value="${currentPageA}"/></label>
    <input type="hidden" name="pageE" value="${currentPageE}">
    <input type="submit" value="Go"/>
</form>

<form action="/teacher/create_exam" method="post">
    <input type="hidden" name="action" value="reset">
    <button type="submit">Reset exam</button>
</form>
<form action="/teacher/create_exam_user" method="post">
    <button type="submit">next step</button>
</form>

<h3 align="center">Creation time (ms): ${creationTime}</h3>
</body>
</html>
