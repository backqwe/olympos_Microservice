package org.olympos.cloud.server.configuration;

import lombok.extern.slf4j.Slf4j;
import org.olympos.cloud.server.handler.enhanceHandler.ApiFaceEnhanceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ApiConfHandle
 *
 * @Describe:
 * @Author Chasen Hao
 * @Date 2023-04-14 13:45
 */
@Slf4j
@Component
public class ApiConfHandle {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 字符串内容转换列表(接口配置使用)
     * @param s
     * @return
     */
    public Map<String,String> string2ListForApiConf(String s) throws IOException {

        // todo 0011 日后需要优化，尽量不使用 Properties
        Properties properties = new Properties();
        properties.load( new StringReader( s));
        return new Hashtable( properties);
    }

    /**
     * 生成增强配置集合
     * todo 0012 接口增强执行（监听器部分 + 启动初始化部分1）
     * @param s
     * @return
     */
    public Map<String, Set<String>> createApiEnhanceMap(String s) throws IOException {
        return this.string2ListForApiConf( s).entrySet()
                .stream()
                .collect( Collectors.toMap(
                        e -> e.getKey(),
                        e -> new HashSet<>( Arrays.asList(
                                e.getValue().split(",")))));
    }

    /**
     * 生成增强处理列表
     * todo 0013 接口增强执行（启动初始化部分2）
     * @return
     */
    public List<ApiFaceEnhanceHandler> makeApiEnhanceHandlerList() {

        // todo 0014 需要测试下是否正常 从springBean容器中获取实现ApiFaceEnhanceHandler的所有类
        Map<String, ApiFaceEnhanceHandler> m = applicationContext.getBeansOfType( ApiFaceEnhanceHandler.class);

        // 排序放入list中
        return m.entrySet().stream()
                .map( Map.Entry::getValue)
                .sorted( Comparator.comparing( ApiFaceEnhanceHandler::getOrder))
                .collect( Collectors.toList());
    }
}
