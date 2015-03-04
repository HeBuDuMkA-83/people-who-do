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
    <title>Редактирование данных о себе</title>
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
        Редактирование данных о себе
        <br><br>

        <form action="" method="post" accept-charset="UTF-8" >

            <table>
                <tr>
                    <td><div id="avatar-holder"></div></td>
                    <td valign="top">
                        <table>
                            <tr><td>Имя</td><td><input type="text" id="full-name-holder"></td></tr>
                            <tr><td>Описание</td><td><input type="text" id="desc-holder"></td></tr>
                            <tr><td>Вконтакте</td><td><input type="text" id="vk-holder"></td></tr>
                            <tr><td colspan="2"><textarea id="text-holder"></textarea></td></tr>
                        </table>
                    </td>
                </tr>
            </table>

            <input type="submit" value="Сохранить">
        </form>
    </td>
    <td width="16.6%"><!-- right column -->


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
                // VAL
                $('#avatar-holder').html('<img src="' + value.avatarPath + '">');
                $('#full-name-holder').val(value.name + ' ' + value.lastName);
                $('#desc-holder').val(value.desc);
                $('#vk-holder').val(value.vkAccount);
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
