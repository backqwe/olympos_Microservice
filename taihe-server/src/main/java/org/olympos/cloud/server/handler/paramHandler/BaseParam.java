package org.olympos.cloud.server.handler.paramHandler;

import org.apache.shenyu.common.constant.Constants;
import org.apache.shenyu.common.dto.MetaData;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * BaseParam
 *
 * @Describe:
 * @Author Chasen Hao
 * @Date 2023-06-18 14:53
 */
public class BaseParam {

    private ServerWebExchange exchange;

    private Map<String, Object> requestParams;

    private List<String> paramTypes;

    protected ServerWebExchange getExchange() {
        return exchange;
    }

    protected void setExchange(ServerWebExchange exchange) {
        this.exchange = exchange;
    }

    protected List<String> getParamTypes() {
        return paramTypes;
    }

    protected void setParamTypes(List<String> paramTypes) {
        this.paramTypes = paramTypes;
    }

    protected Map<String, Object> getRequestParams() {
        return requestParams;
    }

    protected void setRequestParams(Map<String, Object> requestParams) {
        this.requestParams = requestParams;
    }

    protected String getParameterTypes() {
        MetaData metaData = this.exchange.getAttribute( Constants.META_DATA);
        return Objects.isNull( metaData) || Objects.isNull( metaData.getParameterTypes()) ?
                "" : metaData.getParameterTypes();
    }
}
