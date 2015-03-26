<%--
  Created by IntelliJ IDEA.
  User: DuMkA
  Date: 19.02.2015
  Time: 0:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String eventId = request.getParameter("id");
    String loggedUserId = (String)request.getSession().getAttribute("loggedUserId");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache, no-store"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>Редактирование события</title>
    <link rel="stylesheet" href="/css/style.css" type="text/css" media="screen" />
    <script type="text/javascript" src="../js/jquery-1.11.2.min.js" charset="utf-8"></script>
</head>
<body>

<style>

</style>

<table width="100%" height="2000px">
<tr>
    <td width="16.6%" valign="top" class="left-side"><!-- menu -->

        <jsp:include page="menu.jsp"></jsp:include>

    </td>
    <td valign="top" class="center-side"><!-- content -->
<%
    out.write((eventId != null ? "Редактирование события" : "Создание нового события"));
%>
        <br><br>

        <form action="" method="post" accept-charset="UTF-8" >
            <input type="hidden" name="eventId" value="<%=eventId%>">
            <input type="hidden" name="act" value="editEvent">

            <table><tr>
                <td><div id="avatar-holder"></div></td>
                <td valign="top">

                    <!-- //innertable -->
                    <table>
                        <tr><td>Дата</td><td><input type="text" id="date-holder" name="date"></td></tr>
                        <tr><td>Название</td><td><input type="text" id="name-holder" name="name"></td></tr>
                        <tr><td>Описание</td><td><input type="text" id="desc-holder" name="desc"></td></tr>
                        <tr><td colspan="2"><textarea id="text-holder" name="text"></textarea></td></tr>
                    </table>

                </td></tr>
            </table>

            <input type="submit" value="Сохранить">

        </form>

        Участники<br>
        <div id="event-partic-holder"></div>

    </td>
    <td width="16.6%" valign="top" class="right-side"><!-- right column -->

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
                $('#date-holder').val(value.date);
                $('#name-holder').val(value.name);
                $('#desc-holder').val(value.desc);
                $('#text-holder').html(value.text);

                html += '<div>';
                html += '<table><tr><td>';
                html += '<img src="' + value.avatarPath + '">';
                html += '</td><td valign="top">';

                //innertable
                html += '<table>';
                html += '<tr><td>Дата</td><td>' + value.date + '</td></tr>';
                html += '<tr><td>Название</td><td>' + value.name + '</td></tr>';
                html += '<tr><td>Описание</td><td>' + value.desc + '</td></tr>';
                html += '<tr><td colspan="2">' + value.text + '</td></tr>';
                html += '</table>';

                html += '</td></tr>';
                html += '<tr><td colspan="2">Участники<br>';
                html += '<div id="event' + value.id + '-partic-holder"></div>';
                html += '<scri' + 'pt>getParticipants(' + value.id + ');</scri' + 'pt>';
                html += '</td></tr></table>';
                html += '</div>\r\n';


                $('#event-holder').html(html);
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
