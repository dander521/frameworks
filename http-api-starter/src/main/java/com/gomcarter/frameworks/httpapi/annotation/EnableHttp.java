package com.gomcarter.frameworks.httpapi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gomcarter
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableHttp {
    /**
     * nacos data_id
     *
     * @return dataId
     */
    String dataId();

    /**
     * nacos group
     *
     * @return group
     */
    String group();
}
