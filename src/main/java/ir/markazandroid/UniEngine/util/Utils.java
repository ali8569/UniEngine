package ir.markazandroid.UniEngine.util;

import ir.markazandroid.UniEngine.JSONParser.annotations.JSON;
import ir.markazandroid.UniEngine.object.EFile;
import org.apache.commons.io.FileUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.util.*;

/**
 * Created by Ali on 28/11/2017.
 */
public class Utils {


    public static long roundUp(double value){
        double valueAbs = Math.floor(value);
        if (value>valueAbs) return (long) (value+1);
        else return (long) value;
    }

    public static String getTime(long timestamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        return
                String.format("%02d:%02d:%02d",calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        calendar.get(Calendar.SECOND));
    }

    public static String getTime(Time time){
        if (time==null) return "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return
                String.format("%02d:%02d",calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE));
    }

    public static boolean isOn(Date date){

        return System.currentTimeMillis()-date.getTime()<5*60*1000L;
    }

    public static String getSaltString(Random random,int length) {
        String SALTCHARS = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890";
        StringBuilder salt = new StringBuilder();
        while (salt.length() < length) {// length of the random string.
            int index = (int) (random.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();

    }

    public static String getGregorianDate(Date date){
        if (date==null) return "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.format("%04d-%d-%d",calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static ArrayList<Location> getDeviceLatestLocations(String phoneName,int maxCount) throws IOException {
        ArrayList<Location> locations = new ArrayList<>();
        File logsFolder = new File(FileUtil.url+"/devices/"+phoneName+"/logs");
        ArrayList<File> usedFiles=new ArrayList<>();

        while (locations.size()<maxCount){
            File log = getLatestFilefromDir(logsFolder,usedFiles);

            if (log==null) return locations;

            usedFiles.add(log);

            List<String> lines = FileUtils.readLines(log,"UTF-8");

            for (int i = lines.size()-1 ; i > -1; i--) {
                if (lines.size()==maxCount) return locations;
                String line = lines.get(i);
                Location location = extractLocationFromLine(line);
                locations.add(location);
            }
        }

        return locations;
    }

    private static Location extractLocationFromLine(String line){
        String locationString;
        try {
            locationString = line.split("__")[3];
            if (locationString.equals("NA")) return null;
            Map<String,String> values = getValues(locationString);
            return new Location(Double.parseDouble(values.get("lat"))
                    ,Double.parseDouble(values.get("lon"))
                    ,Long.parseLong(values.get("t")));
        }
        catch (IndexOutOfBoundsException e){
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static Map<String,String> getValues(String line){
        Map<String,String> map = new HashMap<>();
        StringBuilder name= new StringBuilder();
        StringBuilder value= new StringBuilder();
        boolean isReadingName=true;
        for(char c:line.toCharArray()){
            if (isReadingName){
                if (c=='=')
                    isReadingName=false;
                else{
                    name.append(c);
                }
            }
            else {
                if (Character.isDigit(c) || c=='.' || c=='-')
                    value.append(c);
                else {
                    map.put(name.toString(),value.toString());
                    name=new StringBuilder(c+"");
                    value=new StringBuilder();
                    isReadingName=true;
                }
            }
        }
        return map;
    }



    private static File getLatestFilefromDir(File dir,ArrayList<File> ignoreList){
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }

        File lastModifiedFile=null;

        int startIndex=0;

        for (int i = 0; i < files.length; i++) {
            if (!ignoreList.contains(files[i])){
                lastModifiedFile=files[i];
                startIndex=i;
                break;
            }
        }

        if (lastModifiedFile==null) return null;

        for (int i = startIndex+1; i < files.length; i++) {
            File currentFile =files[i];
            if (ignoreList.contains(currentFile)) continue;
            if (lastModifiedFile.lastModified() < currentFile.lastModified()) {
                lastModifiedFile = currentFile;
            }
        }
        return lastModifiedFile;
    }



    public static class Location implements Serializable{
        private double latitude;
        private double longitude;
        private long timestamp;

        Location(double latitude, double longitude, long timestamp) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.timestamp = timestamp;
        }

        @JSON
        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        @JSON
        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        @JSON
        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }

    public static class LocationArrayContainer implements Serializable{
        private ArrayList<Location> list;

        public LocationArrayContainer(ArrayList<Location> list) {
            this.list = list;
        }

        @JSON(classType = JSON.CLASS_TYPE_ARRAY,clazz = Location.class)
        public ArrayList<Location> getList() {
            return list;
        }

        public void setList(ArrayList<Location> list) {
            this.list = list;
        }
    }

    public static boolean versionGreaterThan(String version,int greater){
        try {
            return Integer.parseInt(version)>=greater;

        }catch (Exception ignored){
            return false;
        }
    }

    public static String encodeString(String string){
        return new String(Base64.getEncoder().encode(string.getBytes(StandardCharsets.UTF_8)));
    }

    public static String decodeString(String encodedString){
        return new String(Base64.getDecoder().decode(encodedString.getBytes(StandardCharsets.UTF_8)));
    }

    public static String getFileUrl(String fileId,String contextPath){
        return contextPath+"/data/read/"+fileId;
    }
    public static String getFileUrl(String fileId, HttpServletRequest request){
        return getFileUrl(fileId,request.getContextPath());
    }
    public static String getEFileUrl(EFile eFile, HttpServletRequest request){
        return getFileUrl(decodeString(eFile.geteFileId()),request);
    }
    public static void setEFileUrl(EFile eFile, HttpServletRequest request){
        eFile.setUrl(getEFileUrl(eFile,request));
    }

}
