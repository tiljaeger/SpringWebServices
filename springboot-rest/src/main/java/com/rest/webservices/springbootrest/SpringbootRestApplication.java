package com.rest.webservices.springbootrest;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@SpringBootApplication
public class SpringbootRestApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringbootRestApplication.class, args);
  }

  // for internationalization
  @Bean
  public LocaleResolver localeResolver() {
    AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
    localeResolver.setDefaultLocale(Locale.US);
    return localeResolver;
  }

  // for internationalization
//  @Bean
//  public ResourceBundleMessageSource messageSource() {
//    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//    messageSource.setBasename("messages");
//    return messageSource;
//  }
  
  // or use for internationalization application.properties: spring.messages.basename=messages
}
