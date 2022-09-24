package com.atguigu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //配置当前类是配置类，需要被扫描
@EnableWebSecurity //@EnableWebSecurity 开启springSecurity的自动化配置，会给我们生成一个登录页面
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启controller方法中权限控制,就是没有这个权限就不可以做这个事
public class MySpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /*
    1.如果只是配置上面那些配置，SpringSecurity会拦截下来，什么都访问不了
    2.因此在内存中设置认证的用户名和密码，需要重写configure方法，参数是AuthenticationManagerBuilder auth
    3.配置密码时可以新建BCryptPasswordEncoder对象来进行加密
     */

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//            auth.inMemoryAuthentication().withUser("admin")
//                .password(new BCryptPasswordEncoder().encode("111111"))
//                .roles("");
//    }

    //配置密码加密器的bean
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*
    1.需要重写configure(HttpSecurity http)
    2.配置iframe标签放行
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //这里需要重写父类的方法，不然认证过程会失效,除非当前方法配置了认证的过程
//        super.configure(http);
        //配置iframe标签放行
        http.headers().frameOptions().sameOrigin();
        /*
        1.当前方法的认证过程配置
        下面也可以使用and()进行关联
         */

        //1.1配置可以匿名访问的资源,并且配置了其它资源必须认证
        http.authorizeRequests().antMatchers("/static/**","/login").permitAll() // 配置可以匿名访问的资源
                .anyRequest().authenticated(); //其它资源必须认证


        //1.2配置自定义登录页面
        http.formLogin().loginPage("/login") //配置自定义页面访问的路径
                .defaultSuccessUrl("/"); //配置登录成功后去往的地址

        //1.3配置登出
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login");

        //关闭跨域请求伪造,因为静态页面没有那个token值，并且不处理get请求，因此要关掉
        http.csrf().disable();

        //配置自定义的无权限访问的处理器
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());
    }
}
