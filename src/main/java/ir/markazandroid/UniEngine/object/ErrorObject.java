package ir.markazandroid.UniEngine.object;


import ir.markazandroid.UniEngine.JSONParser.annotations.JSON;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ali on 28/11/2017.
 */
@JSON
public class ErrorObject implements Serializable {

    private int status;
    private String message;
    private long timestamp;
    private ArrayList<FieldError> errors;


    public ErrorObject() {
    }

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

    @JSON(classType = "array", clazz = FieldError.class)
    public ArrayList<FieldError> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<FieldError> errors) {
        this.errors = errors;
    }
}
