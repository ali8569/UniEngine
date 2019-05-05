package ir.markazandroid.UniEngine.conf.session;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

/**
 * Created by Ali on 4/6/2019.
 */
@Configuration
@EnableSpringHttpSession
@EnableScheduling
public class SessionManager {

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public MapSessionRepository sessionRepository() {
        MapSessionRepository repository =new MapSessionRepository(sessionMap);
        repository.setDefaultMaxInactiveInterval(30*60_000);
        return repository;
    }

    private SessionMap sessionMap;

    public SessionManager(ApplicationEventPublisher eventPublisher) {
        sessionMap=new SessionMap(eventPublisher);
    }

    @Scheduled(fixedRate = 15*60_000L)
    public void removeExpiredSessions(){
        sessionMap.forEach(1,(key,session) -> {
            if (session.isExpired()) sessionMap.remove(key);
        });
    }

    public synchronized <P> void updatePrincipals(PrincipalChooser<P> chooser,PrincipalUpdateAction<P> action){
        sessionMap.forEachValue(1, session -> {
            try {
                P principal = session.getAttribute("");
                if(chooser.accept(principal)) return principal;
            } catch (ClassCastException ignored) {}
            return null;
        }, action::update);
    }
}

//653
