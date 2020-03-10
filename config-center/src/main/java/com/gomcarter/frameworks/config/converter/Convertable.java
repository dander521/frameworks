package com.gomcarter.frameworks.config.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Properties;

/**
 * 类型转换
 *
 * @author gomcarter on 2019-11-16 13:58:11
 */
public interface Convertable {
    Logger logger = LoggerFactory.getLogger(Convertable.class);

    /**
     * convert sourceValue to target T, T should be one of Type
     *
     * @param sourceValue sourceValue
     * @param type        type
     * @param <T>         T convert to
     * @return result
     */
    <T> T convert(Object sourceValue, Type type);


    Convertable PRIMITIVE_CONVERTER = new PrimitiveConverter();
    Convertable ITERABLE_CONVERTER = new IterableConverter();
    Convertable PROPERTIES_CONVERTER = new PropertiesConverter();
    Convertable OBJECT_CONVERTER = new ObjectConverter();

    /**
     * get kls's default converter
     *
     * @param kls kls
     * @return Convertable
     */
    static Convertable getConverter(Class kls) {
        if (BeanUtils.isSimpleProperty(kls) || kls == Object.class) {
            return PRIMITIVE_CONVERTER;
        } else if (kls.isArray() || Iterable.class.isAssignableFrom(kls)) {
            return ITERABLE_CONVERTER;
        } else if (Properties.class.isAssignableFrom(kls) || File.class.isAssignableFrom(kls)) {
            return PROPERTIES_CONVERTER;
        }
        return OBJECT_CONVERTER;
    }
}
