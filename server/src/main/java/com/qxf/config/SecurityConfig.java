package com.qxf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @ClassName SecurityConfig
 * @Description TODO
 * @Author qiuxinfa
 * @Date 2020/5/8 20:06
 **/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint authenticationErrorHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final ApplicationContext applicationContext;

    @Autowired
    private TokenFilter tokenFilter;

    @Autowired
    private UserDetailsService sysUserService;


//    @Autowired
//    private CorsFilter corsFilter;

    public SecurityConfig(TokenProvider tokenProvider,JwtAuthenticationEntryPoint authenticationErrorHandler, JwtAccessDeniedHandler jwtAccessDeniedHandler, ApplicationContext applicationContext) {
        this.tokenProvider = tokenProvider;
        this.authenticationErrorHandler = authenticationErrorHandler;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.applicationContext = applicationContext;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 密码加密方式
        return new BCryptPasswordEncoder();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(sysUserService).passwordEncoder(
//                passwordEncoder());
//        super.configure(auth);
//    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()   // 禁用 csrf
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 禁用 session
                .and()
                .cors()
                .and()
                .authorizeRequests()
                // httpMethod options
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 账号相关，其中"/api/file/**"和头像上传相关
                .antMatchers("/auth/**","/api/file/**").permitAll()
                // 文件上传与下载
                .antMatchers("/file/**").permitAll()
                // webSocket
                .antMatchers("/ws/**").permitAll()
                // 诊断点
                .antMatchers("/actuator/**").permitAll()
                // 静态资源
                .antMatchers("/static/**").permitAll()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/loading/**").permitAll()
                .antMatchers("/logo.png").permitAll()
                .antMatchers("/index.html").permitAll()
                // swagger (生产环境不应该生效)
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
//                .anyRequest().authenticated()
                //基于url的访问控制
                .anyRequest().access("@baseUrlControl.canAccess(request,authentication)")
                .and()
                .headers().cacheControl();
        // 放入自定义拦截器
        httpSecurity.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 安全认证 异常管控
        httpSecurity.exceptionHandling()
                .authenticationEntryPoint(authenticationErrorHandler)   // 未登录处理
                .accessDeniedHandler(jwtAccessDeniedHandler);            // 无权限处理
    }

}
