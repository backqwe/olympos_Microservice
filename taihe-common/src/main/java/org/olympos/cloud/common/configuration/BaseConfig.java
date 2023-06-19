package org.olympos.cloud.common.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * BaseConfig
 *
 * @Describe:
 * @Author Chasen Hao
 * @Date 2023-04-14 13:45
 */
@ConfigurationProperties(prefix = "taihe")
@Component
@Data
public class BaseConfig {

    // 是否启用框架功能
    private boolean enable;

    // 模块配置
    private ModuleBaseConfig module;

    // 延迟时间(ms)
    private long timeout = 500;
}
