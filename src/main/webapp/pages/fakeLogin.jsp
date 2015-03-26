<%@ page import="com.dart.webadmin.utils.StrUtil" %>
<%@ page import="ru.zapoebad.pwd.managers.PersonManager" %>
<%@ page import="ru.zapoebad.pwd.objects.Person" %>
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
    Person logged = null;
    if (loggedUserId != null) {
        try {
            logged = PersonManager.getInstance().getPerson(Integer.parseInt(loggedUserId));
        } catch (Exception e) {}
    }

    /*java.lang.NumberFormatException: For input string: ""
	at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
	at java.lang.Integer.parseInt(Integer.java:592)
	at java.lang.Integer.parseInt(Integer.java:615)
	at org.apache.jsp.pages.fakeLogin_jsp._jspService(fakeLogin_jsp.java:70)*/

    String userId = request.getParameter("id");
%>
workingPage = <%=workingPage%><br><br>
loggedUserId = <%=loggedUserId%><%=(logged != null?(", user " + PersonManager.getInstance().getFullName(logged)):"")%><br>
<%
    if (StrUtil.isEmpty(loggedUserId)) {
        if ("/person".equalsIgnoreCase(workingPage)) {

            // TODO нужен docContext?
%>
<br>
<form method="post" action="" id="loginForm">
    <input type="hidden" name="logIn" value="<%=userId%>">
    <input type="button" value="Log in" onClick="$('#loginForm').submit()">
</form>
<%
        } else if ("/people".equalsIgnoreCase(workingPage)) {
%>
<br>
<input type="button" name="addNewPerson" value="Добавить пользователя" onClick="document.location = '/editPerson'">
<%
        }

    } else {
%>
<br>
<form method="post" action="" id="loginForm">
    <input type="hidden" name="logIn" value="">
    <input type="button" value="Log out" onClick="$('#loginForm').submit()">
</form>
<%
        if ("/crewList".equalsIgnoreCase(workingPage)) {
%>
<br>
<input type="button" name="addNewCrew" value="Создать команду" onClick="document.location = '/editCrew'">
<%
        } else if ("/events".equalsIgnoreCase(workingPage)) {
%>
<br>
<input type="button" name="addNewEvent" value="Создать событие" onClick="document.location = '/editEvent'">
<%
        }
    }
%>