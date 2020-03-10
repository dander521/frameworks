package com.gomcarter.frameworks.config.converter;

import com.gomcarter.frameworks.config.mapper.JsonMapper;

import java.lang.reflect.Type;

/**
 * 对象类型转换器
 *
 * @author gomcarter on 2019-11-16 13:58:11
 */
public class ObjectConverter implements Convertable {

    /**
     * @param sourceValue sourceValue
     * @param type        type
     * @param <T>         T
     * @return Object result
     */
    @Override
    public <T> T convert(Object sourceValue, Type type) {
        return JsonMapper.buildNonNullMapper().fromJson(sourceValue + "", (Class<T>) type);
    }
}
