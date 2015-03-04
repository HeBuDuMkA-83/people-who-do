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
    Integer loggedUserId = (Integer)request.getSession().getAttribute("loggedUserId");

%>
workingPage = <%=workingPage%><br>
loggedUserId = <%=loggedUserId%><br>
<%
    if (loggedUserId == null) {
        if ("/person".equalsIgnoreCase(workingPage)) {
%>
<input type="button" value="Log in">
<%
        }
    }
%>