# 04
## 4.1 servlet的类型转换
Http的请求都是字符串类型，而我们则需要把字符串类型转化为其他类型。比如有一个实体类Goods
```java
public class Goods {
    private String name;
    private double price;
    private int number;
    public Goods() {}

    public Goods(String name, double price, int number) {
        this.name = name;
        this.price = price;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
```
现在我有以下需求将请求的数据转化为Goods对象（DO）,以便对数据库访问，如果使用传统的Servlet就会对请求参数进行类型转化：
```java
public class AddGoodServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        // 设置编码，防止乱码
        req.setCharacterEncoding("utf-8");
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String number = req.getParameter("number");
        // 下面进行类型转换
        double newgoodsprice = Double.parseDouble(price);
        int newgoodsnumber = Integer.parseInt(number);
        // 将转换后的数据封装成goods值对象
        Goods goods = new Goods(name, newgoodsprice, newgoodsnumber);
        // 将goods值对象传递给数据访问层，进行添加操作，代码省略, 这里直接传给jsp显示
        req.setAttribute("goods", goods);
        req.getRequestDispatcher("/WEB-INF/jsp/showgoods.jsp").forward(req,resp);
    }
}
```
web.xml中配置servlet
```xml
    <servlet>
        <servlet-name>goods</servlet-name>
        <servlet-class>com.zzspace.web.AddGoodServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>goods</servlet-name>
        <url-pattern>/addgood</url-pattern>
    </servlet-mapping>
```
编写界面:goods.jsp
```
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
```
showggods.jsp
```
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
```
测试:

