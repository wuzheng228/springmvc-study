# 05
## 5.1 SpringMVC 拦截器的配置及使用
SpringMVC的拦截器类似与Filter，可以拦截请求并做相应的处理。常应用在权限验证、记录请求信息的日志、判断用户是否登录等功能上。

可以通过实现HandlerInterceptor、WebRequestInterceptor来编写自己的拦截器

```java
public class MyIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle执行了..........");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle执行了...........");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion执行了");
    }
}
```
编写测试的Controller
```java
@Controller
public class HelloController {
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String test() {
        System.out.println("控制器执行");
        return "hello";
    }
}
```
配置自定义拦截器:
```xml
<mvc:interceptors>
    <mvc:interceptor>
        <!-- 配置拦截器作用的路径 -->
        <mvc:mapping path="/hello" />
        <!-- 定义在<mvc: interceptor>元素中，表示匹配指定路径的请求才进行拦截 -->
        <bean class="com.zzspace.intercepter.MyIntercepter" />
    </mvc:interceptor>
</mvc:interceptors>
```
测试: 

访问: `http://localhost:8080/springmvc_05_war_exploded/hello`

输出:

![](https://github.com/wuzheng228/springmvc-study/blob/master/images/%E5%8D%95%E4%B8%AA%E6%8B%A6%E6%88%AA%E5%99%A8%E7%9A%84%E6%89%A7%E8%A1%8C%E6%B5%81%E7%A8%8B.png?raw=true)
## 5.2 拦截器的执行流程
上面测试结果显示了单个拦截器的执行流程，如果配置了多个拦截器，它们的 preHandle 方法将按照配置文件中拦截器的配置顺序执行，而它们的 postHandle 方法和 afterCompletion 方法则按照配置顺序的反序执行。

下面再创建一个拦截器并配置，然后测试:
```java
public class MyIntecepter1 implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("MyIntecepter1 preHandle执行了..........");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("MyIntecepter1 postHandle执行了...........");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("MyIntecepter1 afterCompletion执行了");
    }
}
```
配置文件:
```xml
    <mvc:interceptors>
        <mvc:interceptor>
            <!-- 配置拦截器作用的路径 -->
            <mvc:mapping path="/hello" />
            <!-- 定义在<mvc: interceptor>元素中，表示匹配指定路径的请求才进行拦截 -->
            <bean class="com.zzspace.intercepter.MyIntercepter" />
        </mvc:interceptor>
        <mvc:interceptor>
            <mvc:mapping path="/hello"/>
            <bean class="com.zzspace.intercepter.MyIntecepter1" />
        </mvc:interceptor>
    </mvc:interceptors>
```
测试结果:
![](https://github.com/wuzheng228/springmvc-study/blob/master/images/%E9%85%8D%E7%BD%AE%E5%A4%9A%E4%B8%AA%E6%8B%A6%E6%88%AA%E5%99%A8%E7%9A%84%E6%89%A7%E8%A1%8C%E6%B5%81%E7%A8%8B.png?raw=true)

## 5.3 用户登录权限案例
需求:

只有成功登录的用户才能访问系统的主页面 main.jsp，如果没有成功登录而直接访问主页面，则拦截器将请求拦截，并转发到登录页面 login.jsp。当成功登录的用户在系统主页面中单击“退出”链接时回到登录页面。

1.创建视图:

login.jsp关键代码:
```
<body>
${msg}
  <form action="${pageContext.request.contextPath }/login" method="post">
    用户名：<input type="text" name="name" /><br>
    密码：<input type="password" name="pswd" /><br>
    <input type="submit" value="登录" />
  </form>
</body>
```
main.jsp关键代码
```
当前用户：${user.uname }<br />
<a href="${pageContext.request.contextPath }/logout">退出</a>
```
创建实体类User
```java
public class User {
    String name;
    String pswd;

    public User() {
    }

    public User(String name, String pswd) {
        this.name = name;
        this.pswd = pswd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }
}
```
创建Controller
```java
@Controller
public class LoginController {
    @RequestMapping("/login")
    public String login(User user, Model model, HttpSession session) {
        if (user.getName().equals("zz") && user.getPswd().equals("123")) {
            model.addAttribute(user);
            session.setAttribute("user", user);
            return "main";
        } else {
            model.addAttribute("msg", "用户名密码错误");
            return "login";
        }
    }

    @RequestMapping("/logOut")
    public String logOut(HttpSession session) {
        session.removeAttribute("user");
        return "login";
    }
}
```
创建拦截器:
```java
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/login") || requestURI.contains("/toLogin")) {
            return true;
        }
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            return true;
        }
        request.setAttribute("msg", "请先登录！");
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
```
配置拦截器:
```xml
    <mvc:interceptors>
        <mvc:interceptor>
            <mvc:mapping path="/**"/>
            <bean class="com.zzspace.intercepter.LoginInterceptor"/>
        </mvc:interceptor>
    </mvc:interceptors>
```
测试：

访问`http://localhost:8080/springmvc_05_war_exploded/toMian`

![](https://github.com/wuzheng228/springmvc-study/blob/master/images/%E7%99%BB%E9%99%86%E6%9D%83%E9%99%90%E6%A1%88%E4%BE%8B.png?raw=true)

用户成功登录后再次访问:`http://localhost:8080/springmvc_05_war_exploded/toMian`

![](https://github.com/wuzheng228/springmvc-study/blob/master/images/%E7%99%BB%E9%99%86%E6%9D%83%E9%99%90%E6%A1%88%E4%BE%8B1.png?raw=true)

## 5.4 数据验证
在 Spring MVC 框架中有以下两种方法可以验证输入数据：
- 利用 Spring 自带的验证框架。
- 利用 JSR 303 实现。

数据验证分为客户端验证，和服务端验证。

客户端验证在大多数情况下，使用 JavaScript 进行客户端验证的步骤如下：
- 编写验证函数。
- 在提交表单的事件中调用验证函数。
- 根据验证函数来判断是否进行表单提交。

攻击者还可以绕过客户端验证直接进行非法输入，所以必须要有服务端验证

服务端验证:

Spring MVC 的 Converter 和 Formatter 在进行类型转换时是将输入数据转换成领域对象的属性值（一种 Java 类型），一旦成功，服务器端验证器就会介入。也就是说，在 Spring MVC 框架中**先进行数据类型转换，再进行服务器端验证**。

### JSR 303 框架 Hibernate-Validator

1.引入依赖
```xml
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-validator</artifactId>
            <version>5.0.2.Final</version>
        </dependency>
```
2.配置属性文件与验证器
```xml
<bean id="messageSource" class="org.springframework.context.support.ReloadableResourceBundleMessageSource">
    <property name="basenames">
        <list>
            <value>/WEB-INF/resource/errorMessages.properties</value>
        </list>
    </property>
    <!-- 资源文件编码格式 -->
    <property name="fileEncodings" value="utf-8" />
    <!-- 对资源文件内容缓存的时间，单位为秒 -->
    <property name="cacheSeconds" value="120" />
</bean>

        <!-- 注册校验器 -->
<bean id="validator" class="org.springframework.validation.beanvalidation.LocalValidatorFactoryBean">
    <!-- hibernate 校验器 -->
    <property name="providerClass" value="org.hibernate.validator.HibernateValidator" />
    <!-- 指定校验使用的资源文件，在文件中配置校验错误信息，如果不指定则默认使用 classpath下的 ValidationMessages.properties -->
    <property name="validationMessageSource" ref="messageSource" />
</bean>

<mvc:annotation-driven validator="validator"/>
```
3.标注类型

 **空检查**
 - @Null：验证对象是否为 null。
 - @NotNull：验证对象是否不为 null，无法检查长度为 0 的字符串。
 - @NotBlank：检查约束字符串是不是 null，以及被 trim 后的长度是否大于 0，只针对字符串，且会去掉前后空格。
 - @NotEmpty：检查约束元素是否为 null 或者是 empty。

```java
@NotBlank(message="{goods.gname.required}") //goods.gname.required为属性文件的错误代码
private String gname;
```

**boolean 检查**
- @AssertTrue：验证 boolean 属性是否为 true。
- @AssertFalse：验证 boolean 属性是否为 false。

```java
@AssertTrue
private boolean isLogin;
```

**长度检查**
- @Size（min=，max=）：验证对象（Array、Collection、Map、String）长度是否在给定的范围之内。
- @Length（min=，max=）：验证字符串长度是否在给定的范围之内
```java
@Length(min=1,max=100)
private String gdescription;
```
**日期检查**
- @Past：验证 Date 和 Callendar 对象是否在当前时间之前。
- @Future：验证 Date 和 Calendar 对象是否在当前时间之后。
- @Pattern：验证 String 对象是否符合正则表达式的规则。

```java
@Past(message="{gdate.invalid}")
private Date gdate;
```

**数值检查**

![](https://github.com/wuzheng228/springmvc-study/blob/master/images/%E6%95%B0%E5%80%BC%E6%A3%80%E6%9F%A5.png?raw=true)

```java
@Range(min=10,max=100,message="{gprice.invalid}")
private double gprice;
```
例子：

修改User类，给name属性加上注解
```java
    @Length(min = 2,max = 4, message = "{error.name.length}")
    String name;
```
修改LoginController,对需要校验的形参加上`@Valid`注解，并在之后加上`BindingResult result`
```java
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Valid User user, BindingResult result, Model model, HttpSession session) {
        if(result.hasErrors()){
        StringBuffer buffer = new StringBuffer();
        for (ObjectError error : result.getAllErrors()) {
        System.out.println(error.getDefaultMessage());
        buffer.append(error.getDefaultMessage());
        }
        model.addAttribute("msg", buffer.toString());
        return "login";
        }
        if (user.getName().equals("zz") && user.getPswd().equals("123")) {
        model.addAttribute(user);
        session.setAttribute("user", user);
        return "main";
        } else {
        model.addAttribute("msg", "用户名密码错误");
        return "login";
        }
        }
```
测试:

访问:`http://localhost:8080/springmvc_05_war_exploded/toLogin`

输入长度在[2, 4]之外的name




