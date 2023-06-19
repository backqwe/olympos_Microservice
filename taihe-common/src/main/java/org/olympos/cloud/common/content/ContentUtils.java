package org.olympos.cloud.common.content;

import com.alibaba.nacos.common.utils.StringUtils;
import org.olympos.cloud.common.configuration.BaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ContentUtils
 *
 * @Describe:
 * @Author Chasen Hao
 * @Date 2023-04-14 13:45
 */
@Component
public class ContentUtils {

    @Value("${spring.application.name}")
    private String applicationName;
    @Autowired
    private BaseConfig baseConfig;

    public String getApiConfigName() {
        return null == applicationName ?
                "ApiConfig.properties"
                : applicationName + "-ApiConfig.properties";
    }

    public boolean isBusinessModule() {
        return null == baseConfig.getModule()
                || StringUtils.isEmpty( baseConfig.getModule().getType())
                || StringUtils.equals( baseConfig.getModule().getType(), "client")
                || !StringUtils.equals( baseConfig.getModule().getType(), "gateway");
    }

    public boolean isGateway() {
        return null != baseConfig.getModule()
                && StringUtils.equals( baseConfig.getModule().getType(), "gateway");
    }

    public long getTimeout() {
        return baseConfig.getTimeout();
    }

    /**
     * 字符串内容转换列表(监听列表使用)
     * todo 需要提出到 tools
     * @param s
     * @return
     */
    public List<String> string2ListForListenList(String s) {

        if ( StringUtils.isBlank( s))
            return null;

        // todo 0010 此处长度截取可能会出现bug，还有长度比较可能会出bug，需要重新检查
        int var = ApiEnhanceConstants.API_CONFIG_LISTEN_LIST_HEAD.length();
        if ( s.length() <= var)
            return new ArrayList<>();

        return  new ArrayList<>(
                Arrays.asList(
                        s.substring( var).split(",")));
    }
}
