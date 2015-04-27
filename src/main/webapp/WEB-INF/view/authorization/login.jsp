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
        <h1>Login to Web App</h1>

        <h2>${msg}</h2>

        <h2>${exception.exceptionMsg}</h2>
        <form method="post" action="/login">
            <p><input type="text" name="login" value="" placeholder="Username or Email"></p>

            <p><input type="password" name="password" value="" placeholder="Password"></p>

            <p class="remember_me">
                <label>
                    <input type="checkbox" name="remember_me" id="remember_me">
                    <strike> Remember me on this computer </strike>
                </label>
            </p>

            <p class="submit"><input type="submit" name="commit" value="Login"></p>
        </form>
    </div>

    <div class="login-help">
        <p>Forgot your password? <a href="/restore">Click here to reset it</a>.</p>
    </div>
</section>

<section class="about">
    <h3>Creation time (ms): ${creationTime}</h3>
</section>

</body>
</html>