package ir.markazandroid.UniEngine.controller.admin.authenticated;

import ir.markazandroid.UniEngine.persistance.entity.AdminEntity;
import ir.markazandroid.UniEngine.persistance.entity.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by Ali on 10/9/2018.
 */
@ControllerAdvice(basePackages = "ir.markazandroid.UniEngine.controller.admin.authenticated")
public class AdminApiControllerAdvice {

    @ModelAttribute(binding = false)
    public AdminEntity adminEntity(){
        return (AdminEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}
