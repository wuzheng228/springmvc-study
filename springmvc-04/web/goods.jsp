<%--
  Created by IntelliJ IDEA.
  User: 76973
  Date: 2021/4/23
  Time: 21:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <form action="${pageContext.request.contextPath}/addgood" method="post">
    <table border=1 bgcolor="lightblue" align="center">
      <tr>
        <td>商品名称：</td>
        <td><input class="textSize" type="text" name="name" /></td>
      </tr>
      <tr>
        <td>商品价格：</td>
        <td><input class="textSize" type="text" name="price" /></td>
      </tr>
      <tr>
        <td>商品数量：</td>
        <td><input class="textSize" type="text" name="number" /></td>
      </tr>
      <tr>
        <td colspan="2" align="center">
          <input type="submit" value="提交" />
        </td>
      </tr>
      </table>
  </form>
</body>
</html>
