# 03
## 3.1 springmvc获取请求参数
### 准备工作:
index.jsp: 
```
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
```
web.xml这里不再列出。

springmvc-servlet.xml配置文件
```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       https://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/mvc
       https://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <context:component-scan base-package="com.zzspace.web"/>
    <mvc:default-servlet-handler/>
    <mvc:annotation-driven/>

    <!-- 允许css目录下的所有文件可见 -->
    <mvc:resources location="/css/" mapping="/css/**" />
    <!-- 允许html目录下的所有文件可见 -->
    <mvc:resources location="/html/" mapping="/html/**" />
    <!-- 允许css目录下的所有文件可见 -->
    <mvc:resources location="/images/" mapping="/images/**" />


    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="internalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```
编写实体类：User
```java
package com.zzspace.pojo;

public class User {
    private String uname;
    private String upass;

    public User() {
    }

    public User(String uname, String upass) {
        this.uname = uname;
        this.upass = upass;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpass() {
        return upass;
    }

    public void setUpass(String upass) {
        this.upass = upass;
    }
}
```
编写indexController
```java
@Controller
@RequestMapping("/index")
public class indexController {
    @RequestMapping("/login")
    public String login() {
        return "login"; // 跳转到/WEB-INF/jsp下的login.jsp
    }
    @RequestMapping("/register")
    public String register() {
        return "register";
    }
}
```
编写页面：

login.jsp:
```
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
                    <input type="text" name="uname" class="textsize">
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
```
register.jsp:
```
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath }/user/register" method="post" name="registForm">
    <table border=1 bgcolor="lightblue" align="center">
        <tr>
            <td>姓名：</td>
            <td>
                <input class="textSize" type="text" name="uname" value="${uname}" />
            </td>
        </tr>
        <tr>
            <td>密码：</td>
            <td>
                <input class="textSize" type="password" maxlength="20" name="upass" />
            </td>
        </tr>
        <tr>
            <td>确认密码：</td>
            <td>
                <input class="textSize" type="password" maxlength="20" name="reupass" />
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="注册" />
            </td>
        </tr>
        </table>
</form>
${message}
</body>
</html>
```
status.jsp:
```
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%=session.getAttribute("u")%>
</body>
</html>
```

编写UserController
```java
@Controller
@RequestMapping("/user")
public class UserController {
    
}
```
### 3.1.1 通过实体bean接收请求参数
实体 Bean 来接收请求参数，适用于 get 和 post 提交请求方式。Bean 的属性名称必须与请求参数名称相同。
```java
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/login")
    public String login(User user, HttpSession session, Model model) {
        if ("zz".equals(user.getUname())
                && "123".equals(user.getUpass())) {
            session.setAttribute("u", user.getUname());
            return "status"; // 登录成功，跳转到 status.jsp
        } else {
            model.addAttribute("message", "用户名或密码错误");
            return "login";
        }
    }

    @RequestMapping("/register")
    public String register(User user, HttpSession session, Model model) {
        if ("zz".equals(user.getUname())
                && "123".equals(user.getUpass())) {
            session.setAttribute("u", user.getUname());
            return "login";
        } else {
            model.addAttribute("message", user.getUname());
            return "register";
        }
    }
}
```
### 3.1.2 形参接收请求参数
形参名称与请求参数名称完全相同。该接收参数方式适用于 get 和 post 提交请求方式。
```java
@RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String uname, String upass, HttpSession session, Model model) {
        if ("zz".equals(uname)
                && "123".equals(upass)) {
            session.setAttribute("u", uname);
            return "status"; // 登录成功，跳转到 status.jsp
        } else {
            model.addAttribute("message", "用户名或密码错误");
            return "login";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam("uname") String uname, String upass, HttpSession session, Model model) {
        if ("zz".equals(uname)
                && "123".equals(upass)) {
            session.setAttribute("u", uname);
            return "login";
        } else {
            model.addAttribute("message", uname);
            return "register";
        }
    }
```
形参名和请求参数不同时可以使用@RequestParam注解指定映射,当请求参数与接收参数名不一致时，“通过处理方法的形参接收请求参数”不会报 400 错误，而“通过 @RequestParam 接收请求参数”会报 400 错误。
### 3.1.3 @PathVariable 接收 URL 中的请求参数
```java
@RequestMapping("/path/{id}")
public String path(@PathVariable("id") int id, HttpSession session) {
    session.setAttribute("u", id);
    return "status";
}
```
### 3.1.4 HttpServletRequest 接收请求参数
```java
    @RequestMapping("/login")
    public String request(HttpServletRequest rq, HttpSession session) {
        session.setAttribute("u", rq.getParameter("uname") + rq.getParameter("upass"));
        return "status";
    }
```
### 3.1.5 @ModelAttribute 接收请求参数
#### 用在方法参数上
@ModelAttribute 注解放在处理方法的形参上时，用于将多个请求参数封装到一个实体对象，从而简化数据绑定流程，而且**自动暴露为模型数据(model)**，在视图页面展示时使用。
```java
    @RequestMapping("/register")
    public String model(@ModelAttribute("user") User user) {
        return "status";
    }
```
通过实体 Bean 接收请求参数”中只是将多个请求参数封装到一个实体对象，并不能暴露为模型数据（需要使用 model.addAttribute 语句才能暴露为模型数据。

#### 用在方法上
出现在方法上，表示当前方法会在控制器的方法执行之前，先执行，主要作用是更具请求参数查询数据库，封装成Model给控制器。
```java
    @RequestMapping("/register")
    public String model(@ModelAttribute("user") User user) {
        return "status";
    }
```
访问:http://localhost:8080/springmvc_03_war_exploded/test/user?username=zz
![](https://github.com/wuzheng228/springmvc-study/blob/master/images/QQ%E6%88%AA%E5%9B%BE20210423153823.png?raw=true)
也可以做权限控制：
```java
public class BaseController {
    @ModelAttribute
    public void isLogin(HttpSession session) throws Exception {
        if (session.getAttribute("user") == null) {
            throw new Exception("没有权限");
        }
    }
}
```
## 3.2 请求转发和重定向
### 基本概念

- 转发：浏览器向服务器发送请求后，服务器当前处理请求的方法将该请求转发给**容器内**另一个请求处理器，由于请求是在容器内部转发，所以浏览器地址栏不会改变

- 重定向： 客户浏览器发送 http 请求，Web 服务器接受后发送 302 状态码响应及对应新的 location 给客户浏览器，客户浏览器发现是 302 响应，则自动再发送一个新的 http 请求，请求 URL 是新的 location 地址，服务器根据此请求寻找资源并发送给客户。
浏览器地址栏的地址会改变，重定向行为是浏览器做了至少两次的访问请求。

### 例子：
```java
@Controller
@RequestMapping("/fr")
public class FRController {
    @RequestMapping("/login")
    public String login() {
        // 转发到一个视图
        return "login";
    }

    @RequestMapping("/register")
    public String register() {
        // 转发到一个请求方法, 同一个控制器可以省略”fr“
        return "forward:/fr/isRegister";
    }

    @RequestMapping("/isRegister")
    public String isRegister() {
        // 重定向一个请求方法
        return "redirect:/fr/login";
    }
}
```
访问:`http://localhost:8080/springmvc_03_war_exploded/fr/register`
输出:
![](https://github.com/wuzheng228/springmvc-study/blob/master/images/%E8%AF%B7%E6%B1%82%E8%BD%AC%E5%8F%91%E4%B8%8E%E9%87%8D%E5%AE%9A%E5%90%91%E6%B5%8B%E8%AF%95.png?raw=true)

