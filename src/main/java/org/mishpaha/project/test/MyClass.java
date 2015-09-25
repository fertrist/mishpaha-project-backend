package org.mishpaha.project.test;

import org.mishpaha.project.config.MvcConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by fertrist on 25.09.15.
 */
public class MyClass {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MvcConfiguration.class);
    }

}
