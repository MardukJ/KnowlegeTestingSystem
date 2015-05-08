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
<h1 align="center"> CREATE EXAM: STEP 2 </h1>
<h3 align="center"> add some students </h3>

<a href="/home" align="center">Home</a>
<a href="/teacher/questions" align="center">Question menu</a>
<a href="/teacher/exams" align="center">Exam menu</a>
<a href="/logout" align="center">Logout</a><br> <BR>

<h1 align="center">${msg}</h1><BR>
<h2>${exception.exceptionMsg}</h2>

<BR>
<p>Invited students:</p><BR>
<p>Register new email:</p><BR>
<form action="/teacher/create_exam_user" method="get">
    <label> <input type="text" name="userMail" value="%username%"/></label>
    <input type="hidden" name="action" value="newU">
    <input type="submit" value="Go"/>
</form> <BR>

<%-- Using JSTL forEach and out to loop a list and display items in table --%>
<table align="center">
    <tbody>
    <tr>
        <th>Login</th>
        <th></th>
    </tr>
    <c:forEach items="${requestScope.userListA}" var="user">
        <tr>
            <td>
                <strike>
                    <a href="/teacher/user_details?login=${user.email}">
                        <c:out value="${user.email}"></c:out>
                    </a>
                </strike>
            </td>
            <td>
                <form action="/teacher/create_exam_user" method="get">
                    <input type="hidden" name="userMail" value="${user.email}">
                    <input type="hidden" name="action" value="removeU">
                    <input type="submit" value="Remove">
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%--End fo table--%><BR>
<p>Available students:</p><BR>
<%-- Using JSTL forEach and out to loop a list and display items in table --%>
<table align="center">
    <tbody>
    <tr>
        <th>Login</th>
        <th></th>
    </tr>
    <c:forEach items="${requestScope.userListE}" var="user">
        <tr>
            <td>
                <strike>
                <a href="/teacher/user_details?login=${user.email}">
                    <c:out value="${user.email}"></c:out>
                </a>
                </strike>
            </td>
            <td>
                <form action="/teacher/create_exam_user" method="get">
                    <input type="hidden" name="userMail" value="${user.email}">
                    <input type="hidden" name="action" value="addU">
                    <input type="submit" value="Add">
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%--End fo table--%>

First ${firstPageE}
Current ${currentPageE}
Total ${totalPagesE}
<form action="/teacher/create_exam_user" method="get">
    <label>Filter:<BR> <input type="search" name="expression" value="${loginRegexp}"></label> <BR>

    <label>Sort:</label>
    <label><input type="radio" name="sort" value="increase" ${sortIncrease}/>A->Z</label>
    <label><input type="radio" name="sort" value="decrease" ${sortDecrease}/>Z->A</label>
    <label><input type="radio" name="sort" value="no" ${sortNo}/>no</label> <BR>

    <label>Go to<input type="number" name="pageE" value="${currentPageE}"/></label>
    <input type="submit" value="Go"/>
</form>
<p>...</p><BR>
<p>Invite new student by email:</p><BR>
<p>...</p><BR>


<form action="/teacher/create_exam" method="get">
    <input type="hidden" name="action" value="reset">
    <button type="submit">Reset exam</button>
</form>

<form action="/teacher/create_exam" method="get">
    <button type="submit">previous step</button>
</form>


<form action="/teacher/create_exam_final" method="get">
    <button type="submit">next step</button>
</form>

<h3 align="center">Creation time (ms): ${creationTime}</h3>
</body>
</html>
