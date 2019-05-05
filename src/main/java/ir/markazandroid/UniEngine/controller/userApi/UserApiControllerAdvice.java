package ir.markazandroid.UniEngine.controller.userApi;

import ir.markazandroid.UniEngine.persistance.entity.UserEntity;
import ir.markazandroid.UniEngine.util.MultipartFileSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.security.Principal;
import java.util.Collection;

/**
 * Created by Ali on 10/9/2018.
 */
@ControllerAdvice(basePackages = "ir.markazandroid.UniEngine.controller.userApi")
public class UserApiControllerAdvice {

    @ModelAttribute(binding = false)
    public UserEntity userEntity(){
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}
