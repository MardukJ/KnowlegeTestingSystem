<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 28.04.2015
  Time: 23:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<body>
<h1>Spring MVC internationalization example</h1>

Language : <a href="?language=en">English</a>|<a href="?language=zh_CN">Chinese|<a href="?language=ru">Русский</a>

    <h2>
        welcome.springmvc : <spring:message code="msg.test" text="default text"/>
    </h2>

    Current Locale : ${pageContext.response.locale}

</body>
</html>