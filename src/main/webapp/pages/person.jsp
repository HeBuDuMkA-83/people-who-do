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
    <td width="16.6%"><!-- menu -->

        <jsp:include page="menu.jsp"></jsp:include>

    </td>
    <td valign="top"><!-- content -->
        Личность
        <br><br>

        <input type="button" value="Редактировать" onClick="document.location='/editPerson?id=<%=userId%>'">

        <table>
        <tr>
            <td><div id="avatar-holder"></div></td>
            <td valign="top">
                <table>
                <tr><td>Имя</td><td><span id="full-name-holder"></span></td></tr>
                <tr><td>Описание</td><td><span id="desc-holder"></span></td></tr>
                <tr><td>Вконтакте</td><td><span id="vk-holder"></span></td></tr>
                <tr><td rowspan="2"><span id="text-holder"></span></td></tr>
                </table>
            </td>
        </tr>
        </table>

    </td>
    <td width="16.6%"><!-- right column -->

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

</script>

</body>
</html>
