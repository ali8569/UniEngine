package ir.markazandroid.UniEngine.service;

import ir.markazandroid.UniEngine.object.PrivateStorageOwner;
import ir.markazandroid.UniEngine.service.interfaces.StorageService;
import org.apache.commons.io.FileUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;

import static ir.markazandroid.UniEngine.util.FilePath.isWindows;

/**
 * Created by Ali on 4/13/2019.
 */
@Service
public class StorageServiceImp implements StorageService {

    private static final String privateResDir = "/opt/tomcat/appData/UniEngine/";
    private static final String winPrivateResDir= "C:/Users/Ali/Documents/web_test/UniEngine/private/";

    private File baseResFolder;
    private String baseResFolderPath;

    public StorageServiceImp(){
        baseResFolderPath=isWindows()?winPrivateResDir:privateResDir;
        baseResFolder=new File(baseResFolderPath);
    }


    @Override
    public File saveToStorage(PrivateStorageOwner owner, String prefix, String fileName, InputStream dataStream, WriteType type) throws IOException {
        if (prefix==null || prefix.isEmpty()) prefix="/";
        File toSave = new File(createFolder(owner, prefix),fileName);
        if (toSave.exists()){
            if (WriteType.WRITE_TYPE_IGNORE.equals(type))
                return toSave;
            else if (WriteType.WRITE_TYPE_ADD_NUMBER.equals(type)){
                String name= fileName.substring(0,fileName.lastIndexOf("."));
                String fileType= fileName.substring(fileName.lastIndexOf("."));
                int count = 0;
                while (toSave.exists()) {
                    count++;
                    toSave = new File(createFolder(owner, prefix), name + "_" + count + fileType);
                }
            }
        }
        FileUtils.copyInputStreamToFile(dataStream,toSave);
        return toSave;
    }

    @Override
    public Collection<File> getFiles(PrivateStorageOwner owner, String prefix) {
        return FileUtils.listFiles(createFolder(owner, prefix),null,false);
    }

    @Override
    public long getUsedSpace(PrivateStorageOwner owner, String prefix) {
        return FileUtils.sizeOfDirectory(createFolder(owner, prefix));
    }

    @Override
    public void deleteFile(String fileId) {
        FileUtils.deleteQuietly(getFileFromFileId(fileId));
    }

    @Override
    public String moveFile(PrivateStorageOwner owner,String fileId, String newPrefix, String newName) throws IOException {
        File newFile =new File(createFolder(owner,newPrefix),newName);
        FileUtils.moveFile(getFileFromFileId(fileId),newFile);
        return generateFileId(newFile);
    }

    @Override
    public File readFile(String fileId) {
        File file = getFileFromFileId(fileId);
        return file.exists()?file:null;
    }


    private File createFolder(PrivateStorageOwner owner,String prefix){
        if (prefix==null || prefix.isEmpty()) prefix="/";
        File directory = new File(baseResFolderPath+ StorageService.removeSlashesFromPath(owner.getStoragePrefix())+ StorageService.addSlashesToPath(prefix));
        directory.mkdirs();
        return directory;
    }


    @Override
    public String generateFileId(File file) {
        String path = file.getPath();
        path=path.replace("\\","/");
        return path.substring(baseResFolderPath.length());
    }

    private File getFileFromFileId(String fileId) {
       return new File(baseResFolderPath+fileId);
    }
}
