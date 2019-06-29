package ir.markazandroid.UniEngine.controller.deviceApi;

import ir.markazandroid.UniEngine.conf.session.SessionManager;
import ir.markazandroid.UniEngine.persistance.entity.DeviceEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

/**
 * Created by Ali on 10/9/2018.
 */
@ControllerAdvice(basePackages = "ir.markazandroid.UniEngine.controller.deviceApi")
public class DeviceApiControllerAdvice {

    private final SessionManager sessionManager;

    public DeviceApiControllerAdvice(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @ModelAttribute(binding = false)
    public DeviceEntity deviceEntity(HttpSession session) {
        /*return (DeviceEntity) sessionManager.getPrincipal(session);*/
        return null;
    }


}
