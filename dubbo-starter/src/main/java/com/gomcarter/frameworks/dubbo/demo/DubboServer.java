package com.gomcarter.frameworks.dubbo.demo;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

/**
 * 本 demo 基于 nacos 作为配置中心和注册中心
 *
 * @author gomcarter
 */
public class DubboServer {
    public static void main(String[] args) throws InterruptedException {
        // System.setProperty("dubbo.application.logger", "slf4j");

        // 服务实现
        DemoApi demoApi = new DemoApiImpl();

        // RegistryConfig rc = new RegistryConfig("zookeeper://172.18.19.251:2181?backup=172.18.20.6:2181,172.18.19.252:2181");
        // RegistryConfig rc = new RegistryConfig("redis://119.23.240.12:7480");
        String namespace = System.getenv("NACOS_NAMESPACE");
        RegistryConfig rc = new RegistryConfig("nacos://119.23.240.12:10009");
        rc.setParameters(new HashMap<String, String>() {{
            put("namespace", namespace);
        }});
        // rc.setPassword("3zda3caeyx6pn7c5z");
        // rc.setUsername("anywhocares");

        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("dubbo-provider--");


        // 服务提供者暴露服务配置
        ServiceConfig<DemoApi> service = new ServiceConfig<>();
        service.setApplication(application);
        service.setRegistry(rc);
        service.setInterface(DemoApi.class);
        service.setRef(demoApi);

        // 暴露及注册服务
        service.export();

        new CountDownLatch(1).await();
    }
}
