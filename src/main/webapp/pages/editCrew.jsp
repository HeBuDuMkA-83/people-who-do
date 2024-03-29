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

<table width="100%" height="2000px">
<tr>
    <td width="16.6%" valign="top" class="left-side"><!-- menu -->

        <jsp:include page="menu.jsp"></jsp:include>

    </td>
    <td valign="top" class="center-side"><!-- content -->
<%
    out.write((crewId != null ? "Редактирование команды" : "Создание новой команды"));
%>
        <br><br>

        <form action="" method="post" accept-charset="UTF-8" >
            <input type="hidden" name="crewId" value="<%=crewId%>">
            <input type="hidden" name="act" value="editCrew">

            <table>
                <tr>
                    <td><div id="avatar-holder"></div></td>
                    <td valign="top">
                        <table>
                            <tr><td>Название</td><td><input type="text" id="name-holder" name="name"></td></tr>
                            <tr><td>Описание</td><td><input type="text" id="desc-holder" name="desc"></td></tr>
                            <tr><td colspan="2"><input type="text" id="text-holder" name="text"></td></tr>
                        </table>
                    </td>
                </tr>
            </table>

            <input type="submit" value="Сохранить">

        </form>

        Участники
        <div id="participants-holder"></div>

    </td>
    <td width="16.6%" valign="top" class="right-side"><!-- right column -->

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
                $('#name-holder').val(value.name);
                $('#desc-holder').val(value.desc);
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
