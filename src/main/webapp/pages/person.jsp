<%--
  Created by IntelliJ IDEA.
  User: DuMkA
  Date: 19.02.2015
  Time: 0:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String userId = request.getParameter("id");
    String loggedUserId = (String)request.getSession().getAttribute("loggedUserId");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache, no-store"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>Личность</title>
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
        Личность
        <br><br>
<%
    if (loggedUserId != null && loggedUserId.equalsIgnoreCase(userId)) {
%>
        <input type="button" value="Редактировать" onClick="document.location='/editPerson?id=<%=userId%>'">
<%
    }
%>
        <table>
        <tr>
            <td><div id="avatar-holder"></div></td>
            <td valign="top">
                <table>
                <tr><td>Имя</td><td><span id="full-name-holder"></span></td></tr>
                <tr><td>Описание</td><td><span id="desc-holder"></span></td></tr>
                <tr><td>Вконтакте</td><td><span id="vk-holder"></span></td></tr>
                <tr><td colspan="2"><span id="text-holder"></span></td></tr>
                </table>
            </td>
        </tr>
        </table>

        Команды
        <div id="crew-list-holder"></div>

    </td>
    <td width="16.6%" valign="top"><!-- right column -->

        <jsp:include page="fakeLogin.jsp"></jsp:include>

    </td>
</tr>
</table>

<script>
    $.ajax({
        url: "/ajax/getPerson",
        type: "POST",
        dataType: "json",
        async: true,
        cache: false,
        data: {
          id: <%=userId%>
        },
        success: function (resp) {
            if (resp.isError != 0) {
                console.log(resp.errorMsg);
            } else {
                var value = resp.result;
                // HTML
                $('#avatar-holder').html('<img src="' + value.avatarPath + '">');
                $('#full-name-holder').html(value.name + ' ' + value.lastName);
                $('#desc-holder').html(value.desc);
                $('#vk-holder').html(value.vkAccount);
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
        url: "/ajax/getCrewList",
        type: "POST",
        dataType: "json",
        async: true,
        cache: false,
        data: {
            userId: <%=userId%>
        },
        success: function (resp) {
            if (resp.isError != 0) {
                console.log(resp.errorMsg);
            } else {
                var html = '';
                $.each(resp.result, function(index, value) {

                    html += '<div class="preview">';
                    html += '<img src="' + value.avatarPath + '">';
                    html += '<a href="/crew?id=' + value.id + '">';
                    html += value.name;
                    html += '</a>';
                    html += '</div>\r\n';

                });

                $('#crew-list-holder').html(html);
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
