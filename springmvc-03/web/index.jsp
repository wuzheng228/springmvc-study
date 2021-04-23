<%--
  Created by IntelliJ IDEA.
  User: wuzheng001
  Date: 2021/4/23
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
没注册的用户，请<a href="${pageContext.request.contextPath }/index/register"> 注册</a>！
<br/>
已注册的用户，去<a href="${pageContext.request.contextPath }/index/login"> 登录</a>！
</body>
</html>
