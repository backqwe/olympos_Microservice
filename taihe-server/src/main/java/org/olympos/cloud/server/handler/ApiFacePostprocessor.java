package org.olympos.cloud.server.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.shenyu.common.dto.RuleData;
import org.apache.shenyu.common.dto.SelectorData;
import org.apache.shenyu.plugin.api.ShenyuPluginChain;
import org.apache.shenyu.plugin.base.AbstractShenyuPlugin;
import org.olympos.cloud.server.configuration.ApiConfig;
import org.olympos.cloud.server.handler.enhanceHandler.ApiFaceEnhanceHandler;
import org.olympos.cloud.server.handler.paramHandler.PostProcessorParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * ApiFacePostprocessor
 *
 * @Describe: 接口后置处理
 * @Author Chasen Hao
 * @Date 2023-06-18 13:45
 */
@Slf4j
@Component
public class ApiFacePostprocessor extends AbstractShenyuPlugin {

    @Autowired
    private ApiConfig apiConfig;

    @Override
    public Mono<Void> doExecute(ServerWebExchange exchange, ShenyuPluginChain chain, SelectorData selector, RuleData rule) {

        String apiFaceName = getApiFaceName( exchange);

        // 检索增强配置列表，判断是否需要继续进行，无需进行则退出
        if ( !apiConfig.getApiEnhanceConfigs().containsKey( apiFaceName))
            return chain.execute( exchange);

        // 参数
        PostProcessorParam param = PostProcessorParam.getInstance( exchange);

        // 调用处理循环
        for (ApiFaceEnhanceHandler enhanceHandler : apiConfig.getApiEnhanceHandles())
            if ( !enhanceHandler.afterSkip()
                    && apiConfig.getApiEnhanceConfigs().get( apiFaceName).contains( enhanceHandler.getClass().getSimpleName()))
                enhanceHandler.afterHandle( param);

        exchange.getAttributes().put("AfterPlugin", "success");
        return chain.execute( exchange);
    }

    /**
     * todo 0018 日后提出 common方法
     * @param exchange
     * @return
     */
    private String getApiFaceName(ServerWebExchange exchange) {

        // todo 0019 获取前端请求接口名
        String path = exchange.getRequest().getPath().pathWithinApplication().value();

        if ( !path.contains("/"))
            return path;

        String[] var = path.split("/");

        if ( var.length <= 1)
            return var[0];

        return var[var.length-1];
    }

    @Override
    public int getOrder() {
        return 350;
    }

    @Override
    public String named() {
        return "AfterPlugin";
    }

    @Override
    public boolean skip(ServerWebExchange exchange) {
        // todo 0020 日后增加跳过条件，当接口配置列表（后置增强列表）中没有此接口，则跳过
        return false;
    }
}
