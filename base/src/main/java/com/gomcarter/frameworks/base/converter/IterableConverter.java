package com.gomcarter.frameworks.base.converter;

import com.gomcarter.frameworks.base.mapper.JsonMapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 对象类型转换器
 *
 * @author gomcarter on 2019-11-16 13:58:11
 */
public class IterableConverter implements Convertable {

    /**
     * @param sourceValue sourceValue
     * @param type        type
     * @param <T>         T
     * @return Iterable result
     */
    @Override
    public <T> T convert(Object sourceValue, Type type) {
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Class kls = (Class) parameterizedType.getActualTypeArguments()[0];
        Class collectionClass = (Class) parameterizedType.getRawType();

        return (T) JsonMapper.buildNonNullMapper().fromJsonToCollection(sourceValue + "", collectionClass, kls);
    }
}
