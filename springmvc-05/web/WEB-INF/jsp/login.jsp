<%--
  Created by IntelliJ IDEA.
  User: 76973
  Date: 2021/4/24
  Time: 20:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
${msg}
  <form action="${pageContext.request.contextPath }/login" method="post">
    用户名：<input type="text" name="name" /><br>
    密码：<input type="password" name="pswd" /><br>
    <input type="submit" value="登录" />
  </form>
</body>
</html>
