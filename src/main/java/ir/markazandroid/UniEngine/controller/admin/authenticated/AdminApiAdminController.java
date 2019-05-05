package ir.markazandroid.UniEngine.controller.admin.authenticated;

import ir.markazandroid.UniEngine.persistance.entity.AdminEntity;
import ir.markazandroid.UniEngine.persistance.entity.PlayListEntity;
import ir.markazandroid.UniEngine.persistance.entity.UserEntity;
import ir.markazandroid.UniEngine.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ali on 6/25/2018.
 */
@RequestMapping(value = "/admin/api",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class AdminApiAdminController {

    @RequestMapping("/getMe")
    public AdminEntity getMe(AdminEntity adminEntity){
        return adminEntity;
    }

}
