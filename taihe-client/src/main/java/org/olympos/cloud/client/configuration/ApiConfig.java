package org.olympos.cloud.client.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * ApiConfig
 *
 * @Describe:
 * @Author Chasen Hao
 * @Date 2023-04-14 13:45
 */
@PropertySource(value = {"classpath:ApiConfig.yml", "classpath:ApiConfig.yaml", "classpath:ApiConfig.properties"},
        ignoreResourceNotFound = true,
        encoding = "utf-8",
        factory = ConfigurationFactory.class)
@ConfigurationProperties(prefix = "api",
        ignoreInvalidFields = true)
@Component
@Data
public class ApiConfig implements Cloneable {

    private HashMap<String,String> enhances;
}
