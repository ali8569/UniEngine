package ir.markazandroid.UniEngine.exception;

import java.io.Serializable;

/**
 * Created by Ali on 3/14/2018.
 */
public class NotVerifiedException extends RuntimeException {
    private Serializable entity;

    public NotVerifiedException(Serializable entity) {
        this.entity = entity;
    }

    public NotVerifiedException() {
    }

    public Serializable getEntity() {
        return entity;
    }
}
