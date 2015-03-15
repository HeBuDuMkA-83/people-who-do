<%@ page import="ru.zapoebad.pwd.objects.Event" %>
<%@ page import="ru.zapoebad.pwd.managers.EventManager" %>
<%@ page import="com.dart.webadmin.utils.HttpUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: DuMkA
  Date: 19.02.2015
  Time: 0:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Integer eventId = HttpUtil.getIntValue(request, "id");
    String loggedUserId = (String)request.getSession().getAttribute("loggedUserId");
    Event event = null;
    if (eventId != null) {
        event = EventManager.getInstance().getEvent(eventId);
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache, no-store"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>Событие</title>
    <link rel="stylesheet" href="/css/style.css" type="text/css" media="screen" />
    <script type="text/javascript" src="../js/jquery-1.11.2.min.js" charset="utf-8"></script>
</head>
<body>

<style>

</style>

<table width="100%" border="1">
<tr>
    <td width="16.6%" valign="top"><!-- menu -->

        <jsp:include page="menu.jsp"></jsp:include>

    </td>
    <td valign="top"><!-- content -->
        Событие
        <br><br>

<%
    if (loggedUserId != null && event != null && loggedUserId.equalsIgnoreCase(event.getOwner()+"")) {
%>
        <input type="button" value="Редактировать" onClick="document.location='/editEvent?id=<%=eventId%>'">
<%
    }
%>

        <div>
            <table><tr>
                <td><div id="avatar-holder"></div></td>
                <td valign="top">

                    <!-- //innertable -->
                    <table>
                        <tr><td>Дата</td><td><div id="date-holder"></div></td></tr>
                        <tr><td>Название</td><td><div id="name-holder"></div></td></tr>
                        <tr><td>Организатор</td><td><div id="owner-holder"></div></td></tr>
                        <tr><td>Описание</td><td><div id="desc-holder"></div></td></tr>
                        <tr><td colspan="2"><span id="text-holder"></span></td></tr>
                    </table>

            </td></tr>
            <tr><td colspan="2">Участники<br>
                <div id="event-partic-holder"></div>
            </td></tr></table>
        </div>

    </td>
    <td width="16.6%" valign="top"><!-- right column -->

        <jsp:include page="fakeLogin.jsp"></jsp:include>

    </td>
</tr>
</table>

<script>

    $.ajax({
        url: "/ajax/getPeople",
        type: "POST",
        dataType: "json",
        async: true,
        cache: false,
        data: {
            eventId: <%=eventId%>
        },
        success: function (resp) {
            if (resp.isError != 0) {
                console.log(resp.errorMsg);
            } else {
                var html = '';
                $.each(resp.result, function(index, value) {

                    html += '<div class="preview">';
                    html += '<img src="' + value.avatarPath + '">';
                    html += '<a href="/person?id=' + value.id + '">';
                    html += value.lastName + ' ' + value.name;
                    html += '</a>';
                    html += '</div>\r\n';

                });

                $('#event-partic-holder').html(html);
            }
        },
        error: function () {
            console.log("Ошибка доступа к сервису");
        },
        complete: function () {
            //Spinner.close(spinnerId);
        }
    });

    $.ajax({
        url: "/ajax/getEvent",
        type: "POST",
        dataType: "json",
        async: true,
        cache: false,
        data: {
            eventId: <%=eventId%>
        },
        success: function (resp) {
            if (resp.isError != 0) {
                console.log(resp.errorMsg);
            } else {
                var html = '';
                var value = resp.result;

                $('#avatar-holder').html('<img src="' + value.avatarPath + '">');
                $('#date-holder').html(value.date);
                $('#name-holder').html(value.name);
                $('#owner-holder').html(value.owner);
                $('#desc-holder').html(value.desc);
                $('#text-holder').html(value.text);

            }
        },
        error: function () {
            console.log("Ошибка доступа к сервису");
        },
        complete: function () {
            //Spinner.close(spinnerId);
        }
    });

</script>

</body>
</html>
