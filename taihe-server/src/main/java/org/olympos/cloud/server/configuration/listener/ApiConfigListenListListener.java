package org.olympos.cloud.server.configuration.listener;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.common.utils.StringUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.olympos.cloud.common.content.ApiEnhanceConstants;
import org.olympos.cloud.common.content.ContentUtils;
import org.olympos.cloud.server.configuration.ApiConfHandle;
import org.olympos.cloud.server.configuration.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.Executor;

/**
 * ApiConfigListenListListener
 *
 * @Describe:
 * @Author Chasen Hao
 * @Date 2023-04-14 13:45
 */
@Slf4j
@Component
public class ApiConfigListenListListener implements Listener {

    @Autowired
    private ApiConfListenList apiConfListenList;
    @Autowired
    private ApiConfHandle apiConfHandle;
    @Autowired
    private ApiConfig apiConfig;
    @Autowired
    private NacosConfigManager nacosConfigManager;
    @Autowired
    private ApiConfListener apiConfListener;
    @Autowired
    private ContentUtils contentUtils;

    @SneakyThrows
    @Override
    public void receiveConfigInfo(String configInfo) {
        // todo 0004 此处需要检查，很多情况下有bug

        // 获取本地监听列表缓存
        Set<String> listenListLocal = apiConfListenList.getListenList();

        // 获取线上监听列表
        List<String> listenListOnline = contentUtils.string2ListForListenList( configInfo);
        if ( null == listenListOnline)
            listenListOnline = new ArrayList<>();

        Set<String> var = new HashSet<>( listenListLocal);

        for ( String listenOnline : listenListOnline)
            // 遇到本地监听列表不存在的监听器名，则添加此监听器，并且把内容一并存入接口配置列表
            if ( !listenListLocal.contains( listenOnline)) {
                String var2 = this.addApiConfListener( listenOnline);
                Map<String, Set<String>> var3;
                if ( StringUtils.isNotBlank( var2)
                        && null != (var3 = apiConfHandle.createApiEnhanceMap( var2)))
                    apiConfig.getApiEnhanceConfigs().putAll( var3);
            } else
                var.remove( listenOnline);

        // 移除线上监听列表中不存在的监听器
        for ( String s : var)
            this.removeApiConfListener( s);

        // 更新本地缓存的监听列表
        apiConfListenList.getListenList().addAll( listenListOnline);
    }

    /**
     * 添加接口信息的监听器
     * @param apiConfigName
     * @throws NacosException
     */
    public String addApiConfListener(String apiConfigName) throws NacosException {

        nacosConfigManager.getConfigService().addListener(
                apiConfigName,
                ApiEnhanceConstants.NACOS_GROUP_NAME,
                apiConfListener);

        // 激活监听器
        String var = nacosConfigManager.getConfigService().getConfig(
                apiConfigName,
                ApiEnhanceConstants.NACOS_GROUP_NAME,
                contentUtils.getTimeout());
        log.debug( "Listener Loaded! api config is:" + StringUtils.defaultIfEmpty( var, ""));

        return var;
    }

    /**
     * 移除接口信息的监听器
     * @param apiConfigName
     * @throws NacosException
     */
    // todo 0009 0614：监听器优化
    public void removeApiConfListener(String apiConfigName) {

        nacosConfigManager.getConfigService().removeListener(
                apiConfigName,
                ApiEnhanceConstants.NACOS_GROUP_NAME,
                apiConfListener);
        log.debug("Listener Remove! listener name: " + apiConfigName);
    }

    @Override
    public Executor getExecutor() {
        return null;
    }
}
