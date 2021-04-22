# 1 基本概念
## 1.1 MVC架构
MVC 是 Model、View 和 Controller 的缩写，分别代表 Web 应用程序中的 3 种职责。
- 模型：用于存储数据以及处理用户请求的业务逻辑。
- 视图：向控制器提交数据，显示模型中的数据。
- 控制器：根据视图提出的请求判断将请求和数据交给哪个模型处理，将处理后的有关结果交给哪个视图更新显示。

servlet的MVC模式具体实现如下:
![](https://github.com/wuzheng228/springmvc-study/blob/master/images/20210422171000.png?raw=true)

## 1.2 springmvc 的工作流程（重要）
Spring MVC 框架主要由 DispatcherServlet、处理器映射、控制器、视图解析器、视图组成
![](https://github.com/wuzheng228/springmvc-study/blob/master/images/QQ%E5%9B%BE%E7%89%8720210422172524.png?raw=true)

1. 客户端将请求提交到DispatcherServlet
2. DispatcherServlet寻找一个或多个HandlerMapping, 找到处理请求的Controller
3. DispatcherServlet将请求提交到Controller
4. Controller再调用业务逻辑处理后返回ModelAndView
5. DispatcherServlet 寻找一个或多个 ViewResolver 视图解析器，找到 ModelAndView 指定的视图。
6. 视图负责将结果显示到客户端。

上图包含四个接口: DispatcherServlet、HandlerMapping、Controller 和 ViewResolver

从宏观角度考虑，DispatcherServlet 是整个 Web 应用的控制器；从微观考虑，Controller 是单个 Http 请求处理过程中的控制器，而 ModelAndView 是 Http 请求过程中返回的模型（Model）和视图（View）。

ViewResolver 接口（视图解析器）在 Web 应用中负责查找 View 对象，从而将相应结果渲染给客户。

# 2 入门程序
## 2.1 第一步引入Jar包
参见 springmvc-study下的pom文件
```xml
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>5.0.2.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.0.2.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>2.5</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.0</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.0.2.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>5.0.2.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>5.0.2.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>5.0.2.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-expression</artifactId>
            <version>5.0.2.RELEASE</version>
        </dependency>
```
## 2.2 在web.xml下配置DispatcherServlet
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servlet.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```
注意如果不配置<init-param>节点的内容，则springmvc-servlet.xml需要放在WEB-INF文件夹下，这里我们配置放在类路径下
## 2.3 编写入口网页index.jsp
```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  未注册的用户，请<a href="${pageContext.request.contextPath }/register"> 注册</a>！
  <br/>
  已注册的用户，去<a href="${pageContext.request.contextPath }/login"> 登录</a>！
  </body>
</html>
```
## 2.4 编写Controller
```java
public class LoginController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView("/WEB-INF/jsp/login.jsp");
    }
}
```
```java
public class RegisterController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView("/WEB-INF/jsp/register.jsp");
    }
}
```
下面再编写需要跳转的Jsp页面，这里代码省略，放在 /WEB-INF/ 下面的内容是不能直接通过 request 请求的方式请求到的，为了安全性考虑，我们通常会把 jsp 文件放在 WEB-INF 目录下
。

接下来需要将这两个Controller配置到spring容器中，如果开启注解扫描就不要配xml文件了

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    
    <bean name ="/login" class="web.LoginController"/>
    <bean name="/register" class="web.RegisterController"/>
</beans>
```
下面就可以配置Tomcat启动测试了
![](https://raw.githubusercontent.com/wuzheng228/springmvc-study/master/images/%E9%85%8D%E7%BD%AETomcat.bmp)

如果报了ClassNotFoundException:DispathcerServlet的错误是没有导入jar包的问题，解决方法就是：File -> Project Structure -> artifacts
然后选择部署的应用，在WEB-INF新键lib文件夹，将所有jar包导入
![](https://github.com/wuzheng228/springmvc-study/blob/master/images/%E6%B7%BB%E5%8A%A0lib%E6%96%87%E4%BB%B6%E5%A4%B901.png?raw=true)
![](https://github.com/wuzheng228/springmvc-study/blob/master/images/%E5%AF%BC%E5%85%A5%E6%89%80%E6%9C%89jar%E5%8C%85.png?raw=true)
测试结果:
![](https://github.com/wuzheng228/springmvc-study/blob/master/images/%E5%85%A5%E9%97%A8%E6%B5%8B%E8%AF%9501.bmp?raw=true)
![](https://raw.githubusercontent.com/wuzheng228/springmvc-study/master/images/%E5%85%A5%E9%97%A8%E6%B5%8B%E8%AF%9502.bmp)
![](https://raw.githubusercontent.com/wuzheng228/springmvc-study/master/images/%E5%85%A5%E9%97%A8%E6%B5%8B%E8%AF%9503.bmp)
