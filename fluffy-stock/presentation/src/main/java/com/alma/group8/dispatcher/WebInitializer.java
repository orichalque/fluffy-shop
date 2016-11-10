package com.alma.group8.dispatcher;

import com.alma.group8.configuration.SpringConfiguration;
import com.alma.groupe8.configuration.SpringMongoConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Created by Thibault on 10/11/2016.
 * Launch the Spring Context. This class will be run automatically by the servlet container
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {SpringConfiguration.class, SpringMongoConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
