package ir.markazandroid.UniEngine.conf;

import ir.markazandroid.UniEngine.JSONParser.JsonHttpMessageConverter;
import ir.markazandroid.UniEngine.JSONParser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Ali on 01/11/2017.
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JsonHttpMessageConverter parser;
    private final LocaleChangeInterceptor localeChangeInterceptor;

    @Autowired
    public WebConfig(JsonHttpMessageConverter parser,
                     LocaleChangeInterceptor localeChangeInterceptor) {
        this.parser = parser;
        this.localeChangeInterceptor = localeChangeInterceptor;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeAll(converters.stream().filter(httpMessageConverter -> {
                    if (httpMessageConverter==null || httpMessageConverter.getSupportedMediaTypes()==null
                            || httpMessageConverter instanceof Parser) return false;
            return httpMessageConverter.getSupportedMediaTypes().contains(MediaType.APPLICATION_JSON)
                    || httpMessageConverter.getSupportedMediaTypes().contains(MediaType.APPLICATION_JSON_UTF8);
        }).collect(Collectors.toList()));

        converters=null;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(localeChangeInterceptor);
    }

    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/data/read/**")
                .addResourceLocations("file:"+(isWindows()?winPrivateResDir+"\\":privateResDir+"/"))
                .setCacheControl(CacheControl.empty());
    }*/
}
