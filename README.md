# webcase-book-mgmt

#### 介绍
简单的图书管理系统,后端使用Spring Framework,Spring MVC,Spring Boot,Mybatis框架;<br>
前端使用Bootstrap,jQuery,Ajax;<br>
数据库使用MySQL,建表sql在/sql下.<br>

## **零碎知识点**

#### **请求转发和重定向相关的控制器方法返回值关键字**

**forward:（转发）：**

- 用法：return "forward:/someInternalView";
- 功能：指示容器执行内部转发操作，将请求传递给内部的视图处理器。
- 实际开发中的重要性：常用，尤其是在需要保持请求上下文信息，并且希望用户感知不到跳转细节的情况下。

**redirect:（重定向）：**

- 用法：return "redirect:/anotherURL";
- 功能：指示服务器发送一个HTTP重定向响应到客户端浏览器，浏览器会发起一个新的GET请求到指定的新URL。
- 实际开发中的重要性：非常常见，在登录验证后重定向、表单提交成功后的页面跳转、避免POST-Redirect-GET模式下的刷新问题等方面都经常使用。

**viewName 或直接返回视图名称：**

- 用法：return "someView";
- 功能：根据视图解析器配置查找并渲染指定名称的视图，通常用于转发到某个JSP、Thymeleaf模板或其他类型的视图资源。
- 实际开发中的重要性：基础且常用，每个控制器方法处理完业务逻辑后都需要返回一个视图以供显示结果。

**ModelAndView 对象：**

- 用法：return new ModelAndView("viewName", model);
- 功能：除了指定视图名外，还可以携带模型数据，便于在视图层展示。
- 实际开发中的重要性：常用于需要向视图传递复杂模型数据的场景。

**ResponseBody 注解或 ResponseEntity 类型：**

- 用法：配合 @ResponseBody 返回对象，或者直接返回 ResponseEntity。
- 功能：用于RESTful服务，将对象内容直接转换为JSON或其他格式响应给客户端，而不是渲染成HTML视图。
- 实际开发中的重要性：在现代Web应用尤其是API开发中极其常用。

以上关键字和类在实际的Spring MVC开发中都有广泛的应用，其中redirect:和viewName是最常见的返回类型，对于理解控制层如何与视图层交互至关重要。

#### **MyBatis使用查找时需要空参构造**

```java
public class LogMessage {    
    
//日志包含 主键ID、请求发起人【如果没有登录，则显示未登录】、请求URL， 请求时间【年月日时分秒】、请求所耗的时长【以ms为单位】。 
    private Integer id;    
    private String userName;    
    private String url;    
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")    
    private LocalDateTime requestTime;    
    
    private long requestDuration;     
    public LogMessage() {    
    }    
    public LogMessage(String userName, String url, LocalDateTime requestTime, long requestDuration) { 
        this.userName = userName;        
        this.url = url;        
        this.requestTime = requestTime;        
        this.requestDuration = requestDuration;    
    }
}
```

此处由于主键id没有放入有参构造，MyBatis在读取数据库并生成Java bean的时候，会优先寻找空参构造（如果没有构造函数也会默认空参构造），而如果有了有参构造而没有空参构造，变量赋值时会按照有参构造挨个赋值，这会导致赋值于实际需要的类型不匹配而出错，例如以上代码中如果没有空参构造函数，则id会赋值给userName， userName 会赋值给url，url会赋值给requestTime，此时由于requestTime是LocalDateTime类型，url是String类型，会抛出异常。

#### **Spring Bean验证需要BindingResult bindingResult紧跟在@Valid的bean后**

```java
@ResponseBody 
public Object addBook(@Valid Book book, BindingResult bindingResult, 
                      @RequestParam("coverUrlFile") MultipartFile coverUrl) {
    ... 
}
```

如果没有按照顺序写变量，则会出出错。

#### **ApplicationHome**

```
ApplicationHome applicationHome = new ApplicationHome(getClass()); log.debug(">>>> source: {}", applicationHome.getSource()); log.debug(">>> dir: {}", applicationHome.getDir()); //就是当前jar包所在的目录
```

这个类的作用是定位到jar包所在的目录下，在开发环境中是/target/classes目录，在实际运行环境则是jar包的目录。

#### **上传图片操作**

```java
String fileName = null; 
if (coverUrl != null) {    
    fileName = uploadImg(coverUrl); 
} 
private String uploadImg(MultipartFile coverUrl) {    
    log.debug("上传图片");    
    //获取当前jar包的绝对路径    
    ApplicationHome applicationHome = new ApplicationHome(getClass());    
    log.debug(">>>> source: {}", applicationHome.getSource());    
    log.debug(">>> dir: {}", applicationHome.getDir()); 
    //就是当前jar包所在的目录    
    String realPath = applicationHome.getDir().getAbsolutePath() + File.separatorChar + COVER_UPLOAD_DIR;  
    log.debug(realPath);    
    String fileName = null;    
    //上传图片    
    final String originalFilename = coverUrl.getOriginalFilename();    
    if (!originalFilename.isEmpty()) {        
        fileName = UUID.randomUUID().toString(); 
        //用于生成一个全局唯一的、无序的、长度为128位（16字节）的Universally Unique Identifier（UUID）        
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));        
        fileName += suffix;        
        try {            
            coverUrl.transferTo(new File(realPath + File.separatorChar + fileName));        
        } catch (IOException e) {            
            log.error("上传图片失败", e);        
        }    
    }    
    return fileName; 
}
```

#### **File.separatorChar**

在Java编程中，File.separatorChar 是一个静态字段，它代表了当前操作系统文件路径的分隔符字符。

1. 类说明：java.io.File 类是Java标准库提供的，用于表示和操作文件与目录的对象。
2. 字段说明：File.separatorChar 是一个字符类型的常量，其值根据不同操作系统而定。在Windows系统上，它的值是反斜杠 \。在Unix/Linux/Max OS X等使用POSIX标准的系统上，它的值是正斜杠 /。
3. 用途： 使用 File.separatorChar 可以确保编写出跨平台兼容的文件路径。在构造文件路径字符串时，通过该常量可以自动适应运行环境下的路径分隔符，无需手动判断当前的操作系统类型。

#### **Thymeleaf日期格式**

导入Maven依赖

```xml
<dependency>    
    <groupId>org.thymeleaf.extras</groupId>    
    <artifactId>thymeleaf-extras-java8time</artifactId> 
</dependency>
```

\#temporals.format（变量名,"格式"）

```xml
<td th:text="${#temporals.format(logMessage.requestTime, 'yyyy-MM-dd HH:mm:ss')}">     
    2024-02-24 14:36:44 
</td>
```

#### **拦截器中依赖注入失败**

如果在拦截器中使用@Autowired，由于拦截器默认不走IOC容器，则会导致依赖注入失败，出现空指针异常。

以下是一种解决办法：

给拦截器打上@Component注解使其成为一个Spring bean

```java
@Component 
public class PerformanceLogInterceptor extends HandlerInterceptorAdapter {    
    @Autowired    
    private LogService logService;    
    ...... 
}
```

然后在config配置类里构造一个getLogInterceptor()方法并打上@Bean注解，使其成为Spring Bean，然后在添加拦截器的方法中通过调用getLogInterceptor()方法添加拦截器

```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    @Bean
    public PerformanceLogInterceptor getLogInterceptor() {
        return new PerformanceLogInterceptor();
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        
        registry.addInterceptor(getLogInterceptor()).addPathPatterns("/**")
            .excludePathPatterns("/log/list.do");
        
        registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/**")
            .excludePathPatterns("/app/user/**","/static/**");
    }
}
```

