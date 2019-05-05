package ir.markazandroid.UniEngine.exception;

import java.io.Serializable;

/**
 * Created by Ali on 3/14/2018.
 */
public class PhoneCredentialsNotFoundException extends Exception {
    private Serializable entity;

    public PhoneCredentialsNotFoundException(Serializable entity) {
        this.entity = entity;
    }

    public PhoneCredentialsNotFoundException() {
    }

    public Serializable getEntity() {
        return entity;
    }
}
