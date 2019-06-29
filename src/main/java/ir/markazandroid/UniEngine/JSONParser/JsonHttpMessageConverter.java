package ir.markazandroid.UniEngine.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Ali on 01/11/2017.
 */
public class JsonHttpMessageConverter extends Parser implements HttpMessageConverter {

    private ArrayList<MediaType> supportedMediaTypes;

    public JsonHttpMessageConverter() {
        supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(new MediaType("application", "*+json"));
    }

    @Override
    public boolean canRead(Class clazz, MediaType mediaType) {
        return (clazz.getSimpleName().equals("Collection")
                || clazz.getSimpleName().equals("List")
                || clazz.getSimpleName().equals("LinkedHashMap")
                || clazz.getSimpleName().equals("HashMap")
                || clazz.getSimpleName().equals("JSONObject")
                || clazz.getSimpleName().equals("ArrayList")
                || classes.containsKey(clazz.getName()))
                && mediaType != null
                && mediaType.includes(MediaType.APPLICATION_JSON_UTF8);
    }

    @Override
    public boolean canWrite(Class clazz, MediaType mediaType) {
        return mediaType == null
                && clazz.getSimpleName().equals("LinkedHashMap")
                || (clazz.getSimpleName().equals("Collection")
                || clazz.getSimpleName().equals("List")
                || clazz.getSimpleName().equals("HashMap")
                || clazz.getSimpleName().equals("JSONObject")
                || clazz.getSimpleName().equals("LinkedHashMap")
                || clazz.getSimpleName().equals("ArrayList")
                || clazz.equals(JsonProfile.class)
                || classes.containsKey(clazz.getName()))
                && mediaType != null
                && mediaType.includes(MediaType.APPLICATION_JSON_UTF8);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return supportedMediaTypes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object read(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputMessage.getBody()));
        StringBuilder stringBuilder = new StringBuilder();
        String s;
        while ((s = reader.readLine()) != null) {
            stringBuilder.append(s);
        }
        try {
            if (stringBuilder.charAt(0) == '{') {
                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                if (clazz.equals(JSONObject.class)) return jsonObject;
                return get(clazz, jsonObject);
            } else {
                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                return get(clazz, jsonArray);
            }
        } catch (JSONException e) {
            throw new HttpMessageNotReadableException("cannot pars to json", e,inputMessage);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void write(Object o, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        String s;
        if (o instanceof Map) s = new JSONObject((Map) o).toString();
        else if (o instanceof JSONObject) s = o.toString();
        else if (o instanceof Collection) s = getArray((Collection) o).toString();
        else if (o instanceof JsonProfile && ((JsonProfile) o).getEntity() instanceof Collection) s = getArray((JsonProfile) o).toString();
        else s = get(o).toString();
        outputMessage.getHeaders().add(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE);
        outputMessage.getBody().write(s.getBytes(StandardCharsets.UTF_8));
    }




}
