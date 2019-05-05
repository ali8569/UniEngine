package ir.markazandroid.UniEngine.controller.userApi.interfaces;

import ir.markazandroid.UniEngine.JSONParser.annotations.JSON;
import ir.markazandroid.UniEngine.object.EFile;
import ir.markazandroid.UniEngine.object.ResponseObject;
import ir.markazandroid.UniEngine.persistance.entity.UserEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ali on 4/13/2019.
 */
@RequestMapping(value = "/userApi/storage",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface UserApiStorageController {

    @GetMapping("/status")
    StorageStatus getStatus(UserEntity userEntity);

    @GetMapping("/file/list")
    ArrayList<EFile> getFiles(UserEntity userEntity, HttpServletRequest request);

    @PostMapping(value = "/file/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity uploadFile(UserEntity userEntity,@RequestParam MultipartFile file,HttpServletRequest request) throws IOException;

    @PostMapping("/file/delete")
    ResponseEntity<ResponseObject> deleteFile(UserEntity userEntity, @RequestParam(name = "eFileId") String encodedFileId);


    @JSON
    class StorageStatus implements Serializable {
        private long max;
        private long used;

        public StorageStatus(long max, long used) {
            this.max = max;
            this.used = used;
        }

        public StorageStatus() {
        }

        @JSON
        public long getUsed() {
            return used;
        }

        public void setUsed(long used) {
            this.used = used;
        }

        @JSON
        public long getMax() {
            return max;
        }

        public void setMax(long max) {
            this.max = max;
        }
    }

}
