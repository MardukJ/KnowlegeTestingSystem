<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en"> <!--<![endif]-->
<head>
    <title>Login Form</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="/resources/css/style_login.css">
    <!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
</head>
<body>
<section class="container">
    <div class="login">
        <h1><spring:message code="login_page.header" text="?!?!?"/></h1>

        <h2>${msg}</h2>

        <h2>${exception.exceptionMsg}</h2>
        <form method="post" action="/login">
            <p><input type="text" name="login" value="" placeholder="Username or Email"></p>

            <p><input type="password" name="password" value="" placeholder="Password"></p>

            <%--<p class="remember_me">--%>
                <%--<label>--%>
                    <%--<input type="checkbox" name="remember_me" id="remember_me">--%>
                    <%--<strike><spring:message code="login_page.remember_me" text="?!?!?"/></strike>--%>
                <%--</label>--%>
            <%--</p>--%>

            <p class="submit"><input type="submit" name="commit"
                                     value="<spring:message code="login_page.login_button" text="?!?!?"/>"></p>
        </form>
    </div>
    <div class="login-help">
        <p><a href="/restore"><spring:message code="login_page.forgot_link" text="?!?!?"/></a></p>
    </div>

    <div class="login-help">
        Language : <a href="?language=en">English</a>|<a href="?language=zh_CN">Chinese|<a href="?language=ru">Русский</a>
    </div>
</section>


<section class="about">
    <h3>Creation time (ms): ${creationTime}</h3>
</section>

</body>
</html>