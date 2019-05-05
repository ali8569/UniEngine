package ir.markazandroid.UniEngine.persistance;


import ir.markazandroid.UniEngine.object.Token;
import ir.markazandroid.UniEngine.persistance.interfaces.TokenRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.PreDestroy;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Ali on 31/10/2017.
 */
@Repository
@EnableScheduling
public class TokenRepositoryImp implements TokenRepository {


    private final long removeTime = 20 * 60 * 1000L;
    private ConcurrentHashMap<String, Token> tokenMap;

    @SuppressWarnings("unchecked")
    public TokenRepositoryImp() {
        tokenMap = new ConcurrentHashMap();
    }

    @Override
    public Token getTokenByUsername(String username) {
        return tokenMap.get(username);
    }

    @Override
    public void add(String username, Token token) {
        tokenMap.put(username, token);
    }

    @Override
    public void remove(String username) {
        tokenMap.remove(username);
    }

    @Scheduled(fixedDelay = removeTime, initialDelay = removeTime)
    protected void removeExpiredTokens() {
        final long currentTime = System.currentTimeMillis();
        tokenMap.forEach((s, token) -> {
            if (currentTime - token.getTime() >= removeTime) remove(s);
        });
    }

    @PreDestroy
    public void destroy() {
        tokenMap.clear();
    }

}
