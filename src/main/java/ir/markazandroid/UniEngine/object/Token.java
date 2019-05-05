package ir.markazandroid.UniEngine.object;

import java.util.Objects;

/**
 * Created by Ali on 31/10/2017.
 */
public class Token {

    private String token;
    private long time;


    public Token(String token, long time) {
        this.token = token;
        this.time = time;
    }

    public String getToken() {
        return token;
    }

    public long getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token1 = (Token) o;
        return time == token1.time &&
                Objects.equals(token, token1.token);
    }

    @Override
    public int hashCode() {

        return Objects.hash(token, time);
    }
}
