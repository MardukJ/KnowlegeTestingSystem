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
<h1 align="center"> GROUP LIST </h1>

<h1>${msg}</h1>

<%-- Using JSTL forEach and out to loop a list and display items in table --%>
<table align="center">
    <tbody>
    <tr>
        <th>ID</th>
        <th>Login</th>
        <th>Status</th>
    </tr>
    <c:forEach items="${requestScope.groupList}" var="group">
        <tr>
            <td><c:out value="${group.id}"></c:out></td>
            <td>
                <a href="/admin/group_details?name=${group.groupName}">
                    <c:out value="${group.groupName}"></c:out>
                </a>
            </td>
            <td>
                <c:choose>
                    <c:when test="${group.blocked}">
                        Blocked
                    </c:when>
                    <c:otherwise>
                        Active
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<form action="/admin/group_list" method="get">
    <label>Login regexp <BR> <input type="text" name="expression" value="${groupRegexp}"></label> <BR>

    <label><input type="radio" name="blocked" value="active" ${blockedActive}/>active</label>
    <label><input type="radio" name="blocked" value="blocked" ${blockedBlocked}/>blocked</label>
    <label><input type="radio" name="blocked" value="both" ${blockedBoth}/>both</label> <BR>

    <label><input type="radio" name="sort" value="increase" ${sortIncrease}/>A->Z</label>
    <label><input type="radio" name="sort" value="decrease" ${sortDecrease}/>Z->A</label>
    <label><input type="radio" name="sort" value="no" ${sortNo}/>no</label> <BR>

    First ${firstPage}
    Current ${currentPage}
    Total ${totalPages}<BR>

    <label>Go to<input type="number" name="page" value="${currentPage}"/></label>
    <input type="submit" value="Go"/>
</form>

<section class="about">
    <h3>Creation time (ms): ${creationTime}</h3>
</section>

</body>
</html>
