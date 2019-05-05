package ir.markazandroid.UniEngine.exception;

import java.io.Serializable;

/**
 * Created by Ali on 3/14/2018.
 */
public class NotAssignedException extends RuntimeException {
    private Serializable entity;

    public NotAssignedException(Serializable entity) {
        this.entity = entity;
    }

    public NotAssignedException() {
    }

    public Serializable getEntity() {
        return entity;
    }
}
