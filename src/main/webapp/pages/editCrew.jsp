<%--
  Created by IntelliJ IDEA.
  User: DuMkA
  Date: 19.02.2015
  Time: 0:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String crewId = request.getParameter("id");
    String loggedUserId = (String)request.getSession().getAttribute("loggedUserId");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache, no-store"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>Редактирование команды</title>
    <link rel="stylesheet" href="/css/style.css" type="text/css" media="screen" />
    <script language="JavaScript" type="text/javascript" src="../js/jquery-1.11.2.min.js" charset="utf-8"></script>
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
        Редактирование команды
        <br><br>

        <form action="" method="post" accept-charset="UTF-8" >

            <table>
                <tr>
                    <td><div id="avatar-holder"></div></td>
                    <td valign="top">
                        <table>
                            <tr><td>Название</td><td><input type="text" id="full-name-holder"></td></tr>
                            <tr><td>Описание</td><td><input type="text" id="desc-holder"></td></tr>
                            <tr><td>Основатель</td><td><input type="text" id="owner-holder"></td></tr>
                            <tr><td colspan="2"><input type="text" id="text-holder"></td></tr>
                        </table>
                    </td>
                </tr>
            </table>

            <input type="submit" value="Сохранить">

        </form>

        Участники
        <div id="participants-holder"></div>

    </td>
    <td width="16.6%" valign="top"><!-- right column -->

        <jsp:include page="fakeLogin.jsp"></jsp:include>

    </td>
</tr>
</table>

<script>
    $.ajax({
        url: "/ajax/getCrew",
        type: "POST",
        dataType: "json",
        async: true,
        cache: false,
        data: {
            id: <%=crewId%>
        },
        success: function (resp) {
            if (resp.isError != 0) {
                console.log(resp.errorMsg);
            } else {
                var value = resp.result;
                // VAL
                $('#avatar-holder').html('<img src="' + value.avatarPath + '">');
                $('#full-name-holder').val(value.name);
                $('#desc-holder').val(value.desc);
                $('#owner-holder').val(value.owner);
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

    $.ajax({
        url: "/ajax/getPeople",
        type: "POST",
        dataType: "json",
        async: true,
        cache: false,
        data: {
            crewId: <%=crewId%>
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

                $('#participants-holder').html(html);
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
