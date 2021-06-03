package com.in28minutes.restfulWebservices.controllers;

import com.in28minutes.restfulWebservices.models.HelloWorldBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloWorldController {
    //private MessageSource messageSource;
    private ResourceBundleMessageSource resourceBundleMessageSource;

    /*
    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
     */

    @Autowired
    public void setResourceBundleMessageSource(ResourceBundleMessageSource resourceBundleMessageSource) {
        this.resourceBundleMessageSource = resourceBundleMessageSource;
    }

    //@RequestMapping(method = RequestMethod.GET, path = "/hello-world")
    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    //hello-world-bean
    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World");
    }

    //hello-world/path-variable/in28minutes
    @GetMapping(path = "/hello-world/path-variable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }

    /*
    @GetMapping(path = "/hello-world-internationalized")
    //public String helloWorldInternationalized(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
    public String helloWorldInternationalized() {
        return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
    }
     */

    /*
    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized() {
        return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
    }
     */

    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized() {
        return resourceBundleMessageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
    }
}
