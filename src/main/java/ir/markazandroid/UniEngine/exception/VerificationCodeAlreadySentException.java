package ir.markazandroid.UniEngine.exception;

/**
 * Created by Ali on 3/14/2018.
 */
public class VerificationCodeAlreadySentException extends RuntimeException {

    private long remainTime;

    public VerificationCodeAlreadySentException(long remainTime) {
        this.remainTime = remainTime;
    }

    public long getRemainTime() {
        return remainTime;
    }
}
