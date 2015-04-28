<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 24.04.2015
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>User list</title>
    <link rel="stylesheet" href="/resources/css/main.css">
    <!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
</head>
<body align="center">
<h1>${msg}</h1>

<%-- Using JSTL forEach and out to loop a list and display items in table --%>
<table align="center">
    <tbody>
    <tr>
        <th>ID</th>
        <th>Login</th>
        <th>Blocked?</th>
    </tr>
    <c:forEach items="${requestScope.userList}" var="user">
        <tr>

            <td><c:out value="${user.id}"></c:out></td>
            <td>
                <a href="/admin/user_details?login=${user.email}">
                    <c:out value="${user.email}"></c:out>
                </a>
            </td>
            <td><c:out value="${user.blocked}"></c:out></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

First ${firstPage}
Current ${currentPage}
Total ${totalPages}

<form action="/admin/all_users" method="get">
    <label><input type="search" name="expression" value="*">Login regexp</label>

    <label><input type="radio" name="blocked" value="active"/>active</label>
    <label><input type="radio" name="blocked" value="blocked"/>blocked</label>
    <label><input type="radio" name="blocked" value="both" checked/>both</label> <BR>

    <strike>
        <label><input type="radio" name="role" value="student"/>student</label>
        <label><input type="radio" name="role" value="teacher"/>teacher</label>
        <label><input type="radio" name="role" value="both" checked/>both</label> <BR>
    </strike>

    <strike>
        <label><input type="radio" name="sort" value="increase"/>A->Z</label>
        <label><input type="radio" name="sort" value="decrease"/>Z->A</label>
        <label><input type="radio" name="sort" value="no" checked/>no</label> <BR>
    </strike>

    <label>Name filter<input type="number" name="page" value="*"/></label>

    <label>Go to<input type="number" name="page" value="${currentPage}"/></label>
    <input type="submit" value="Go"/>
</form>

<%--pagination here--%>
<%--<section class="container">--%>
<%--<nav class="pagination">--%>
<%--<a href="/admin/all_users" class="prev">&lt;</a>--%>
<%--<a href="/admin/all_users">1</a>--%>
<%--<a href="/admin/all_users">2</a>--%>
<%--<a href="/admin/all_users">3</a>--%>
<%--<span>4</span>--%>
<%--<a href="/admin/all_users">5</a>--%>
<%--<a href="/admin/all_users" class="next">&gt;</a>--%>
<%--</nav>--%>
<%--</section>--%>

<section class="about">
    <h3>Creation time (ms): ${creationTime}</h3>
</section>

</body>
</html>
