package com.tbd.common.config;

import com.tbd.common.exceptions.handler.GlobalExceptionHandler;
import com.tbd.common.utils.Translator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonAutoConfiguration {

    @Bean
    public Translator translator(MessageSource messageSource) {
        return new Translator(messageSource);
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler(Translator translator) {
        return new GlobalExceptionHandler(translator);
    }
}
