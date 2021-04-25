<%--
  Created by IntelliJ IDEA.
  User: 76973
  Date: 2021/4/24
  Time: 9:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <form action="${pageContext.request.contextPath}/fm/date" method="post">
    <input type="text" name="date" value="2020-01-01">
    <input type="submit" value="提交">
  </form>
</body>
</html>
