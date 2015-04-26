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
    <title>Login Form</title>
    <link rel="stylesheet" href="/resources/css/style_login.css">
    <!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
</head>
<body>
<section class="container">
    <div class="login">
        <h1>Password restore</h1>

        <form method="post" action="/restore">
            <p><input type="text" name="login" value="" placeholder="Username or Email"></p>

            <p class="submit"><input type="submit" name="commit" value="Restore"></p>
        </form>
    </div>

    <div class="login-help">
        <p><a href="/login">Back to login page</a>.</p>
    </div>
</section>

<section class="about">
    <h1>${msg}</h1>

    <h1>${exception.exceptionMsg}</h1>

    <h3>Creation time (ms): ${creationTime}</h3>
</section>

</body>
</html>