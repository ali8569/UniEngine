package ir.markazandroid.UniEngine.persistance.interfaces;


import ir.markazandroid.UniEngine.object.Token;

/**
 * Created by Ali on 31/10/2017.
 */
public interface TokenRepository {

    Token getTokenByUsername(String username);

    void add(String username, Token token);

    void remove(String username);

}
