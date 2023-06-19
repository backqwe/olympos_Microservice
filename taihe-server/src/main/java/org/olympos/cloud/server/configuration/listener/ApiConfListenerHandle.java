package org.olympos.cloud.server.configuration.listener;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.olympos.cloud.common.content.ApiEnhanceConstants;
import org.olympos.cloud.common.content.ContentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * ApiFaceConfListenerHandle
 *
 * @Describe:
 * @Author Chasen Hao
 * @Date 2023-04-14 13:45
 */
@Slf4j
@Component
public class ApiConfListenerHandle {

    @Autowired
    private NacosConfigManager nacosConfigManager;
    @Autowired
    private ContentUtils contentUtils;
    @Autowired
    private ApiConfigListenListListener apiConfigListenListListener;

    public void apiConfigListenListHandle(ApiConfListenList apiConfListenList) throws NacosException {
        // todo 0007 部分方法提出一个共通文件中，新建core，放入其中
        // todo 0008 2 优化整理，初始化不进行配置更新等处理，全部放到监听器中去做

        // 获取最新监听列表
        String listenListContentOnline = nacosConfigManager.getConfigService().getConfig(
                ApiEnhanceConstants.API_CONFIG_LISTEN_LIST,
                ApiEnhanceConstants.NACOS_GROUP_NAME,
                500);

        // 初始化本地监听列表
        nacosConfigManager.getConfigService().addListener(
                ApiEnhanceConstants.API_CONFIG_LISTEN_LIST,
                ApiEnhanceConstants.NACOS_GROUP_NAME,
                // todo
                apiConfigListenListListener);

        if ( null != listenListContentOnline) {
            // 发布配置 & 本地缓存监听列表
            apiConfListenList.getListenList().addAll(
                    contentUtils.string2ListForListenList( listenListContentOnline));
            return;
        }

        // 初始化线上监听列表
        nacosConfigManager.getConfigService().publishConfig(
                ApiEnhanceConstants.API_CONFIG_LISTEN_LIST,
                ApiEnhanceConstants.NACOS_GROUP_NAME,
                ApiEnhanceConstants.API_CONFIG_LISTEN_LIST_HEAD);
    }

    /**
     * 获取线上监听列表
     * @return
     * @throws NacosException
     * @throws IOException
     */
    public List<String> getListenListOnline() throws NacosException {

        String listenListConf = nacosConfigManager.getConfigService().getConfig(
                ApiEnhanceConstants.API_CONFIG_LISTEN_LIST,
                ApiEnhanceConstants.NACOS_GROUP_NAME,
                contentUtils.getTimeout());

        if ( StringUtils.isEmpty( listenListConf))
            return null;

        return contentUtils.string2ListForListenList( listenListConf);
    }
}
