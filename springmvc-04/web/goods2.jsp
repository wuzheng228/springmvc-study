<%--
  Created by IntelliJ IDEA.
  User: 76973
  Date: 2021/4/24
  Time: 7:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/my/converter" method= "post">
        请输入商品信息（格式为apple, 10.58,200）:
        <input type="text" name="goods" /><br>
        <input type="submit" value="提交" />
    </form>
</body>
</html>
