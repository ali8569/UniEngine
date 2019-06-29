package ir.markazandroid.UniEngine.conf.session;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        MapSessionRepository repository = new MapSessionRepository(sessionMap);
        repository.setDefaultMaxInactiveInterval(30 * 60_000);
        return repository;
    }

    private SessionMap sessionMap;

    public SessionManager(ApplicationEventPublisher eventPublisher) {
        sessionMap = new SessionMap(eventPublisher);
    }

    @Scheduled(fixedRate = 15 * 60_000L)
    public void removeExpiredSessions() {
        sessionMap.forEach(1, (key, session) -> {
            if (session.isExpired()) sessionMap.remove(key);
        });
    }

    public synchronized <P> void updatePrincipals(PrincipalChooser<P> chooser, PrincipalUpdateAction<P> action) {
        sessionMap.forEachValue(1, session -> {
            try {
                P principal = (P) getPrincipal(session);
                if (principal == null) return null;
                if (chooser.accept(principal)) return principal;
            } catch (ClassCastException ignored) {}
            return null;
        }, p -> {
            if (p != null)
                action.update(p);
        });
    }

    @SuppressWarnings("unchecked")
    public <T extends MainPrincipalObject> T getMainPrincipal(MainPrincipalObject identifier) {
        return (T) sessionMap.getPrincipals().get(identifier.getUniqueKey());
    }

    public <T extends MainPrincipalObject> List<T> getMainPrincipals(Class<T> clazz, Predicate<T> predicate) {
        return sessionMap.getPrincipals()
                .values()
                .parallelStream()
                .filter((Predicate<MainPrincipalObject>) clazz::isInstance)
                .map((Function<MainPrincipalObject, T>) mainPrincipalObject -> (T) mainPrincipalObject)
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public static Object extractPrincipal(Session session) {
        if (session == null) return null;
        SecurityContext securityContext = session.getAttribute("SPRING_SECURITY_CONTEXT");
        if (securityContext == null || securityContext.getAuthentication() == null) return null;
        return securityContext.getAuthentication().getPrincipal();
    }

    public static Object getPrincipal(Session session) {
        return session.getAttribute("OBJECT");
    }

    public Object getPrincipal(HttpSession session) {
        //System.out.println("foundId=" + session.getId());
        Session ses = sessionMap.get(session.getId());
        return ses.getAttribute("OBJECT");
    }

    public static void setPrincipal(Object principal, Session session) {
        session.setAttribute("OBJECT", principal);
    }
}

//653
