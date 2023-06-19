package org.olympos.cloud.client;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.common.utils.StringUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.olympos.cloud.client.configuration.ApiConfig;
import org.olympos.cloud.common.content.ApiEnhanceConstants;
import org.olympos.cloud.common.content.ContentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * BusinessInitialization
 *
 * @Describe:
 * @Author Chasen Hao
 * @Date 2023-04-14 13:45
 */
@Slf4j
@Component
public class ClientInitialization {

    @Autowired
    private ApiConfig apiConfig;
    @Autowired
    private NacosConfigManager nacosConfigManager;
    @Autowired
    private ContentUtils contentUtils;

    @PostConstruct
    public void init() {
        try {
            this.publishApiConfig();
            this.publishListenList();
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    private void publishApiConfig() throws NacosException {
        StringBuilder var = new StringBuilder();

        if ( null != apiConfig.getEnhances())
            var.append( apiConfig.getEnhances()
                    .entrySet()
                    .stream()
                    .map( e -> e.getKey() + "=" + e.getValue() + "\n\r")
                    .collect( Collectors.joining()));
        // 默认拼写 ApiInfo=ApiInfo 是为了让 nacos 在内容为空时也发布配置文件
        else
            var.append("ApiInfo=ApiInfo");

        // 客户端初始化内容（业务模块）
        boolean result = nacosConfigManager.getConfigService()
                .publishConfig(
                        contentUtils.getApiConfigName(),
                        ApiEnhanceConstants.NACOS_GROUP_NAME,
                        var.toString());

        // todo 0001 【日志】日志部分可以总体重构
        log.info( "【ApiConfig】 publish result：" + result);
    }

    private void publishListenList() throws NacosException {

        String listenListContentOnline = nacosConfigManager.getConfigService().getConfig(
                ApiEnhanceConstants.API_CONFIG_LISTEN_LIST,
                ApiEnhanceConstants.NACOS_GROUP_NAME,
                500);

        StringBuilder listenListContent = new StringBuilder();
        // 编辑配置内容，无需处理时返回
        if ( !this.listenListContentEdit( listenListContentOnline, listenListContent))
            return;
        // 发布监听列表
        boolean result = nacosConfigManager.getConfigService().publishConfig(
                ApiEnhanceConstants.API_CONFIG_LISTEN_LIST,
                ApiEnhanceConstants.NACOS_GROUP_NAME,
                listenListContent.toString());
        log.info( "【ListenList】 publish result：" + result);
    }

    /**
     * 内容处理
     * @return 是否需要上传更新listenList：true-是 false-否
     */
    private boolean listenListContentEdit(String listenListContentOnline, StringBuilder listenListContent) {

        // 业务模块处理
        // 线上无配置，或者空配置
        if ( StringUtils.isBlank( listenListContentOnline))
            // 添加自己的配置名
            listenListContent.append( ApiEnhanceConstants.API_CONFIG_LISTEN_LIST_HEAD)
                    .append( contentUtils.getApiConfigName());
        // 线上有配置
        else
            // 包含自己的配置，则不需处理
            if ( listenListContentOnline.contains( contentUtils.getApiConfigName()))
                return false;
            // 不包含自身配置，则在末尾添加自己的配置
            else
                this.addSelfListenInfo( listenListContent, listenListContentOnline);

        return true;
    }

    private void addSelfListenInfo(StringBuilder listenListContent, String listenListContentOnline) {

        listenListContent.append( listenListContentOnline)
                .append( listenListContentOnline.equals( ApiEnhanceConstants.API_CONFIG_LISTEN_LIST_HEAD) ?
                                "" : ",")
                .append( contentUtils.getApiConfigName());
    }
}
