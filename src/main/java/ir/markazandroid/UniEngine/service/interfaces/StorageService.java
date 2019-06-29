package ir.markazandroid.UniEngine.service.interfaces;

import ir.markazandroid.UniEngine.object.PrivateStorageOwner;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * Created by Ali on 4/13/2019.
 */
public interface StorageService {

    /**
     * reads from inputStream and writes to private storage.
     * @param owner the owner of private storage
     * @param prefix folder prefix (can be null)
     * @param fileName file name to save
     * @param dataStream file stream
     * @param type write type
     * @return the saved file access id (never null)
     */
    @PreAuthorize(WRITE_WITH_PSO)
    File saveToStorage(PrivateStorageOwner owner, String prefix, String fileName, InputStream dataStream, WriteType type) throws IOException;

    @PreAuthorize(READ_WITH_PSO)
    Collection<File> getFiles(PrivateStorageOwner owner, String prefix) ;

    @PreAuthorize(READ_WITH_PSO)
    long getUsedSpace(PrivateStorageOwner owner, String prefix) ;

    @PreAuthorize(WRITE_WITH_ID)
    void deleteFile(String fileId);

    @PreAuthorize(WRITE_WITH_ID)
    String moveFile(PrivateStorageOwner owner,String fileId, String newPrefix, String newName) throws IOException;

    //TODO
    //@PreAuthorize(READ_WITH_ID)
    File readFile(String fileId);

    String generateFileId(File file);

    enum WriteType{
        WRITE_TYPE_OVER_WRITE,
        WRITE_TYPE_IGNORE,
        WRITE_TYPE_ADD_NUMBER
    }

    String READ_WITH_ID="hasPermission(#fileId,T(ir.markazandroid.UniEngine.security.privilege.MethodPermissionEvaluator).PERMISSION_READ_STORAGE)";
    String WRITE_WITH_PSO="hasPermission(#owner.storagePrefix,T(ir.markazandroid.UniEngine.security.privilege.MethodPermissionEvaluator).PERMISSION_WRITE_STORAGE)";
    String READ_WITH_PSO="hasPermission(#owner.storagePrefix,T(ir.markazandroid.UniEngine.security.privilege.MethodPermissionEvaluator).PERMISSION_READ_STORAGE)";
    String WRITE_WITH_ID="hasPermission(#fileId,T(ir.markazandroid.UniEngine.security.privilege.MethodPermissionEvaluator).PERMISSION_WRITE_STORAGE)";

    static String addSlashesToPath(String s){
        StringBuilder builder = new StringBuilder(s);
        if (!s.startsWith("/"))
            builder.insert(0,"/");
        if (!s.endsWith("/"))
            builder.append("/");
        return builder.toString();
    }

    static String removeSlashesFromPath(String s){
        StringBuilder builder = new StringBuilder(s);
        if (s.startsWith("/"))
            builder.deleteCharAt(0);
        if (s.endsWith("/"))
            builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }
}
