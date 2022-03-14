package config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ApplicationInitializer extends
        AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/api/*" };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { MvcConfig.class };
    }

//    protected Class<?>[] getServletConfigClasses() {
//        return new Class[] { DbConfig.class };
//    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {};
    }

}
