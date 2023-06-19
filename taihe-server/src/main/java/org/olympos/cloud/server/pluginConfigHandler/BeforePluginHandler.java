package org.olympos.cloud.server.pluginConfigHandler;

import org.apache.shenyu.common.dto.PluginData;
import org.apache.shenyu.common.dto.RuleData;
import org.apache.shenyu.common.dto.SelectorData;
import org.apache.shenyu.plugin.base.handler.PluginDataHandler;
import org.springframework.stereotype.Component;

/**
 * BeforePluginHandler
 *
 * @Describe: 接口前置处理的插件处理
 * @Author Chasen Hao
 * @Date 2023-06-19 02:07
 */
@Component
public class BeforePluginHandler implements PluginDataHandler {

    public void handlerPlugin(PluginData pluginData) {
        System.out.println("######### PluginData of BeforePlugin update  #########");
    }

    public void removePlugin(PluginData pluginData) {
        System.out.println("######### SelectorData of BeforePlugin update in remove Plugin #########");
    }

    public void handlerSelector(SelectorData selectorData) {
        System.out.println("######### SelectorData of BeforePlugin update #########");
    }

    public void removeSelector(SelectorData selectorData) {
        System.out.println("######### SelectorData of BeforePlugin update in remove Plugin #########");
    }

    public void handlerRule(RuleData ruleData) {
        System.out.println("######### RuleData of BeforePlugin update #########");
    }

    public void removeRule(RuleData ruleData) {
        System.out.println("######### RuleData of BeforePlugin update in remove Plugin #########");
    }

    public String pluginNamed() {
        return "BeforePlugin";
    }
}
