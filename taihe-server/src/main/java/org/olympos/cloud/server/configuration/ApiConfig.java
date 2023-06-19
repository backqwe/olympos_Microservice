package org.olympos.cloud.server.configuration;

import lombok.Data;
import org.olympos.cloud.server.handler.enhanceHandler.ApiFaceEnhanceHandler;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ApiConfig
 *
 * @Describe:
 * @Author Chasen Hao
 * @Date 2023-04-14 13:45
 */
@Component
@Data
public class ApiConfig implements Cloneable {

    // 接口增强配置容器
    private ConcurrentHashMap<String, Set<String>> apiEnhanceConfigs = new ConcurrentHashMap<>();
    // 接口增强处理容器
    private CopyOnWriteArrayList<ApiFaceEnhanceHandler> apiEnhanceHandles = new CopyOnWriteArrayList<>();

    public ApiConfig clone() throws CloneNotSupportedException {
        return (ApiConfig)super.clone();
    }
}
