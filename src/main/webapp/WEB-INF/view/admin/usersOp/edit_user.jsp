<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 27.04.2015
  Time: 1:50
  To change this template use File | Settings | File Templates.
--%>
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
            <div class="pic">
                <a href="#"><img
                        src="/resources/img/03_satan_clown.jpg"
                        width="200" height="300"/></a>
            </div>
            <h3>${msg}</h3>

            <h3>${exception.exceptionMsg}</h3>

            <div class="data">
                <h4><a href="mailto:${login}">${login}</a></h4>
                <h4>status: ${status}</a></h4>

                <form action="/admin/user_details" method="post">
                    <c:choose>
                        <c:when test="${status=='Blocked'}">
                            <input type="hidden" name="login" value="${login}">
                            <input type="hidden" name="block_action" value="unblock">
                            <input type="submit" value="Unblock ${login}">
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" name="login" value="${login}">
                            <input type="hidden" name="block_action" value="block">
                            <input type="submit" value="Block ${login}">
                        </c:otherwise>
                    </c:choose>
                </form>
                <h5><a href="/admin/groups_of_user?login=${login}">Member of ${groupsCounter} groups</a></h5>
            </div>
        </div>
    </section>
</div>
<section class="about">
    <h3>Creation time (ms): ${creationTime}</h3>
</section>
</body>
</html>
