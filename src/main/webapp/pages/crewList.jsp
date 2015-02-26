<%--
  Created by IntelliJ IDEA.
  User: dumka
  Date: 20.02.15
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache, no-store"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>Команды</title>
    <link rel="stylesheet" href="/css/style.css" type="text/css" media="screen" />
    <script src="/js/jquery-1.11.2.min.js"></script>
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
        Команды
        <br><br>

        <div id="scopes-holder"></div>

        <br><br>

        <div id="crew-list-holder"></div>

    </td>
    <td width="16.6%"><!-- right column -->


    </td>
</tr>
</table>

<script>
    $.ajax({
        url: "/ajax/getCrewList",
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

                    html += '<div class="preview">';
                    html += '<img src="' + value.avatarPath + '">';
                    html += value.name;
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
