package ir.markazandroid.UniEngine.controller.userApi.interfaces;

import ir.markazandroid.UniEngine.object.ResponseObject;
import ir.markazandroid.UniEngine.persistance.entity.PlayListEntity;
import ir.markazandroid.UniEngine.persistance.entity.UserEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Ali on 4/13/2019.
 */
@RequestMapping(value = "/userApi/playlist", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface UserApiPlayListController {

    @GetMapping("/list")
    List<PlayListEntity> getPlayLists(UserEntity userEntity);

    @PostMapping(value = "/saveOrUpdate", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseObject> saveOrUpdatePlayList(UserEntity userEntity, @RequestBody PlayListEntity playListEntity);

}
