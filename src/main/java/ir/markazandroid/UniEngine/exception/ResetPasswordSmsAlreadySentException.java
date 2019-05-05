package ir.markazandroid.UniEngine.exception;

/**
 * Created by Ali on 3/14/2018.
 */
public class ResetPasswordSmsAlreadySentException extends RuntimeException {

    private long remainTime;

    public ResetPasswordSmsAlreadySentException(long remainTime) {
        this.remainTime = remainTime;
    }

    public long getRemainTime() {
        return remainTime;
    }
}
