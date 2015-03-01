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
    <script language="JavaScript" type="text/javascript" src="../js/jquery-1.11.2.min.js" charset="utf-8"></script>
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

        <table>
        <tr>
            <td><div id="avatar-holder"></div></td>
            <td>
                <span>Имя</span><span id="full-name-holder"></span><br>
                <span>Описание</span><span id="desc-holder"></span><br>
                <span>Вконтакте</span><span id="vk-holder"></span><br>
                <span id="text-holder"></span>
            </td>
        </tr>
        </table>




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
