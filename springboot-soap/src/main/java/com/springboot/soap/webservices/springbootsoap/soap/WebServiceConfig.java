package com.springboot.soap.webservices.springbootsoap.soap;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

// Enable Spring Webservices
@EnableWs
// Spring Configuration
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

  // MessageDispatcherServlet
  // ApplicationContext
  // url -> /ws/*

  // map uri to servlet

  @Bean
  public ServletRegistrationBean nessageDispatcherServlet(ApplicationContext context) {
    MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
    messageDispatcherServlet.setApplicationContext(context);
    messageDispatcherServlet.setTransformWsdlLocations(true);
    ;
    return new ServletRegistrationBean(messageDispatcherServlet, "/ws/*");
  }

  // ws/courses.wsdl
  // PortType - CoursePort
  // Namespace - http://soabwebservives.com/courses
  // course-details.xsd
  @Bean(name = "courses")
  public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema coursesSchema) {
    DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
    definition.setPortTypeName("CoursePort");
    definition.setTargetNamespace("http://soabwebservives.com/courses");
    definition.setLocationUri("/ws");
    definition.setSchema(coursesSchema);
    return definition;
  }

  // Spring creates this bean and autowires it in defaultWsdl11Definition above
  @Bean
  public XsdSchema coursesSchema() {
    return new SimpleXsdSchema(new ClassPathResource("course-details.xsd"));
  }

  // XwsSecurityInterceptor
  // Callback Handler -> SimplePassWordValidationCallbackHandler
  // SecurityPolicy -> securityPolicy.xml
  // add to Spring WS Interceptors

  // XwsSecurityInterceptor
  @Bean
  public XwsSecurityInterceptor securityInterceptor() {
    XwsSecurityInterceptor securityInterceptor = new XwsSecurityInterceptor();
    // Callback Handler -> SimplePasswordValidationCallbackHandler
    securityInterceptor.setCallbackHandler(callbackHandler());
    // Security Policy -> securityPolicy.xml
    securityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
    return securityInterceptor;
  }

  @Bean
  public SimplePasswordValidationCallbackHandler callbackHandler() {
    SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
    handler.setUsersMap(Collections.singletonMap("tjaeger", "abc"));
    return handler;
  }

  // Interceptors.add -> XwsSecurityInterceptor
  // overwritten from WsConfigurerAdapter
  @Override
  public void addInterceptors(List<EndpointInterceptor> interceptors) {
    interceptors.add(securityInterceptor());
  }

}
