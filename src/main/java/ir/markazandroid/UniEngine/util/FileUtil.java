package ir.markazandroid.UniEngine.util;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.stream.FileImageOutputStream;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali on 12/04/2017.
 */
public class FileUtil {

    public static final String resDir = "/var/www/data/advertiser";


    //TODO
    //public static final String resDir= "C:\\Users\\Ali\\Documents\\web_test\\advertiser";

    //public static final String resDir= "C:\\nginx-1.14.0\\html";

    //TODO
    //public static final String url = "http://192.168.45.44";

    public static final String url = "http://data.harajgram.ir/advertiser";

    public static String writeToImageFile(String folder, InputStream uploadedInputStream, String filename) throws IOException {

        String uploadedFileLocation = resDir + "/" + folder;
        File dir = new File(uploadedFileLocation);
        dir.mkdirs();
        File file = new File(resDir + "/" + folder + "/" + filename + "." + "jpg");

        int count = 0;
        while (file.exists()) {
            count++;
            uploadedFileLocation = resDir + "/" + folder + "/" + filename + "_" + count + "." + "jpg";
            file = new File(uploadedFileLocation);
        }

        int read;
        int total = 0;
        byte[] bytes = new byte[1024];
        FileImageOutputStream out = new FileImageOutputStream(file);
        while ((read = uploadedInputStream.read(bytes)) != -1) {
            total += read;
            /*if (total > 2000 * 1024) {
                out.close();
                file.delete();
             //   throw new FileSizeLimitExepction();
            }*/
            out.write(bytes, 0, read);
        }
        out.flush();
        out.close();
        if (total == 0) {
            file.delete();
            return null;
        }
        return url + "/" + folder + "/" + filename + "." + "jpg";

    }

    public static String writeToFile(String folder, InputStream uploadedInputStream, String filename) throws IOException {

        String uploadedFileLocation = resDir + "/" + folder;
        File dir = new File(uploadedFileLocation);
        dir.mkdirs();
        File file = new File(resDir + "/" + folder + "/" + filename);
        /*String name= filename.substring(0,filename.lastIndexOf("."));
        String type= filename.substring(filename.lastIndexOf("."));
        int count = 0;
        while (file.exists()) {
            count++;
            uploadedFileLocation = resDir + "/" + folder + "/" + name + "_" + count + type;
            file = new EFile(uploadedFileLocation);
        }*/

        int read;
        int total = 0;
        byte[] bytes = new byte[1024];
        FileOutputStream out = new FileOutputStream(file);
        while ((read = uploadedInputStream.read(bytes)) != -1) {
            total += read;
            /*if (total > 2000 * 1024) {
                out.close();
                file.delete();
                //   throw new FileSizeLimitExepction();
            }*/
            out.write(bytes, 0, read);
        }
        out.flush();
        out.close();
        if (total == 0) {
            file.delete();
            return null;
        }
        return url + "/" + folder + "/" + filename;

    }

    public synchronized static String writeToTextFile(String html, String filename, String fileType) throws IOException {
      //  if (html.length() > 50 * 1024) throw new FileSizeLimitExepction();
        String uploadedFileLocation = resDir + filename + '.' + fileType;
        File file = new File(uploadedFileLocation);
        int count = 0;

        while (file.exists()) {
            count++;
            uploadedFileLocation = resDir + filename + "_" + count + "." + fileType;
            file = new File(uploadedFileLocation);
        }
        OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8"));
        BufferedWriter writer = new BufferedWriter(w);
        //   BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(html);
        writer.flush();
        writer.close();
        return url + (count == 0 ? filename + '.' + fileType : filename + "_" + count + "." + fileType);

    }

    public static void deleteFile(String folder,String filename){
        File file = new File(resDir + "/" + folder + "/" + filename);
        try {
            FileUtils.forceDelete(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFolder(String folder){
        File file = new File(resDir + "/" + folder +"/");
        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> mergeFiles(ArrayList<String> existing, List<MultipartFile> inputs, String folder) throws IOException {
        ArrayList<String> newList = new ArrayList<>();
        if (inputs!=null) {
            for (int i=0;i<inputs.size();i++) {
                MultipartFile inputFile=inputs.get(i);
                if (notEmpty(inputFile)) {

                    //have a file change? delete the present
                    if (exists(existing, i)) {
                        deleteFile(folder, existing.get(i).substring(existing.get(i).lastIndexOf('/') + 1));
                    }
                    String fileUrl = FileUtil.writeToFile(folder,
                            inputFile.getInputStream(),
                            inputFile.getOriginalFilename());

                    newList.add(fileUrl);
                }
                else {
                    if (exists(existing, i)) {
                        newList.add(existing.get(i));
                    }
                }
            }
            // delete the remains
            if (newList.size() < existing.size()) {
                for (int i = newList.size(); i < existing.size(); i++) {
                    FileUtil.deleteFile(folder, existing.get(i).substring(existing.get(i).lastIndexOf('/') + 1));
                }
            }
        }
        else {
            FileUtil.deleteFolder(folder);
        }
        return newList;
    }

    public static boolean notEmpty(MultipartFile multipartFile){
        return multipartFile != null && !multipartFile.isEmpty();
    }

    public static boolean exists(ArrayList array,int index){
        return array.size()>index;
    }
}
