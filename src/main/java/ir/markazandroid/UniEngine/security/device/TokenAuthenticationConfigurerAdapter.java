package ir.markazandroid.UniEngine.security.device;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Created by Ali on 5/3/2018.
 */
public class TokenAuthenticationConfigurerAdapter
        extends AbstractAuthenticationFilterConfigurer<HttpSecurity, TokenAuthenticationConfigurerAdapter, TokenAuthenticationFilter> {

    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl);
    }

    public TokenAuthenticationConfigurerAdapter() {
        super(new TokenAuthenticationFilter(new AntPathRequestMatcher("/login")), "/login");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        try {
            super.configure(http);
        } catch (IllegalArgumentException e) {
            http.addFilterBefore(getAuthenticationFilter(),
                    UsernamePasswordAuthenticationFilter.class);
        }
    }

}
