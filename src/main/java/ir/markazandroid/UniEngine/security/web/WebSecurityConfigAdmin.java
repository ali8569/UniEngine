package ir.markazandroid.UniEngine.security.web;

import ir.markazandroid.UniEngine.UniEngineApplication;
import ir.markazandroid.UniEngine.persistance.entity.PrivilegeEntity;
import ir.markazandroid.UniEngine.security.api.RestAuthenticationEntryPoint;
import ir.markazandroid.UniEngine.security.api.RestAuthenticationSuccessHandler;
import ir.markazandroid.UniEngine.service.interfaces.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.bind.annotation.CrossOrigin;

@EnableWebSecurity
@Configuration
@Order(104)
@CrossOrigin
public class WebSecurityConfigAdmin extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder passwordEncoder;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final AdminService adminService;


    @Autowired
    public WebSecurityConfigAdmin(BCryptPasswordEncoder passwordEncoder, RestAuthenticationEntryPoint restAuthenticationEntryPoint, AdminService adminService) {
        this.passwordEncoder = passwordEncoder;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.adminService = adminService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.antMatcher("/admin/**")
                .authorizeRequests()
                .antMatchers("/admin/authentication/**").permitAll()

                .antMatchers("/admin/PLServices/**").hasAuthority(PrivilegeEntity.ACCESS_PL_SERVICES)
                .anyRequest().hasAuthority(PrivilegeEntity.ACCESS_ADMIN_DASHBOARD)
                .and()
                .csrf().disable()

                .exceptionHandling()
                .defaultAuthenticationEntryPointFor(restAuthenticationEntryPoint, request -> {
                    String accept = request.getHeader("Accept");
                    if (accept==null || accept.equals("*/*")) return true;
                    String[] contentTypes = accept.split(",");
                    for (String contentType: contentTypes){
                        if (contentType.contains("json")) return true;
                    }
                    return false;
                })
                .defaultAuthenticationEntryPointFor(new LoginUrlAuthenticationEntryPoint("/admin/authentication/login"), request -> {
                    String accept = request.getHeader("Accept");
                    if (accept==null || accept.equals("*/*"))  return false;
                    String[] contentTypes = accept.split(",");
                    for (String contentType: contentTypes){
                        if (contentType.contains("json")) return false;
                    }
                    return true;
                })

                .and()
                .formLogin()
                .loginPage("/admin/authentication/login")
                .successForwardUrl("/admin")
                //.permitAll()

                .and()
                .apply(new FormLoginConfigurer<>())
                .loginProcessingUrl("/admin/authentication/restLogin")
                .successHandler(new RestAuthenticationSuccessHandler("/admin/api/getMe"))
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                //.permitAll()

                .and()
                .rememberMe()

                .and()
                .logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/authentication/login");

                /*.antMatchers("/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .and()
                .formLogin()
                .loginPage("/admin/loginAdmin")
                .loginProcessingUrl("/admin/admin_login")
                .failureUrl("/admin/loginAdmin?error=loginError")
                .defaultSuccessUrl("/admin/adminPage")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/admin/admin_logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403")

                .and()*/
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminService).passwordEncoder(passwordEncoder);
    }
}
