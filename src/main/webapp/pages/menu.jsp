<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: dumka
  Date: 20.02.15
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<String> links = new ArrayList<String>();
    links.add("События,/events");
    links.add("Люди,/people");
    links.add("Команды,/crewList");
    links.add("Места,/places");
    links.add("Проекты,/projectList");
    links.add("Публикации,/postList");
%>
<table align="center">
<tr>
    <td>
        <span style="font-size: 20px; padding-top: 50px">People Who Do</span>
    </td>
</tr>
<%
    for (String link : links) {
%>
<tr>
    <td>
        <span style="padding-left: 20px"><a href="<%=link.split(",")[1]%>"><%=link.split(",")[0]%></a></span>
    </td>
</tr>
<%
    }
%>
</table>