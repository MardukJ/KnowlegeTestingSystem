<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 30.04.2015
  Time: 0:57
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User details</title>
    <link rel="stylesheet" href="/resources/css/main.css">
</head>
<body>

<nav>
    <ul id="n" class="clearfix">
        <li><a href="/admin/home">Back to admin homepage</a></li>
    </ul>
</nav>

<div id="content" class="clearfix">
    <section id="left">
        <div id="userStats" class="clearfix">
            <h3>${msg}</h3>

            <h3>${exception.exceptionMsg}</h3>

            <div class="data">
                <h1>Name: ${name}</h1>
                <h4>status: ${status}</a></h4>

                <form action="/admin/group_details" method="post">
                    <c:choose>
                        <c:when test="${status=='Blocked'}">
                            <input type="hidden" name="name" value="${name}">
                            <input type="hidden" name="block_action" value="unblock">
                            <input type="submit" value="Unblock ${name}">
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" name="name" value="${name}">
                            <input type="hidden" name="block_action" value="block">
                            <input type="submit" value="Block ${name}">
                        </c:otherwise>
                    </c:choose>
                </form>
                <h5><a href="/admin/users_of_group?name=${name}">Members: ${membersCount}</a></h5>
            </div>
        </div>

        <h1>Members:</h1>
        <h5><a href="/admin/add_to_group?name=${name}">Invite new member</a></h5>
</div>
<section class="about">
    <h3>Creation time (ms): ${creationTime}</h3>
</section>
</body>
</html>
