<%--
  Created by IntelliJ IDEA.
  User: DuMkA
  Date: 19.02.2015
  Time: 0:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache, no-store"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>Список событий</title>
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
        Список событий
        <br><br>

        <div id="event-list-holder"></div>

    </td>
    <td width="16.6%" valign="top"><!-- right column -->

        <jsp:include page="fakeLogin.jsp"></jsp:include>

    </td>
</tr>
</table>

<script>
    function getParticipants(id) {
        $.ajax({
            url: "/ajax/getPeople",
            type: "POST",
            dataType: "json",
            async: true,
            cache: false,
            data: {
                eventId: id
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

                    $('#event' + id + '-partic-holder').html(html);
                }
            },
            error: function () {
                console.log("Ошибка доступа к сервису");
            },
            complete: function () {
                //Spinner.close(spinnerId);
            }
        });
    }

    $.ajax({
        url: "/ajax/getEventList",
        type: "POST",
        dataType: "json",
        async: true,
        cache: false,
        /*data: {
         mrf: mrf,
         region: region,
         city: city,
         ctype: ctype,
         city_lvl: city_lvl,
         from: from,
         to: to
         },*/
        success: function (resp) {
            if (resp.isError != 0) {
                console.log(resp.errorMsg);
            } else {
                var html = '';
                $.each(resp.result, function(index, value) {

                    html += '<div>';
                    html += '<table><tr><td>';
                    html += '<img src="' + value.avatarPath + '">';
                    html += '</td><td valign="top">';

                    /*html += '<a href="/crew?id=' + value.id + '">';
                     html += value.name;
                     html += '</a>';*/

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
                    html += '</div><hr>\r\n';

                });

                $('#event-list-holder').html(html);
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
