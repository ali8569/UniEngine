package ir.markazandroid.UniEngine.util;

/**
 * Created by Ali on 3/13/2018.
 */
public class ThymeleafUtil {

    public static String resolvePartName(String part){
        switch (part){
            case "icon": return "manage_logo";
            case "slideshow": return "manage_pic";
            case "sound": return "manage_music";
            case "subtitle": return "manage_subtitle";
            case "video": return "manage_film";
            case "options": return "settings";
            case "extras": return "special_features";
            case "content1": return "content1";
            case "content2": return "content2";
            case "content3": return "content3";
            default: return "";
        }
    }

    public static String divideStringWithComma(int toDivide,int max){
        return divideStringWithComma(toDivide+"",max);
    }

    public static String divideStringWithComma(long toDivide,int max){
        return divideStringWithComma(toDivide+"",max);
    }

    public static String divideStringWithComma(String toDivide,int max){
        StringBuilder builder = new StringBuilder(toDivide);
        for (int i = 1; i < toDivide.length(); i++) {
            if (i%max==0)
                builder.insert(toDivide.length()-i,',');
        }
        return builder.toString();
    }
}
