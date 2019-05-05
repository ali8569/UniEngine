package ir.markazandroid.UniEngine.controller.userApi;

import com.google.gson.JsonObject;
import ir.markazandroid.UniEngine.JSONParser.Parser;
import ir.markazandroid.UniEngine.persistance.entity.PlayListEntity;
import ir.markazandroid.UniEngine.persistance.entity.UserEntity;
import ir.markazandroid.UniEngine.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ali on 6/25/2018.
 */
@RequestMapping(value = "/userApi/user",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class UserApiUserController {

    private final UserService userService;

    public UserApiUserController(UserService userService) {
        this.userService = userService;
    }


    @Autowired
    PersistentTokenRepository jdbcTokenRepository;


    @RequestMapping("/getMe")
    public UserEntity getMe(UserEntity userEntity){
        //System.out.println(userEntity);
        return userEntity;
    }

    @RequestMapping(value = "/invalidate")
    public void invalidate(@RequestBody PlayListEntity.Data data){

       /*  ArrayList<Session> usersSessions = new ArrayList<>(this.sessions
                 .findByPrincipalName(userEntity.getUsername()).values());
        System.out.println(userEntity);
        jdbcTokenRepository.removeUserTokens(userEntity.getUsername());
        sessions.deleteById(usersSessions.get(0).getId());*/
    }


/*
    @RequestMapping("/list")
    public List<UserEntity> list(UserEntity userEntity){
        return sessionRegistry.getAllPrincipals().stream()
                .filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
                .map(u -> (UserEntity)u )
                .collect(Collectors.toList());
    }*/

}
