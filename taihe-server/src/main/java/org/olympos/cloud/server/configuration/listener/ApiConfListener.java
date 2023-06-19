package org.olympos.cloud.server.configuration.listener;

import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.common.utils.StringUtils;
import org.olympos.cloud.server.configuration.ApiConfHandle;
import org.olympos.cloud.server.configuration.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * ApiConfListener
 *
 * @Describe:
 * @Author Chasen Hao
 * @Date 2023-04-14 13:45
 */
@Component
public class ApiConfListener implements Listener {

    @Autowired
    private ApiConfHandle apiConfHandle;
    @Autowired
    private ApiConfig apiConfig;

    @Override
    public void receiveConfigInfo(String configInfo) {
        // todo 0005 【异常】异常处理需要添加
        try {
            // 删除旧配置
            apiConfig.getApiEnhanceConfigs().clear();
            // 添加接口增强配置（todo 0006 接口增强执行（监听器） 目前是覆盖型，当出现相同配置条目，就会覆盖掉旧的配置，日后可以优化，增加配置项）
            Map<String, Set<String>> var3;
            if ( StringUtils.isNotBlank( configInfo)
                    && null != (var3 = apiConfHandle.createApiEnhanceMap( configInfo)))
                apiConfig.getApiEnhanceConfigs().putAll( var3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Executor getExecutor() {
        return null;
    }
}
