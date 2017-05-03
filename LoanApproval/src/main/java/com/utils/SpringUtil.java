package com.utils;

//import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SpringUtil implements ApplicationContextAware {
	
	static ApplicationContext appContext;

    public static ApplicationContext getApplicationContext() {
        return appContext;
    }
    

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        appContext =  context;
    }    
    
    /**
     * Returns the HTTP Servlet Request associated with the current thread.
     * @return The HTTP request if it is available. If the request is not available
     * e.g. when triggered from a deadline, null is returned.
     */
    /*
    public static HttpServletRequest getHttpServletRequest() {
        try {

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            return request;
        } catch (NoClassDefFoundError e) {
            // ignore if servlet request is not available, e.g. when triggered from a deadline
            return null;
        }

    }    
    */
}
