package ir.markazandroid.UniEngine.controller.admin.authenticated;

import ir.markazandroid.UniEngine.conf.session.SessionManager;
import ir.markazandroid.UniEngine.persistance.entity.AdminEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

/**
 * Created by Ali on 10/9/2018.
 */
@ControllerAdvice(basePackages = "ir.markazandroid.UniEngine.controller.admin.authenticated")
public class AdminApiControllerAdvice {

    private final SessionManager sessionManager;

    public AdminApiControllerAdvice(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @ModelAttribute(binding = false)
    public AdminEntity adminEntity(HttpSession session) {
        return (AdminEntity) sessionManager.getPrincipal(session);
    }

}
