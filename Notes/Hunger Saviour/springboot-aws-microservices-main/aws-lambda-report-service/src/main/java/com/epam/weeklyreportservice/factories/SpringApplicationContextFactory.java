package com.epam.weeklyreportservice.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringApplicationContextFactory {

    @Autowired
    private ApplicationContext context;

    public static ApplicationContext getApplicationContext(){
        SpringApplicationContextFactory factory = new SpringApplicationContextFactory();
        return factory.context;
    }

}
