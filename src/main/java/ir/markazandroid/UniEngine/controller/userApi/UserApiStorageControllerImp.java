package ir.markazandroid.UniEngine.controller.userApi;

import ir.markazandroid.UniEngine.controller.userApi.interfaces.UserApiStorageController;
import ir.markazandroid.UniEngine.object.EFile;
import ir.markazandroid.UniEngine.object.ResponseObject;
import ir.markazandroid.UniEngine.persistance.entity.UserEntity;
import ir.markazandroid.UniEngine.service.interfaces.StorageService;
import ir.markazandroid.UniEngine.util.MultipartFileSender;
import ir.markazandroid.UniEngine.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by Ali on 4/13/2019.
 */

@RestController
public class UserApiStorageControllerImp implements UserApiStorageController {

    private final static String USER_STORAGE_PREFIX="files";

    private final StorageService storageService;

    @Autowired
    public UserApiStorageControllerImp(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public StorageStatus getStatus(UserEntity userEntity) {
        return new StorageStatus(userEntity.getMaxCapacity(),
                storageService.getUsedSpace(userEntity,USER_STORAGE_PREFIX));
    }

    @Override
    public ArrayList<EFile> getFiles(UserEntity userEntity, HttpServletRequest request) {
        return storageService.getFiles(userEntity,USER_STORAGE_PREFIX).stream()
                .map(file -> {
                    EFile f = new EFile();
                    f.seteFileId(Utils.encodeString(storageService.generateFileId(file)));
                    f.setUrl(Utils.getEFileUrl(f,request));
                    f.setLastModified(file.lastModified());
                    return f;
                }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ResponseEntity uploadFile(UserEntity userEntity, MultipartFile file,HttpServletRequest request) throws IOException {
        long freeSpace = userEntity.getMaxCapacity()-storageService.getUsedSpace(userEntity,USER_STORAGE_PREFIX);
        if (freeSpace<file.getSize())
            return new ResponseObject.Builder().message("Not enough free space").timestamp(System.currentTimeMillis())
                    .status(HttpStatus.NOT_ACCEPTABLE.value()).build();

        java.io.File fi =storageService.saveToStorage(userEntity,USER_STORAGE_PREFIX,file.getOriginalFilename(),file.getInputStream(), StorageService.WriteType.WRITE_TYPE_ADD_NUMBER);

        EFile f = new EFile();
        f.seteFileId(Utils.encodeString(storageService.generateFileId(fi)));
        f.setUrl(Utils.getEFileUrl(f,request));
        f.setLastModified(fi.lastModified());

        return ResponseEntity.ok(f);

    }

    @Override
    public ResponseEntity<ResponseObject> deleteFile(UserEntity userEntity, String encodedFileId) {
        storageService.deleteFile(Utils.decodeString(encodedFileId));
        return new ResponseObject.Builder().status(200).message("File deleted successfully").timestamp(System.currentTimeMillis()).build();
    }

}
