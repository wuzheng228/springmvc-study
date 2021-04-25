<%--
  Created by IntelliJ IDEA.
  User: 76973
  Date: 2021/4/24
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
当前用户：${user.name}<br />
<a href="${pageContext.request.contextPath }/logOut">退出</a>
</body>
</html>
