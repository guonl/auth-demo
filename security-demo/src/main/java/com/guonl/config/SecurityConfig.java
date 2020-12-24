package com.guonl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailService userDetailService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("admin")
//                .password(new BCryptPasswordEncoder().encode("123456"))
//                .roles("USER", "ADMIN");
//        auth.inMemoryAuthentication().withUser("user")
//                .password(new BCryptPasswordEncoder().encode("123456"))
//                .roles("USER");
//        super.configure(auth);

        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
//        auth.jdbcAuthentication().usersByUsernameQuery("").authoritiesByUsernameQuery("").passwordEncoder(passwordEncoder());

    }




//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/css/**", "/js/**", "/fonts/**", "/index").permitAll()  // 虽都可以访问
//                .antMatchers("/users/**").hasRole("USER")   // 需要相应的角色才能访问
//                .antMatchers("/admins/**").hasRole("ADMIN")   // 需要相应的角色才能访问
//                .and()
//                .formLogin();  //基于 Form 表单登录验证
////                .loginPage("/login").failureUrl("/login-error");  // 自定义登录界面
//    }



    /**
     * 认证信息管理
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }


    public static void main(String[] args) {

        String encode = new BCryptPasswordEncoder().encode("123456");
        System.out.println(encode);

    }


}
