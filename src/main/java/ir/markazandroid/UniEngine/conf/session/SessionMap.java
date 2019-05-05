package ir.markazandroid.UniEngine.conf.session;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.session.Session;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.session.events.SessionDestroyedEvent;
import org.springframework.session.events.SessionExpiredEvent;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Ali on 4/6/2019.
 */
public class SessionMap extends ConcurrentHashMap<String,Session> {

    private ApplicationEventPublisher eventPublisher;

    public SessionMap(ApplicationEventPublisher eventPublisher){
        this.eventPublisher=eventPublisher;
    }

    @Override
    public Session put(String key, Session value) {
        Session session= super.put(key, value);
        if (session==null){
            SessionCreatedEvent event = new SessionCreatedEvent(this,value);
            eventPublisher.publishEvent(event);
        }
        return session;
    }

    @Override
    public Session remove(Object key) {
        Session session= super.remove(key);
        if (session!=null) {
            SessionDestroyedEvent event;
            if (session.isExpired())
                event = new SessionExpiredEvent(this, session);
            else
                event = new SessionDeletedEvent(this, session);

            eventPublisher.publishEvent(event);
        }
        return session;
    }
}
