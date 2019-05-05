package ir.markazandroid.UniEngine.exception;

import java.io.Serializable;

/**
 * Created by Ali on 3/14/2018.
 */
public class PhoneNameExistsException extends RuntimeException {
    private Serializable entity;

    public PhoneNameExistsException(Serializable entity) {
        this.entity = entity;
    }

    public PhoneNameExistsException() {
    }

    public Serializable getEntity() {
        return entity;
    }
}
