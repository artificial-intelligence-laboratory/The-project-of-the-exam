package com.gcc.fns.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author xiaozhi
 * @description
 * @create 2022-10-2022/10/12 15:02
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    //    http://localhost:9999/swagger-ui.html     原路径
    //    http://localhost:9999/doc.html            新路径

    // 配置swagger2核心配置 docket
    @Bean
    public Docket createRestApi() {
        Predicate<RequestHandler> predicate = RequestHandlerSelectors.basePackage("com.gcc.fns.controller");

        return new Docket(DocumentationType.SWAGGER_2)  // 指定api类型为swagger2
                .apiInfo(apiInfo())                 // 用于定义api文档汇总信息
                .select()
                .apis(Predicates.and(predicate))
                .paths(PathSelectors.any())         // 所有controller
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("GCC小灵通API")                       // 文档页标题
                .contact(new Contact("gcc-fns",
                        "https://www.gcc-fns.com",
                        "123@qq.com"))                   // 联系人信息
                .description("为GCC小灵通提供的API")      // 详细信息
                .version("1.0.1")                               // 文档版本号
                .termsOfServiceUrl("https://www.gcc-fns.com")     // 网站地址
                .build();
    }
}
