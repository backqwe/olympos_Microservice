package org.olympos.cloud.server;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.common.utils.StringUtils;
import jakarta.annotation.PostConstruct;
import org.olympos.cloud.server.configuration.ApiConfHandle;
import org.olympos.cloud.server.configuration.ApiConfig;
import org.olympos.cloud.server.configuration.listener.ApiConfListenList;
import org.olympos.cloud.server.configuration.listener.ApiConfListenerHandle;
import org.olympos.cloud.server.configuration.listener.ApiConfigListenListListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ApiConfigUpdate
 *
 * @Describe:
 * for 业务模块
 *
 * @Author Chasen Hao
 * @Date 2021-05-22 12:07
 */
@Component
public class ApiFaceEnhanceInit {

    @Autowired
    private ApiConfigListenListListener apiConfigListenListListener;
    @Autowired
    private ApiConfListenerHandle apiConfListenerHandle;
    @Autowired
    private ApiConfListenList apiConfListenList;
    @Autowired
    private ApiConfHandle apiConfHandle;
    @Autowired
    private ApiConfig apiConfig;

    /**
     * 接口配置刷新（启动时）
     * @return
     * @throws NacosException
     */
    @PostConstruct
    public void ApiFaceEnhanceInitHandle() throws NacosException, IOException {

        apiConfListenerHandle.apiConfigListenListHandle( apiConfListenList);

        List<String> listenList;
        // 监听列表为空则退出
        if ( null == (listenList = apiConfListenerHandle.getListenListOnline()))
            return;

        // 循环添加监听并添加接口配置内容到本地配置Map
        for ( String apiConfigName : listenList) {
            // 添加监听器
            String configInfo = apiConfigListenListListener.addApiConfListener( apiConfigName);

            // 添加接口增强配置（todo 0028 接口增强执行（启动初始化部分1） 目前是覆盖型，当出现相同配置条目，就会覆盖掉旧的配置，日后可以优化，增加配置项）
            Map<String, Set<String>> var3;
            if ( StringUtils.isNotBlank( configInfo)
                    && null != (var3 = apiConfHandle.createApiEnhanceMap( configInfo)))
                apiConfig.getApiEnhanceConfigs().putAll( var3);
        }

        // todo 0029 接口增强执行（启动初始化部分2）
        apiConfig.getApiEnhanceHandles().addAll( apiConfHandle.makeApiEnhanceHandlerList());
    }
}
