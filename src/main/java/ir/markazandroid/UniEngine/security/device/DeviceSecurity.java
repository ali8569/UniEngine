package ir.markazandroid.UniEngine.security.device;

import ir.markazandroid.UniEngine.security.api.RestAuthenticationEntryPoint;
import ir.markazandroid.UniEngine.security.api.RestAuthenticationSuccessHandler;
import ir.markazandroid.UniEngine.security.api.RestLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * Created by Ali on 07/11/2017.
 */
@EnableWebSecurity
@Order(103)
public class DeviceSecurity extends WebSecurityConfigurerAdapter {

    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final PersistentTokenRepository tokenRepository;
    private final RestLogoutSuccessHandler restLogoutSuccessHandler;
    private final UUIDAuthenticationProvider UUIDAuthenticationProvider;

    @Autowired
    public DeviceSecurity(RestAuthenticationEntryPoint restAuthenticationEntryPoint,
                          PersistentTokenRepository tokenRepository,
                          RestLogoutSuccessHandler restLogoutSuccessHandler, UUIDAuthenticationProvider uuidAuthenticationProvider) {
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.tokenRepository = tokenRepository;
        this.restLogoutSuccessHandler = restLogoutSuccessHandler;
        UUIDAuthenticationProvider = uuidAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().antMatchers("/deviceApi/**").and()
                .sessionManagement()
                .maximumSessions(1)
                .and()
                .and()
                .authorizeRequests()
                //add  //.anyRequest().hasRole(UniEngineApplication.ROLE_DEVICE.getRole())
                //add  //.antMatchers("/deviceApi/authentication/login").permitAll()
                //.antMatchers("/api/**","/socket/phone/**"/*,"/socket/phone/**"*/).hasRole("PHONE")
                //rem
                .anyRequest().permitAll()

                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .apply(new TokenAuthenticationConfigurerAdapter())
                .loginProcessingUrl("/deviceApi/authentication/login")
                .successHandler(new RestAuthenticationSuccessHandler("/deviceApi/device/getMe"))
                .failureHandler(new SimpleUrlAuthenticationFailureHandler());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(UUIDAuthenticationProvider);
    }
}
