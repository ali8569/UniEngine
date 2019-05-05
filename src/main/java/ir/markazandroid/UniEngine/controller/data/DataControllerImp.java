package ir.markazandroid.UniEngine.controller.data;

import ir.markazandroid.UniEngine.controller.data.interfaces.DataController;
import ir.markazandroid.UniEngine.exception.NotFoundException;
import ir.markazandroid.UniEngine.service.interfaces.StorageService;
import ir.markazandroid.UniEngine.util.MultipartFileSender;
import ir.markazandroid.UniEngine.util.Utils;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * Created by Ali on 4/15/2019.
 */
@Controller
public class DataControllerImp implements DataController {

    private final StorageService storageService;

    public DataControllerImp(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public void readFile(String encodedFileId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileId;
        if (encodedFileId!=null)  fileId= Utils.decodeString(encodedFileId);
        else
            fileId=request.getServletPath().replaceFirst("/data/read/","");

        System.out.println(fileId);
        File file = storageService.readFile(fileId);
        if (file==null) throw new NotFoundException();
        MultipartFileSender.fromFile(file).with(request).with(response).serveResource();
    }
}
