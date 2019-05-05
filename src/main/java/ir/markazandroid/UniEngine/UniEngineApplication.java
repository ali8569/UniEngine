package ir.markazandroid.UniEngine;

import ir.markazandroid.UniEngine.JSONParser.JsonHttpMessageConverter;
import ir.markazandroid.UniEngine.JSONParser.Parser;
import ir.markazandroid.UniEngine.JSONParser.ParserInitializr;
import ir.markazandroid.UniEngine.controller.userApi.interfaces.UserApiStorageController;
import ir.markazandroid.UniEngine.notification.NotificationSender;
import ir.markazandroid.UniEngine.notification.NotificationSenderImp;
import ir.markazandroid.UniEngine.object.*;
import ir.markazandroid.UniEngine.persistance.entity.PlayListEntity;
import ir.markazandroid.UniEngine.persistance.entity.PrivilegeEntity;
import ir.markazandroid.UniEngine.persistance.entity.RoleEntity;
import ir.markazandroid.UniEngine.persistance.entity.UserEntity;
import ir.markazandroid.UniEngine.sms.KavenegarApi;
import okhttp3.OkHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.sql.DataSource;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@EnableCaching
@EnableAsync
@SpringBootApplication
public class UniEngineApplication extends SpringBootServletInitializer {

    public static final RoleEntity ROLE_USER=new RoleEntity("USER",new PrivilegeEntity(PrivilegeEntity.ACCESS_USER_DASHBOARD));
    public static final RoleEntity ROLE_ADMIN=new RoleEntity("ADMIN",new PrivilegeEntity(PrivilegeEntity.ACCESS_ADMIN_DASHBOARD));
	public static final RoleEntity ROLE_PL_AGENT=new RoleEntity("PL_AGENT",new PrivilegeEntity(PrivilegeEntity.ACCESS_PL_SERVICES)
            ,new PrivilegeEntity(PrivilegeEntity.ACCESS_ADMIN_DASHBOARD));

	public static void main(String[] args) {
		SpringApplication.run(UniEngineApplication.class, args).registerShutdownHook();
	}


	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JsonHttpMessageConverter parser() throws NoSuchMethodException {
		JsonHttpMessageConverter parser = new JsonHttpMessageConverter();
		ParserInitializr initializr = new ParserInitializr(parser);
		initializr.findAnnotatedClasses();
        return parser;
	}

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(UniEngineApplication.class);
    }

	@Bean
	public PersistentTokenRepository jdbcTokenRepository(DataSource dataSource) {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		//tokenRepository.setCreateTableOnStartup(true);
		return tokenRepository;
	}
	@Bean
	public TaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(10);
		executor.setThreadNamePrefix("default_task_executor_thread");
		executor.initialize();
		return executor;
	}

	@Bean
	public OkHttpClient httpClient(){
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.connectTimeout(60, TimeUnit.SECONDS)
				.readTimeout(60, TimeUnit.SECONDS)
				.writeTimeout(60, TimeUnit.SECONDS)
				.build();
		System.out.println("Http client created");
		return okHttpClient;
	}

	@Bean
	public NotificationSender notificationSender(OkHttpClient httpClient, Parser parser) throws NoSuchMethodException {
		return new NotificationSenderImp(httpClient,
				"AAAAPcDiPgc:APA91bFCuZcvUtQSuC21zEaEtroj0GjJS6ykeV4mGgfMXegGPag26h6HdyrlG4LyWo4mEGFh4spi8qnn7d89uVRaZP6EfJ4hqr7U00r0qGU8aMxwfU0AYX2UsSqPvS9BJzs8gN4mVSdG", parser);
	}

	@Bean
	public KavenegarApi kavenegarApi(OkHttpClient client){
		return new KavenegarApi(client,"68397A3470724E70384472775A782B4B4B684E78566B3434463835444F656C6B");
	}

	@Bean
	public SecureRandom getSecureRandom() throws NoSuchAlgorithmException {
		return SecureRandom.getInstanceStrong();
	}

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver slr = new CookieLocaleResolver();
		slr.setDefaultLocale(new Locale("fa", ""));
		return slr;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

    /*@Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher(){
            @Override
            public void sessionCreated(HttpSessionEvent event) {
                super.sessionCreated(event);
                            }

            @Override
            public void sessionDestroyed(HttpSessionEvent event) {
                super.sessionDestroyed(event); }
        };
    }*/

    @EventListener
    public void onEvent(Object o){
        System.out.println("Event: "+o.toString());
    }

    /*@Bean
    public HttpSessionListener httpSessionListener(){
	    return new HttpSessionListener() {
            @Override
            public void sessionCreated(HttpSessionEvent event) {
                SecurityContext imp= (SecurityContext) event.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
                System.out.println(event.getSession().getAttribute("created: "+imp.getAuthentication().getPrincipal().toString()));
            }

            @Override
            public void sessionDestroyed(HttpSessionEvent event) {
                SecurityContext imp= (SecurityContext) event.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
                System.out.println(event.getSession().getAttribute("destroyed : "+imp.getAuthentication().getPrincipal().toString()));

            }
        };
    }*/

}
