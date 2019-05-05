/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.markazandroid.UniEngine.sms.exceptions;

/**
 *
 * @author mohsen
 */
public class HttpException extends BaseException {

    private final int code;

    public HttpException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
