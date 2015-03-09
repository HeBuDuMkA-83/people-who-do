<%@ page import="com.dart.webadmin.utils.StrUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: DuMkA
  Date: 05.03.2015
  Time: 2:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String workingPage = (String)request.getSession().getAttribute("workingPage");
    String loggedUserId = (String)request.getSession().getAttribute("loggedUserId");
    String userId = request.getParameter("id");
%>
workingPage = <%=workingPage%><br>
loggedUserId = <%=loggedUserId%><br>
<%
    if (StrUtil.isEmpty(loggedUserId)) {
        if ("/person".equalsIgnoreCase(workingPage)) {

            // TODO нужен docContext?
%>
<form method="post" action="" id="loginForm">
<input type="hidden" name="logIn" value="<%=userId%>">
<input type="button" value="Log in" onClick="$('#loginForm').submit()">
</form>
<%
        }
    } else {
%>
<form method="post" action="" id="loginForm">
    <input type="hidden" name="logIn" value="">
    <input type="button" value="Log out" onClick="$('#loginForm').submit()">
</form>
<%
    }
%>