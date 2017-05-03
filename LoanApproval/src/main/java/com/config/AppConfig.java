package com.config;  
  
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * Application Configuration to read the applicationContext.xml
 * @author anupama
 *
 */

@ImportResource(value = {"WEB-INF/applicationContext.xml"})
@Configuration 
@ComponentScan("com") 
@EnableWebMvc   
public class AppConfig {  
}  
