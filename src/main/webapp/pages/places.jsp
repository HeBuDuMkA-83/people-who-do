<%--
  Created by IntelliJ IDEA.
  User: DuMkA
  Date: 19.02.2015
  Time: 0:39
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
    <title>Места</title>
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
        Места

    </td>
    <td width="16.6%" valign="top" class="right-side"><!-- right column -->

        <jsp:include page="fakeLogin.jsp"></jsp:include>

    </td>
</tr>
</table>

</body>
</html>
