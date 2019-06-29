package ir.markazandroid.UniEngine.controller.userApi;

import ir.markazandroid.UniEngine.conf.session.SessionManager;
import ir.markazandroid.UniEngine.persistance.entity.UserEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

/**
 * Created by Ali on 10/9/2018.
 */
@ControllerAdvice(basePackages = "ir.markazandroid.UniEngine.controller.userApi")
public class UserApiControllerAdvice {

    private final SessionManager sessionManager;

    public UserApiControllerAdvice(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @ModelAttribute(binding = false)
    public UserEntity userEntity(HttpSession session) {
        return (UserEntity) sessionManager.getPrincipal(session);
    }


}
