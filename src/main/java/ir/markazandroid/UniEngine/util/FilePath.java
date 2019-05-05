package ir.markazandroid.UniEngine.util;

/**
 * Created by Ali on 4/19/2018.
 */
public class FilePath {
    private String fileName;
    private String folderName;
    private String dir;

    private FilePath(String folderName, String fileName, String resDir) {
        this.fileName = fileName;
        this.folderName = folderName;
        dir=resDir;
    }


    public String getFileName() {
        return fileName;
    }


    public String getFolderFullPath() {
        return dir+"/"+folderName;
    }

    public String getFileFullPath() {
        return dir+"/"+folderName+"/"+fileName;
    }

    public String getFolderName() {
        return folderName;
    }


    public static boolean isWindows(){
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
}
