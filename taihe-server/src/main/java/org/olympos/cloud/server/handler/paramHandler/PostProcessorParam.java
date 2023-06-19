package org.olympos.cloud.server.handler.paramHandler;

import org.apache.shenyu.common.constant.Constants;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.web.server.ServerWebExchange;

import java.util.*;

/**
 * ProcessorParam
 *
 * @Describe:
 * @Author Chasen Hao
 * @Date 2023-06-18 13:45
 */
public class PostProcessorParam extends BaseParam {

    public static PostProcessorParam getInstance(ServerWebExchange exchange) {
        return new PostProcessorParam( exchange);
    }

    private PostProcessorParam(ServerWebExchange exchange) {
        super.setExchange( exchange);

        Object params = exchange.getAttributes().get( Constants.PARAM_TRANSFORM);
        super.setRequestParams( Objects.nonNull( params) ? new JacksonJsonParser().parseMap( params.toString()) : new TreeMap<>());
        super.setParamTypes( new ArrayList<>( Arrays.asList( super.getParameterTypes().split(","))));
    }

    public Map<String, String> getResults() {
        return super.getExchange().getAttribute( Constants.RPC_RESULT);
    }

    public String getResult(String k) {
        Map<String, String> result = super.getExchange().getAttribute( Constants.RPC_RESULT);
        return Objects.nonNull( result) ? result.get( k) : "";
    }

    public void setResult(String resultName, String resultValue) {
        Map<String, String> result = super.getExchange().getAttribute( Constants.RPC_RESULT);
        if ( Objects.nonNull( result))
            result.put( resultName, resultValue);
    }

    public String getResultType() {
        return super.getExchange().getAttribute( Constants.CLIENT_RESPONSE_RESULT_TYPE);
    }

    public void setResultType(String resultType) {
        super.getExchange().getAttributes().put( Constants.CLIENT_RESPONSE_RESULT_TYPE, resultType);
    }

    public Map<String, Object> getParams() {
        Object params = super.getExchange().getAttributes().get( Constants.PARAM_TRANSFORM);
        return Objects.nonNull( params) ? new JacksonJsonParser().parseMap( params.toString()) : new TreeMap<>();
    }

    public List<String> getParamTypes() {
        return super.getParamTypes();
    }
}
