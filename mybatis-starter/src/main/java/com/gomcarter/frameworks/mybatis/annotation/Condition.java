package com.gomcarter.frameworks.mybatis.annotation;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;

/**
 * @author gomcarter on 2019-11-09 22:53:32
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Documented
public @interface Condition {

    /**
     * @return 对应数据库的字段，不写默认为该字段名
     */
    String field() default "";

    /**
     * @return 匹配方式，默认的 equal
     */
    MatchType type() default MatchType.EQ;

    /**
     * @return 匹配策略
     */
    MatchStrategy strategy() default MatchStrategy.NOT_NULL;

    /**
     * 可以是 json 数据。
     * 也可以是基本数据。
     * <p>
     * 设置了此属性strategy将失效。将直接加入语句：field = ${fixedValue}
     *
     * @return 此属性强制匹配时使用此值
     */
    String fixedValue() default StringUtils.EMPTY;
}
