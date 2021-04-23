# 02
## 2.1 视图解析器
在前面入门的例子基础上我们可以配置一个视图解析器
```xml
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="WEB-INF/jsp"/>
        <property name="suffix" value=".jsp"/>
</bean>
```
RegisterController 和 LoginController 控制器类的视图路径仅需提供 register 和 login，视图解析器将会自动添加前缀和后缀。

```java
public class LoginController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView("login");
    }
}
```
```java
public class RegisterController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView("register");
    }
}
```
上面通过实现Controller接口来实现控制器控制器不仅需要在配置文件中部署映射，而且只能编写一个处理方法，不够灵活。使用基于注解的控制器具有以下两个优点：

- 在基于注解的控制器类中可以编写多个处理方法，进而可以处理多个请求（动作），这就允许将相关的操作编写在同一个控制器类中，从而减少控制器类的数量，方便以后的维护。
- 基于注解的控制器不需要在配置文件中部署映射，仅需要使用 RequestMapping 注释类型注解一个方法进行请求处理。
## 2.2 注解开发
首先在springmvc-servlet中开启注解扫描
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
    <context:component-scan base-package="webanoo"/>
    <mvc:default-servlet-handler/>
    <mvc:annotation-driven/>

    <bean name="/login" class="web.LoginController"/>
    <bean name="/register" class="web.RegisterController"/>

    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="internalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```
然后在webanno包下添加IndexController类，并使用注解注释为Controller

### 2.2.3 @Controller
```java
@Controller
public class IndexController {
}
```
### 2.2.4 @RequestMapping

#### 加在方法上
可以为每个请求编写处理方法，请求与处理方法对应起来需要使用@RequestMapping注解
```java
@Controller
public class IndexController {
    @RequestMapping("/index/login")
    public String indexLogin() {
        return "login";
    }
}
```

可以通过以下URL访问IndexLogin方法

`http://localhost:8080/springmvc_02_war_exploded/index/login`
#### 加在类上
@RequestMapping也可以加在类上，代表一级目录
```java
@Controller
@RequestMapping("/login")
public class IndexController {
    @RequestMapping("/index/login")
    public String indexLogin() {
        return "login";
    }
}
```
可以通过以下链接访问：
`http://localhost:8080/springmvc_02_war_exploded/login/index/login`

### 2.2.5 请求处理方法

#### 常见的请求参数类型

1.可以使用使用servlet API 类型

```java
@Controller
@RequestMapping("/servlet")
public class ServletController {
    @RequestMapping("/login")
    public String login(HttpSession session, HttpServletRequest rq) {
        session.setAttribute("skey", "session范围的值");
        rq.setAttribute("rkey", "request范围的值");
        return "login";
    }
}
```

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p><%=request.getAttribute("rkey")%></p>
<p><%=session.getAttribute("skey")%></p>
登录页面
</body>
</html>
```

访问: `http://localhost:8080/springmvc_02_war_exploded/servlet/login`

输出：![](https://github.com/wuzheng228/springmvc-study/blob/master/images/Servlet%20API.png?raw=true)

2.Model

该类型是一个包含 Map 的 Spring 框架类型。在每次调用请求处理方法时 Spring MVC 都将创建 org.springframework.ui.Model 对象。Model 参数类型的示例代码如下：

```java
@Controller
@RequestMapping("/login")
public class IndexController {
    @RequestMapping("/index/login")
    public String indexLogin(Model model) {
        /*在视图中可以使用EL表达式${success}取出model中的值*/
        model.addAttribute("success", "注册成功");
        return "login";
    }
}
```

login.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p><%=request.getAttribute("rkey")%></p>
<p><%=session.getAttribute("skey")%></p>
<p>${success}</p>
登录页面
</body>
</html>
```

访问：``

输出：![](https://github.com/wuzheng228/springmvc-study/blob/master/images/Model%E6%B5%8B%E8%AF%95.png?raw=true)

#### 请求处理方法的返回类型

最常见的返回类型就是代表**逻辑视图名称**的 String 类型，例如前面教程中的请求处理方法。除了 String 类型以外，还有 ModelAndView、Model、View 以及其他任意的 Java 类型。

返回ModelAndView例子:

```java
@Controller
@RequestMapping("/servlet")
public class ServletController {
    @RequestMapping("/user")
    public ModelAndView login() {
        User user = new User();
        user.setName("zzspace");
        user.setPassword("12345");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(user);
        modelAndView.setViewName("user");
        return modelAndView;
    }
}
```

添加user.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
user:
<p>${user.name}</p>
<p>${user.password}</p>
</body>
</html>
```

访问: `http://localhost:8080/springmvc_02_war_exploded/servlet/user`

输出:

![](https://github.com/wuzheng228/springmvc-study/blob/master/images/ModelAndView%E6%B5%8B%E8%AF%95.png?raw=true)





