package com.gomcarter.frameworks.redis;

import com.gomcarter.frameworks.base.common.AssertUtils;
import com.gomcarter.frameworks.base.common.BeanRegistrationUtils;
import com.gomcarter.frameworks.redis.annotation.EnableNacosRedis;
import com.gomcarter.frameworks.redis.factory.NacosRedisFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;

import java.util.HashMap;

/**
 * @author  gomcarter on 2019-11-09 23:31:48
 */
@Order
public class NacosRedisRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata,
                                        BeanDefinitionRegistry registry) {

        AnnotationAttributes attributes = AnnotationAttributes
                .fromMap(annotationMetadata.getAnnotationAttributes(EnableNacosRedis.class.getName()));

        AssertUtils.notNull(attributes, new RuntimeException("未配置：EnableNacosRedis"));

        String dataId = attributes.getString("dataId");
        AssertUtils.isTrue(StringUtils.isNotBlank(dataId), new RuntimeException("未配置：dataId"));

        // 注入redis
        BeanRegistrationUtils.registerBeanDefinitionIfNotExists(registry, "redisProxy",
                NacosRedisFactory.class, new HashMap<String, Object>(2, 1) {{
                    put("dataId", dataId);
                    put("group", attributes.getString("group"));
                }});

        // 注入切面
        BeanRegistrationUtils.registerBeanDefinitionIfNotExists(registry, "redisConfiguration", RedisConfiguration.class);
    }


}
