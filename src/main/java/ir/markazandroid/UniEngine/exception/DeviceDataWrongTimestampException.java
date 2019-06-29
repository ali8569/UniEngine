package ir.markazandroid.UniEngine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Ali on 6/17/2019.
 */

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class DeviceDataWrongTimestampException extends RuntimeException {
}
