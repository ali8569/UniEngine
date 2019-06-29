package ir.markazandroid.UniEngine.security.device;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ali on 5/3/2018.
 */
public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    TokenAuthenticationFilter(AntPathRequestMatcher matcher) {
        super(matcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException,
            IOException, ServletException {

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            request.getRequestDispatcher("/deviceApi/device/getMe").forward(request, response);
            return null;
        }

        String uuid = request.getParameter("uuid");

        Authentication authentication;

        if (uuid == null) {
            throw new BadCredentialsException("uuid phone");
        }
        authentication = new UUIDAuthentication(uuid, "uuid");
        return this.getAuthenticationManager().authenticate(authentication);

    }

}
