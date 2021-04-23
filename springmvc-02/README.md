# 2 视图解析器
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
