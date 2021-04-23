<%--
  Created by IntelliJ IDEA.
  User: wuzheng001
  Date: 2021/4/23
  Time: 14:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/user/login" method="post">
        <table>
            <tr>
                <td colspan="2">

                </td>
            </tr>
            <tr>
                <td>姓名:</td>
                <td>
                    <input type="text" name="name" class="textsize">
                </td>
            </tr>
            <tr>
                <td>密码：</td>
                <td>
                    <input type="password" name="upass" class="textsize">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="login" >
                </td>
            </tr>
        </table>
        ${message}
    </form>
</body>
</html>
