package com.company.config;

import com.company.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Authentication
//        auth.inMemoryAuthentication()
//                .withUser("alish").password("{noop}1").roles("MODERATOR")
//                .and()
//                .withUser("000").password("{noop}123").roles("USER")
//                .and()
//                .withUser("111").password("{noop}123").roles("USER")
//                .and()
//                .withUser("234").password("{noop}123").roles("SUPER_MODERATOR")
//                .and()
//                .withUser("22").password("{noop}434343").roles("USER")
//                .and()
//                .withUser("2ew2").password("{noop}434343").roles("USER")
//                .and()
//                .withUser("32332").password("{noop}434343").roles("USER")
//                .and()
//                .withUser("3232323").password("{noop}434343").roles("USER")
//                .and()
//                .withUser("1212").password("{noop}434343").roles("USER")
//                .and()
//                .withUser("1234").password("{noop}123").roles("ADMIN");
        auth.userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Authorization
        http.authorizeRequests()
                .antMatchers("/auth", "/auth/*").permitAll()
                .antMatchers("/region").permitAll()
                .antMatchers("/type/open","/type/open/*").permitAll()
                .antMatchers("/attach/open", "/attach/download").permitAll()
                .antMatchers("article","/article/public","/article/public/*").permitAll()
                .antMatchers("/article/adm","/article/adm/*").hasAnyRole( "SUPER_MODERATOR","MODERATOR")
                .antMatchers("/article/list").hasAnyRole("SUPER_MODERATOR","MODERATOR", "ADMIN")
                .antMatchers("/attach/upload").hasAnyRole("MODERATOR", "SUPER_MODERATOR","ADMIN")
                .antMatchers("/attach/deleted", "/attach/pagination").hasAnyRole("ADMIN")
                .antMatchers("/category/adm", "/category/adm/*").hasAnyRole("ADMIN")
                .antMatchers("/email/pagination").hasAnyRole("ADMIN")
                .antMatchers("/sms/pagination").hasAnyRole("ADMIN")
                .antMatchers("/profile/adm","/profile/adm/*").hasAnyRole("ADMIN")
                .antMatchers("/region/adm","/region/adm/*").hasAnyRole("ADMIN")
                .antMatchers("/type/adm","/type/adm/*").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
        http.csrf().disable();
        http.cors().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
//        return new PasswordEncoder() {
//            @Override
//            public String encode(CharSequence rawPassword) {
//                return rawPassword.toString();
//            }
//
//            @Override
//            public boolean matches(CharSequence rawPassword, String encodedPassword) {
//                String md5 = .getMd5(rawPassword.toString());
//                return md5.equals(encodedPassword);
//            }
//        };
    }
}
