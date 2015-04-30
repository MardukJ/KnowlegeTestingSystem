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
                <strike><h1>Johnny Appleseed</h1></strike>
                <strike><h3>San Francisco, CA</h3></strike>
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
                <strike><h4><a href="http://spyrestudios.com/">http://example.com/</a></h4></strike>
                <strike><h5><a href="tel:+38-050-111-22-33">+38-050-111-22-33</a></h5></strike>

                <h5>Member of ${groupsCounter} groups</h5>


            </div>
        </div>

        <h1>About:</h1>
        <strike><p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore
            et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip
            ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu
            fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
            mollit anim id est laborum.</p></strike>
    </section>
</div>
</body>
</html>
