package ir.markazandroid.UniEngine.controller.admin;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Ali on 5/1/2019.
 */
@Controller
@RequestMapping(value = "/admin/authentication")
public class AdminAuthenticationController {

    @RequestMapping("/login")
    public void login(){

    }
}
