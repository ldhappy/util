package com.ld.util.transition.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;


/**
 * @ClassName PayAutoConfiguration
 * @Description todo
 * @Author 梁聃
 * @Date 2021/8/12 10:34
 */
@Configuration
public class TransitionAutoConfiguration{

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("com.ld.util.transition.messages");
        resourceBundleMessageSource.setDefaultEncoding("utf-8");
        return resourceBundleMessageSource;
    }

}
