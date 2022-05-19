package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable() // csrf 사용안함 (API이기 때문)
                .httpBasic().disable() // jwt니까 basic인증 disable
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session 기반이 아님을 선언
                .and()

                // 아래 두 경로는 인증 안해도 됨
                .authorizeRequests()
                .antMatchers("/", "/auth/**").permitAll()

                // 위의 경로 외는 모두 인증해야 한다
                .anyRequest()
                .authenticated();

        // CorsFilter 이후에 jwtAuthenticationFilter를 실행한다 (순서가 바껴있는데 맞다고 함)
        http.addFilterAfter(
                jwtAuthenticationFilter,
                CorsFilter.class // import 주의.
        );

    }

}
