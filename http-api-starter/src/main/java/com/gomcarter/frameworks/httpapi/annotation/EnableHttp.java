package com.gomcarter.frameworks.httpapi.annotation;

import com.gomcarter.frameworks.httpapi.HttpApiRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 暂时不支持file和body一起传送
 *
 * @author gomcarter
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(HttpApiRegistrar.class)
public @interface EnableHttp {
}
