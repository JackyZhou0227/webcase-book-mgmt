package com.pxxy.springboot.webcase.config;

//import com.pxxy.springboot.webcase.filter.LoginFilter;
import com.pxxy.springboot.webcase.interceptor.AuthInterceptor;
import com.pxxy.springboot.webcase.interceptor.PerformanceLogInterceptor;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/******************
 *
 * @Description TODO
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final static String UPLOAD_IMAGE_URL = "images\\book_cover_img";

    //@Bean
    /*public FilterRegistrationBean filterRegistrationBean() {
        System.out.println("执行了 FilterRegistrationBean...");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new LoginFilter());
        filterRegistrationBean.addUrlPatterns("/book/*");
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }*/

    /**
     * 获取验证器
     * 该方法用于创建并返回一个LocalValidatorFactoryBean实例，该实例是Hibernate验证器的工厂，
     * 它通过设置属性并初始化后返回。
     *
     * @return Validator 验证器实例
     */
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean lvfb = new LocalValidatorFactoryBean();

        // 设置验证器的属性，将消息源(messageSource())与验证器关联
        lvfb.setValidationMessageSource(messageSource());

        // 返回配置好的验证器实例
        return lvfb;
    }

    /*****
     * 配置并返回一个消息源，用于在验证时读取资源文件。
     * 这个消息源特别用于加载bean验证相关的本地化消息。
     *
     * @return MessageSource 返回配置好的消息源实例。
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        // 配置消息源属性
        messageSource.setBasename("beanValidation"); // 设置资源文件的基础名称
        messageSource.setDefaultEncoding("UTF-8"); // 设置默认编码为UTF-8
        messageSource.setCacheSeconds(60); // 设置缓存时间为60秒
        messageSource.setAlwaysUseMessageFormat(true); // 总是使用消息格式化

        //
        return messageSource;
    }

    /**
     * 创建并配置 CommonsMultipartResolver，用于处理multipart/form-data类型的请求，即文件上传。
     * 这个方法不接受任何参数，配置了全局的最大上传大小和每个文件的最大大小。
     *
     * @return 返回配置好的CommonsMultipartResolver实例，用于解析multipart/form-data类型的请求。
     */
    @Bean
    public MultipartResolver multipartResolver() {
        // 创建 CommonsMultipartResolver 实例
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        // 配置最大上传大小为2MB
        multipartResolver.setMaxUploadSize(2*1024*1024);
        // 配置每个文件的最大大小为2MB
        multipartResolver.setMaxUploadSizePerFile(2*1024*1024);
        // 返回配置好的解析器实例
        return multipartResolver;
    }

    /**
     * 创建并返回一个PerformanceLogInterceptor实例。
     * 这个方法没有参数。
     *
     * @return PerformanceLogInterceptor 返回一个性能日志拦截器实例，
     * 用于拦截请求并记录性能日志。
     */
    @Bean
    public PerformanceLogInterceptor getLogInterceptor() {
        return new PerformanceLogInterceptor();
    }


    /**
     * 添加资源处理器
     *
     * @param registry 资源处理器注册表
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ApplicationHome applicationHome = new ApplicationHome(getClass());
        //添加一个文件上传的静态路径映射, 如果是文件目录，则需要以 file: 开头
        String realPath = applicationHome.getDir().getAbsolutePath() + File.separatorChar + UPLOAD_IMAGE_URL;
        //在Spring Boot中配置静态资源路径时，对于文件系统的绝对路径，需要在路径前加上"file:"协议前缀
        registry.addResourceHandler("/images/**").addResourceLocations("file:"+realPath);
    }

    /**
     * 添加拦截器到拦截器注册表中。
     * 该方法用于配置应用中的拦截器，决定哪些请求需要被拦截以及哪些不需要。
     *
     * @param registry 拦截器注册表，用于注册和管理拦截器。
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加日志拦截器，并设置拦截所有请求，但排除特定路径"log/list.do"
        //registry.addInterceptor(new PerformanceLogInterceptor()).addPathPatterns("/**");此方法不可行，因为拦截器需要作为Spring Bean依赖注入
        registry.addInterceptor(getLogInterceptor()).addPathPatterns("/**").excludePathPatterns("/log/list.do");

        // 添加认证拦截器，并设置拦截所有请求，但排除特定路径"app/user/**"和"static/**"
        registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/**").excludePathPatterns("/app/user/**","/static/**");
    }
}
