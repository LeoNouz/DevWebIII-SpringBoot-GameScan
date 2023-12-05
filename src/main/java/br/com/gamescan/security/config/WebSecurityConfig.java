package br.com.gamescan.security.config;

import br.com.gamescan.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/",
                        "/games",
                        "/games-alternative",
                        "/game/detalhes/**",
                        "/jogando/save",
                        "/jogando/delete",
                        "/parajogar/save",
                        "/parajogar/delete",
                        "/zerados/save",
                        "/zerados/delete",
                        "/games/user*/**",
                        "/user/register",
                        "/admin/register/**",
                        "/user/save",
                        "/admin/save",
                        "/user/new/**",
                        "/admin/new/**",
                        "/admin*/edit/user*/**",
                        "/admin*/edit/admin*/**",
                        "/admin*/delete/user*/**",
                        "/admin*/delete/admin*/**",
                        "/login-post",
                        "/index/user*/**",
                        "/index/admin*/**",
                        "/games/user*/**",
                        "/games/admin*/**",
                        "/games/user*/*/game-detalhes/**",
                        "/games/admin*/*/game-detalhes/**",
                        "/dashboard/user*/**",
                        "/dashboard/admin*/**",
                        "/control-panel/**",
                        "/css/**",
                        "/static_dashboard/**",
                        "/js/**",
                        "/images/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appUserService);
        return provider;
    }
}