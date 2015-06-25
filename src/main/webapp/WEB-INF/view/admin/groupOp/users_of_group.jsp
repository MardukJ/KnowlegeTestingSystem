<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 30.04.2015
  Time: 23:14
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<h1>Users of <a href="/admin/group_details?name=${name}">${name}</a></h1>
<%-- Using JSTL forEach and out to loop a list and display items in table --%>
<table align="center">
  <tbody>
  <tr>
    <th>ID</th>
    <th>Login</th>
    <th>Status</th>
    <th>Delete</th>
  </tr>
  <c:forEach items="${requestScope.userList}" var="user">
    <tr>

      <td><c:out value="${user.id}"></c:out></td>
      <td>
        <a href="/admin/user_details?login=${user.email}">
          <c:out value="${user.email}"></c:out>
        </a>
      </td>
      <td>
        <c:choose>
          <c:when test="${user.blocked}">
            Blocked
          </c:when>
          <c:otherwise>
            Active
          </c:otherwise>
        </c:choose>
      </td>
      <td>
          <form action="/admin/remove_from_group" method="post">
              <input type="hidden" name="name" value="${name}">
              <input type="hidden" name="login" value="${user.email}">
              <button type="submit">Delete</button>
          </form>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
<%--End fo table--%>

First ${firstPage}
Current ${currentPage}
Total ${totalPages}

<form action="/admin/users_of_group" method="get">
  <input type="hidden" name="name" value="${name}">
  <label>Go to<input type="number" name="page" value="${currentPage}"/></label>
  <input type="submit" value="Go"/>
</form>

<h5><a href="/admin/group_details?name=${name}">Group ${name} info</a></h5>
<h5><a href="/admin/add_to_group?name=${name}">Invite new member</a></h5>

<section class="about">
  <h3>Creation time (ms): ${creationTime}</h3>
</section>

</body>
</html>