访问:http://localhost:8080/springmvc_04_war_exploded/addgood
![](https://raw.githubusercontent.com/wuzheng228/springmvc-study/master/images/%E7%B1%BB%E5%9E%8B%E8%BD%AC%E5%8C%96%E6%B5%8B%E8%AF%9501.bmp)
输出:
![](https://github.com/wuzheng228/springmvc-study/blob/master/images/%E7%B1%BB%E5%9E%8B%E8%BD%AC%E5%8C%96%E6%B5%8B%E8%AF%9502.png?raw=true)

从这个例子可以看到，开发者需要自己在 Servlet 中进行类型转换，并将其封装成值对象。这些类型转换操作全部手工完成，异常烦琐。对于spring框架来说可以自动实现转换。

## 4.2 springmvc的类型转换器
SpringMVC框架的 Converter<S，T> 是一个可以将一种数据类型转换成另一种数据类型的接口，这里 S 表示源类型，T 表示目标类型。开发者在实际应用中使用框架内置的类型转换器基本上就够了，但有时需要编写具有特定功能的类型转换器。

### 内置转换器
标量转换器:
![](https://github.com/wuzheng228/springmvc-study/blob/master/images/%E5%86%85%E7%BD%AE%E8%BD%AC%E6%8D%A2%E5%99%A8.png?raw=true)
集合数组转换器:
![](https://github.com/wuzheng228/springmvc-study/blob/master/images/%E9%9B%86%E5%90%88%E8%BD%AC%E6%8D%A2%E5%99%A8.png?raw=true)
类型转换是在视图与控制器相互传递数据时发生的, 对于上面Servlet的例子可以改为(先注释调web.xml的servlet)：
```java
@Controller
public class GoodController {
    @RequestMapping("/addgood")
    public String add(HttpServletRequest req, Goods goods) {
        req.setAttribute("goods", goods);
        return "showgoods";
    }
}
```
防止乱码，web.xml配置字符过滤器
```
    <filter>
        <filter-name>charencoding</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>charencoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```
### 自定义类型转换器
当SpringMVC的内置类型转换器不能满足需求时，开发者可以实现自己的类型转换器。

现在有以下需求：将字符串 goods : “apple, 20.0, 2000”, 转换成Goods类

首先创建视图:goods2.jsp
```
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
```
然后创建接受该请求的Controller
```java
@Controller
@RequestMapping("/my")
public class ConverterController {
    @RequestMapping("/converter")
    public String myConverter(@RequestParam("goods")Goods goods, HttpServletRequest req) {
        req.setAttribute("goods", goods);
        return "showgoods";
    }
}
```
下面开始自定义类型转换器，首先实现Convert<S,T>接口，重写convert方法
```java
public class GoodsConverter implements Converter<String, Goods> {
    @Override
    public Goods convert(String s) {
        String[] strs = s.split(",");
        String name = strs[0];
        double price = Double.parseDouble(strs[1]);
        int number = Integer.parseInt(strs[2]);
        return new Goods(name, price, number);
    }
}
```
自定义完成类型转换器后，需要进行注册
```xml
<bean class="org.springframework.context.support.ConversionServiceFactoryBean">
    <property name="converters">
        <list>
            <bean class="com.zzspace.converter.GoodsConverter" />
        </list>
    </property>
</bean>
```
注意springmvc-servlet.xml中还需要在注解支持中添加类型转换器，让其生效
```xml
 <!--开启springMVC框架注解的支持,增加类型转换器，使其生效-->
<mvc:annotation-driven conversion-service="conversionService"/>
```
测试:



访问: `http://localhost:8080/springmvc_04_war_exploded/goods2.jsp`
![](https://github.com/wuzheng228/springmvc-study/blob/master/images/%E8%87%AA%E5%AE%9A%E4%B9%89%E7%B1%BB%E5%9E%8B%E8%BD%AC%E6%8D%A2%E5%99%A8%E6%B5%8B%E8%AF%95.png?raw=true)

输出:
![](https://github.com/wuzheng228/springmvc-study/blob/master/images/%E8%87%AA%E5%AE%9A%E4%B9%89%E7%B1%BB%E5%9E%8B%E8%BD%AC%E6%8D%A2%E5%99%A8.png?raw=true)

## 4.3 数据格式化
Spring MVC 框架的 Formatter<T> 与 Converter<S，T> 一样，也是一个可以将一种数据类型转换成另一种数据类型的接口。不同的是，Formatter<T> 的源数据类型必须是 String 类型，而 Converter<S，T> 的源数据类型是任意数据类型。

在 Web 应用中由 **HTTP 发送的请求数据到控制器中都是以 String 类型获取**，因此在 Web 应用中选择 Formatter<T> 比选择 Converter<S，T> 更加合理。

### 内置的格式化转换器
- NumberFormatter：实现 Number 与 String 之间的解析与格式化。
- CurrencyFormatter：实现 Number 与 String 之间的解析与格式化（带货币符号）。
- PercentFormatter：实现 Number 与 String 之间的解析与格式化（带百分数符号）。
- DateFormatter：实现 Date 与 String 之间的解析与格式化。

### 自定义格式化转换器
需求：将字符串“2021-01-01” 转化为实体类MyDate。

1.MyDate
```java
public class MyDate {
    private Date date;

    public MyDate() {}
    
    public MyDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
```
2.创建视图
```
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <form action="/date" method="post">
    <input type="text" value="日期">
    <input type="submit" value="提交">
  </form>
</body>
</html>
```
3.创建Controller
```java
@Controller
@RequestMapping("/fm")
public class FomatterController {
    @RequestMapping(value = "/date", method = RequestMethod.POST)
    public String convertDate(@RequestParam("date") MyDate date, Model model) {
        model.addAttribute(date);
        return "showdate";
    }
}
```
4.自定义Formatter
```java
public class DateFormatter implements Formatter<MyDate> {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public MyDate parse(String s, Locale locale) throws ParseException {
        return new MyDate(dateFormat.parse(s));
    }

    @Override
    public String print(MyDate date, Locale locale) {
        return dateFormat.format(date.getDate());
    }
}
```
parse 方法的功能是利用指定的 Locale 将一个 String 类型转换成目标类型，print 方法与之相反，用于返回目标对象的字符串表示。
5.注册格式化转换器
```xml
<bean id="conversionService" class="org.springframework.context.support.ConversionServiceFactoryBean">
<bean id="conversionService2" class="org.springframework.format.support.FormattingConversionServiceFactoryBean">
    <property name="formatters">
        <list>
            <bean class="com.zzspace.formatter.DateFormatter" />
        </list>
    </property>
</bean>    
```
测试:

访问:`http://localhost:8080/springmvc_04_war_exploded/input.jsp`

## 4.4 SpringMVC Json数据交互
### Json
JSON（JavaScript Object Notation, JS 对象标记）是一种轻量级的数据交换格式。与 XML 一样，JSON 也是基于纯文本的数据格式。它有对象结构和数组结构两种数据结构。

### Json数据转换
在使用注解开发时需要用到两个重要的 JSON 格式转换注解，分别是 @RequestBody 和 @ResponseBody。
- @RequestBody：用于将请求体中的数据绑定到方法的形参中，该注解应用在方法的形参上。
- @ResponseBody：用于直接返回 return 对象，该注解应用在方法上。

如果一个Controller返回的数据都是Json则可以使用@RestController

例子：
先导入jackson的依赖,*注意: 一定要将所有依赖包导入lib中: File -> Project Structure -> Artifacts -> WEB-INF/lib*
```xml
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-core</artifactId>
            <version>2.12.3</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.12.3</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-annotations</artifactId>
            <version>2.12.3</version>
        </dependency>
```
新建一个实体类:User
```java
public class User {
    private String name;
    private String pawd;

    public User() {
    }

    public User(String name, String pawd) {
        this.name = name;
        this.pawd = pawd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPawd() {
        return pawd;
    }

    public void setPawd(String pawd) {
        this.pawd = pawd;
    }
}
```
新键一个Controller：
```java
@Controller
@RequestMapping("/json")
public class JsonController {
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String test(@RequestBody User user, Model model) {
        model.addAttribute(user);
        return "user";
    }

    @RequestMapping(value = "/user2", method = RequestMethod.POST)
    @ResponseBody
    public User test(@RequestBody User user) {
        return user;
    }
}
```

我们使用Postman向该接口发一个Json数据.

测试user:
![](https://github.com/wuzheng228/springmvc-study/blob/master/images/@ResquestBody.png?raw=true)

测试user2:
![](https://github.com/wuzheng228/springmvc-study/blob/master/images/@ResponseBody.png?raw=true)