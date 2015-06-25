<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<% pageContext.setAttribute("newLineChar", "\n"); %>

<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 03.05.2015
  Time: 2:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="/resources/css/main.css">
</head>
<body>
<h1 align="center">Test result</h1>
<a href="/home" align="center">Home</a>
<a href="/exams" align="center">Exams</a>
<a href="/logout" align="center">Logout</a><br>

<BR>
<c:choose>
    <c:when test="${invite.inviteExam.showResults && !invite.noShow}">
        <p>Test result: ${invite.result}/${invite.maxResult}</p>
        <c:forEach items="${requestScope.invite.inviteExam.questions}" var="question">
            <%--<tr>--%>
                <%--<th>Body:</th>--%>
            <%--</tr>--%>
            <BR>
            <tr>
                <th>${fn:replace(question.body, newLineChar, "<BR>")}</th>
            </tr>
            <BR>
            <tr>
                <th><br>Comment:</th>
            </tr>
            <BR>
            <tr>
                <th><b><i>${fn:replace(question.reviewComment, newLineChar, "<BR>")}</i></b><br>
                </th>
            </tr>
            <BR>
            <c:forEach items="${question.options}" var="option">
                <c:set var="idQAO" value='${option.id}'/>
                <c:set var="crrAnsw" value='${option.correctAnswer}'/>
                <c:set var="realAnsw" value='${requestScope.invite.getAnswerForQuestionOption(idQAO)}'/>

                <c:choose >
                    <c:when test="${crrAnsw eq realAnsw}">
                        <tr>
                            <th>Correct answer:
                            <c:choose>
                                <c:when test="${option.correctAnswer}">
                                    <input type="checkbox" checked>
                                </c:when>
                                <c:otherwise>
                                    <input type="checkbox">
                                </c:otherwise>
                            </c:choose>
                            </th>
                            <th>Student answer:
                                <c:choose>
                                    <c:when test="${requestScope.invite.getAnswerForQuestionOption(idQAO)}">
                                        <input type="checkbox" checked>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="checkbox">
                                    </c:otherwise>
                                </c:choose>
                            </th>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <b><i>
                        <tr>
                            <th>Correct answer:
                                <c:choose>
                                    <c:when test="${option.correctAnswer}">
                                        <input type="checkbox" checked>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="checkbox">
                                    </c:otherwise>
                                </c:choose>
                            </th>
                            <th>Student answer:
                                <c:choose>
                                    <c:when test="${requestScope.invite.getAnswerForQuestionOption(idQAO)}">
                                        <input type="checkbox" checked>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="checkbox">
                                    </c:otherwise>
                                </c:choose>
                            </th>
                        </tr>
                        </i></b>
                    </c:otherwise>
                </c:choose>
                <br>
            </c:forEach>
            <br>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <p>Thank you for participating in our online test</p>
    </c:otherwise>
</c:choose>
<BR>
<h3 align="center">Creation time (ms): ${creationTime}</h3>
</body>
</html>