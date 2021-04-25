<%@ page import="com.zzspace.pojo.Goods" %><%--
  Created by IntelliJ IDEA.
  User: 76973
  Date: 2021/4/23
  Time: 21:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%=((Goods)request.getAttribute("goods")).getName()%>
<%=((Goods)request.getAttribute("goods")).getPrice()%>
<%=((Goods)request.getAttribute("goods")).getNumber()%>
</body>
</html>
