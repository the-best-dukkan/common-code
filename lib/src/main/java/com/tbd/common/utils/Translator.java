package com.tbd.common.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Translator {

    private final MessageSource messageSource;

    public String translate(String messageKey) {
        return messageSource.getMessage(
                messageKey,
                null,
                LocaleContextHolder.getLocale()
        );
    }

    public String translate(String messageKey, Object... args) {
        return messageSource.getMessage(
                messageKey,
                args,
                LocaleContextHolder.getLocale()
        );
    }

    public static String translate(MessageSource messageSource, String messageKey, Object... args) {
        return messageSource.getMessage(
                messageKey,
                args,
                LocaleContextHolder.getLocale()
        );
    }
}
