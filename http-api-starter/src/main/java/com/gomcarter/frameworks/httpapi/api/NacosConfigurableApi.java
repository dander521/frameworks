package com.gomcarter.frameworks.httpapi.api;


import com.gomcarter.frameworks.base.common.NacosClientUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author gomcarter 2018年2月28日 18:31:53
 */
public abstract class NacosConfigurableApi extends BaseApi {

    private Map<String, Properties> propertiesMap = new HashMap<>();

    public abstract NacosConfig getConfig();

    @Override
    protected Map<String, String> getUrlRouter() {
        Map<String, String> urlRouterMap = new HashMap<>();
        try {
            Properties properties = this.getProperties();
            for (Object key : properties.keySet()) {
                urlRouterMap.put((String) key, properties.getProperty((String) key));
            }
        } catch (IOException e) {
            logger.error("初始化接口地址失败：{}", this.getConfig(), e);
        }

        return urlRouterMap;
    }

    synchronized private Properties getProperties() throws IOException {
        NacosConfig config = getConfig();
        String dataId = config.getDataId(),
                group = config.getGroup(),
                key = dataId + "_" + group;
        Properties properties = propertiesMap.get(key);
        if (properties != null) {
            return properties;
        }

        properties = NacosClientUtils.getConfigAsProperties(dataId, group);

        propertiesMap.put(key, properties);
        return properties;
    }
}
