<%--
  Created by IntelliJ IDEA.
  User: wuzheng001
  Date: 2021/4/23
  Time: 14:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%=session.getAttribute("u")%>
${user.uname}
${user.upass}
</body>
</html>
