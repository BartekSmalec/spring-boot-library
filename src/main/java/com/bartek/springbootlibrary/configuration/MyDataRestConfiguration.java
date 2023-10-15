package com.bartek.springbootlibrary.configuration;

import com.bartek.springbootlibrary.entity.Book;
import com.bartek.springbootlibrary.entity.History;
import com.bartek.springbootlibrary.entity.Message;
import com.bartek.springbootlibrary.entity.Review;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfiguration implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        String allowedOrigins = "http://localhost:3000";

        HttpMethod[] theUnsupportedMethods = {HttpMethod.DELETE, HttpMethod.PATCH, HttpMethod.PUT, HttpMethod.POST};

        config.exposeIdsFor(Book.class);
        config.exposeIdsFor(Review.class);
        config.exposeIdsFor(History.class);
        config.exposeIdsFor(Message.class);

        disableHttpMethods(Book.class, config, theUnsupportedMethods);
        disableHttpMethods(Review.class, config, theUnsupportedMethods);
        disableHttpMethods(History.class, config, theUnsupportedMethods);
        disableHttpMethods(Message.class, config, theUnsupportedMethods);

        /* Configure CORS mapping */

        cors.addMapping(config.getBasePath() + "/**")
                .allowedOrigins(allowedOrigins);
    }

    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedMethods) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure(((metdata, httpMethods) ->
                        httpMethods.disable(theUnsupportedMethods)))
                .withCollectionExposure(((metdata, httpMethods) ->
                        httpMethods.disable(theUnsupportedMethods)));
    }
}
