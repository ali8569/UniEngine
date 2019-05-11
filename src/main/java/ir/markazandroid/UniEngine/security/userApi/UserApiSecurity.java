package ir.markazandroid.UniEngine.security.userApi;

import ir.markazandroid.UniEngine.UniEngineApplication;
import ir.markazandroid.UniEngine.security.api.RestAuthenticationEntryPoint;
import ir.markazandroid.UniEngine.security.api.RestAuthenticationSuccessHandler;
import ir.markazandroid.UniEngine.security.api.RestLogoutSuccessHandler;
import ir.markazandroid.UniEngine.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Created by Ali on 23/12/2017.
 */
@Component
@Configuration
@Order(105)
public class UserApiSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final PersistentTokenRepository persistentTokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final RestLogoutSuccessHandler restLogoutSuccessHandler;


    @Autowired
    public UserApiSecurity(UserService userService,
                           PersistentTokenRepository persistentTokenRepository, BCryptPasswordEncoder passwordEncoder, RestAuthenticationEntryPoint restAuthenticationEntryPoint, RestLogoutSuccessHandler restLogoutSuccessHandler) {
        this.userService = userService;
        this.persistentTokenRepository = persistentTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.restLogoutSuccessHandler = restLogoutSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry).and().and()
                .antMatcher("/userApi/**")
                .authorizeRequests()
                .antMatchers("/userApi/authentication/**")
                .permitAll()
                .antMatchers("/userApi/**").hasRole(UniEngineApplication.ROLE_USER.getRole())
                .and()
                .csrf().disable()

                .cors()

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)

                .and()
                .formLogin()
                .loginProcessingUrl("/userApi/authentication/login")
                .successHandler(new RestAuthenticationSuccessHandler("/userApi/user/getMe"))
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .permitAll()

                .and()
                //.defaultSuccessUrl("/web",true)
                .rememberMe()
                //.alwaysRemember(true)
                .tokenRepository(persistentTokenRepository)

                .and()
                .logout()
                .logoutUrl("/userApi/authentication/logout")
                .logoutSuccessHandler(restLogoutSuccessHandler);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200"); // @Value: http://localhost:8080
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setSameSite(null);
        return cookieSerializer;
    }

}
