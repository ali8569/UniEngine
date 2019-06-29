package ir.markazandroid.UniEngine.object;

import ir.markazandroid.UniEngine.JSONParser.annotations.JSON;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Ali on 11/30/2018.
 */
@JSON
public class ResponseObject implements Serializable {

    private int status;
    private String message;
    private long timestamp;


    @JSON
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @JSON
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JSON
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseObject)) return false;
        ResponseObject that = (ResponseObject) o;
        return status == that.status &&
                timestamp == that.timestamp &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, message, timestamp);
    }

    public static class Builder{
        private ResponseObject responseObject;
        private Map<String, Object> map;

        public Builder(){
            responseObject=new ResponseObject();
            map = new HashMap<>();
        }

        public Builder status(int status){
            responseObject.setStatus(status);
            return this;
        }

        public Builder message(String message){
            responseObject.setMessage(message);
            return this;
        }

        public Builder timestamp(long timestamp){
            responseObject.setTimestamp(timestamp);
            return this;
        }

        public Builder param(String key, Object value) {
            map.put(key, value);
            return this;
        }

        public ResponseEntity build() {
            return ResponseEntity.status(responseObject.getStatus())
                    .body(map.isEmpty() ? responseObject : map);
        }
    }
}
