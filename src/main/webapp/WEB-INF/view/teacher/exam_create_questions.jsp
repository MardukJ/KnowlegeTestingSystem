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
<h1 align="center"> CREATE EXAM: STEP 1 </h1>
<h3 align="center"> add some questions </h3>

<a href="/home" align="center">Home</a>
<a href="/teacher/questions" align="center">Question menu</a>
<a href="/teacher/exams" align="center">Exam menu</a>
<a href="/logout" align="center">Logout</a><br> <BR>

<h1 align="center">${msg}</h1><BR>
<h2>${exception.exceptionMsg}</h2>

<form action="/teacher/create_exam" method="get">
    <input type="hidden" name="action" value="reset">
    <button type="submit">Reset exam</button>
</form>

<form action="/teacher/create_exam_user" method="get">
    <button type="submit">next step</button>
</form>
<BR>

<p>Available questions:</p><BR>
First ${firstPageA}
Current ${currentPageA}
Total ${totalPagesA}
<form action="/teacher/create_exam" method="get">
    <label>Go to<input type="number" name="pageA" value="${currentPageA}"/></label>
    <input type="hidden" name="pageE" value="${currentPageE}">
    <input type="submit" value="Go"/>
</form>
<BR>
<%-- Using JSTL forEach and out to loop a list and display items in table --%>
<p>
<table align="center" style="border:1px solid black;"  width="90%">
    <tbody>
    <c:forEach items="${requestScope.availableQuestions}" var="questionA">
        <tr>
            <th>Body:</th>
        </tr>
        <tr>
            <th>${fn:replace(questionA.body, newLineChar, "<BR>")}</th>
        </tr>
        <tr>
            <th>Teacher comment:</th>
        </tr>
        <tr>
            <th>${fn:replace(questionA.teacherComment, newLineChar, "<BR>")}</th>
        </tr>
        <%--<tr>--%>
        <%--<th>Review comment:</th>--%>
        <%--</tr>--%>
        <%--<tr>--%>
        <%--<th>${fn:replace(questionA.reviewComment, newLineChar, "<BR>")}</th>--%>
        <%--</tr>--%>
        <%--<tr>--%>
        <%--<th>Current version:</th>--%>
        <%--</tr>--%>
        <%--<tr>--%>
        <%--<th>${questionA.version}</th>--%>
        <%--</tr>--%>

        <%--<c:if test="${questionA.correctOptionsCountAdvise}">--%>
        <%--<tr>--%>
        <%--<th>--%>
        <%--Correct answers number advise--%>
        <%--<input type="checkbox" checked></input>--%>
        <%--</th>--%>
        <%--</tr>--%>
        <%--</c:if>--%>
        <%--<c:forEach items="${questionA.options}" var="option">--%>
        <%--<tr>--%>
        <%--<td>--%>
        <%--${option.optionText}--%>
        <%--</td>--%>
        <%--<td>--%>
        <%--${option.correctAnswer}--%>
        <%--</td>--%>
        <%--</tr>--%>
        <%--</c:forEach>--%>
        <tr>
            <th>
                <form action="/teacher/create_exam" method="get">
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
</p>

<BR>
<p>Exam questions (total ${requestScope.examQuestionsSize}):</p><BR>
First ${firstPageE}
Current ${currentPageE}
Total ${totalPagesE}
<form action="/teacher/create_exam" method="get">
    <label>Go to<input type="number" name="pageE" value="${currentPageE}"/></label>
    <input type="hidden" name="pageA" value="${currentPageA}">
    <input type="submit" value="Go"/>
</form>
<%-- Using JSTL forEach and out to loop a list and display items in table --%>
<p>
<table align="center" style="border:1px solid black;" width="90%">
    <tbody>
    <c:forEach items="${requestScope.examQuestions}" var="question">
        <%--<tr>--%>
            <%--<th>--%>
                <%--Question ID = ${questionE.id}--%>
                <%--<form action="/teacher/delete_question" method="post">--%>
                    <%--<input type="hidden" name = "idQuestion" value="${questionE.id}">--%>
                    <%--<button type="submit">delete</button>--%>
                <%--</form>--%>
                <%--<form action="/teacher/edit_question" method="get">--%>
                    <%--<input type="hidden" name = "idQuestion" value="${questionE.id}">--%>
                    <%--<button type="submit">edit</button>--%>
                <%--</form>--%>
            <%--</th>--%>
        <%--</tr>--%>
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
        <%--<tr>--%>
            <%--<th>Review comment:</th>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<th>${fn:replace(questionE.reviewComment, newLineChar, "<BR>")}</th>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<th>Current version:</th>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<th>${questionE.version}</th>--%>
        <%--</tr>--%>

        <%--<c:if test="${questionE.correctOptionsCountAdvise}">--%>
            <%--<tr>--%>
                <%--<th>--%>
                    <%--Correct answers number advise--%>
                    <%--<input type="checkbox" checked></input>--%>
                <%--</th>--%>
            <%--</tr>--%>
        <%--</c:if>--%>
        <%--<c:forEach items="${questionE.options}" var="option">--%>
            <%--<tr>--%>
                <%--<td>--%>
                        <%--${option.optionText}--%>
                <%--</td>--%>
                <%--<td>--%>
                        <%--${option.correctAnswer}--%>
                <%--</td>--%>
            <%--</tr>--%>
        <%--</c:forEach>--%>

        <tr>
            <th>
                <form action="/teacher/create_exam" method="get">
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
</p>
<BR>

<h3 align="center">Creation time (ms): ${creationTime}</h3>
</body>
</html>
