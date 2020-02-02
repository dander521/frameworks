package com.gomcarter.frameworks.httpapi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gomcarter
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckToken {

    /**
     * @return 密钥
     */
    String key();

    /**
     * @return token name
     */
    String tokenName() default "backToken";

}
