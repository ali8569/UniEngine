package ir.markazandroid.UniEngine.conf.session;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.session.Session;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.session.events.SessionDestroyedEvent;
import org.springframework.session.events.SessionExpiredEvent;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by Ali on 4/6/2019.
 */
public class SessionMap extends ConcurrentHashMap<String,Session> {

    private ConcurrentHashMap<String, MainPrincipalObject> principals;

    private ApplicationEventPublisher eventPublisher;

    public SessionMap(ApplicationEventPublisher eventPublisher){
        this.eventPublisher=eventPublisher;
        principals = new ConcurrentHashMap<>();
    }

    @Override
    public Session put(String key, Session value) {
        Session session = get(key);
        if (session==null){
            Object p = SessionManager.extractPrincipal(value);
            if (p instanceof MainPrincipalObject) {
                MainPrincipalObject principal = principals.get(((MainPrincipalObject) p).getUniqueKey());
                if (principal != null) {
                    p = principal;
                } else {
                    principals.put(((MainPrincipalObject) p).getUniqueKey(), (MainPrincipalObject) p);
                }

                SessionManager.setPrincipal(p, value);
            }
            //System.out.println("mainId="+key);
            super.put(key, value);
            SessionCreatedEvent event = new SessionCreatedEvent(this,value);
            eventPublisher.publishEvent(event);
        }
        //System.out.println("mainId="+key);
        //System.out.println("mainSize="+size());
        return session;
    }

    @Override
    public Session remove(Object key) {
        Session session= super.remove(key);
        //System.out.println("removed: "+key);
        if (session!=null) {
            Object p = SessionManager.extractPrincipal(session);
            if (p instanceof MainPrincipalObject) {
                if (sessionsForMainPrincipalObjects((MainPrincipalObject) p).isEmpty())
                    principals.remove(((MainPrincipalObject) p).getUniqueKey());
            }
            SessionDestroyedEvent event;
            if (session.isExpired())
                event = new SessionExpiredEvent(this, session);
            else
                event = new SessionDeletedEvent(this, session);

            eventPublisher.publishEvent(event);
        }
        return session;
    }

    ConcurrentHashMap<String, ? extends MainPrincipalObject> getPrincipals() {
        return principals;
    }

    List<Session> sessionsForMainPrincipalObjects(MainPrincipalObject key) {
        return values().stream().filter(session -> {
            Object principal = SessionManager.getPrincipal(session);
            return principal instanceof MainPrincipalObject
                    && Objects.equals(key.getUniqueKey(), ((MainPrincipalObject) principal).getUniqueKey());
        }).collect(Collectors.toList());
    }
}
